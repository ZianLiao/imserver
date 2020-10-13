package com.css.im.websocket.model;

import java.util.ArrayList;
import java.util.List;

public class WsServerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WsServerExample() {
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

        public Criteria andServerIdIsNull() {
            addCriterion("server_id is null");
            return (Criteria) this;
        }

        public Criteria andServerIdIsNotNull() {
            addCriterion("server_id is not null");
            return (Criteria) this;
        }

        public Criteria andServerIdEqualTo(String value) {
            addCriterion("server_id =", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotEqualTo(String value) {
            addCriterion("server_id <>", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThan(String value) {
            addCriterion("server_id >", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThanOrEqualTo(String value) {
            addCriterion("server_id >=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThan(String value) {
            addCriterion("server_id <", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThanOrEqualTo(String value) {
            addCriterion("server_id <=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLike(String value) {
            addCriterion("server_id like", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotLike(String value) {
            addCriterion("server_id not like", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdIn(List<String> values) {
            addCriterion("server_id in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotIn(List<String> values) {
            addCriterion("server_id not in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdBetween(String value1, String value2) {
            addCriterion("server_id between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotBetween(String value1, String value2) {
            addCriterion("server_id not between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerKeyIsNull() {
            addCriterion("server_key is null");
            return (Criteria) this;
        }

        public Criteria andServerKeyIsNotNull() {
            addCriterion("server_key is not null");
            return (Criteria) this;
        }

        public Criteria andServerKeyEqualTo(String value) {
            addCriterion("server_key =", value, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyNotEqualTo(String value) {
            addCriterion("server_key <>", value, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyGreaterThan(String value) {
            addCriterion("server_key >", value, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyGreaterThanOrEqualTo(String value) {
            addCriterion("server_key >=", value, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyLessThan(String value) {
            addCriterion("server_key <", value, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyLessThanOrEqualTo(String value) {
            addCriterion("server_key <=", value, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyLike(String value) {
            addCriterion("server_key like", value, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyNotLike(String value) {
            addCriterion("server_key not like", value, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyIn(List<String> values) {
            addCriterion("server_key in", values, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyNotIn(List<String> values) {
            addCriterion("server_key not in", values, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyBetween(String value1, String value2) {
            addCriterion("server_key between", value1, value2, "serverKey");
            return (Criteria) this;
        }

        public Criteria andServerKeyNotBetween(String value1, String value2) {
            addCriterion("server_key not between", value1, value2, "serverKey");
            return (Criteria) this;
        }

        public Criteria andAppVersionsIsNull() {
            addCriterion("app_versions is null");
            return (Criteria) this;
        }

        public Criteria andAppVersionsIsNotNull() {
            addCriterion("app_versions is not null");
            return (Criteria) this;
        }

        public Criteria andAppVersionsEqualTo(String value) {
            addCriterion("app_versions =", value, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsNotEqualTo(String value) {
            addCriterion("app_versions <>", value, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsGreaterThan(String value) {
            addCriterion("app_versions >", value, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsGreaterThanOrEqualTo(String value) {
            addCriterion("app_versions >=", value, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsLessThan(String value) {
            addCriterion("app_versions <", value, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsLessThanOrEqualTo(String value) {
            addCriterion("app_versions <=", value, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsLike(String value) {
            addCriterion("app_versions like", value, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsNotLike(String value) {
            addCriterion("app_versions not like", value, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsIn(List<String> values) {
            addCriterion("app_versions in", values, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsNotIn(List<String> values) {
            addCriterion("app_versions not in", values, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsBetween(String value1, String value2) {
            addCriterion("app_versions between", value1, value2, "appVersions");
            return (Criteria) this;
        }

        public Criteria andAppVersionsNotBetween(String value1, String value2) {
            addCriterion("app_versions not between", value1, value2, "appVersions");
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

        public Criteria andWsVersionIsNull() {
            addCriterion("ws_version is null");
            return (Criteria) this;
        }

        public Criteria andWsVersionIsNotNull() {
            addCriterion("ws_version is not null");
            return (Criteria) this;
        }

        public Criteria andWsVersionEqualTo(String value) {
            addCriterion("ws_version =", value, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionNotEqualTo(String value) {
            addCriterion("ws_version <>", value, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionGreaterThan(String value) {
            addCriterion("ws_version >", value, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionGreaterThanOrEqualTo(String value) {
            addCriterion("ws_version >=", value, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionLessThan(String value) {
            addCriterion("ws_version <", value, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionLessThanOrEqualTo(String value) {
            addCriterion("ws_version <=", value, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionLike(String value) {
            addCriterion("ws_version like", value, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionNotLike(String value) {
            addCriterion("ws_version not like", value, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionIn(List<String> values) {
            addCriterion("ws_version in", values, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionNotIn(List<String> values) {
            addCriterion("ws_version not in", values, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionBetween(String value1, String value2) {
            addCriterion("ws_version between", value1, value2, "wsVersion");
            return (Criteria) this;
        }

        public Criteria andWsVersionNotBetween(String value1, String value2) {
            addCriterion("ws_version not between", value1, value2, "wsVersion");
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