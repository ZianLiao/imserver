package com.css.im.chat;

import com.css.common.BaseStatus;

/**
 * Create by wx on 2020-09-21
 */
public abstract class  ChatStatus {

    public enum MessageContentType implements BaseStatus {

        /**
         * 纯文本
         */
        Text(0),
        /**
         * 纯文件
         */
        File(1),
        /**文本带文件*/
        Text_File(2),
        /**阅后即焚消息*/
        Read_Burn(3),
        /**焚毁消息*/
        To_Burn(4);

        private Short statusValue;

        MessageContentType(int i) {
            statusValue = Short.valueOf(i + "");
        }

        @Override
        public Short status() {
            return this.statusValue;
        }
    }

    public enum ChatType implements BaseStatus {

        /**
         * 单聊
         */
        Chat(0),
        /**
         * 群聊
         */
        GroupChat(1),
        /**
         * 群操作
         */
        GroupOperate(4),
        /**
         * 广播消息
         */
        SysChat(2);

        private Short statusValue;

        ChatType(int i) {
            statusValue = Short.valueOf(i + "");
        }

        @Override
        public Short status() {
            return this.statusValue;
        }
    }


    public enum RecallStatus implements BaseStatus {

        /**
         * 正常
         */
        Chat(0),
        /**
         * 召回
         */
        Recalled(1);

        private Short statusValue;

        RecallStatus(int i) {
            statusValue = Short.valueOf(i + "");
        }

        @Override
        public Short status() {
            return this.statusValue;
        }
    }

    public enum MessageStatus implements BaseStatus {

        /**
         * 尚未发送
         */
        UnSend(0),
        /**
         * 已发送
         */
        Send(1),
        /**
         * 已读
         * 分为已读未反馈发送人和反馈给发送人
         */
        Read_not_inform(2),
        Read_informed(3),
        /**
         * 拒收
         */
        Reject(3),
        /**
         * 撤回
         */
        Withdraw(4),
        Downloaded(5);

        private Short statusValue;

        MessageStatus(int i) {
            statusValue = Short.valueOf(i + "");
        }

        @Override
        public Short status() {
            return this.statusValue;
        }
    }

    public enum ChatGroupStatus implements BaseStatus {

        /**
         * 正常
         */
        Normal(0),
        /**
         * 已删除
         */
        Send(-1),
        /**
         * 不可发消息
         */
        Disable(1);

        private Short statusValue;

        ChatGroupStatus(int i) {
            statusValue = Short.valueOf(i + "");
        }

        @Override
        public Short status() {
            return this.statusValue;
        }
    }

    public enum FileStatus implements BaseStatus {

        /**
         * 正常
         */
        Normal(0),
        /**
         * 删除
         */
        Delete(-1),
        /**
         * 接收
         **/
        Accept(1),
        /**
         * 拒收
         **/
        Reject(2),
        /**
         * 撤回
         */
        Withdraw(3);

        private Short statusValue;

        FileStatus(int i) {
            statusValue = Short.valueOf(i + "");
        }

        @Override
        public Short status() {
            return this.statusValue;
        }
    }

}
