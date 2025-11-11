package com.saftyhub.project1.security.jwt; // **تأكدي أن هذا السطر يطابق اسم الحزمة لديكِ**

import com.saftyhub.project1.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    
    @Autowired private JwtUtils jwtUtils; // نستخدم مولد التوكن
    @Autowired private UserDetailsServiceImpl userDetailsService; // نستخدم خدمة جلب المستخدم

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request); // 1. جلب التوكن من الطلب
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) { // 2. التحقق من صلاحية التوكن
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // 3. جلب تفاصيل المستخدم
                
                // إنشاء كائن المصادقة (Authentication Object)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. وضع المستخدم في سياق Spring Security (جعله مصادق عليه)
                SecurityContextHolder.getContext().setAuthentication(authentication); 
            }
        } 
           
        }

        filterChain.doFilter(request, response); // الانتقال إلى الفلتر/المسار التالي
    }

    // دالة مساعدة لاستخلاص التوكن من الـ Header (عادة يكون "Bearer token...")
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // نأخذ النص بعد "Bearer "
        }

        return null;
    }
}