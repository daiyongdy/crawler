package com.crawler.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by daiyong on 2018/8/13.
 * email daiyong@qiyi.com
 */
public class AroundInfoDTO {

	private String createrName;
	private int minParticipantsNum;
	private BigDecimal money;
	private int currentParticipantsNum;
	private BigDecimal totalAmout;
	private String desc;
	private Boolean isOver;
	private List<Participant> participants;

	public Boolean getIsOver() {
		return isOver;
	}

	public void setIsOver(Boolean over) {
		isOver = over;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public int getMinParticipantsNum() {
		return minParticipantsNum;
	}

	public void setMinParticipantsNum(int minParticipantsNum) {
		this.minParticipantsNum = minParticipantsNum;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public int getCurrentParticipantsNum() {
		return currentParticipantsNum;
	}

	public void setCurrentParticipantsNum(int currentParticipantsNum) {
		this.currentParticipantsNum = currentParticipantsNum;
	}

	public BigDecimal getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(BigDecimal totalAmout) {
		this.totalAmout = totalAmout;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public static class Participant {
		private String participantName;
		private Date endTime;
		private String point;
		private String rank;

		public String getParticipantName() {
			return participantName;
		}

		public void setParticipantName(String participantName) {
			this.participantName = participantName;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public String getPoint() {
			return point;
		}

		public void setPoint(String point) {
			this.point = point;
		}

		public String getRank() {
			return rank;
		}

		public void setRank(String rank) {
			this.rank = rank;
		}

	}

}