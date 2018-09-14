package com.crawler.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawler.exception.BizException;
import com.crawler.model.WebUserDTO;
import com.crawler.model.WebUserHolder;
import com.crawler.util.AesUtils;
import com.crawler.util.HttpClientUtil;
import com.crawler.util.SignUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

public class ApiInceptor implements HandlerInterceptor {

	private static final Logger LOG = LogManager.getLogger("itoboxLog");

	private static final String USER_KEY = "ck"; //邮箱

	@Value("${api.user.get}")
	private String API_USER_GET;

	@Value("${itobox.secret}")
	private String ITOBOX_SECRET;

	@Value("${h5.secret}")
	private String H5_SECRET;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String ck = request.getParameter(USER_KEY);
		if (!StringUtils.isNotBlank(ck)) {
			throw BizException.NOT_LOGIN;
		}

		String domain = request.getServerName();
		String coinType = domain.split(".")[0];

		Map<String, Object> params = Maps.newHashMap();
		params.put("email", ck);
		params.put("sign", SignUtils.sign(params, ITOBOX_SECRET));
		params.put("coinType", coinType);
		String result = HttpClientUtil.httpGetRequest(API_USER_GET, params, 10000, 10000);
		LOG.info("itobox 获取用户信息, params:{}, result:{}", JSON.toJSONString(params), result);

		JSONObject resultObject = JSONObject.parseObject(result);
		if (resultObject.getIntValue("RET") == 1) {
			String data = resultObject.getString("DATA");
			if (!StringUtils.isNotBlank(data)) {
				throw BizException.GET_USER_INFO_FAIL;
			}
			JSONObject dataObject = JSONObject.parseObject(data);
			WebUserDTO userDTO = new WebUserDTO();
			userDTO.setUserId(dataObject.getString("id"));
			userDTO.setUserName(dataObject.getString("email"));
			userDTO.setBalance(new BigDecimal(dataObject.getString("balance")));
			userDTO.setCoinType(coinType);
			WebUserHolder.setUser(userDTO);
		} else {
			throw BizException.GET_USER_INFO_FAIL;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> params = Maps.newHashMap();
//		params.put("email", "qq351273576@live.com");
//		params.put("sign", SignUtils.sign(params, "vKwaQPulTNpCkr9X"));
		String result = HttpClientUtil.httpGetRequest("https://eth.itobox.io/api/v1/game/balance.exclusion", params, 10000, 10000);
		System.out.println(result);

		System.out.println(AesUtils.aesEncrypt("1", "cfNHrSHWrhVSTxjK"));
	}
}
