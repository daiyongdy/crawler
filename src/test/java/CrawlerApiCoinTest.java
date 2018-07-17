import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crawler.Bootstrap;
import com.crawler.dao.model.db.CrawlerApiCoin;
import com.crawler.service.CrawlerApiCoinService;
import com.crawler.util.httpclient.CoohuaHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by daiyong on 2018/6/25.
 * email daiyong@qiyi.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstrap.class)
@WebAppConfiguration
public class CrawlerApiCoinTest {

	@Autowired
	private CrawlerApiCoinService crawlerApiCoinService;

	@Test
	public void getCoinFromApiTest(){
		String url = "https://api.coinmarketcap.com/v2/listings/";
		String response = CoohuaHttpClient.get(url, "");
		JSONObject responseObject = JSON.parseObject(response);
		if (responseObject != null) {
			JSONArray datas = responseObject.getJSONArray("data");
			for (Object data : datas) {
				JSONObject object = (JSONObject) data;
				CrawlerApiCoin coin = new CrawlerApiCoin();
				coin.setId(object.getLongValue("id"));
				coin.setCnName(null);
				coin.setSymbol(object.getString("symbol"));
				coin.setName(object.getString("name"));
				crawlerApiCoinService.add(coin);
			}
		}
	}

}
