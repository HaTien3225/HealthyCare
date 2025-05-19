package com.xhht.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.xhht.dto.UserDTO;
import com.xhht.pojo.User;
import com.xhht.services.RoleService;
import com.xhht.services.UserService;
import com.xhht.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = false) User u) {

        if (u == null || u.getUsername() == null || u.getPassword() == null) {
            if (u == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("u == null");
            }
            if (u.getUsername() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("username == null");
            }
            if (u.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password == null");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu thông tin đăng nhập");
        }

        if (userService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                User existingUser = userService.getUserByUsername(u.getUsername());
                String role = existingUser.getRole().getRole();
                String token = JwtUtils.generateToken(u.getUsername(), role);

                return ResponseEntity.ok(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi khi tạo JWT: " + e.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
        if (principal != null) {
            return new ResponseEntity<>(this.userService.getUserByUsername(principal.getName()), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestParam Map<String, String> params,
            @RequestParam(name = "avatar", required = false) MultipartFile avatar) {
        try {
            User u = this.userService.addUser(params, avatar);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi: Không thể tạo người dùng.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(u));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
        }
    }

//    @GetMapping("/secure/profile")
//    @ResponseBody
//    public ResponseEntity<?> getSecureProfile(Principal principal) {
//        if (principal != null) {
//            return new ResponseEntity<>(this.userService.getUserByUsername(principal.getName()), HttpStatus.OK);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
//    }
    @PostMapping("/firebase-login")
public ResponseEntity<?> firebaseLogin(@RequestBody Map<String, String> payload) {
    String idToken = payload.get("idToken");

    if (idToken == null || idToken.trim().isEmpty()) {
        return ResponseEntity.badRequest().body("ID token không được để trống");
    }

    try {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        String email = decodedToken.getEmail();

        User existingUser = userService.getUserByEmail(email);
        if (existingUser == null) {
            existingUser = userService.registerFromFirebase(email, uid);
        }

        String jwtToken = JwtUtils.generateToken(existingUser.getUsername(), existingUser.getRole().getRole());
        return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Firebase token không hợp lệ: " + e.getMessage());
    }
}

}
