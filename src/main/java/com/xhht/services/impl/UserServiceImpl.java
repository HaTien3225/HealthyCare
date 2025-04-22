/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.User;
import com.xhht.repositories.UserRepository;
import com.xhht.services.UserService;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lehuy
 */
@Service("userDetailService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User u = this.userRepo.getUserByUsername(username);
//        if (u == null) {
//            throw new UsernameNotFoundException("Invalid username!");
//        }
//
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority(u.getRole().getRole()));
//
//        return new org.springframework.security.core.userdetails.User(
//                u.getUsername(), u.getPassword(), authorities);
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("[DEBUG] load user: " + username);

        User u = this.userRepo.getUserByUsername(username);

        if (u == null) {
            System.out.println("[ERROR] can't find any user: " + username);
            throw new UsernameNotFoundException("Invalid username!");
        }

        System.out.println("[DEBUG] User found: " + u.getUsername());
        System.out.println("[DEBUG] Password from DB: " + u.getPassword());
        System.out.println("[DEBUG] Role: " + u.getRole().getRole());

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole().getRole()));

        System.out.println("[DEBUG] Authorities: " + authorities);
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
        
    }

}
