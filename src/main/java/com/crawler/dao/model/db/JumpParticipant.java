package com.crawler.dao.model.db;

import java.math.BigDecimal;
import java.util.Date;

public class JumpParticipant {
    private Long participantId;

    private String userId;

    private String participantName;

    private String aroundId;

    private BigDecimal money;

    private Integer point;

    private Integer rankNum;

    private Boolean isWin;

    private Boolean isOver;

    private Date participantTime;

    private Date updateTime;

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName == null ? null : participantName.trim();
    }

    public String getAroundId() {
        return aroundId;
    }

    public void setAroundId(String aroundId) {
        this.aroundId = aroundId == null ? null : aroundId.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getRankNum() {
        return rankNum;
    }

    public void setRankNum(Integer rankNum) {
        this.rankNum = rankNum;
    }

    public Boolean getIsWin() {
        return isWin;
    }

    public void setIsWin(Boolean isWin) {
        this.isWin = isWin;
    }

    public Boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(Boolean isOver) {
        this.isOver = isOver;
    }

    public Date getParticipantTime() {
        return participantTime;
    }

    public void setParticipantTime(Date participantTime) {
        this.participantTime = participantTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}