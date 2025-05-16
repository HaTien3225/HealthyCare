package com.xhht.filters;

import com.xhht.utils.JwtUtils;
import com.xhht.utils.JwtUtils.JwtUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String username = JwtUtils.validateTokenAndGetUsername(token);
                String role = JwtUtils.validateTokenAndGetRole(token); // Lấy role từ token

                if (username != null && role != null) {
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                    UsernamePasswordAuthenticationToken auth
                            = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ hoặc hết hạn");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
