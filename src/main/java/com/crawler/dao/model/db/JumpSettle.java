package com.crawler.dao.model.db;

import java.util.Date;

public class JumpSettle {
    private Long settleId;

    private String aroundId;

    private Boolean hasSettleFinished;

    private Date createTime;

    private Date updateTime;

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