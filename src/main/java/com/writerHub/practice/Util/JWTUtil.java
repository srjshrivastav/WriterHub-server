package com.writerHub.practice.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.writerHub.practice.Exception.AuthenticationExceptions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JWTUtil {

    public  static String getToken(String username, String url,String role){
        Algorithm algorithm  = Algorithm.HMAC512("TestToken".getBytes(StandardCharsets.UTF_8));
        String token;
        if(role == null){
            token = JWT.create()
                    .withSubject(username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 600*60*1000 ))
                    .withIssuer(url)
                    .sign(algorithm);
        }
        else {
            token = JWT.create()
                    .withSubject(username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .withIssuer(url)
                    .withClaim("roles",role)
                    .sign(algorithm);
        }
        return  token;
    }

    public static DecodedJWT decodeToken(String token) throws Exception {
        if (token != null) {
            Algorithm algo = Algorithm.HMAC512("TestToken".getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algo).build();
            DecodedJWT decoded = verifier.verify(token);
            return decoded;
        }
        return  null;
    }

    public static String extractToken(HttpServletRequest request) throws AuthorizationHeaderMissing {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.split(" ")[1];
            return  token;
        }
        throw new AuthorizationHeaderMissing("You are unauthorized");
    }
}
