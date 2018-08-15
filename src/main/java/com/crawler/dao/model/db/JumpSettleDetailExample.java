package com.crawler.dao.model.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JumpSettleDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JumpSettleDetailExample() {
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

        public Criteria andJumpSettleDetailIdIsNull() {
            addCriterion("jump_settle_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdIsNotNull() {
            addCriterion("jump_settle_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdEqualTo(Long value) {
            addCriterion("jump_settle_detail_id =", value, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdNotEqualTo(Long value) {
            addCriterion("jump_settle_detail_id <>", value, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdGreaterThan(Long value) {
            addCriterion("jump_settle_detail_id >", value, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("jump_settle_detail_id >=", value, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdLessThan(Long value) {
            addCriterion("jump_settle_detail_id <", value, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdLessThanOrEqualTo(Long value) {
            addCriterion("jump_settle_detail_id <=", value, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdIn(List<Long> values) {
            addCriterion("jump_settle_detail_id in", values, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdNotIn(List<Long> values) {
            addCriterion("jump_settle_detail_id not in", values, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdBetween(Long value1, Long value2) {
            addCriterion("jump_settle_detail_id between", value1, value2, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andJumpSettleDetailIdNotBetween(Long value1, Long value2) {
            addCriterion("jump_settle_detail_id not between", value1, value2, "jumpSettleDetailId");
            return (Criteria) this;
        }

        public Criteria andSettleIdIsNull() {
            addCriterion("settle_id is null");
            return (Criteria) this;
        }

        public Criteria andSettleIdIsNotNull() {
            addCriterion("settle_id is not null");
            return (Criteria) this;
        }

        public Criteria andSettleIdEqualTo(Long value) {
            addCriterion("settle_id =", value, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdNotEqualTo(Long value) {
            addCriterion("settle_id <>", value, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdGreaterThan(Long value) {
            addCriterion("settle_id >", value, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("settle_id >=", value, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdLessThan(Long value) {
            addCriterion("settle_id <", value, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdLessThanOrEqualTo(Long value) {
            addCriterion("settle_id <=", value, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdIn(List<Long> values) {
            addCriterion("settle_id in", values, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdNotIn(List<Long> values) {
            addCriterion("settle_id not in", values, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdBetween(Long value1, Long value2) {
            addCriterion("settle_id between", value1, value2, "settleId");
            return (Criteria) this;
        }

        public Criteria andSettleIdNotBetween(Long value1, Long value2) {
            addCriterion("settle_id not between", value1, value2, "settleId");
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

        public Criteria andPrizeIsNull() {
            addCriterion("prize is null");
            return (Criteria) this;
        }

        public Criteria andPrizeIsNotNull() {
            addCriterion("prize is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeEqualTo(BigDecimal value) {
            addCriterion("prize =", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotEqualTo(BigDecimal value) {
            addCriterion("prize <>", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThan(BigDecimal value) {
            addCriterion("prize >", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prize >=", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThan(BigDecimal value) {
            addCriterion("prize <", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prize <=", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeIn(List<BigDecimal> values) {
            addCriterion("prize in", values, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotIn(List<BigDecimal> values) {
            addCriterion("prize not in", values, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize between", value1, value2, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize not between", value1, value2, "prize");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedIsNull() {
            addCriterion("has_settle_finished is null");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedIsNotNull() {
            addCriterion("has_settle_finished is not null");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedEqualTo(Boolean value) {
            addCriterion("has_settle_finished =", value, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedNotEqualTo(Boolean value) {
            addCriterion("has_settle_finished <>", value, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedGreaterThan(Boolean value) {
            addCriterion("has_settle_finished >", value, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_settle_finished >=", value, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedLessThan(Boolean value) {
            addCriterion("has_settle_finished <", value, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedLessThanOrEqualTo(Boolean value) {
            addCriterion("has_settle_finished <=", value, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedIn(List<Boolean> values) {
            addCriterion("has_settle_finished in", values, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedNotIn(List<Boolean> values) {
            addCriterion("has_settle_finished not in", values, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedBetween(Boolean value1, Boolean value2) {
            addCriterion("has_settle_finished between", value1, value2, "hasSettleFinished");
            return (Criteria) this;
        }

        public Criteria andHasSettleFinishedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_settle_finished not between", value1, value2, "hasSettleFinished");
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