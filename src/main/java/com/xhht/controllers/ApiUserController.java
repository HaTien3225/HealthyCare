package com.xhht.controllers;

import com.xhht.dto.UserDTO;
import com.xhht.pojo.Role;
import com.xhht.pojo.User;
import com.xhht.repositories.RoleRepository;
import com.xhht.services.UserService;
import com.xhht.utils.JwtUtils;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = false) User u) {
        if (u == null || u.getUsername() == null || u.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu thông tin đăng nhập");
        }

        // Xác thực người dùng
        if (userService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                User existingUser = userService.getUserByUsername(u.getUsername());
                String role = existingUser.getRole().getRole();

                String token = JwtUtils.generateToken(u.getUsername(), role);
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tạo JWT: " + e.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestParam Map<String, String> params,
            @RequestParam("avatar") MultipartFile avatar) {
        try {
            User u = this.userService.addUser(params, avatar);

            if (u == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi: Không thể tạo người dùng.");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(u));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
        }
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

}
