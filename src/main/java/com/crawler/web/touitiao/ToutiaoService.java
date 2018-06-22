package com.crawler.web.touitiao;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daiyong on 2018/5/20.
 * email daiyong@qiyi.com
 */
public class ToutiaoService {

	private static Logger LOG = LogManager.getLogger("touTiaoLog");

	protected static final String PARAM_AS = "as";

	protected static final String PARAM_CP = "cp";

	public String buildArticleUrl(String url) {
		StringBuffer stringBuffer = new StringBuffer(url);

		stringBuffer.append("&utm_source=toutiao&widen=1")    //不变参数
				.append("&").append("max_behot_time=").append(0)   //时间戳
				.append("&").append("max_behot_time_tmp=").append(0)  //时间戳
				.append("&tadrequire=true");

		Map<String, String> ascpMap = getASCP();

		stringBuffer.append("&as=").append(ascpMap.get(PARAM_AS))
				.append("&cp=").append(ascpMap.get(PARAM_CP));

		return stringBuffer.toString();
	}

	/**
	 * 构造up主列表页url
	 * http://www.toutiao.com/c/user/article/?page_type=1&user_id=50995644629&max_behot_time=0&count=1&as=A195381F6ECAD30&cp=58FE8A7DB3103E1
	 * @return
	 */
	public String buildUperUrl(String uperId, Long timestamp) {
		StringBuffer stringBuffer = new StringBuffer("http://www.toutiao.com/c/user/article/?page_type=1&");

		stringBuffer.append("&user_id=").append(uperId)
				.append("&max_behot_time=").append(timestamp)
				.append("&count=").append(50);

		Map<String, String> ascpMap = getASCP();
		stringBuffer.append("&as=").append(ascpMap.get(PARAM_AS))
				.append("&cp=").append(ascpMap.get(PARAM_CP));

		return stringBuffer.toString();
	}

	/**
	 * 生成as cp
	 * @return
	 */
	private static Map<String, String> getASCP() {
		Map<String, String> resultMap = new HashMap<String, String>();

		JSONObject jsonObject = null;
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("javascript");
			engine.eval(ASCP.script);
			if (engine instanceof Invocable) {
				Invocable invoke = (Invocable) engine;
				Object obj = invoke.invokeFunction("getParam");
				jsonObject = JSONObject.parseObject(obj != null ? obj
						.toString() : null);

				if (null != jsonObject) {
					resultMap.put(PARAM_AS, jsonObject.getString(PARAM_AS));
					resultMap.put(PARAM_CP, jsonObject.getString(PARAM_CP));
				}
			}
		} catch (Exception e) {
			LOG.error("request ascp 异常, e:{}", ExceptionUtils.getStackTrace(e));
		}

		return resultMap;
	}






}
