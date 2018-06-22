import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crawler.Bootstrap;
import com.crawler.dao.model.db.ZombieUser;
import com.crawler.service.ZombieService;
import com.crawler.util.httpclient.CoohuaHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * Created by daiyong on 2018/5/20.
 * email daiyong@qiyi.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstrap.class)
@WebAppConfiguration
public class FlowerTest {

	private static final List<String> topicList =
			new ArrayList<String>(Arrays.asList("19550994",
											     "19551557", "19556231", "19550564", "19552266", "19570709",
												 "19551404", "19551915", "19552706", "19559052", "19559424",
												 "19562832", "19556950", "19555513", "19604128"));

	@Autowired
	private ZombieService zombieService;

	@Test
	public void flowerTest() throws InterruptedException {

		for (String topic : topicList) {

			int num = 0;

			String originUrl = "https://www.zhihu.com/api/v4/topics/" + topic + "/followers?include=data%5B%2A%5D.gender%2Canswer_count%2Carticles_count%2Cfollower_count%2Cis_following%2Cis_followed&limit=20&offset=";

			while (true) {

				String url = originUrl + num;

				String returnStr = CoohuaHttpClient.get(url, null);

				JSONObject jsonObject = JSONObject.parseObject(returnStr);

				JSONArray datas = jsonObject.getJSONArray("data");

				if (datas.size() > 0) {
					Iterator<Object> iterator = datas.iterator();
					while (iterator.hasNext()) {
						try {
							JSONObject data = (JSONObject) iterator.next();
							String name = data.getString("name");

							String detailUrl = "https://www.zhihu.com/people/" + data.getString("url_token");
							String detailHtml = CoohuaHttpClient.get(detailUrl, null);
							Document detailDoc = Jsoup.parse(detailHtml);
							Elements element = detailDoc.select("img.Avatar.Avatar--large.UserAvatar-inner");
							String imgUrl = element.attr("src");

							ZombieUser zombieUser = new ZombieUser();
							zombieUser.setName(name);
							zombieUser.setHeadUrl(imgUrl);
							zombieUser.setCreateTime(new Date());
							zombieService.add(zombieUser);

							System.out.println("topic: " + topic + " num:" + num + "  " + name + "---" + imgUrl);

							Thread.sleep(5000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					num += 20;

				} else {
					break;
				}

			}
		}
	}

	@Test
	public void removeDumpTest(){
		zombieService.deleteDump();
	}

}
