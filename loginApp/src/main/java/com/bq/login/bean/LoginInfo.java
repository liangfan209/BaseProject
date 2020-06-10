package com.bq.login.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public class LoginInfo {


    /**
     * is_first_login : 0
     * renew_flag : fFx5dNXlc95t4Td4sr4glvhGCvI8AnvcJz2GqXqbIk84WLtwlBDx9HT5U3UyGE8m
     * auth_token : XGQiA2PbomguDguRGb8j_HF_9LAeqW8rH2ljP86BVEww-tkcisMHb2letEKMM9zY
     * sip_seat : {"sip_password":"1234567809","seat_user_id":"9203","sip_server":"47.92.83.149","sip_port":"5511",
     * "sip_account":"2005"}
     */

    private String is_first_login;
    private String renew_flag;
    private String auth_token;
    private SipSeatBean sip_seat;

    public String getIs_first_login() {
        return is_first_login;
    }

    public void setIs_first_login(String is_first_login) {
        this.is_first_login = is_first_login;
    }

    public String getRenew_flag() {
        return renew_flag;
    }

    public void setRenew_flag(String renew_flag) {
        this.renew_flag = renew_flag;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public SipSeatBean getSip_seat() {
        return sip_seat;
    }

    public void setSip_seat(SipSeatBean sip_seat) {
        this.sip_seat = sip_seat;
    }

    public static class SipSeatBean {
        /**
         * sip_password : 1234567809
         * seat_user_id : 9203
         * sip_server : 47.92.83.149
         * sip_port : 5511
         * sip_account : 2005
         */

        private String sip_password;
        private String seat_user_id;
        private String sip_server;
        private String sip_port;
        private String sip_account;

        public String getSip_password() {
            return sip_password;
        }

        public void setSip_password(String sip_password) {
            this.sip_password = sip_password;
        }

        public String getSeat_user_id() {
            return seat_user_id;
        }

        public void setSeat_user_id(String seat_user_id) {
            this.seat_user_id = seat_user_id;
        }

        public String getSip_server() {
            return sip_server;
        }

        public void setSip_server(String sip_server) {
            this.sip_server = sip_server;
        }

        public String getSip_port() {
            return sip_port;
        }

        public void setSip_port(String sip_port) {
            this.sip_port = sip_port;
        }

        public String getSip_account() {
            return sip_account;
        }

        public void setSip_account(String sip_account) {
            this.sip_account = sip_account;
        }
    }
}
