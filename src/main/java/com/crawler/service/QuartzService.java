package com.crawler.service;

import com.crawler.dao.mapper.db.EtherScanMapper;
import com.crawler.dao.model.db.EtherScan;
import com.crawler.web.ethrescan.Line;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuartzService {

    private static final Logger logger = LogManager.getLogger("asyncLog");

    @Autowired
    private EtherScanService etherScanService;

    @Autowired
    private EtherScanMapper etherScanMapper;

//    每分钟启动
    @Scheduled(cron = "0/10 * * * * ?")
    public void timerToNow(){

        logger.info("[etherscan] 定时任务启动");

        try {
            List<EtherScan> timeoutEtherScans = etherScanService.getTimeoutEtherScans();
            for (EtherScan timeoutEtherScan : timeoutEtherScans) {
                Line line = etherScanService.getNextLine(timeoutEtherScan.getCurrHeight(), timeoutEtherScan.getId(), true);
                if (line != null) {
                    String currentHeight = line.getHeight();
                    Long prevHeight = Long.valueOf(currentHeight) - 1;
                    timeoutEtherScan.setNextHeight(String.valueOf(prevHeight));
                    timeoutEtherScan.setHash(line.getHash());
                    timeoutEtherScan.setTimestamp(line.getTimestamp());
                    timeoutEtherScan.setIsProcessed(true);
                    timeoutEtherScan.setUpdatedAt(new Date());
                    etherScanMapper.updateByPrimaryKey(timeoutEtherScan);
                } else {
                    logger.error("[etherscan] 定时任务 没有获取下一个height, id:{}", timeoutEtherScan.getId());
                }
			}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[etherscan] 定时任务异常, e:{}", ExceptionUtils.getStackTrace(e));
        }

        logger.info("[etherscan] 定时任务结束");
    }

}