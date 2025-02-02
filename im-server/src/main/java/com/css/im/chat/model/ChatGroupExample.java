package com.css.im.chat.model;

import java.util.ArrayList;
import java.util.List;

public class ChatGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChatGroupExample() {
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

        public Criteria andChatGroupIdIsNull() {
            addCriterion("chat_group_id is null");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdIsNotNull() {
            addCriterion("chat_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdEqualTo(String value) {
            addCriterion("chat_group_id =", value, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdNotEqualTo(String value) {
            addCriterion("chat_group_id <>", value, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdGreaterThan(String value) {
            addCriterion("chat_group_id >", value, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("chat_group_id >=", value, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdLessThan(String value) {
            addCriterion("chat_group_id <", value, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdLessThanOrEqualTo(String value) {
            addCriterion("chat_group_id <=", value, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdLike(String value) {
            addCriterion("chat_group_id like", value, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdNotLike(String value) {
            addCriterion("chat_group_id not like", value, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdIn(List<String> values) {
            addCriterion("chat_group_id in", values, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdNotIn(List<String> values) {
            addCriterion("chat_group_id not in", values, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdBetween(String value1, String value2) {
            addCriterion("chat_group_id between", value1, value2, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupIdNotBetween(String value1, String value2) {
            addCriterion("chat_group_id not between", value1, value2, "chatGroupId");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameIsNull() {
            addCriterion("chat_group_name is null");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameIsNotNull() {
            addCriterion("chat_group_name is not null");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameEqualTo(String value) {
            addCriterion("chat_group_name =", value, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameNotEqualTo(String value) {
            addCriterion("chat_group_name <>", value, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameGreaterThan(String value) {
            addCriterion("chat_group_name >", value, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("chat_group_name >=", value, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameLessThan(String value) {
            addCriterion("chat_group_name <", value, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameLessThanOrEqualTo(String value) {
            addCriterion("chat_group_name <=", value, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameLike(String value) {
            addCriterion("chat_group_name like", value, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameNotLike(String value) {
            addCriterion("chat_group_name not like", value, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameIn(List<String> values) {
            addCriterion("chat_group_name in", values, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameNotIn(List<String> values) {
            addCriterion("chat_group_name not in", values, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameBetween(String value1, String value2) {
            addCriterion("chat_group_name between", value1, value2, "chatGroupName");
            return (Criteria) this;
        }

        public Criteria andChatGroupNameNotBetween(String value1, String value2) {
            addCriterion("chat_group_name not between", value1, value2, "chatGroupName");
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

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNull() {
            addCriterion("chat_type is null");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNotNull() {
            addCriterion("chat_type is not null");
            return (Criteria) this;
        }

        public Criteria andChatTypeEqualTo(Short value) {
            addCriterion("chat_type =", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotEqualTo(Short value) {
            addCriterion("chat_type <>", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThan(Short value) {
            addCriterion("chat_type >", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("chat_type >=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThan(Short value) {
            addCriterion("chat_type <", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThanOrEqualTo(Short value) {
            addCriterion("chat_type <=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeIn(List<Short> values) {
            addCriterion("chat_type in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotIn(List<Short> values) {
            addCriterion("chat_type not in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeBetween(Short value1, Short value2) {
            addCriterion("chat_type between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotBetween(Short value1, Short value2) {
            addCriterion("chat_type not between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("create_user_id like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("create_user_id not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
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

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
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