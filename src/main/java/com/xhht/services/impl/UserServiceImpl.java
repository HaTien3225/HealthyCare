/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.xhht.pojo.Role;
import com.xhht.pojo.User;
import com.xhht.repositories.RoleRepository;
import com.xhht.repositories.UserRepository;
import com.xhht.services.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lehuy
 */
@Service("userDetailService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public User createOrUpdate(User u) {
        // Kiểm tra xem người dùng có upload file avatar hay không
        if (!u.getFile().isEmpty()) {
            try {
                // Upload ảnh avatar lên Cloudinary
                Map res = cloudinary.uploader().upload(u.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Kiểm tra mật khẩu để đảm bảo không bị ghi đè nếu người dùng không thay đổi
        if (u.getPassword() == null || u.getPassword().isEmpty()) {
            // Nếu password không thay đổi, giữ nguyên mật khẩu cũ
            User existingUser = this.userRepo.getUserById(u.getId());
            if (existingUser != null) {
                u.setPassword(existingUser.getPassword());  // Giữ nguyên mật khẩu cũ
            }
        }

        // Lưu user vào cơ sở dữ liệu
        return this.userRepo.createOrUpdate(u);
    }

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

    @Override
    public List<User> getAllUser(Map<String, String> params) {
        return this.userRepo.getAllUser(params);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

    public List<User> getDoctorsPendingVerification() {
        Role doctorRole = roleRepo.getRoleById(3);
        if (doctorRole != null) {
            return userRepo.findByRoleAndIsVerified(doctorRole, false);
        }
        return new ArrayList<>();
    }

    // Xác nhận bác sĩ
    public void verifyDoctor(int doctorId) {
        User doctor = userRepo.getUserById(doctorId);
        if (doctor != null && doctor.getRole().getId() == 3) {
            doctor.setIsActive(true); // Đánh dấu bác sĩ đã được xác nhận
            userRepo.createOrUpdate(doctor);  // Lưu thay đổi
        }
    }
}
