package com.crawler.controller;


import com.crawler.exception.BizException;
import com.crawler.model.WebMessage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LogManager.getLogger("errorLog");

    /**
     * 运行时异常
     *
     * @param e
     * @param response
     * @return WebMessage
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public WebMessage exceptionHandler(Exception e, HttpServletResponse response) {
        //业务异常
        if (e instanceof BizException) {
            BizException bex = (BizException) e;

            LOG.error("发生业务异常, code:{}, e:{}", bex.getCode(), ExceptionUtils.getStackTrace(e));

            return WebMessage.build(Integer.valueOf(bex.getCode()), bex.getMsg());
        }
        //其他服务器异常
        else {

            LOG.error("发生内部服务异常, code:{}, e:{}", "500", ExceptionUtils.getStackTrace(e));

            return WebMessage.build(500, "系统正忙,请稍后再试");
        }
    }

}
