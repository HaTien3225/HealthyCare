package com.xhht.utils;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;

public class JwtUtils {

    private static final String SECRET = "74857603750179589243685715785052";
    private static final long EXPIRATION_MS = 86400000;

    // Tạo token chứa payload gồm subject (username) và claim role
    public static String generateToken(String username, String role) throws Exception {
        JWSSigner signer = new MACSigner(SECRET);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .claim("role", role)
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .issueTime(new Date())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claimsSet
        );

        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    // Phương thức xác thực token và trả về đối tượng JwtUser chứa username và role
    public static JwtUser validateToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET);

        if (signedJWT.verify(verifier)) {
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expiration.after(new Date())) {
                String username = signedJWT.getJWTClaimsSet().getSubject();
                String role = (String) signedJWT.getJWTClaimsSet().getClaim("role");
                return new JwtUser(username, role);
            }
        }
        return null;
    }

    // Phương thức cũ vẫn còn để lấy username, tránh ảnh hưởng đến các phần khác của ứng dụng
    public static String validateTokenAndGetUsername(String token) throws Exception {
        JwtUser user = validateToken(token);
        return user != null ? user.getUsername() : null;
    }

    public static String validateTokenAndGetRole(String token) throws Exception {
        JwtUser user = validateToken(token);
        return user != null ? user.getRole() : null;
    }

    public static class JwtUser {

        private final String username;
        private final String role;

        public JwtUser(String username, String role) {
            this.username = username;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }
    }
}
