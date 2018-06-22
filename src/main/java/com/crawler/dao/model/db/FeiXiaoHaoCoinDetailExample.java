package com.crawler.dao.model.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeiXiaoHaoCoinDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FeiXiaoHaoCoinDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCoinDetailIdIsNull() {
            addCriterion("coin_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdIsNotNull() {
            addCriterion("coin_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdEqualTo(Long value) {
            addCriterion("coin_detail_id =", value, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdNotEqualTo(Long value) {
            addCriterion("coin_detail_id <>", value, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdGreaterThan(Long value) {
            addCriterion("coin_detail_id >", value, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("coin_detail_id >=", value, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdLessThan(Long value) {
            addCriterion("coin_detail_id <", value, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdLessThanOrEqualTo(Long value) {
            addCriterion("coin_detail_id <=", value, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdIn(List<Long> values) {
            addCriterion("coin_detail_id in", values, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdNotIn(List<Long> values) {
            addCriterion("coin_detail_id not in", values, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdBetween(Long value1, Long value2) {
            addCriterion("coin_detail_id between", value1, value2, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andCoinDetailIdNotBetween(Long value1, Long value2) {
            addCriterion("coin_detail_id not between", value1, value2, "coinDetailId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdIsNull() {
            addCriterion("feixiaohaocoin_id is null");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdIsNotNull() {
            addCriterion("feixiaohaocoin_id is not null");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdEqualTo(Integer value) {
            addCriterion("feixiaohaocoin_id =", value, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdNotEqualTo(Integer value) {
            addCriterion("feixiaohaocoin_id <>", value, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdGreaterThan(Integer value) {
            addCriterion("feixiaohaocoin_id >", value, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("feixiaohaocoin_id >=", value, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdLessThan(Integer value) {
            addCriterion("feixiaohaocoin_id <", value, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdLessThanOrEqualTo(Integer value) {
            addCriterion("feixiaohaocoin_id <=", value, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdIn(List<Integer> values) {
            addCriterion("feixiaohaocoin_id in", values, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdNotIn(List<Integer> values) {
            addCriterion("feixiaohaocoin_id not in", values, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdBetween(Integer value1, Integer value2) {
            addCriterion("feixiaohaocoin_id between", value1, value2, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andFeixiaohaocoinIdNotBetween(Integer value1, Integer value2) {
            addCriterion("feixiaohaocoin_id not between", value1, value2, "feixiaohaocoinId");
            return (Criteria) this;
        }

        public Criteria andEngAbbrIsNull() {
            addCriterion("eng_abbr is null");
            return (Criteria) this;
        }

        public Criteria andEngAbbrIsNotNull() {
            addCriterion("eng_abbr is not null");
            return (Criteria) this;
        }

        public Criteria andEngAbbrEqualTo(String value) {
            addCriterion("eng_abbr =", value, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrNotEqualTo(String value) {
            addCriterion("eng_abbr <>", value, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrGreaterThan(String value) {
            addCriterion("eng_abbr >", value, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrGreaterThanOrEqualTo(String value) {
            addCriterion("eng_abbr >=", value, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrLessThan(String value) {
            addCriterion("eng_abbr <", value, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrLessThanOrEqualTo(String value) {
            addCriterion("eng_abbr <=", value, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrLike(String value) {
            addCriterion("eng_abbr like", value, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrNotLike(String value) {
            addCriterion("eng_abbr not like", value, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrIn(List<String> values) {
            addCriterion("eng_abbr in", values, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrNotIn(List<String> values) {
            addCriterion("eng_abbr not in", values, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrBetween(String value1, String value2) {
            addCriterion("eng_abbr between", value1, value2, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andEngAbbrNotBetween(String value1, String value2) {
            addCriterion("eng_abbr not between", value1, value2, "engAbbr");
            return (Criteria) this;
        }

        public Criteria andCnIsNull() {
            addCriterion("cn is null");
            return (Criteria) this;
        }

        public Criteria andCnIsNotNull() {
            addCriterion("cn is not null");
            return (Criteria) this;
        }

        public Criteria andCnEqualTo(String value) {
            addCriterion("cn =", value, "cn");
            return (Criteria) this;
        }

        public Criteria andCnNotEqualTo(String value) {
            addCriterion("cn <>", value, "cn");
            return (Criteria) this;
        }

        public Criteria andCnGreaterThan(String value) {
            addCriterion("cn >", value, "cn");
            return (Criteria) this;
        }

        public Criteria andCnGreaterThanOrEqualTo(String value) {
            addCriterion("cn >=", value, "cn");
            return (Criteria) this;
        }

        public Criteria andCnLessThan(String value) {
            addCriterion("cn <", value, "cn");
            return (Criteria) this;
        }

        public Criteria andCnLessThanOrEqualTo(String value) {
            addCriterion("cn <=", value, "cn");
            return (Criteria) this;
        }

        public Criteria andCnLike(String value) {
            addCriterion("cn like", value, "cn");
            return (Criteria) this;
        }

        public Criteria andCnNotLike(String value) {
            addCriterion("cn not like", value, "cn");
            return (Criteria) this;
        }

        public Criteria andCnIn(List<String> values) {
            addCriterion("cn in", values, "cn");
            return (Criteria) this;
        }

        public Criteria andCnNotIn(List<String> values) {
            addCriterion("cn not in", values, "cn");
            return (Criteria) this;
        }

        public Criteria andCnBetween(String value1, String value2) {
            addCriterion("cn between", value1, value2, "cn");
            return (Criteria) this;
        }

        public Criteria andCnNotBetween(String value1, String value2) {
            addCriterion("cn not between", value1, value2, "cn");
            return (Criteria) this;
        }

        public Criteria andEngFullIsNull() {
            addCriterion("eng_full is null");
            return (Criteria) this;
        }

        public Criteria andEngFullIsNotNull() {
            addCriterion("eng_full is not null");
            return (Criteria) this;
        }

        public Criteria andEngFullEqualTo(String value) {
            addCriterion("eng_full =", value, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullNotEqualTo(String value) {
            addCriterion("eng_full <>", value, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullGreaterThan(String value) {
            addCriterion("eng_full >", value, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullGreaterThanOrEqualTo(String value) {
            addCriterion("eng_full >=", value, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullLessThan(String value) {
            addCriterion("eng_full <", value, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullLessThanOrEqualTo(String value) {
            addCriterion("eng_full <=", value, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullLike(String value) {
            addCriterion("eng_full like", value, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullNotLike(String value) {
            addCriterion("eng_full not like", value, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullIn(List<String> values) {
            addCriterion("eng_full in", values, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullNotIn(List<String> values) {
            addCriterion("eng_full not in", values, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullBetween(String value1, String value2) {
            addCriterion("eng_full between", value1, value2, "engFull");
            return (Criteria) this;
        }

        public Criteria andEngFullNotBetween(String value1, String value2) {
            addCriterion("eng_full not between", value1, value2, "engFull");
            return (Criteria) this;
        }

        public Criteria andImgIsNull() {
            addCriterion("img is null");
            return (Criteria) this;
        }

        public Criteria andImgIsNotNull() {
            addCriterion("img is not null");
            return (Criteria) this;
        }

        public Criteria andImgEqualTo(String value) {
            addCriterion("img =", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotEqualTo(String value) {
            addCriterion("img <>", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgGreaterThan(String value) {
            addCriterion("img >", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgGreaterThanOrEqualTo(String value) {
            addCriterion("img >=", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLessThan(String value) {
            addCriterion("img <", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLessThanOrEqualTo(String value) {
            addCriterion("img <=", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLike(String value) {
            addCriterion("img like", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotLike(String value) {
            addCriterion("img not like", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgIn(List<String> values) {
            addCriterion("img in", values, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotIn(List<String> values) {
            addCriterion("img not in", values, "img");
            return (Criteria) this;
        }

        public Criteria andImgBetween(String value1, String value2) {
            addCriterion("img between", value1, value2, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotBetween(String value1, String value2) {
            addCriterion("img not between", value1, value2, "img");
            return (Criteria) this;
        }

        public Criteria andWhitePagerIsNull() {
            addCriterion("white_pager is null");
            return (Criteria) this;
        }

        public Criteria andWhitePagerIsNotNull() {
            addCriterion("white_pager is not null");
            return (Criteria) this;
        }

        public Criteria andWhitePagerEqualTo(String value) {
            addCriterion("white_pager =", value, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerNotEqualTo(String value) {
            addCriterion("white_pager <>", value, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerGreaterThan(String value) {
            addCriterion("white_pager >", value, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerGreaterThanOrEqualTo(String value) {
            addCriterion("white_pager >=", value, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerLessThan(String value) {
            addCriterion("white_pager <", value, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerLessThanOrEqualTo(String value) {
            addCriterion("white_pager <=", value, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerLike(String value) {
            addCriterion("white_pager like", value, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerNotLike(String value) {
            addCriterion("white_pager not like", value, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerIn(List<String> values) {
            addCriterion("white_pager in", values, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerNotIn(List<String> values) {
            addCriterion("white_pager not in", values, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerBetween(String value1, String value2) {
            addCriterion("white_pager between", value1, value2, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWhitePagerNotBetween(String value1, String value2) {
            addCriterion("white_pager not between", value1, value2, "whitePager");
            return (Criteria) this;
        }

        public Criteria andWebsiteIsNull() {
            addCriterion("website is null");
            return (Criteria) this;
        }

        public Criteria andWebsiteIsNotNull() {
            addCriterion("website is not null");
            return (Criteria) this;
        }

        public Criteria andWebsiteEqualTo(String value) {
            addCriterion("website =", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotEqualTo(String value) {
            addCriterion("website <>", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteGreaterThan(String value) {
            addCriterion("website >", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteGreaterThanOrEqualTo(String value) {
            addCriterion("website >=", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLessThan(String value) {
            addCriterion("website <", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLessThanOrEqualTo(String value) {
            addCriterion("website <=", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLike(String value) {
            addCriterion("website like", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotLike(String value) {
            addCriterion("website not like", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteIn(List<String> values) {
            addCriterion("website in", values, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotIn(List<String> values) {
            addCriterion("website not in", values, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteBetween(String value1, String value2) {
            addCriterion("website between", value1, value2, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotBetween(String value1, String value2) {
            addCriterion("website not between", value1, value2, "website");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeIsNull() {
            addCriterion("turn_volume is null");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeIsNotNull() {
            addCriterion("turn_volume is not null");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeEqualTo(String value) {
            addCriterion("turn_volume =", value, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeNotEqualTo(String value) {
            addCriterion("turn_volume <>", value, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeGreaterThan(String value) {
            addCriterion("turn_volume >", value, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeGreaterThanOrEqualTo(String value) {
            addCriterion("turn_volume >=", value, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeLessThan(String value) {
            addCriterion("turn_volume <", value, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeLessThanOrEqualTo(String value) {
            addCriterion("turn_volume <=", value, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeLike(String value) {
            addCriterion("turn_volume like", value, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeNotLike(String value) {
            addCriterion("turn_volume not like", value, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeIn(List<String> values) {
            addCriterion("turn_volume in", values, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeNotIn(List<String> values) {
            addCriterion("turn_volume not in", values, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeBetween(String value1, String value2) {
            addCriterion("turn_volume between", value1, value2, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTurnVolumeNotBetween(String value1, String value2) {
            addCriterion("turn_volume not between", value1, value2, "turnVolume");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationIsNull() {
            addCriterion("total_circulation is null");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationIsNotNull() {
            addCriterion("total_circulation is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationEqualTo(String value) {
            addCriterion("total_circulation =", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationNotEqualTo(String value) {
            addCriterion("total_circulation <>", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationGreaterThan(String value) {
            addCriterion("total_circulation >", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationGreaterThanOrEqualTo(String value) {
            addCriterion("total_circulation >=", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationLessThan(String value) {
            addCriterion("total_circulation <", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationLessThanOrEqualTo(String value) {
            addCriterion("total_circulation <=", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationLike(String value) {
            addCriterion("total_circulation like", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationNotLike(String value) {
            addCriterion("total_circulation not like", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationIn(List<String> values) {
            addCriterion("total_circulation in", values, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationNotIn(List<String> values) {
            addCriterion("total_circulation not in", values, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationBetween(String value1, String value2) {
            addCriterion("total_circulation between", value1, value2, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationNotBetween(String value1, String value2) {
            addCriterion("total_circulation not between", value1, value2, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andPubTimeIsNull() {
            addCriterion("pub_time is null");
            return (Criteria) this;
        }

        public Criteria andPubTimeIsNotNull() {
            addCriterion("pub_time is not null");
            return (Criteria) this;
        }

        public Criteria andPubTimeEqualTo(String value) {
            addCriterion("pub_time =", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotEqualTo(String value) {
            addCriterion("pub_time <>", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeGreaterThan(String value) {
            addCriterion("pub_time >", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeGreaterThanOrEqualTo(String value) {
            addCriterion("pub_time >=", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLessThan(String value) {
            addCriterion("pub_time <", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLessThanOrEqualTo(String value) {
            addCriterion("pub_time <=", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLike(String value) {
            addCriterion("pub_time like", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotLike(String value) {
            addCriterion("pub_time not like", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeIn(List<String> values) {
            addCriterion("pub_time in", values, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotIn(List<String> values) {
            addCriterion("pub_time not in", values, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeBetween(String value1, String value2) {
            addCriterion("pub_time between", value1, value2, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotBetween(String value1, String value2) {
            addCriterion("pub_time not between", value1, value2, "pubTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}