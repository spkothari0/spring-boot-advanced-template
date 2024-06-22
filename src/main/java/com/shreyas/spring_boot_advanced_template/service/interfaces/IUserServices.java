package com.shreyas.spring_boot_advanced_template.service.interfaces;

import com.shreyas.spring_boot_advanced_template.business.bean.UserBean;

public interface IUserServices {
    UserBean createUser(UserBean userBean);
    String verifyUser(String token);
}
