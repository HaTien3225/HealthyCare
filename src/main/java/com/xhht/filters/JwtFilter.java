package com.xhht.filters;

import com.xhht.utils.JwtUtils;
import com.xhht.utils.JwtUtils.JwtUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        // Áp dụng JWT Filter cho các đường dẫn bắt đầu bằng /api/secure
        if (uri.startsWith(contextPath + "/api/secure")) {
            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Thiếu hoặc sai định dạng Authorization header.");
                return;
            }

            String token = header.substring(7); // Bỏ "Bearer "
            try {
                JwtUser user = JwtUtils.validateToken(token);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user.getUsername(),
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                    return;
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Xác thực token thất bại", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ hoặc đã hết hạn.");
                return;
            }
        }

        // Nếu không nằm trong /api/secure => tiếp tục filter như bình thường
        filterChain.doFilter(request, response);
    }
}
