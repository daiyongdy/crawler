package com.crawler.dao.model.db;

import java.util.Date;

public class EtherScan {
    private String id;

    private String currHeight;

    private String nextHeight;

    private String hash;

    private String timestamp;

    private Boolean isProcessed;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCurrHeight() {
        return currHeight;
    }

    public void setCurrHeight(String currHeight) {
        this.currHeight = currHeight == null ? null : currHeight.trim();
    }

    public String getNextHeight() {
        return nextHeight;
    }

    public void setNextHeight(String nextHeight) {
        this.nextHeight = nextHeight == null ? null : nextHeight.trim();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? null : timestamp.trim();
    }

    public Boolean getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
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