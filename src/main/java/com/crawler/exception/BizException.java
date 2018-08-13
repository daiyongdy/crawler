package com.crawler.exception;

/**
 * Created by daiyong on 2018/7/3.
 * email daiyong@qiyi.com
 */
public class BizException extends RuntimeException {

	private String code;
	private String msg;

	public BizException(String code, String msg) {
		super(code + ":" + msg);
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static final BizException NOT_LOGIN = new BizException("50001", "用户未登录");
	public static final BizException BALANCE_NOT_EMPTY = new BizException("50002", "余额不足");
	public static final BizException AROUND_NOT_EXISTS = new BizException("50003", "回合不存在");
	public static final BizException USER_AROUND_NOT_EXISTS = new BizException("50004", "此用户没有未完成回合");
	public static final BizException AROUND_IS_OVER = new BizException("50005", "您已经完成此回合游戏");

}
