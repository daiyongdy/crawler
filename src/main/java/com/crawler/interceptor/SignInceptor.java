package com.crawler.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInceptor implements HandlerInterceptor {

	private static final String TOKEN_NAME = "token";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//判断是否登录
//		String token = request.getParameter(TOKEN_NAME);
//		if (!StringUtils.isNotBlank(token)) {
//			throw BizException.NOT_LOGIN;
//		}

		//FDY 2018/8/11 下午3:55 调用api获取用户信息
//		WebUserDTO userDTO = new WebUserDTO();
//		WebUserHolder.setUser(userDTO);

		

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
