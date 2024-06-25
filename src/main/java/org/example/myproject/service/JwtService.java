package org.example.myproject.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.example.myproject.dto.UserPrinciple;
import org.example.myproject.model.JwtToken;
import org.example.myproject.model.User;
import org.example.myproject.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Autowired
    private JwtTokenRepository tokenRepository;

    @Autowired
    UserService userService;

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRE_TIME = 86400000000L;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    public String generateTokenLogin(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findById(((UserPrinciple) authentication.getPrincipal()).getId()).get();
        JwtToken jwtToken = tokenRepository.findByUser(user);
        if (!user.isStatus()){
            if (!user.isState()) {
                return "Tài khoản của bạn chưa được xác nhận";
            }
            return "Tài khoản của bạn đã bị chặn";
        }
        if (jwtToken != null) {
            tokenRepository.save(new JwtToken(jwtToken.getId(), user, Jwts.builder()
                    .setSubject((userPrincipal.getUsername()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 1000))
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                    .compact(), true));
        }else {
            tokenRepository.save(new JwtToken(user, Jwts.builder()
                    .setSubject((userPrincipal.getUsername()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 1000))
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                    .compact(), true));
        }
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 1000))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
