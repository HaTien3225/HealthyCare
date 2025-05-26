package com.xhht.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.xhht.pojo.GiayPhepHanhNghe;
import com.xhht.pojo.Role;
import com.xhht.pojo.User;
import com.xhht.repositories.GiayPhepHanhNgheRepository;
import com.xhht.repositories.RoleRepository;
import com.xhht.repositories.UserRepository;
import com.xhht.services.UserService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("userDetailService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GiayPhepHanhNgheRepository giayphepReppo;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepo.getUserByEmail(email);
    }

    @Override
    public User createOrUpdate(User u) {
        if (u.getFile() != null && !u.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(u.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (u.getPassword() == null || u.getPassword().isEmpty()) {
            User existingUser = this.userRepo.getUserById(u.getId());
            if (existingUser != null) {
                u.setPassword(existingUser.getPassword());
            }
        }

        return this.userRepo.createOrUpdate(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.userRepo.getUserByUsername(username);

        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole().getRole()));

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

    public void verifyDoctor(int doctorId) {
        User doctor = userRepo.getUserById(doctorId);
        if (doctor != null && doctor.getRole().getId() == 3) {
//            doctor.setIsActive(true);
            if (doctor.getGiayPhepHanhNgheId() != null) {
                doctor.getGiayPhepHanhNgheId().setIsValid(true);
            }
            userRepo.createOrUpdate(doctor);
        }
    }

    @Override
    public void updateUserStatus(int id, Boolean isActive) {
        this.userRepo.updateUserStatus(id, isActive);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    public boolean registerUser(User u) {
        if (userRepo.existsByUsername(u.getUsername())) {
            return false;
        }

        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setIsActive(true);
        u.setRole(roleRepo.getRoleById(2));
        userRepo.createOrUpdate(u);
        return true;
    }

    @Override
    public boolean registerDoctor(String username, String password, String email, String ho, String ten,
            MultipartFile licenseFile) {

        if (userRepo.existsByUsername(username)) {
            return false;
        }

        User doctor = new User();
        doctor.setUsername(username);
        doctor.setPassword(passwordEncoder.encode(password));
        doctor.setEmail(email);
        doctor.setHo(ho);
        doctor.setTen(ten);
        doctor.setIsActive(false);
        doctor.setRole(roleRepo.getRoleById(3));

        userRepo.createOrUpdate(doctor);

        if (!licenseFile.isEmpty()) {
            try {

                Map res = cloudinary.uploader().upload(licenseFile.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                String fileUrl = res.get("secure_url").toString();

                GiayPhepHanhNghe license = new GiayPhepHanhNghe();
                license.setBacSiId(doctor);
                license.setImage(fileUrl);
                license.setCreated_date(LocalDate.now());
                license.setIsValid(false);

                giayphepReppo.save(license);

                doctor.setGiayPhepHanhNgheId(license);
                userRepo.createOrUpdate(doctor);
            } catch (IOException e) {

                System.err.println("Error uploading license file: " + e.getMessage());
                return false;
            }
        }

        return true;
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();

        u.setHo(params.get("ho"));
        u.setTen(params.get("ten"));
        u.setUsername(params.get("username"));
        u.setPassword(this.passEncoder.encode(params.get("password")));
        u.setEmail(params.get("email"));
        u.setCccd(params.get("cccd"));
        u.setPhone(params.get("phone"));
        u.setCreatedDate(LocalDate.now());
        u.setIsActive(true);
        Role role = this.roleRepo.getRoleById(2);
        u.setRole(role);

        // Upload avatar
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.userRepo.addUser(u);
    }

    @Override
    public User registerFromFirebase(String email, String uid) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }

        if (userRepo.existsByEmail(email)) {
            return userRepo.getUserByEmail(email);
        }

        // Lấy role mặc định
        Role role = roleRepo.getRoleById(2);
        if (role == null) {
            throw new RuntimeException("Vai trò mặc định ROLE_USER không tồn tại trong hệ thống");
        }

        // Tạo người dùng mới
        User user = new User();
        user.setEmail(email);
        user.setUsername(email); 
        user.setPassword(uid);   
        user.setRole(role);
        user.setCccd("");
        user.setHo("");
        user.setIsActive(true);
        user.setCreatedDate(LocalDate.now());
        user.setPhone("");
        user.setTen("");

        return userRepo.createOrUpdate(user);
    }

}
