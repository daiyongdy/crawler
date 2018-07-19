package com.crawler.util.httpclient;

import com.crawler.pool.HttpHost;
import com.crawler.pool.HttpHostHolder;
import com.crawler.util.httpclient.builder.HCB;
import com.crawler.util.httpclient.common.HttpConfig;
import com.crawler.util.httpclient.common.HttpHeader;
import com.crawler.util.httpclient.exception.HttpProcessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by daiyong on 2017/5/10.
 * email daiyong@coohua.com
 */
public class CoohuaHttpClient {

	//微博cookie
	private static final String DEFAULT_COOKIE = "SUB=_2AkMu2QjKf8NxqwJRmP4RxGrkaY52zAnEieKYhfkRJRMxHRl-yT83qk0btRBjeod4nKDyXZsSgeJeHDDYpukACA..; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9Wh2NIwnG4jCXgvmpE4sYUKy; SINAGLOBAL=1205852903088.1382.1502671123626; UOR=ent.ifeng.com,widget.weibo.com,note.youdao.com; TC-V5-G0=40eeee30be4a1418bde327baf365fcc0; ULOGIN_IMG=15268922976472; _s_tentry=-; Apache=9147716551882.836.1526892298064; ULV=1526892298071:13:4:1:9147716551882.836.1526892298064:1526635064062; TC-Page-G0=4c4b51307dd4a2e262171871fe64f295";

	private static Logger proxyLog = LogManager.getLogger("CoohuaHttpClient");

	/**
	 * 普通获取
	 * @param url
	 * @param cookie
	 * @return
	 */
	public static String get(String url, String cookie) {
		HttpClient client = getHttpClient();

		HttpConfig config = getHttpConfig(url, client, cookie);

		try {
			return doRequest(config);
		} catch (HttpProcessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用代理获取
	 * @param url
	 * @return
	 */
	public static String getByProxy(String url, String cookie) {
		HttpClient client;

		String value = null;

//		url = "https://www.baidu.com";

		for (int i = 0; i < 500; i++) {

			HttpHost httpHost = HttpHostHolder.httpHost;
			proxyLog.info("当前使用代理: ip:{}, port:{}", httpHost.getIp(), httpHost.getPort());

			client = getProxyHttpClient(httpHost, url);

			HttpConfig config = getHttpConfig(url, client, cookie);

			try {

				int code = HttpClientUtil.status(config);

				if (code == 403 || code == 429) {
					proxyLog.info("第 {} 尝试请求页面 当前使用代理被禁止: ip:{}, port:{}", (i + 1), httpHost.getIp(), httpHost.getPort());
					HttpHostHolder.switchProxy();
					continue;
				}

				value = doRequest(config);

				/*if (StringUtils.isNotBlank(value)) {
					break;
				} else {
					proxyLog.error("第 {} 尝试页面 当前使用代理返回空值: ip:{}, port:{}", (i + 1), httpHost.getIp(), httpHost.getPort());
					HttpHostHolder.switchProxy();
				}*/
			} catch (Exception ex) {
				proxyLog.error("第 {} 尝试页面 当前使用代理异常: ip:{}, port:{}, e:{}", (i + 1), httpHost.getIp(), httpHost.getPort(), ex.getMessage());
//				HttpHostHolder.switchProxy();
			} finally {
				HttpHostHolder.switchProxy();
			}
		}

		return value;
	}

	public static String getByProxy(String url, String cookie, HttpHost httpHost) {
		HttpClient client;

		String value = null;

		client = getProxyHttpClient(httpHost, url);

		HttpConfig config = getHttpConfig(url, client, cookie);

		try {

			int code = HttpClientUtil.status(config);

			if (code == 403 || code == 429) {
				HttpHostHolder.switchProxy();
			}

			value = doRequest(config);

			if (StringUtils.isNotBlank(value)) {
			} else {
				HttpHostHolder.switchProxy();
			}
		} catch (Exception ex) {
			HttpHostHolder.switchProxy();
		}

		return value;
	}

	public static HttpClient getProxyHttpClient(HttpHost httpHost, String url) {
		HttpClient client = null;
		try {

			if(url.toLowerCase().startsWith("https://")){
				client = HCB.custom()
						.timeout(15000)
						.ssl()
//					.pool(20, 5)
						.proxy(httpHost.getIp(), httpHost.getPort())
						.build();
			}else{
				client = HCB.custom()
						.timeout(15000)
//					.pool(20, 5)
						.proxy(httpHost.getIp(), httpHost.getPort())
						.build();
			}


		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建httpclient 异常", e);
		}
		return client;
	}



	/**
	 * 发起请求
	 * @param config
	 * @return
	 */
	public static String doRequest(HttpConfig config) throws HttpProcessException {
		String value =  HttpClientUtil.get(config);
		return value;
	}

	/**
	 * 获取httpClient
	 * @return
	 */
	private static HttpClient getHttpClient() {
		HttpClient client = null;
		try {
			client = HCB.custom()
					.timeout(100000)
					.pool(20, 5)
					.build();
		} catch (HttpProcessException e) {
			e.printStackTrace();
			throw new RuntimeException("创建httpclient 异常", e);
		}
		return client;
	}


	/**
	 * 获取请求配置
	 * @param url
	 * @param client
	 * @param cookie
	 * @return
	 */
	public static HttpConfig getHttpConfig(String url, HttpClient client, String cookie) {
		return HttpConfig.custom()
				.headers(buildHeaders(url, cookie))
				.client(client)
				.url(url);
	}

//	:authority: etherscan.io
//	:method: GET
//	:path: /blocks
//	:scheme: https
//		accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//	accept-encoding: gzip, deflate, br
//	accept-language: zh-CN,zh;q=0.9
//	cache-control: max-age=0
//	cookie: __cfduid=de31abae3ab193225dcc537883940562f1531820787; __gads=ID=fe3f8502f759c041:T=1531820789:S=ALNI_MYD39speYyaZ-qPEFQfMq9XwjhGoA; _ga=GA1.2.177327049.1531820788; _gid=GA1.2.119003211.1531820788; __cflb=1731742964; ASP.NET_SessionId=gdfjqk135ztfsrburwnj11q3
//	upgrade-insecure-requests: 1
//	user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36


	/**
	 * 伪造header
	 * @param url
	 * @param cookie
	 * @return
	 */
	private static Header[] buildHeaders(String url, String cookie) {
		String finalCookie = StringUtils.isNotBlank(cookie) ? cookie : DEFAULT_COOKIE;


		String ua = AgentStore.getAgent();
		Header[] headers = HttpHeader.custom()
				.userAgent(ua)
//				.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
//				.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko")
				.accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				.acceptLanguage("zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
				.acceptEncoding("gzip, deflate, sdch")
				.host(getHost(url))
//				.cookie("UM_distinctid=15b7aadcf3b2b3-0e655053bde6b4-6a11157a-1fa400-15b7aadcf3c358; uuid=\"w:906a9d4a70da482c8bda864d81e24cbd\"; tt_webid=57814516998; csrftoken=31b9c153fc9032336dce3f888e4fb224; _ba=BA0.2-20170515-51d9e-pAQCxSwCq92ZqmfiyM9F; _ga=GA1.2.137239853.1492411863; _gid=GA1.2.126982906.1494837642; CNZZDATA1259612802=1648927536-1492411278-null%7C1494834501; __tasessionId=trdjly8391494839731665; utm_source=toutiao")
//				.cookie("_zap=8e237f5f-d317-4788-b284-08c4c05e901e; q_c1=83e0608030274386ae4fde85359739fe|1508235511000|1501900161000; __DAYU_PP=VF2fbn6revyYEv7ViJYu7542b3bbd0a7; q_c1=83e0608030274386ae4fde85359739fe|1525327162000|1501900161000; l_cap_id=\"NDZmMDBlNGM1YWI3NGI4Y2IyNjU4OTVhOWRkYWQzMjg=|1525336216|5360ffc426d107cc7cb5ae9c2efcd725e146ac26\"; r_cap_id=\"ZDhjZjc2N2ZiNmM5NGMwMGJjYmZjZWMyMzAzNTVkMGY=|1525336216|4bb2749edd9161ce67fe18bd6208e500958823d3\"; cap_id=\"NTdiY2NhMmY5MGI3NGRhNDg2MjRiOTU5YmMyODYwZWM=|1525336216|3bc200c67265383bcb9e7abc55ac7698d44b632b\"; d_c0=\"AOBgmFZfiQ2PTty0vBjBFOZDloW0P5eoctY=|1525337206\"; __utma=155987696.1820563335.1525404793.1525404793.1525404793.1; __utmz=155987696.1525404793.1.1.utmcsr=zhihu|utmccn=gw145|utmcmd=desktop_web|utmcct=right_sidebar; _xsrf=aca75661-c030-4a5b-a319-f3555bd7013d; capsion_ticket=\"2|1:0|10:1526612509|14:capsion_ticket|44:OGI3ZTU0NjUwOTE4NDI4ZmFmMjE3ZTM1ZmI3ODA4NDA=|25c2dcbccdfcf651a00918bf70a817d3766a28a9f7c121b3431e5810e8eabca8\"; z_c0=\"2|1:0|10:1526612516|4:z_c0|92:Mi4xdmJsYkJnQUFBQUFBNEdDWVZsLUpEU1lBQUFCZ0FsVk5KSkRyV3dDWTQ5VXdGZkV4SmRkTlZwcHI3T2wtbUxJNUdB|e4f11ca20591d06999c5b728b02735a906b908bead4e28f6836ab3eb89b5cc89\"")
				.cookie("__cfduid=de31abae3ab193225dcc537883940562f1531820787; __gads=ID=fe3f8502f759c041:T=1531820789:S=ALNI_MYD39speYyaZ-qPEFQfMq9XwjhGoA; _ga=GA1.2.177327049.1531820788; _gid=GA1.2.119003211.1531820788; __cflb=1731742964; ASP.NET_SessionId=gdfjqk135ztfsrburwnj11q3")
				.build();
		return headers;
	}



	/**
	 * 获取
	 * @param url
	 * @return
	 */
/*	public static String targetHost(String url) {

		HttpClient client = getHttpClient();

		HttpConfig config = getHttpConfig(url, client);

		return HttpClientUtil.getTargetHost(config);
	}*/


	/**
	 * 获取域名
	 * @return
	 */
	public static String getHost(String targetUrl) {
		try {
			URL url = new URL(targetUrl);
			return url.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
//		System.out.println(CoohuaHttpClient.get("http://www.toutiao.com/a6416271435904893186/"));
//		System.out.println(CoohuaHttpClient.getByABuYunProxy("http://www.toutiao.com/c/user/5834755613/"));

//		String byProxy = CoohuaHttpClient.getByProxy("https://www.toutiao.com/group/6420134451590365442/");
//		System.out.println(byProxy);

	}


}
