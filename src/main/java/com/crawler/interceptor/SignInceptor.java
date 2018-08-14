package com.crawler.interceptor;

import com.alibaba.fastjson.JSON;
import com.crawler.exception.BizException;
import com.crawler.model.WebUserDTO;
import com.crawler.model.WebUserHolder;
import com.crawler.util.MD5Utils;
import com.crawler.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SignInceptor implements HandlerInterceptor {

	private static final Logger LOG = LogManager.getLogger("errorLog");

	@Value("${h5.secret}")
	private String H5_SECRET;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		Map<String, String> params = RequestUtils.getParameterMap(request);

		if (!StringUtils.isNotBlank(params.get("sign"))) {
			WebUserDTO user = WebUserHolder.getUser();
			LOG.error("数据上报没有签名 拒绝请求, userId:{}, userName:{}, params:{}",
					user.getUserId(), user.getUserName(), JSON.toJSONString(params));
			throw BizException.REJECT;
		}

		SortedMap<String, String> sortedParams = new TreeMap<String, String>(params);
		sortedParams.remove("sign");
		StringBuilder sb = new StringBuilder();
		for (String key : sortedParams.keySet()) {
			String val = sortedParams.get(key);
			sb.append(key).append("=").append(StringUtils.defaultIfEmpty(val, "")).append("|");
		}

		String sourceSign = MD5Utils.getDigest(sb.append(H5_SECRET).toString());
		if (!sourceSign.equals(params.get("sign"))) {
			WebUserDTO user = WebUserHolder.getUser();
			LOG.error("数据上报签名错误 拒绝请求, userId:{}, userName:{}, params:{}",
					user.getUserId(), user.getUserName(), JSON.toJSONString(params));
			throw BizException.REJECT;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
