package com.learn.reptile.web.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.springframework.data.redis.core.ValueOperations;

import com.learn.reptile.config.shiro.WebConstants;
import com.learn.reptile.entity.po.User;

public class BaseController {

	 @Resource
	 ValueOperations<String, String> valueOperations;
	 
	 public User getCurrentLoginUser() {
		 User user = (User) SecurityUtils.getSubject().getSession().getAttribute(WebConstants.LOGIN_USER);
		 if(user==null) {
			 throw new AccountException();
		 }
		 return user;
	 }
}
