package com.crawler.util.httpclient;

import java.util.*;

/**
 * Created by daiyong on 2017/4/28.
 * email daiyong@coohua.com
 */
public class AgentStore {

	private final static ResourceBundle AGENT_BUNDLE;

	private static List<String> agents = new ArrayList<String>();

	static {
		Locale locale = new Locale("zh", "CN");
		AGENT_BUNDLE = ResourceBundle.getBundle("userAgent", locale);
		Enumeration<String> keys = AGENT_BUNDLE.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			agents.add(AGENT_BUNDLE.getString(key));
		}
	}

	public static String getAgent() {
		return agents.get(new Random().nextInt(agents.size()));
	}

	public static void main(String[] args) {
		System.out.println(AGENT_BUNDLE.getString("ua1"));
	}

}
