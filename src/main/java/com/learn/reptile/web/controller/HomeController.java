package com.learn.reptile.web.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.reptile.config.shiro.WebConstants;
import com.learn.reptile.entity.po.User;
import com.learn.reptile.entity.vo.R;
import com.learn.reptile.utils.MD5SecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class HomeController extends BaseController {
	
	@Resource
	RedisTemplate redisTemplate;

	@PostMapping("login")
	public R<User> login(String username, String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, MD5SecurityUtil.encryptMD5(password));
		subject.login(token);
		return R.ok(getCurrentLoginUser());
	}
	
	@PostMapping("logout")
	public R logout() {
		Subject subject = SecurityUtils.getSubject();
		String token = (String)subject.getSession().removeAttribute(WebConstants.LOGIN_USER_TOKEN);
		if(StringUtils.isNotBlank(token)) {
			redisTemplate.delete(token);
		}
		subject.getSession().removeAttribute(WebConstants.LOGIN_USER_ID);
		subject.getSession().removeAttribute(WebConstants.LOGIN_USER_NAME);
		subject.getSession().removeAttribute(WebConstants.LOGIN_USER);
		subject.logout();
		return R.ok();
	}
	
	@GetMapping("info")
	public R info() {
		return R.ok(getCurrentLoginUser());
	}
}
