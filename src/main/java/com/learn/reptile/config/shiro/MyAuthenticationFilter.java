package com.learn.reptile.config.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.reptile.entity.po.User;
import com.learn.reptile.entity.vo.R;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyAuthenticationFilter extends FormAuthenticationFilter {
	
	private Long timeout;
	
	private ValueOperations<String, Object> valueOperations;
	
	public MyAuthenticationFilter(Long timeout, ValueOperations<String, Object> valueOperations) {
		this.timeout = timeout;
		this.valueOperations = valueOperations;
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(200);
		httpResponse.setContentType("application/json;charset=utf-8");
		
		Session session = SecurityUtils.getSubject().getSession();
		User user = (User)session.getAttribute(WebConstants.LOGIN_USER);
		String token = httpRequest.getHeader("token");
		if(user==null) {
			log.warn("没有获取到登录信息，当前登录值:{}", token);
			
			if(StringUtils.isNotBlank(token)) {
				String userStr = (String)valueOperations.get(token);
				if(StringUtils.isBlank(userStr)) {
					writeNeedLoginInfo(httpResponse);
					return false;
				}
				
				user = JSON.parseObject(userStr, User.class);
				session.setAttribute(WebConstants.LOGIN_USER_TOKEN, token);
				session.setAttribute(WebConstants.LOGIN_USER_ID, user.getId());
				session.setAttribute(WebConstants.LOGIN_USER_NAME, user.getUsername());
				session.setAttribute(WebConstants.LOGIN_USER, user);
				session.setTimeout(timeout);
				return true;
			}
		} else {
			session.setAttribute(WebConstants.LOGIN_USER_TOKEN, token);
			session.setAttribute(WebConstants.LOGIN_USER_ID, user.getId());
			session.setAttribute(WebConstants.LOGIN_USER_NAME, user.getUsername());
			session.setAttribute(WebConstants.LOGIN_USER, user);
			session.setTimeout(timeout);
			return true;
		}
		
		writeNeedLoginInfo(httpResponse);
		return false;
	}

	private void writeNeedLoginInfo(HttpServletResponse httpServletResponse) throws IOException {
        PrintWriter out = httpServletResponse.getWriter();
        out.println(JSON.toJSONString(R.failWithCode(7777, "请先登录")));
        out.flush();
        out.close();
    }
}
