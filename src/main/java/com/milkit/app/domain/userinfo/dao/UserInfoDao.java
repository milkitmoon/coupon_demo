package com.milkit.app.domain.userinfo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkit.app.domain.userinfo.UserInfo;


@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
	
	Optional<UserInfo> findByUserID(String userID);
	
}
