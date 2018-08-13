package com.crawler.dao.model.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JumpAroundExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JumpAroundExample() {
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

        public Criteria andAroundNameIsNull() {
            addCriterion("around_name is null");
            return (Criteria) this;
        }

        public Criteria andAroundNameIsNotNull() {
            addCriterion("around_name is not null");
            return (Criteria) this;
        }

        public Criteria andAroundNameEqualTo(String value) {
            addCriterion("around_name =", value, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameNotEqualTo(String value) {
            addCriterion("around_name <>", value, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameGreaterThan(String value) {
            addCriterion("around_name >", value, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameGreaterThanOrEqualTo(String value) {
            addCriterion("around_name >=", value, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameLessThan(String value) {
            addCriterion("around_name <", value, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameLessThanOrEqualTo(String value) {
            addCriterion("around_name <=", value, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameLike(String value) {
            addCriterion("around_name like", value, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameNotLike(String value) {
            addCriterion("around_name not like", value, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameIn(List<String> values) {
            addCriterion("around_name in", values, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameNotIn(List<String> values) {
            addCriterion("around_name not in", values, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameBetween(String value1, String value2) {
            addCriterion("around_name between", value1, value2, "aroundName");
            return (Criteria) this;
        }

        public Criteria andAroundNameNotBetween(String value1, String value2) {
            addCriterion("around_name not between", value1, value2, "aroundName");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumIsNull() {
            addCriterion("min_participants_num is null");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumIsNotNull() {
            addCriterion("min_participants_num is not null");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumEqualTo(Integer value) {
            addCriterion("min_participants_num =", value, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumNotEqualTo(Integer value) {
            addCriterion("min_participants_num <>", value, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumGreaterThan(Integer value) {
            addCriterion("min_participants_num >", value, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_participants_num >=", value, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumLessThan(Integer value) {
            addCriterion("min_participants_num <", value, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumLessThanOrEqualTo(Integer value) {
            addCriterion("min_participants_num <=", value, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumIn(List<Integer> values) {
            addCriterion("min_participants_num in", values, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumNotIn(List<Integer> values) {
            addCriterion("min_participants_num not in", values, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumBetween(Integer value1, Integer value2) {
            addCriterion("min_participants_num between", value1, value2, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andMinParticipantsNumNotBetween(Integer value1, Integer value2) {
            addCriterion("min_participants_num not between", value1, value2, "minParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumIsNull() {
            addCriterion("current_participants_num is null");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumIsNotNull() {
            addCriterion("current_participants_num is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumEqualTo(Integer value) {
            addCriterion("current_participants_num =", value, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumNotEqualTo(Integer value) {
            addCriterion("current_participants_num <>", value, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumGreaterThan(Integer value) {
            addCriterion("current_participants_num >", value, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_participants_num >=", value, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumLessThan(Integer value) {
            addCriterion("current_participants_num <", value, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumLessThanOrEqualTo(Integer value) {
            addCriterion("current_participants_num <=", value, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumIn(List<Integer> values) {
            addCriterion("current_participants_num in", values, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumNotIn(List<Integer> values) {
            addCriterion("current_participants_num not in", values, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumBetween(Integer value1, Integer value2) {
            addCriterion("current_participants_num between", value1, value2, "currentParticipantsNum");
            return (Criteria) this;
        }

        public Criteria andCurrentParticipantsNumNotBetween(Integer value1, Integer value2) {
            addCriterion("current_participants_num not between", value1, value2, "currentParticipantsNum");
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

        public Criteria andTotalAmoutIsNull() {
            addCriterion("total_amout is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutIsNotNull() {
            addCriterion("total_amout is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutEqualTo(BigDecimal value) {
            addCriterion("total_amout =", value, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutNotEqualTo(BigDecimal value) {
            addCriterion("total_amout <>", value, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutGreaterThan(BigDecimal value) {
            addCriterion("total_amout >", value, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_amout >=", value, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutLessThan(BigDecimal value) {
            addCriterion("total_amout <", value, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_amout <=", value, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutIn(List<BigDecimal> values) {
            addCriterion("total_amout in", values, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutNotIn(List<BigDecimal> values) {
            addCriterion("total_amout not in", values, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_amout between", value1, value2, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andTotalAmoutNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_amout not between", value1, value2, "totalAmout");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIsNull() {
            addCriterion("creater_id is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIsNotNull() {
            addCriterion("creater_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterIdEqualTo(String value) {
            addCriterion("creater_id =", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotEqualTo(String value) {
            addCriterion("creater_id <>", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdGreaterThan(String value) {
            addCriterion("creater_id >", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdGreaterThanOrEqualTo(String value) {
            addCriterion("creater_id >=", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLessThan(String value) {
            addCriterion("creater_id <", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLessThanOrEqualTo(String value) {
            addCriterion("creater_id <=", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLike(String value) {
            addCriterion("creater_id like", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotLike(String value) {
            addCriterion("creater_id not like", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIn(List<String> values) {
            addCriterion("creater_id in", values, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotIn(List<String> values) {
            addCriterion("creater_id not in", values, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdBetween(String value1, String value2) {
            addCriterion("creater_id between", value1, value2, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotBetween(String value1, String value2) {
            addCriterion("creater_id not between", value1, value2, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterNameIsNull() {
            addCriterion("creater_name is null");
            return (Criteria) this;
        }

        public Criteria andCreaterNameIsNotNull() {
            addCriterion("creater_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterNameEqualTo(String value) {
            addCriterion("creater_name =", value, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameNotEqualTo(String value) {
            addCriterion("creater_name <>", value, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameGreaterThan(String value) {
            addCriterion("creater_name >", value, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameGreaterThanOrEqualTo(String value) {
            addCriterion("creater_name >=", value, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameLessThan(String value) {
            addCriterion("creater_name <", value, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameLessThanOrEqualTo(String value) {
            addCriterion("creater_name <=", value, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameLike(String value) {
            addCriterion("creater_name like", value, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameNotLike(String value) {
            addCriterion("creater_name not like", value, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameIn(List<String> values) {
            addCriterion("creater_name in", values, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameNotIn(List<String> values) {
            addCriterion("creater_name not in", values, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameBetween(String value1, String value2) {
            addCriterion("creater_name between", value1, value2, "createrName");
            return (Criteria) this;
        }

        public Criteria andCreaterNameNotBetween(String value1, String value2) {
            addCriterion("creater_name not between", value1, value2, "createrName");
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