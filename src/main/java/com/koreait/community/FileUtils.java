package com.koreait.community;

import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtils {

	@Autowired
	private ServletContext ctx; // servletContext = spring이라 생각하면 됨 이걸 주면 root 주소값을 줌

	public void makeFolders(String path) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	public String getBasePath(String... moreFolder) { // ... 가변인자
		String temp = "";
		for (String s : moreFolder) {
			temp += s;
		}

		String basePath = ctx.getRealPath(temp);
		return basePath;
	}

	// 확장자 리턴
	public String getExt(String fileNm) {
		return fileNm.substring(fileNm.lastIndexOf(".") + 1);
		// +1 해줘야 확장자만 return
	}

	// 랜덤파일명 리턴
	public String getRandomFileNm(String fileNm) {
		return UUID.randomUUID().toString() + "." + getExt(fileNm);
		// UUID > 엄청난 종류의 랜덤한 값이 나올 수 있는 것 , 중복된 값이 나올 확률 낮음
	}

	// 파일저장 & 랜덤파일명 구하기
	public String transferTo(MultipartFile mf, String... target) {
		String fileNm = null;
		String basePath = getBasePath(target);
		makeFolders(basePath);

		try {
			fileNm = getRandomFileNm(mf.getOriginalFilename());
			File file = new File(basePath, fileNm); // target 객체생성 > 파일이 저장되어야 할 위치정보를 transfer메서드에 주면 만들어줌
			mf.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileNm;
	}
}
