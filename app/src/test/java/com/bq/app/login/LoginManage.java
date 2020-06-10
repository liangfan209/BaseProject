package com.bq.app.login;

import com.bq.app.register.Component;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
public class LoginManage extends Component {

    public LoginManage(){
        this.model = new LoginModel();
        this.view = new LoginView();
        this.process = new LoginProcess();
        this.routePath = "Login";
    }

}
