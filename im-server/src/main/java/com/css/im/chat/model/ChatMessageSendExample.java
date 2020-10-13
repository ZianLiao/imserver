package com.css.im.chat.model;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageSendExample {
    protected String orderByClause;


    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChatMessageSendExample() {
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

        public Criteria andMsgIdIsNull() {
            addCriterion("msg_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgIdIsNotNull() {
            addCriterion("msg_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgIdEqualTo(String value) {
            addCriterion("msg_id =", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotEqualTo(String value) {
            addCriterion("msg_id <>", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThan(String value) {
            addCriterion("msg_id >", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_id >=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThan(String value) {
            addCriterion("msg_id <", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThanOrEqualTo(String value) {
            addCriterion("msg_id <=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLike(String value) {
            addCriterion("msg_id like", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotLike(String value) {
            addCriterion("msg_id not like", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdIn(List<String> values) {
            addCriterion("msg_id in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotIn(List<String> values) {
            addCriterion("msg_id not in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdBetween(String value1, String value2) {
            addCriterion("msg_id between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotBetween(String value1, String value2) {
            addCriterion("msg_id not between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andFromUidIsNull() {
            addCriterion("from_uid is null");
            return (Criteria) this;
        }

        public Criteria andFromUidIsNotNull() {
            addCriterion("from_uid is not null");
            return (Criteria) this;
        }

        public Criteria andFromUidEqualTo(String value) {
            addCriterion("from_uid =", value, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidNotEqualTo(String value) {
            addCriterion("from_uid <>", value, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidGreaterThan(String value) {
            addCriterion("from_uid >", value, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidGreaterThanOrEqualTo(String value) {
            addCriterion("from_uid >=", value, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidLessThan(String value) {
            addCriterion("from_uid <", value, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidLessThanOrEqualTo(String value) {
            addCriterion("from_uid <=", value, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidLike(String value) {
            addCriterion("from_uid like", value, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidNotLike(String value) {
            addCriterion("from_uid not like", value, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidIn(List<String> values) {
            addCriterion("from_uid in", values, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidNotIn(List<String> values) {
            addCriterion("from_uid not in", values, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidBetween(String value1, String value2) {
            addCriterion("from_uid between", value1, value2, "fromUid");
            return (Criteria) this;
        }

        public Criteria andFromUidNotBetween(String value1, String value2) {
            addCriterion("from_uid not between", value1, value2, "fromUid");
            return (Criteria) this;
        }

        public Criteria andToUidIsNull() {
            addCriterion("to_uid is null");
            return (Criteria) this;
        }

        public Criteria andToUidIsNotNull() {
            addCriterion("to_uid is not null");
            return (Criteria) this;
        }

        public Criteria andToUidEqualTo(String value) {
            addCriterion("to_uid =", value, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidNotEqualTo(String value) {
            addCriterion("to_uid <>", value, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidGreaterThan(String value) {
            addCriterion("to_uid >", value, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidGreaterThanOrEqualTo(String value) {
            addCriterion("to_uid >=", value, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidLessThan(String value) {
            addCriterion("to_uid <", value, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidLessThanOrEqualTo(String value) {
            addCriterion("to_uid <=", value, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidLike(String value) {
            addCriterion("to_uid like", value, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidNotLike(String value) {
            addCriterion("to_uid not like", value, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidIn(List<String> values) {
            addCriterion("to_uid in", values, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidNotIn(List<String> values) {
            addCriterion("to_uid not in", values, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidBetween(String value1, String value2) {
            addCriterion("to_uid between", value1, value2, "toUid");
            return (Criteria) this;
        }

        public Criteria andToUidNotBetween(String value1, String value2) {
            addCriterion("to_uid not between", value1, value2, "toUid");
            return (Criteria) this;
        }

        public Criteria andMsgTimeIsNull() {
            addCriterion("msg_time is null");
            return (Criteria) this;
        }

        public Criteria andMsgTimeIsNotNull() {
            addCriterion("msg_time is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTimeEqualTo(String value) {
            addCriterion("msg_time =", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeNotEqualTo(String value) {
            addCriterion("msg_time <>", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeGreaterThan(String value) {
            addCriterion("msg_time >", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeGreaterThanOrEqualTo(String value) {
            addCriterion("msg_time >=", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeLessThan(String value) {
            addCriterion("msg_time <", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeLessThanOrEqualTo(String value) {
            addCriterion("msg_time <=", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeLike(String value) {
            addCriterion("msg_time like", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeNotLike(String value) {
            addCriterion("msg_time not like", value, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeIn(List<String> values) {
            addCriterion("msg_time in", values, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeNotIn(List<String> values) {
            addCriterion("msg_time not in", values, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeBetween(String value1, String value2) {
            addCriterion("msg_time between", value1, value2, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgTimeNotBetween(String value1, String value2) {
            addCriterion("msg_time not between", value1, value2, "msgTime");
            return (Criteria) this;
        }

        public Criteria andMsgSeqIsNull() {
            addCriterion("msg_seq is null");
            return (Criteria) this;
        }

        public Criteria andMsgSeqIsNotNull() {
            addCriterion("msg_seq is not null");
            return (Criteria) this;
        }

        public Criteria andMsgSeqEqualTo(String value) {
            addCriterion("msg_seq =", value, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqNotEqualTo(String value) {
            addCriterion("msg_seq <>", value, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqGreaterThan(String value) {
            addCriterion("msg_seq >", value, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqGreaterThanOrEqualTo(String value) {
            addCriterion("msg_seq >=", value, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqLessThan(String value) {
            addCriterion("msg_seq <", value, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqLessThanOrEqualTo(String value) {
            addCriterion("msg_seq <=", value, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqLike(String value) {
            addCriterion("msg_seq like", value, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqNotLike(String value) {
            addCriterion("msg_seq not like", value, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqIn(List<String> values) {
            addCriterion("msg_seq in", values, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqNotIn(List<String> values) {
            addCriterion("msg_seq not in", values, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqBetween(String value1, String value2) {
            addCriterion("msg_seq between", value1, value2, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andMsgSeqNotBetween(String value1, String value2) {
            addCriterion("msg_seq not between", value1, value2, "msgSeq");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNull() {
            addCriterion("content_type is null");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNotNull() {
            addCriterion("content_type is not null");
            return (Criteria) this;
        }

        public Criteria andContentTypeEqualTo(Short value) {
            addCriterion("content_type =", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotEqualTo(Short value) {
            addCriterion("content_type <>", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThan(Short value) {
            addCriterion("content_type >", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("content_type >=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThan(Short value) {
            addCriterion("content_type <", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThanOrEqualTo(Short value) {
            addCriterion("content_type <=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeIn(List<Short> values) {
            addCriterion("content_type in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotIn(List<Short> values) {
            addCriterion("content_type not in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeBetween(Short value1, Short value2) {
            addCriterion("content_type between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotBetween(Short value1, Short value2) {
            addCriterion("content_type not between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(String value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(String value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(String value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(String value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(String value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(String value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLike(String value) {
            addCriterion("file_id like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotLike(String value) {
            addCriterion("file_id not like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<String> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<String> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(String value1, String value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(String value1, String value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
            return (Criteria) this;
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

        public Criteria andRecallIsNull() {
            addCriterion("recall is null");
            return (Criteria) this;
        }

        public Criteria andRecallIsNotNull() {
            addCriterion("recall is not null");
            return (Criteria) this;
        }

        public Criteria andRecallEqualTo(Short value) {
            addCriterion("recall =", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallNotEqualTo(Short value) {
            addCriterion("recall <>", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallGreaterThan(Short value) {
            addCriterion("recall >", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallGreaterThanOrEqualTo(Short value) {
            addCriterion("recall >=", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallLessThan(Short value) {
            addCriterion("recall <", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallLessThanOrEqualTo(Short value) {
            addCriterion("recall <=", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallIn(List<Short> values) {
            addCriterion("recall in", values, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallNotIn(List<Short> values) {
            addCriterion("recall not in", values, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallBetween(Short value1, Short value2) {
            addCriterion("recall between", value1, value2, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallNotBetween(Short value1, Short value2) {
            addCriterion("recall not between", value1, value2, "recall");
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

        public Criteria andChatTypeEqualTo(Integer value) {
            addCriterion("chat_type =", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotEqualTo(Integer value) {
            addCriterion("chat_type <>", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThan(Integer value) {
            addCriterion("chat_type >", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("chat_type >=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThan(Integer value) {
            addCriterion("chat_type <", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThanOrEqualTo(Integer value) {
            addCriterion("chat_type <=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeIn(List<Integer> values) {
            addCriterion("chat_type in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotIn(List<Integer> values) {
            addCriterion("chat_type not in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeBetween(Integer value1, Integer value2) {
            addCriterion("chat_type between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("chat_type not between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andPlainTextIsNull() {
            addCriterion("plain_text is null");
            return (Criteria) this;
        }

        public Criteria andPlainTextIsNotNull() {
            addCriterion("plain_text is not null");
            return (Criteria) this;
        }

        public Criteria andPlainTextEqualTo(String value) {
            addCriterion("plain_text =", value, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextNotEqualTo(String value) {
            addCriterion("plain_text <>", value, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextGreaterThan(String value) {
            addCriterion("plain_text >", value, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextGreaterThanOrEqualTo(String value) {
            addCriterion("plain_text >=", value, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextLessThan(String value) {
            addCriterion("plain_text <", value, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextLessThanOrEqualTo(String value) {
            addCriterion("plain_text <=", value, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextLike(String value) {
            addCriterion("plain_text like", value, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextNotLike(String value) {
            addCriterion("plain_text not like", value, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextIn(List<String> values) {
            addCriterion("plain_text in", values, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextNotIn(List<String> values) {
            addCriterion("plain_text not in", values, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextBetween(String value1, String value2) {
            addCriterion("plain_text between", value1, value2, "plainText");
            return (Criteria) this;
        }

        public Criteria andPlainTextNotBetween(String value1, String value2) {
            addCriterion("plain_text not between", value1, value2, "plainText");
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