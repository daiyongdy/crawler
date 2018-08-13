package com.crawler.dao.model.db;

import java.math.BigDecimal;
import java.util.Date;

public class JumpSettleDetail {
    private Long jumpSettleDetailId;

    private String userId;

    private String participantName;

    private BigDecimal prize;

    private Boolean hasSettleFinished;

    private Date createTime;

    private Date updateTime;

    public Long getJumpSettleDetailId() {
        return jumpSettleDetailId;
    }

    public void setJumpSettleDetailId(Long jumpSettleDetailId) {
        this.jumpSettleDetailId = jumpSettleDetailId;
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