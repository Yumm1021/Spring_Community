package com.koreait.community;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.koreait.community.model.UserEntity;

@Component
public class SecurityUtils {

	public int getLoginUserPk(HttpSession hs) { // 로그인 유저
		UserEntity loginUser = getLoginUser(hs);
		return loginUser == null ? -1 : loginUser.getUserPk(); // 로그인 안되어 있으면 -1 넘김
	}

	public UserEntity getLoginUser(HttpSession hs) {
		return (UserEntity) hs.getAttribute(Const.KEY_LOGINUSER);
	}

	public String getSalt() {
		return BCrypt.gensalt();
	}

	public String getHashPw(String pw, String salt) {
		// BCrypt.checkpw(plaintext,hashed)
		return BCrypt.hashpw(pw, salt);
	}

}