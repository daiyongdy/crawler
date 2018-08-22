package com.crawler.dao.model.db;

import java.math.BigDecimal;
import java.util.Date;

public class JumpAround {
    private String aroundId;

    private String aroundName;

    private Integer minParticipantsNum;

    private Integer currentParticipantsNum;

    private BigDecimal money;

    private BigDecimal totalAmout;

    private Integer status;

    private Long no;

    private String createrId;

    private String createrName;

    private Date createTime;

    private Date updateTime;

    public String getAroundId() {
        return aroundId;
    }

    public void setAroundId(String aroundId) {
        this.aroundId = aroundId == null ? null : aroundId.trim();
    }

    public String getAroundName() {
        return aroundName;
    }

    public void setAroundName(String aroundName) {
        this.aroundName = aroundName == null ? null : aroundName.trim();
    }

    public Integer getMinParticipantsNum() {
        return minParticipantsNum;
    }

    public void setMinParticipantsNum(Integer minParticipantsNum) {
        this.minParticipantsNum = minParticipantsNum;
    }

    public Integer getCurrentParticipantsNum() {
        return currentParticipantsNum;
    }

    public void setCurrentParticipantsNum(Integer currentParticipantsNum) {
        this.currentParticipantsNum = currentParticipantsNum;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(BigDecimal totalAmout) {
        this.totalAmout = totalAmout;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName == null ? null : createrName.trim();
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