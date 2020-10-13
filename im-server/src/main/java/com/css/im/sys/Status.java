package com.css.im.sys;

import com.css.common.BaseStatus;

/**
 * Create by wx on 2020-09-16
 */
public abstract class Status {

    public enum UserStatus implements BaseStatus {

        Deleted(-1),
        Normal(0),
        Locked(1),
        Disabled(2);

        private Short statusValue;

        UserStatus(int i) {
            statusValue = Short.valueOf(i + "");
        }

        @Override
        public Short status() {
            return this.statusValue;
        }
    }

    public enum UserOnlineStatus implements BaseStatus {

        ONLINE(1, "在线"),
        BUSY(2, "忙碌"),
        LEAVE(3, "离开"),
        OFFLINE(4, "脱机");

        private Short statusValue;
        private String statusInfo;

        UserOnlineStatus(int i, String status) {
            statusValue = Short.valueOf(i + "");
            statusInfo = status;
        }

        @Override
        public Short status() {
            return this.statusValue;
        }

        public String getStatusInfo() {
            return this.statusInfo;
        }

        public static UserOnlineStatus statusOf(Short status) {
            if (BUSY.status().shortValue() == status) {
                return BUSY;
            }
            if (LEAVE.status().shortValue() == status) {
                return LEAVE;
            }
            if (OFFLINE.status().shortValue() == status) {
                return OFFLINE;
            }
            return ONLINE;
        }
    }

}
