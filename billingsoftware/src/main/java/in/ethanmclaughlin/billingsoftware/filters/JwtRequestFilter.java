package in.ethanmclaughlin.billingsoftware.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.ethanmclaughlin.billingsoftware.service.impl.AppUserDetailsService;
import in.ethanmclaughlin.billingsoftware.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{

    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Skip filter for login and encode endpoints
        String path = request.getRequestURI();
        if (path.contains("/login") || path.contains("/encode")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractUsername(jwt);
        }
        
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userdetails = userDetailsService.loadUserByUsername(email);
            
            if(jwtUtil.validateToken(jwt, userdetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    
        filterChain.doFilter(request, response);
    }

}
