package com.crawler.model;

/**
 * Created by daiyong on 2018/8/11.
 * email daiyong@qiyi.com
 */
public class CheckDTO {

	private Boolean isFromMobile; //是否来自移动端

	private Boolean isInAround; //是否在回合中

	private String aroundId;  //回合id

	private Boolean aroundOver; //回合是否结束

	private Boolean selfCreate;  //是否是自己创建

	private Boolean hasBalance;  //是否有余额

	private Boolean isBalanceEnough; //是否余额充足

	public Boolean getIsFromMobile() {
		return isFromMobile;
	}

	public void setIsFromMobile(Boolean fromMobile) {
		isFromMobile = fromMobile;
	}

	public Boolean getIsInAround() {
		return isInAround;
	}

	public void setIsInAround(Boolean inAround) {
		isInAround = inAround;
	}

	public String getAroundId() {
		return aroundId;
	}

	public void setAroundId(String aroundId) {
		this.aroundId = aroundId;
	}

	public Boolean getIsAroundOver() {
		return aroundOver;
	}

	public void setIsAroundOver(Boolean aroundOver) {
		this.aroundOver = aroundOver;
	}

	public Boolean getIsSelfCreate() {
		return selfCreate;
	}

	public void setIsSelfCreate(Boolean selfCreate) {
		this.selfCreate = selfCreate;
	}

	public Boolean getHasBalance() {
		return hasBalance;
	}

	public void setHasBalance(Boolean hasBalance) {
		this.hasBalance = hasBalance;
	}

	public Boolean getIsBalanceEnough() {
		return isBalanceEnough;
	}

	public void setIsBalanceEnough(Boolean balanceEnough) {
		isBalanceEnough = balanceEnough;
	}
}
