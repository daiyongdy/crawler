package com.crawler.dao.model.db;

import java.math.BigDecimal;
import java.util.Date;

public class JumpSettle {
    private Long settleId;

    private Long aroundId;

    private BigDecimal wrater;

    private Integer winNum;

    private Boolean hasSettleFinished;

    private Date createTime;

    private Date updateTime;

    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
    }

    public Long getAroundId() {
        return aroundId;
    }

    public void setAroundId(Long aroundId) {
        this.aroundId = aroundId;
    }

    public BigDecimal getWrater() {
        return wrater;
    }

    public void setWrater(BigDecimal wrater) {
        this.wrater = wrater;
    }

    public Integer getWinNum() {
        return winNum;
    }

    public void setWinNum(Integer winNum) {
        this.winNum = winNum;
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