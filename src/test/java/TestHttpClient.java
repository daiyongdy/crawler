import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crawler.Bootstrap;
import com.crawler.dao.model.db.ZombieUser;
import com.crawler.service.ZombieService;
import com.crawler.util.HttpClientUtil;
import com.crawler.util.httpclient.CoohuaHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by daiyong on 2018/5/20.
 * email daiyong@qiyi.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstrap.class)
@WebAppConfiguration
public class TestHttpClient {

	@Autowired
	private ZombieService zombieService;

	@Test
	public void insertTest(){
		ZombieUser zombieUser = new ZombieUser();
		zombieUser.setName("test");
		zombieUser.setHeadUrl("test");
		zombieUser.setCreateTime(new Date());
		zombieService.add(zombieUser);
	}

	@Test
	public void httpClientTest() throws URISyntaxException {
		String url = "https://www.zhihu.com/api/v3/feed/topstory?action_feed=True&limit=7&session_token=20f028cbb079348f14bc0e8e12972ed9&action=down&after_id=20&desktop=true";

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Cache-Control", "max-age=0");
		headers.put("Connection", "keep-alive");
		headers.put("Cookie", "zap=8e237f5f-d317-4788-b284-08c4c05e901e; q_c1=83e0608030274386ae4fde85359739fe|1508235511000|1501900161000; __DAYU_PP=VF2fbn6revyYEv7ViJYu7542b3bbd0a7; q_c1=83e0608030274386ae4fde85359739fe|1525327162000|1501900161000; l_cap_id=\"NDZmMDBlNGM1YWI3NGI4Y2IyNjU4OTVhOWRkYWQzMjg=|1525336216|5360ffc426d107cc7cb5ae9c2efcd725e146ac26\"; r_cap_id=\"ZDhjZjc2N2ZiNmM5NGMwMGJjYmZjZWMyMzAzNTVkMGY=|1525336216|4bb2749edd9161ce67fe18bd6208e500958823d3\"; cap_id=\"NTdiY2NhMmY5MGI3NGRhNDg2MjRiOTU5YmMyODYwZWM=|1525336216|3bc200c67265383bcb9e7abc55ac7698d44b632b\"; d_c0=\"AOBgmFZfiQ2PTty0vBjBFOZDloW0P5eoctY=|1525337206\"; __utma=155987696.1820563335.1525404793.1525404793.1525404793.1; __utmz=155987696.1525404793.1.1.utmcsr=zhihu|utmccn=gw145|utmcmd=desktop_web|utmcct=right_sidebar; _xsrf=aca75661-c030-4a5b-a319-f3555bd7013d; capsion_ticket=\"2|1:0|10:1526612509|14:capsion_ticket|44:OGI3ZTU0NjUwOTE4NDI4ZmFmMjE3ZTM1ZmI3ODA4NDA=|25c2dcbccdfcf651a00918bf70a817d3766a28a9f7c121b3431e5810e8eabca8\"; z_c0=\"2|1:0|10:1526612516|4:z_c0|92:Mi4xdmJsYkJnQUFBQUFBNEdDWVZsLUpEU1lBQUFCZ0FsVk5KSkRyV3dDWTQ5VXdGZkV4SmRkTlZwcHI3T2wtbUxJNUdB|e4f11ca20591d06999c5b728b02735a906b908bead4e28f6836ab3eb89b5cc89\"");
		headers.put("Host", "www.zhihu.com");
		headers.put("If-None-Match", "W/\"e003e39eae184cf2abae1b56159bb477aa0613e7\"");
		headers.put("Upgrade-Insecure-Requests", 1);
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		headers.put(":authority", "www.zhihu.com");
		headers.put(":method", "GET");
		headers.put(":path", "/api/v3/feed/topstory?action_feed=True&limit=7&session_token=20f028cbb079348f14bc0e8e12972ed9&action=down&after_id=20&desktop=true");
		headers.put(":scheme", "https");

//		String s = HttpClientUtil.httpGetRequest(url, headers, new HashMap<String, Object>(), 5000, 5000);
		String s = HttpClientUtil.httpGetRequest(url, 5000, 5000);

		System.out.println("------" + s);
	}

	@Test
	public void httpClient2Test() throws InterruptedException {

		String url = "https://www.zhihu.com/api/v3/feed/topstory?action_feed=True&limit=7&session_token=20f028cbb079348f1e1065566428e73f&action=down&after_id=6&desktop=true";

		while (true) {

			String s = CoohuaHttpClient.get(url, null);
			JSONObject jsonObject = JSONObject.parseObject(s);

			JSONArray datas= jsonObject.getJSONArray("data");
			Iterator<Object> iterator = datas.iterator();
			while(iterator.hasNext()) {
				JSONObject next = (JSONObject) iterator.next();
				JSONObject target = next.getJSONObject("target");
				JSONObject question = target.getJSONObject("question");
				if (question != null) {
					JSONObject author = question.getJSONObject("author");
					if (!author.getString("name").contains("匿名用户")) {

						try {
							System.out.println(author.getString("name") + "--" + author.getString("url") + "---" + author.getString("url_token"));

							String detailUrl = "https://www.zhihu.com/people/" + author.getString("url_token");
							String html = CoohuaHttpClient.get(detailUrl, null);
							Document doc = Jsoup.parse(html);
							Elements element = doc.select("img.Avatar.Avatar--large.UserAvatar-inner");
							String imgUrl = element.attr("src");

							ZombieUser zombieUser = new ZombieUser();
							zombieUser.setName(author.getString("name"));
							zombieUser.setHeadUrl(imgUrl);
							zombieUser.setCreateTime(new Date());
							zombieService.add(zombieUser);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}

			}

			JSONObject paging = jsonObject.getJSONObject("paging");
			url = paging.getString("next");

//			Thread.sleep(5000);
		}



		//System.out.println(datas);

	}

	@Test
	public void imageTest() throws InterruptedException {
		/*String url = "https://www.zhihu.com/people/li-wei-36-2-44";
		String s = CoohuaHttpClient.get(url);
		Document doc = Jsoup.parse(s);
		Elements element = doc.select("img.Avatar.Avatar--large.UserAvatar-inner");
		System.out.println(element.attr("src"));*/

		int num = 300;

		while (num <= 558) {
			String url = "https://www.zhihu.com/collection/38887091?utm_medium=social&utm_source=wechat_session&page=" + num;

			String html = CoohuaHttpClient.get(url, null);
			Document doc = Jsoup.parse(html);

			Elements authors = doc.select("a.author-link");
			Iterator<Element> iterator = authors.iterator();
			while(iterator.hasNext()) {


				try {
					Element next = iterator.next();
					String name = next.text();

					String detailUrl = "https://www.zhihu.com" + next.attr("href");
					String detailHtml = CoohuaHttpClient.get(detailUrl, null);
					Document detailDoc = Jsoup.parse(detailHtml);
					Elements element = detailDoc.select("img.Avatar.Avatar--large.UserAvatar-inner");
					String imgUrl = element.attr("src");

					ZombieUser zombieUser = new ZombieUser();
					zombieUser.setName(name);
					zombieUser.setHeadUrl(imgUrl);
					zombieUser.setCreateTime(new Date());
					zombieService.add(zombieUser);
					System.out.println("num: " + num + " " + JSONArray.toJSONString(zombieUser));

				} catch (Exception e) {
					e.printStackTrace();
				}

				Thread.sleep(2000);
			}

			num++;
		}






	}

	@Test
	public void ToutiaoTest(){
		String url = "https://www.toutiao.com/api/pc/feed/?category=news_tech&utm_source=toutiao&widen=1&max_behot_time=1526788835&max_behot_time_tmp=1526788835&tadrequire=true&as=A1C57BD011D1203&cp=5B0161B230D3DE1&_signature=Pa6b4wAAZrVJaOO-f-IzzT2um.";

	}

}


