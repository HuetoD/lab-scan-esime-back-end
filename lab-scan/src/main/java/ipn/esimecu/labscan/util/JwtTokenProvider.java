package ipn.esimecu.labscan.util;

import ipn.esimecu.labscan.web.security.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtTokenProvider implements InitializingBean {

    private Algorithm algorithm;

    @Override
    public void afterPropertiesSet() {
        algorithm = Algorithm.HMAC512(SecurityConstant.JWT_SECRET.getBytes());
    }


    public String generateJwtToken(UserDetails userDetails) {
        String [] claims = getClaimsFromUser(userDetails);
        return JWT.create()
                .withIssuer(SecurityConstant.JWT_ISSUER)
                .withIssuedAt(new Date())
                .withSubject(userDetails.getUsername())
                .withArrayClaim(SecurityConstant.JWT_AUTHORITIES, claims)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.JWT_EXPIRATION_TIME_MILLIS))
                .sign(algorithm);

    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                =  new UsernamePasswordAuthenticationToken(username, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String username, String token) {
        return !username.isEmpty() && hasTokenNotExpired(token);
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        return Stream.of(this.getClaimsFromToken(token))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getSubject(String token) {
        return verifier().verify(token).getSubject();
    }

    private String [] getClaimsFromToken(String token) {
        return verifier().verify(token)
                .getClaim(SecurityConstant.JWT_AUTHORITIES)
                .asArray(String.class);
    }

    private JWTVerifier verifier() {
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(algorithm).withIssuer(SecurityConstant.JWT_ISSUER).build();
        }catch(JWTVerificationException exception) {
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private String [] getClaimsFromUser(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    private boolean hasTokenNotExpired(String token) {
        return verifier().verify(token).getExpiresAt().after(new Date());
    }


}
