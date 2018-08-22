package com.crawler.dao.model.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JumpParticipantExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JumpParticipantExample() {
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

        public Criteria andParticipantIdIsNull() {
            addCriterion("participant_id is null");
            return (Criteria) this;
        }

        public Criteria andParticipantIdIsNotNull() {
            addCriterion("participant_id is not null");
            return (Criteria) this;
        }

        public Criteria andParticipantIdEqualTo(Long value) {
            addCriterion("participant_id =", value, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdNotEqualTo(Long value) {
            addCriterion("participant_id <>", value, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdGreaterThan(Long value) {
            addCriterion("participant_id >", value, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("participant_id >=", value, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdLessThan(Long value) {
            addCriterion("participant_id <", value, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdLessThanOrEqualTo(Long value) {
            addCriterion("participant_id <=", value, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdIn(List<Long> values) {
            addCriterion("participant_id in", values, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdNotIn(List<Long> values) {
            addCriterion("participant_id not in", values, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdBetween(Long value1, Long value2) {
            addCriterion("participant_id between", value1, value2, "participantId");
            return (Criteria) this;
        }

        public Criteria andParticipantIdNotBetween(Long value1, Long value2) {
            addCriterion("participant_id not between", value1, value2, "participantId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andParticipantNameIsNull() {
            addCriterion("participant_name is null");
            return (Criteria) this;
        }

        public Criteria andParticipantNameIsNotNull() {
            addCriterion("participant_name is not null");
            return (Criteria) this;
        }

        public Criteria andParticipantNameEqualTo(String value) {
            addCriterion("participant_name =", value, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameNotEqualTo(String value) {
            addCriterion("participant_name <>", value, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameGreaterThan(String value) {
            addCriterion("participant_name >", value, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameGreaterThanOrEqualTo(String value) {
            addCriterion("participant_name >=", value, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameLessThan(String value) {
            addCriterion("participant_name <", value, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameLessThanOrEqualTo(String value) {
            addCriterion("participant_name <=", value, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameLike(String value) {
            addCriterion("participant_name like", value, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameNotLike(String value) {
            addCriterion("participant_name not like", value, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameIn(List<String> values) {
            addCriterion("participant_name in", values, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameNotIn(List<String> values) {
            addCriterion("participant_name not in", values, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameBetween(String value1, String value2) {
            addCriterion("participant_name between", value1, value2, "participantName");
            return (Criteria) this;
        }

        public Criteria andParticipantNameNotBetween(String value1, String value2) {
            addCriterion("participant_name not between", value1, value2, "participantName");
            return (Criteria) this;
        }

        public Criteria andAroundIdIsNull() {
            addCriterion("around_id is null");
            return (Criteria) this;
        }

        public Criteria andAroundIdIsNotNull() {
            addCriterion("around_id is not null");
            return (Criteria) this;
        }

        public Criteria andAroundIdEqualTo(String value) {
            addCriterion("around_id =", value, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdNotEqualTo(String value) {
            addCriterion("around_id <>", value, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdGreaterThan(String value) {
            addCriterion("around_id >", value, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdGreaterThanOrEqualTo(String value) {
            addCriterion("around_id >=", value, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdLessThan(String value) {
            addCriterion("around_id <", value, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdLessThanOrEqualTo(String value) {
            addCriterion("around_id <=", value, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdLike(String value) {
            addCriterion("around_id like", value, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdNotLike(String value) {
            addCriterion("around_id not like", value, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdIn(List<String> values) {
            addCriterion("around_id in", values, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdNotIn(List<String> values) {
            addCriterion("around_id not in", values, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdBetween(String value1, String value2) {
            addCriterion("around_id between", value1, value2, "aroundId");
            return (Criteria) this;
        }

        public Criteria andAroundIdNotBetween(String value1, String value2) {
            addCriterion("around_id not between", value1, value2, "aroundId");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNull() {
            addCriterion("money is null");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNotNull() {
            addCriterion("money is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyEqualTo(BigDecimal value) {
            addCriterion("money =", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotEqualTo(BigDecimal value) {
            addCriterion("money <>", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThan(BigDecimal value) {
            addCriterion("money >", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("money >=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThan(BigDecimal value) {
            addCriterion("money <", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("money <=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyIn(List<BigDecimal> values) {
            addCriterion("money in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotIn(List<BigDecimal> values) {
            addCriterion("money not in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("money between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("money not between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andPointIsNull() {
            addCriterion("point is null");
            return (Criteria) this;
        }

        public Criteria andPointIsNotNull() {
            addCriterion("point is not null");
            return (Criteria) this;
        }

        public Criteria andPointEqualTo(Integer value) {
            addCriterion("point =", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotEqualTo(Integer value) {
            addCriterion("point <>", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointGreaterThan(Integer value) {
            addCriterion("point >", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointGreaterThanOrEqualTo(Integer value) {
            addCriterion("point >=", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointLessThan(Integer value) {
            addCriterion("point <", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointLessThanOrEqualTo(Integer value) {
            addCriterion("point <=", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointIn(List<Integer> values) {
            addCriterion("point in", values, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotIn(List<Integer> values) {
            addCriterion("point not in", values, "point");
            return (Criteria) this;
        }

        public Criteria andPointBetween(Integer value1, Integer value2) {
            addCriterion("point between", value1, value2, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotBetween(Integer value1, Integer value2) {
            addCriterion("point not between", value1, value2, "point");
            return (Criteria) this;
        }

        public Criteria andRankNumIsNull() {
            addCriterion("rank_num is null");
            return (Criteria) this;
        }

        public Criteria andRankNumIsNotNull() {
            addCriterion("rank_num is not null");
            return (Criteria) this;
        }

        public Criteria andRankNumEqualTo(Integer value) {
            addCriterion("rank_num =", value, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumNotEqualTo(Integer value) {
            addCriterion("rank_num <>", value, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumGreaterThan(Integer value) {
            addCriterion("rank_num >", value, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("rank_num >=", value, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumLessThan(Integer value) {
            addCriterion("rank_num <", value, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumLessThanOrEqualTo(Integer value) {
            addCriterion("rank_num <=", value, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumIn(List<Integer> values) {
            addCriterion("rank_num in", values, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumNotIn(List<Integer> values) {
            addCriterion("rank_num not in", values, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumBetween(Integer value1, Integer value2) {
            addCriterion("rank_num between", value1, value2, "rankNum");
            return (Criteria) this;
        }

        public Criteria andRankNumNotBetween(Integer value1, Integer value2) {
            addCriterion("rank_num not between", value1, value2, "rankNum");
            return (Criteria) this;
        }

        public Criteria andIsWinIsNull() {
            addCriterion("is_win is null");
            return (Criteria) this;
        }

        public Criteria andIsWinIsNotNull() {
            addCriterion("is_win is not null");
            return (Criteria) this;
        }

        public Criteria andIsWinEqualTo(Boolean value) {
            addCriterion("is_win =", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinNotEqualTo(Boolean value) {
            addCriterion("is_win <>", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinGreaterThan(Boolean value) {
            addCriterion("is_win >", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_win >=", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinLessThan(Boolean value) {
            addCriterion("is_win <", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinLessThanOrEqualTo(Boolean value) {
            addCriterion("is_win <=", value, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinIn(List<Boolean> values) {
            addCriterion("is_win in", values, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinNotIn(List<Boolean> values) {
            addCriterion("is_win not in", values, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinBetween(Boolean value1, Boolean value2) {
            addCriterion("is_win between", value1, value2, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsWinNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_win not between", value1, value2, "isWin");
            return (Criteria) this;
        }

        public Criteria andIsOverIsNull() {
            addCriterion("is_over is null");
            return (Criteria) this;
        }

        public Criteria andIsOverIsNotNull() {
            addCriterion("is_over is not null");
            return (Criteria) this;
        }

        public Criteria andIsOverEqualTo(Boolean value) {
            addCriterion("is_over =", value, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverNotEqualTo(Boolean value) {
            addCriterion("is_over <>", value, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverGreaterThan(Boolean value) {
            addCriterion("is_over >", value, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_over >=", value, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverLessThan(Boolean value) {
            addCriterion("is_over <", value, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverLessThanOrEqualTo(Boolean value) {
            addCriterion("is_over <=", value, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverIn(List<Boolean> values) {
            addCriterion("is_over in", values, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverNotIn(List<Boolean> values) {
            addCriterion("is_over not in", values, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverBetween(Boolean value1, Boolean value2) {
            addCriterion("is_over between", value1, value2, "isOver");
            return (Criteria) this;
        }

        public Criteria andIsOverNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_over not between", value1, value2, "isOver");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeIsNull() {
            addCriterion("participant_time is null");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeIsNotNull() {
            addCriterion("participant_time is not null");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeEqualTo(Date value) {
            addCriterion("participant_time =", value, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeNotEqualTo(Date value) {
            addCriterion("participant_time <>", value, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeGreaterThan(Date value) {
            addCriterion("participant_time >", value, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("participant_time >=", value, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeLessThan(Date value) {
            addCriterion("participant_time <", value, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeLessThanOrEqualTo(Date value) {
            addCriterion("participant_time <=", value, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeIn(List<Date> values) {
            addCriterion("participant_time in", values, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeNotIn(List<Date> values) {
            addCriterion("participant_time not in", values, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeBetween(Date value1, Date value2) {
            addCriterion("participant_time between", value1, value2, "participantTime");
            return (Criteria) this;
        }

        public Criteria andParticipantTimeNotBetween(Date value1, Date value2) {
            addCriterion("participant_time not between", value1, value2, "participantTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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