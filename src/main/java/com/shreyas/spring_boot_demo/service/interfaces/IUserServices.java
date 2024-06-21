package com.shreyas.spring_boot_demo.service.interfaces;

import com.shreyas.spring_boot_demo.business.bean.UserBean;

public interface IUserServices {
    UserBean createUser(UserBean userBean);
    String verifyUser(String token);
}
