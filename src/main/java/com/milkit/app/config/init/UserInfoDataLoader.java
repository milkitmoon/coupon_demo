package com.milkit.app.config.init;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.milkit.app.domain.userinfo.UserInfo;
import com.milkit.app.domain.userinfo.service.UserInfoServiceImpl;

@Component
public class UserInfoDataLoader implements ApplicationRunner {

	@Autowired
    private UserInfoServiceImpl userInfoService;

    public void run(ApplicationArguments args) {
		String password = "$2a$10$1rThoKu6Tt0osRcHVd98A.tiv0./T4tIUQwfRWN3bpkZFQFhf54tq";	//test

		UserInfo userInfo = new UserInfo("admin", password);
		userInfo.setUserNM("관리자");
		userInfo.setAuthRole("ROLE_ADMIN");
		userInfo.setUseYN("Y");
		userInfo.setInstTime(new Date());
		userInfo.setUpdTime(new Date());
		userInfo.setInstUser("admin");
		userInfo.setUpdUser("admin");
		
		UserInfo userInfo2 = new UserInfo("test", password);
		userInfo2.setUserNM("김복돌");
		userInfo2.setAuthRole("ROLE_MEMBER");
		userInfo2.setUseYN("Y");
		userInfo2.setInstTime(new Date());
		userInfo2.setUpdTime(new Date());
		userInfo2.setInstUser("admin");
		userInfo2.setUpdUser("admin");
		
		try {
			userInfoService.insert(userInfo);
			userInfoService.insert(userInfo2);
		} catch (Exception e) {
			e.printStackTrace();
		}

    }


}