package top.yueshushu.learn.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Data
public class JwtUtils {
    //签名私钥
    @Value("${jwt.key}")
    private String key = "saaa-ihrm";
    //签名的实效时间
    @Value("${jwt.ttl}")
    private String ttl;
    /**
     * 设置认证token
     */
    public String createJwt(int id, String name, Map<String, Object> map) {
        //1.设置失效时间
        //当前毫秒
        long now = System.currentTimeMillis();
        long exp = now + Long.valueOf(ttl);
        //2.创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(String.valueOf(id)).setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        //3.根据map设置claims
        jwtBuilder.setClaims(map);
        jwtBuilder.setExpiration(new Date(exp));
        //4.创建token
        String token = jwtBuilder.compact();
        return token;
    }
    public Claims parseJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }
}
