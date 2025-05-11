/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.Role;
import com.xhht.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lehuy
 */
public interface UserRepository  {
    User getUserByUsername(String username);
    User createOrUpdate(User u);
    List<User> getAllUser(Map<String, String> params);
    User getUserById(int id);
    List<User> findByRoleAndIsVerified(Role role, boolean isVerified);
}
