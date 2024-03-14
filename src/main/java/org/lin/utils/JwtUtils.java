package org.lin.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.lin.entity.bo.User;
import org.lin.enums.ResultCodeEnum;
import org.lin.exception.BussinessException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author LvWei
 * @Date 2024/3/14 15:21
 */
public class JwtUtils {
    public static String createToken(User user){
        //设置算法以及签名
        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        //设置头部
        Map map = new HashMap<>();
        map.put("typ","JWT");
        map.put("alg","HS256");
        String token = JWT.create().withHeader(map)
                //签发人
                .withIssuer("auth0")
                //主题
                .withSubject("login")
                //受众
                .withAudience("users")
                .withExpiresAt(Instant.now().plus(12, ChronoUnit.HOURS))
                //自定义载荷
                .withClaim("name",user.getUsername())
                .withClaim("email", user.getEmail())
                .withClaim("id", user.getId())
                .sign(algorithmHS);
        return token;
    }

    public static User verifyToken(String token){
        User user = new User();

        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithmHS)
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
        try {
            DecodedJWT jwt = verifier.verify(token);
            user.setUsername(jwt.getClaim("name").asString());
            user.setEmail(jwt.getClaim("email").asString());
            user.setId(jwt.getClaim("id").asInt());
            return user;
        }catch (Exception e){
            throw new BussinessException(ResultCodeEnum.TOKEN_EXPIRED);
        }
    }

}
