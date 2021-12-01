package com.writerHub.practice.Controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.writerHub.practice.Util.JWTUtil;
import com.writerHub.practice.models.AuthenticationError;
import com.writerHub.practice.models.Author;
import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.service.WriterHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.writerHub.practice.Exception.AuthenticationExceptions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth")
public class AuthenticationContoller {

    @Autowired
    private WriterHubUserService writerHubUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody WriterHubUser writerHubUser) throws AuthorExists {
        writerHubUser.setPassword(passwordEncoder.encode(writerHubUser.getPassword()));
        return writerHubUserService.addUser(writerHubUser);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String refresh_token = JWTUtil.extractToken(request);
            DecodedJWT decoded = JWTUtil.decodeToken(refresh_token);
            String username = decoded.getSubject();
            WriterHubUser user = writerHubUserService.getUser(username);
            String access_token = JWTUtil.getToken(user.getUsername(),request.getServletPath().toString(),user.getRole());
            Map<String,String> responseBody = new HashMap<>();
            responseBody.put("access_token",access_token);
            responseBody.put("refresh_token",refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),responseBody);
        }
        catch (Exception exception){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            AuthenticationError error = new AuthenticationError();
            error.setStatusCode(HttpStatus.UNAUTHORIZED);
            error.setMessage(exception.getMessage());
            new ObjectMapper().writeValue(response.getOutputStream(),error);
        }
    }
}
