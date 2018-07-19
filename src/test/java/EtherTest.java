import com.crawler.util.httpclient.CoohuaHttpClient;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

/**
 * email daiyong@qiyi.com
 */
public class EtherTest {

	/**
	 * Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep,
	 Oct, Nov, Dec
	 */
	private static Map<String, String> monthMap = Maps.newHashMap();
	static {
		monthMap.put("Jan", "01");
		monthMap.put("Feb", "02");
		monthMap.put("Mar", "03");
		monthMap.put("Apr", "04");
		monthMap.put("May", "05");
		monthMap.put("Jun", "06");
		monthMap.put("Jul", "07");
		monthMap.put("Aug", "08");
		monthMap.put("Sep", "09");
		monthMap.put("Oct", "10");
		monthMap.put("Nov", "11");
		monthMap.put("Dec", "12");
	}

	@Test
	public void TestRequestTest(){
		/*for (int i = 0; i < 1000000; i++) {
			String s = CoohuaHttpClient.get("https://etherscan.io/blocks", null);
			System.out.println( i + " :  " + s.contains("<th>Height</th>"));
			*//*try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*//*
		}*/

		String s = CoohuaHttpClient.get("https://etherscan.io/blocks", null);
		Document document = Jsoup.parse(s);
		Element table = document.select("table.table.table-hover").get(0);
		Element tbody_tr = table.select("tbody tr").get(0);
		Elements tds = tbody_tr.select("td");
		Element td = tds.get(0);
		Element a = td.select("a").get(0);
		System.out.println("+++++++++++++" + a.text());
		System.out.println("end...");



	}

	@Test
	public void testNextUrlTest(){
		String html = CoohuaHttpClient.get("https://etherscan.io/block/5990767", null);
		Document document = Jsoup.parse(html);
		Element totalDiv = document.getElementById("ContentPlaceHolder1_maintable");
		Element heightDiv = totalDiv.select("div").get(2);
		System.out.println("heightDev : " + heightDiv.text());

		Element hashDiv = totalDiv.select("div").get(8);
		System.out.println("heightDev : " + hashDiv.text().trim());

		Element timestampDiv = totalDiv.select("div").get(4);
		System.out.println("timestampDiv : " + timestampDiv.text().trim());
//		String text = "6 mins ago (Jul-19-2018 01:34:26 AM +UTC)";

		Pattern pattern = Pattern.compile(".*\\((.*)\\)");
		Matcher matcher = pattern.matcher(timestampDiv.text().trim());
		if (matcher.find()) {
			String utcStr = matcher.group(1);
			utcStr = utcStr.replace("+UTC", "").replace("AM", "").replace("PM", "");
			String[] ymdArr = utcStr.trim().split(" ");
			String[] ymd = ymdArr[0].split("-");
			String year = ymd[2];
			String month = ymd[0];
			String date = ymd[1];
			System.out.println(year + "-" + monthMap.get(month) + "-" + date + " " + ymdArr[1]);
		}




	}

}
