package com.todo.todo_app_backend.security;

import com.todo.todo_app_backend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Authorization Header: {}", authHeader != null ? "Present" : "Missing");

        // Skip filter if no Bearer token is present
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("No Bearer token found, continuing filter chain");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract token and username
            jwt = authHeader.substring(7);
            logger.info("Token extracted (first 20 chars): {}", jwt.substring(0, Math.min(20, jwt.length())));

            userEmail = jwtUtil.extractUsername(jwt);
            logger.info("Extracted email: {}", userEmail);

            // Validate token and set authentication
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                logger.info("UserDetails loaded: {}", userDetails.getUsername());

                if (jwtUtil.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Authentication set successfully for user: {}", userEmail);
                } else {
                    logger.warn("Token validation failed for user: {}", userEmail);
                }
            }
        } catch (Exception e) {
            logger.error("Error in JwtAuthFilter: {}", e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication error: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldSkip = path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.equals("/api/v1/todos/public-debug");  // Skip filter for public debug endpoint

        if (shouldSkip) {
            logger.info("Skipping JWT filter for path: {}", path);
        }
        return shouldSkip;
    }
}