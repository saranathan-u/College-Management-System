package com.example.college_management.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        //Skip auth for public endpoints
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
            System.out.println("🔹 Received Token: " + token);
            try {
                // subject = email
                username = jwtUtils.extractUsername(token);
                System.out.println("✅ Extracted Email from JWT: " + username);
            } catch (Exception e) {
                System.out.println("❌ Failed to extract email from token: " + e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            System.out.println("❌ No Authorization header found in request: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        // If we have an email and no auth yet in the context, authenticate from JWT
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Validate token by signature + expiry only (no DB lookup)
            if (jwtUtils.validateToken(token)) {
                // Get role from JWT claims, e.g. "ROLE_STUDENT"
                String role = jwtUtils.extractRole(token);
                if (role == null || role.isBlank()) {
                    role = "ROLE_USER";
                }
                System.out.println("🔍 Role from JWT: " + role);

                // Build authorities list
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(role));

                // Principal = email from token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("✅ Authenticated user from JWT only: " + username);
            } else {
                System.out.println("❌ Token validation failed (signature/expiry) for user: " + username);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/api/students/register")
                || path.startsWith("/api/students/login")
                || path.startsWith("/api/faculty/register")
                || path.startsWith("/api/faculty/login")
                || path.startsWith("/api/auth/")
                || path.startsWith("/uploads")
                || path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs");
    }
}
