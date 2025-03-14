package com.madhav.blog.app.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 🔹 Extract Authorization Header
        String requestToken = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) { // ✅ Fix: Ensure "Bearer " has a space
            token = requestToken.substring(7);

            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.warn("Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                log.warn("JWT token has expired");
            } catch (MalformedJwtException e) {
                log.warn("Invalid JWT token");
            } catch (SignatureException e) {
                log.warn("Invalid JWT signature");
            }
        } else {
            log.warn("JWT token does not begin with Bearer");
        }

        // 🔹 Validate Token & Set Authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("User '{}' authenticated successfully", username);
            } else {
                log.warn("Invalid JWT token");
            }
        } else {
            log.warn("Username is null or authentication context is already set");
        }

        // 🔹 Proceed with the request
        filterChain.doFilter(request, response);
    }
}
