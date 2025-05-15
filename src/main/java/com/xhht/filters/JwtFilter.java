package com.xhht.filters;

import com.xhht.utils.JwtUtils;
import com.xhht.utils.JwtUtils.JwtUser;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(JwtFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Áp dụng filter cho các đường dẫn bắt đầu bằng /api/secure
        if (uri.startsWith(contextPath + "/api/secure")) {

            String header = httpRequest.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Missing or invalid Authorization header.");
                return;
            }

            String token = header.substring(7); // Bỏ qua "Bearer "
            try {
                JwtUser user = JwtUtils.validateToken(token);

                if (user != null) {
                    httpRequest.setAttribute("username", user.getUsername());

                    // Gắn role vào context nếu cần
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user.getUsername(),
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(request, response);
                    return;
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Token validation failed", e);
            }

            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Token không hợp lệ hoặc đã hết hạn.");
            return;
        }

        // Nếu không phải /api/secure thì tiếp tục filter bình thường
        chain.doFilter(request, response);
    }
    
}
