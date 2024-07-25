package com.learn.reptile.config.shiro;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.reptile.entity.po.User;
import com.learn.reptile.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRealm extends AuthorizingRealm {
	
	@Resource
	UserMapper userMapper;
	
	@Resource
	ValueOperations<String, String> valueOperations;
	
	@Resource
	RedisTemplate redisTemplate;
	
	@Value("${shiro.session-timeout}")
	Long sessionTimeout;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String token = (String) principals.getPrimaryPrincipal();
		String userStr = valueOperations.get(token);
		if(StringUtils.isBlank(userStr)) {
			throw new AccountException("登录失效，请重新登录");
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String username = (String)authenticationToken.getPrincipal();
		String password = new String((char[]) authenticationToken.getCredentials());
		
		User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
		if(user==null || !user.getPassword().equals(password)) {
			throw new AuthenticationException("用户名或密码错误");
		}
		
		Session session = SecurityUtils.getSubject().getSession();
		String token = (String)session.getAttribute(WebConstants.LOGIN_USER_TOKEN);
		if(StringUtils.isBlank(token)) {
			token = System.currentTimeMillis() + "-" + user.getId();
			user.setToken(token);
			valueOperations.set(token, JSON.toJSONString(user), 1, TimeUnit.DAYS);
			
			session.setAttribute(WebConstants.LOGIN_USER_TOKEN, token);
			session.setAttribute(WebConstants.LOGIN_USER_ID, user.getId());
			session.setAttribute(WebConstants.LOGIN_USER_NAME, user.getUsername());
			session.setAttribute(WebConstants.LOGIN_USER, user);
			session.setTimeout(sessionTimeout);
		}
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
