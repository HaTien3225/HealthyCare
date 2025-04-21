/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.User;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface UserRepository {
    User getUserByUsername(String username);
    User addUser(User u);
}
