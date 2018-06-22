import com.crawler.Bootstrap;
import com.crawler.dao.model.db.Coin;
import com.crawler.service.CoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by daiyong on 2018/5/21.
 * email daiyong@qiyi.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstrap.class)
@WebAppConfiguration
public class CoinTest {

	@Autowired
	private CoinService coinService;

	@Test
	public void GetTest(){
		List<Coin> allCoin = coinService.getAllCoin();
	}

}
