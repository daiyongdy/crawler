import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crawler.Bootstrap;
import com.crawler.Constants;
import com.crawler.dao.model.db.BishijieArticle;
import com.crawler.dao.model.db.BishijieKeyword;
import com.crawler.dao.model.db.Coin;
import com.crawler.service.BishijieArticleService;
import com.crawler.service.BishijieService;
import com.crawler.service.CoinService;
import com.crawler.util.httpclient.CoohuaHttpClient;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by daiyong on 2018/6/22.
 * email daiyong@qiyi.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstrap.class)
@WebAppConfiguration
public class BIshijieTest {

	@Autowired
	private CoinService coinService;

	@Autowired
	private BishijieService bishijieService;

	@Autowired
	private BishijieArticleService bishijieArticleService;

	@Before
	public void before() {
		List<BishijieKeyword> all = bishijieService.getAll();
		for (BishijieKeyword keyword : all) {
			Constants.KEYWORDS.add(keyword.getKeyword());
		}
	}

	@Test
	public void KeywordTest(){
		List<Coin> allCoin = coinService.getAllCoin();
		for (Coin coin : allCoin) {
			BishijieKeyword keyword = new BishijieKeyword();
			keyword.setKeyword(coin.getName());
			bishijieService.add(keyword);
		}
	}

	@Test
	public void CrawlerTest() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate = sdf.parse("2017-12-01 00:00:00");
		long endTimestamp = endDate.getTime() / 1000;

		String startUrl = "http://www.bishijie.com/api/newsv17/index?size=50&client=pc&timestamp=1521530836";
		String nextUrl = "http://www.bishijie.com/api/newsv17/index?size=50&client=pc&timestamp=${time}";

		long time = add(startUrl);

		while (time > endTimestamp) {
			String url = nextUrl.replace("${time}", String.valueOf(time));
//			try {
////				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			time = add(url);
		}

	}

	public long add(String url) {

		System.out.println("current url: " + url);

		String firstPage = CoohuaHttpClient.get(url, "");

		JSONObject firstJsonObject = JSON.parseObject(firstPage);

		List<BishijieObjecct> bishijieObjecctList = parse(firstJsonObject);

		System.out.println("current size: " + bishijieObjecctList.size());

		long time = System.currentTimeMillis() / 1000;

		for (BishijieObjecct bishijieObjecct : bishijieObjecctList) {

			time = bishijieObjecct.getTimestamp();

			String sourceId = bishijieObjecct.getSourceId();
			BishijieArticle originArticle = bishijieArticleService.getBySourceId(sourceId);
			if (originArticle == null) {
				BishijieArticle article = new BishijieArticle();
				article.setContent(bishijieObjecct.getContent());
				article.setTitle(bishijieObjecct.getTitle());
				article.setCreateTime(new Date());
				article.setPubTime(new Date(bishijieObjecct.getTimestamp() * 1000));
				article.setCoinName(getCoinName(bishijieObjecct.getTitle()));
				article.setSourceId(bishijieObjecct.getSourceId());
				try {
					bishijieArticleService.add(article);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		return time;
	}

	private String getCoinName(String title) {
		for (String keyword : Constants.KEYWORDS) {
			if (title.toUpperCase().contains(keyword.toUpperCase())) {
				return keyword;
			}
		}

		return null;
	}

	List<BishijieObjecct> parse(JSONObject jsonObject) {
		List<BishijieObjecct> result = Lists.newArrayList();

		JSONArray data = jsonObject.getJSONArray("data");
		JSONObject data_0_object = data.getJSONObject(0);

		JSONArray objects = data_0_object.getJSONArray("buttom");
		if (objects != null) {
			for (int i = 0; i < objects.size(); i++) {
				JSONObject jsonObject1 = objects.getJSONObject(i);
				BishijieObjecct bishijieObjecct = new BishijieObjecct();
				bishijieObjecct.setContent(jsonObject1.getString("content"));
				bishijieObjecct.setTitle(jsonObject1.getString("title"));
				bishijieObjecct.setTimestamp(jsonObject1.getLongValue("issue_time"));
				bishijieObjecct.setSourceId(String.valueOf(jsonObject1.getLongValue("newsflash_id")));
				result.add(bishijieObjecct);
			}
		}

		return result;
	}



}

class BishijieObjecct {
	private String content;
	private Long timestamp;
	private String title;
	private String sourceId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
