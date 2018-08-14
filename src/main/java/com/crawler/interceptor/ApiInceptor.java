package com.crawler.interceptor;

import com.crawler.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiInceptor implements HandlerInterceptor {

	private static final String TOKEN_NAME = "token";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String token = null;

		//判断是否登录
		Cookie cookie = CookieUtil.getCookie(request, TOKEN_NAME);
		token = cookie.getValue();

		if (StringUtils.isNotBlank(token)) {
			token = request.getParameter(TOKEN_NAME);
		}

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
