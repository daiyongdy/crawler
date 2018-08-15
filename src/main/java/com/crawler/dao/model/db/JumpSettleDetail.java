package com.crawler.dao.model.db;

import java.math.BigDecimal;
import java.util.Date;

public class JumpSettleDetail {
    private Long jumpSettleDetailId;

    private Long settleId;

    private String aroundId;

    private String userId;

    private String participantName;

    private BigDecimal prize;

    private Integer type;

    private Boolean hasSettleFinished;

    private Date createTime;

    private Date updateTime;

    public Long getJumpSettleDetailId() {
        return jumpSettleDetailId;
    }

    public void setJumpSettleDetailId(Long jumpSettleDetailId) {
        this.jumpSettleDetailId = jumpSettleDetailId;
    }

    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
    }

    public String getAroundId() {
        return aroundId;
    }

    public void setAroundId(String aroundId) {
        this.aroundId = aroundId == null ? null : aroundId.trim();
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

    public BigDecimal getPrize() {
        return prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getHasSettleFinished() {
        return hasSettleFinished;
    }

    public void setHasSettleFinished(Boolean hasSettleFinished) {
        this.hasSettleFinished = hasSettleFinished;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}