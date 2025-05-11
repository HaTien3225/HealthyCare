/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

import com.xhht.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lehuy
 */

public interface UserService extends UserDetailsService{
    User getUserByUsername(String username);
    User createOrUpdate(User u);
    List<User> getAllUser(Map<String, String> params);
    User getUserById(int id);
    List<User> getDoctorsPendingVerification();
    void verifyDoctor(int doctorId);
}
