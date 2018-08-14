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
	public static final BizException BALANCE_NOT_ENOUGH = new BizException("50002", "余额不足");
	public static final BizException AROUND_NOT_EXISTS = new BizException("50003", "回合不存在");
	public static final BizException USER_AROUND_NOT_EXISTS = new BizException("50004", "您没有未完成回合");
	public static final BizException AROUND_IS_OVER = new BizException("50005", "您已经完成此回合游戏");
	public static final BizException USER_IS_IN_AROUND = new BizException("50006", "您已经参与该回合");
	public static final BizException HAS_UNFINISHED_AROUND = new BizException("50007", "您有未完成回合, 不能加入游戏");
	public static final BizException REJECT = new BizException("50008", "请求拒绝");
	public static final BizException MIN_MONEY_EXEED = new BizException("50009", "金额超出允许范围");
	public static final BizException NAME_EXISTS = new BizException("50010", "回合名称已存在");

}
