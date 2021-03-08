package com.koreait.community.user;

import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.community.Const;
import com.koreait.community.FileUtils;
import com.koreait.community.SecurityUtils;
import com.koreait.community.model.UserEntity;

@Service
public class UserService {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private SecurityUtils sUtils;

	@Autowired
	private FileUtils fUtils;

	// 1:회원가입 성공, 0: 회원가입 실패
	public int join(UserEntity p) {
		if (p.getUserId() == null || p.getUserId().length() < 2) {
			return 0;
		}

		// 비밀번호 암호화
		String salt = sUtils.getSalt();
		String hashPw = sUtils.getHashPw(p.getUserPw(), salt);

		p.setSalt(salt);
		p.setUserPw(hashPw); // 암호화된 비밀번호

		return mapper.insUser(p);
	}

	// 1: 로그인 성공, 2: 아이디 없음, 3: 비밀번호 틀림
	public int login(UserEntity p, HttpSession hs) {
		UserEntity vo = mapper.selUser(p);
		if (vo == null) {
			return 2; // 아이디 없음
		}
		String salt = vo.getSalt();
		String hashPw = sUtils.getHashPw(p.getUserPw(), salt);
		if (!hashPw.equals(vo.getUserPw())) {
			return 3; // 비밀번호 틀림
		}

		vo.setUserPw(null);
		vo.setSalt(null);
		vo.setRegDt(null);
		vo.setProfileImg(null);
		hs.setAttribute(Const.KEY_LOGINUSER, vo);
		return 1; // 로그인 성공
	}

	// 아이디가 있으면 1, 없으면 0 리턴
	public int chkId(UserEntity p) {
		UserEntity vo = mapper.selUser(p);
		if (vo == null) {
			return 0;
		}
		return 1;
	}

	public UserEntity selUser(UserEntity p) {
		return mapper.selUser(p);
	}

	public int uploadProfile(MultipartFile mf, HttpSession hs) {
		int userPk = sUtils.getLoginUserPk(hs);
		if (userPk == 0 || mf == null) { // 로그인이 안 되어 있는 경우 , 파일이 없는 경우
			return 0;
		}

		String folder = "/res/img/user/" + userPk;
		String profileImg = fUtils.transferTo(mf, folder);
		if (profileImg == null) { // 파일 생성 실패
			return 0;
		}

		UserEntity p = new UserEntity();
		p.setUserPk(userPk);
		
		// 기존 이미지가 존재했다면 이미지 삭제
		UserEntity userInfo = mapper.selUser(p);
		if(userInfo.getProfileImg() != null) { // 기존이미지가 있음
			String basePath = fUtils.getBasePath(folder);
			File file = new File(basePath, userInfo.getProfileImg());
			if(file.exists()) {
				file.delete();
			}
		}
		
		p.setProfileImg(profileImg);
		return mapper.updUser(p);
	}
}