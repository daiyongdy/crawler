package com.crawler.dao.model.db;

import java.util.Date;

public class FeiXiaoHaoCoinDetail {
    private Long coinDetailId;

    private Integer feixiaohaocoinId;

    private String engAbbr;

    private String cn;

    private String engFull;

    private String img;

    private String whitePager;

    private String website;

    private String turnVolume;

    private String totalCirculation;

    private String pubTime;

    private Date createTime;

    public Long getCoinDetailId() {
        return coinDetailId;
    }

    public void setCoinDetailId(Long coinDetailId) {
        this.coinDetailId = coinDetailId;
    }

    public Integer getFeixiaohaocoinId() {
        return feixiaohaocoinId;
    }

    public void setFeixiaohaocoinId(Integer feixiaohaocoinId) {
        this.feixiaohaocoinId = feixiaohaocoinId;
    }

    public String getEngAbbr() {
        return engAbbr;
    }

    public void setEngAbbr(String engAbbr) {
        this.engAbbr = engAbbr == null ? null : engAbbr.trim();
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn == null ? null : cn.trim();
    }

    public String getEngFull() {
        return engFull;
    }

    public void setEngFull(String engFull) {
        this.engFull = engFull == null ? null : engFull.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getWhitePager() {
        return whitePager;
    }

    public void setWhitePager(String whitePager) {
        this.whitePager = whitePager == null ? null : whitePager.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getTurnVolume() {
        return turnVolume;
    }

    public void setTurnVolume(String turnVolume) {
        this.turnVolume = turnVolume == null ? null : turnVolume.trim();
    }

    public String getTotalCirculation() {
        return totalCirculation;
    }

    public void setTotalCirculation(String totalCirculation) {
        this.totalCirculation = totalCirculation == null ? null : totalCirculation.trim();
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime == null ? null : pubTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}