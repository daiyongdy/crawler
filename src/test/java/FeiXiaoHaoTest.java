import com.alibaba.fastjson.JSON;
import com.crawler.Bootstrap;
import com.crawler.dao.model.db.FeiXiaoHaoCoin;
import com.crawler.dao.model.db.FeiXiaoHaoCoinDetail;
import com.crawler.service.FeiXiaoHaoCoinDetailService;
import com.crawler.service.FeiXiaoHaoService;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daiyong on 2018/5/31.
 * email daiyong@qiyi.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstrap.class)
@WebAppConfiguration
public class FeiXiaoHaoTest {

	@Autowired
	private FeiXiaoHaoService feiXiaoHaoService;

	@Autowired
	private FeiXiaoHaoCoinDetailService feiXiaoHaoCoinDetailService;

	@Test
	public void getCoinTest() throws InterruptedException {
		String url = "https://www.feixiaohao.com/list_{page}.html";
		for (int i = 1; i <= 21; i++) {
			String html = CoohuaHttpClient.get(url.replace("{page}", String.valueOf(i)), "");
			Document document = Jsoup.parse(html);
			Elements tbody = document.select("table#table tbody");
			Elements trs = tbody.select("tr");
			for (int i1 = 0; i1 < trs.size(); i1++) {
				Element tr = trs.get(i1);
				Elements tds = tr.select("td");
				Element td = tds.get(1);
				String name = td.select("a").text();
				String detailUrl = td.select("a").attr("href");
				FeiXiaoHaoCoin feiXiaoHaoCoin = new FeiXiaoHaoCoin();
				feiXiaoHaoCoin.setName(name);
				feiXiaoHaoCoin.setUrl("https://www.feixiaohao.com" + detailUrl);
				feiXiaoHaoCoin.setCreateTime(new Date());
				feiXiaoHaoService.add(feiXiaoHaoCoin);
				System.out.println("feixiaohaocoin add " + name + ":" + feiXiaoHaoCoin.getUrl());
			}
			Thread.sleep(3000);
		}
	}

	@Test
	public void getCoinDetailTest() throws ParseException {

		for (int j = 3; j <= 2035; j++) {

			FeiXiaoHaoCoin feiXiaoHaoCoin = feiXiaoHaoService.getById(Long.valueOf(j));
			FeiXiaoHaoCoinDetail feiXiaoHaoCoinDetail = new FeiXiaoHaoCoinDetail();
			System.out.println("--------" + feiXiaoHaoCoin.getName());

			String name = feiXiaoHaoCoin.getName();
			if (name.contains("-")) {
				String[] names = name.split("-");
				feiXiaoHaoCoinDetail.setEngAbbr(names[0]);
				feiXiaoHaoCoinDetail.setCn(names[1]);
			} else {
				feiXiaoHaoCoinDetail.setEngAbbr(name);
				feiXiaoHaoCoinDetail.setCn(null);
			}

			String html = CoohuaHttpClient.get(feiXiaoHaoCoin.getUrl(), "");
			Document document = Jsoup.parse(html);

			Element div = document.select("div.cell.maket").get(0);
			String engFull = div.select("h1").get(0).select("small").get(0).text();
			feiXiaoHaoCoinDetail.setEngFull(engFull);
			String img = div.select("h1").get(0).select("img").get(0).attr("src");
			feiXiaoHaoCoinDetail.setImg("http:" + img);

			String whitePaper = null;
			Elements whitePaperAs = document.select("div.secondPark").select("li").get(4).select("a");
			if (whitePaperAs.size() > 0) {
				whitePaper = whitePaperAs.get(0).attr("href");
			}
			feiXiaoHaoCoinDetail.setWhitePager(whitePaper);

			Element publicTime = document.select("div.secondPark").select("li").get(3).select("span.value").get(0);
			feiXiaoHaoCoinDetail.setPubTime(publicTime.text());

			Elements as = document.select("div.secondPark").select("li").get(5).select("a");
			List<String> webs = new ArrayList<String>();
			for (int i = 0; i < as.size(); i++) {
				Element a = as.get(i);
				String href = a.attr("href");
				if (!href.contains("http") && !href.contains("https")) {
					href = "https:" + href;
				}
				webs.add(href);
			}
			feiXiaoHaoCoinDetail.setWebsite(JSON.toJSONString(webs));

			Element cellDiv = document.select("div.firstpart div.cell").get(2);
			Element ltl = cellDiv.select("div").get(2);
			feiXiaoHaoCoinDetail.setTurnVolume(ltl.text());
			Element total = cellDiv.select("div").get(4);
			feiXiaoHaoCoinDetail.setTotalCirculation(total.text());
			feiXiaoHaoCoinDetail.setCreateTime(new Date());
			feiXiaoHaoCoinDetail.setFeixiaohaocoinId(feiXiaoHaoCoin.getId().intValue());
			feiXiaoHaoCoinDetailService.add(feiXiaoHaoCoinDetail);
			System.out.println("add " + JSON.toJSONString(feiXiaoHaoCoinDetail));
		}


	}

}
