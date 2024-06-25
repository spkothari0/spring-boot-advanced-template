package com.shreyas.spring_boot_advanced_template.service.interfaces;

import com.shreyas.spring_boot_advanced_template.business.bean.UserBean;
import org.springframework.web.multipart.MultipartFile;

public interface IUserServices {
    UserBean createUser(UserBean userBean);
    String verifyUser(String token);
    boolean uploadProfileImage(String username, MultipartFile file);
}
