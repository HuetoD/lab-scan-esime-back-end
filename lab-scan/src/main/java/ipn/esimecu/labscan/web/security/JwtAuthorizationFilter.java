package ipn.esimecu.labscan.web.security;

import ipn.esimecu.labscan.repository.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtAuthorizationFilter.doFilterInternal");
        if(request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            System.out.println(request.getHeaderNames());
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstant.JWT_TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizationHeader.replace(SecurityConstant.JWT_TOKEN_PREFIX, "");
            String username = jwtTokenProvider.getSubject(token);

            System.out.println("token = " +  token);
            System.out.println("username = " + username);

            boolean isTokenValid = jwtTokenProvider.isTokenValid(username, token);
            boolean isAuthNull = SecurityContextHolder.getContext().getAuthentication() == null;

            try {
                if(isTokenValid &&  isAuthNull) {
                    List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                    Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } catch (Exception e) {
                log.error(e.toString());
                SecurityConstant.handleUnauthorized(response);
            }
        }
        filterChain.doFilter(request, response);
    }
}
