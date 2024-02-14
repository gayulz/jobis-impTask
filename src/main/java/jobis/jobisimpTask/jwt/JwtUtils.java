package jobis.jobisimpTask.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jobis.jobisimpTask.entity.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    private final Key key;
    private final long accessTokenTime;

    public JwtUtils(
            @Value("${jwt.secret.key}") String key,
            @Value("${jwt.expirationMs}") long accessTokenTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenTime = accessTokenTime;
    }

    /*
    Access Token 생성
    */
    public String createAccessToken(MemberEntity member){
        return createToken(member, accessTokenTime);
    }

    /*
    JWT 생성
    */
    private String createToken(MemberEntity member, long expireTime){
        Claims claims = Jwts.claims();
        claims.put("userId", member.getUserId());
        claims.put("userName", member.getName());
        claims.put("role", member.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
    Token 에서 ID 추출
    */
    public Long getUserId(String token){
        return parseClaims(token).get("userId", Long.class);
    }

    /*
    JWT 검증
    */
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("유효하지 않은 JWT 토큰", e);
        }catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰", e);
        }catch (UnsupportedJwtException e){
            log.info("지원되지 않는 JWT 토큰", e);
        }catch (IllegalArgumentException e){
            log.info("JWT 클레임 문자열이 비어 있습니다", e);
        }
        return false;
    }

    /*
    JWT Claims 추출
    */
    public Claims parseClaims(String accessToken){
       try {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
       } catch (ExpiredJwtException e){
           return e.getClaims();
       }
    }














}
//
//    /*
//    * JWT 토큰 생성
//    * 1. 토큰 생성
//    * 2. 토큰 반환
//    * */
//    public static String generateToken(String userName, String key, long expiredTimeMs){
//        Claims claims = Jwts.claims();
//        claims.put("userName", userName);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
//                .signWith(getKey(key), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    private static Key getKey(String key){
//        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
