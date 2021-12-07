package com.writerHub.practice.Controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.writerHub.practice.Util.JWTUtil;
import com.writerHub.practice.filter.AuthenticationFilter;
import com.writerHub.practice.models.AuthenticationError;
import com.writerHub.practice.models.AuthenticationSuccess;
import com.writerHub.practice.models.Author;
import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.service.WriterHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody WriterHubUser writerHubUser) throws AuthorExists {
        ResponseEntity<?> response;
        try{
            writerHubUser.setPassword(passwordEncoder.encode(writerHubUser.getPassword()));
            WriterHubUser user = writerHubUserService.saveUser(writerHubUser);
            response = new ResponseEntity<>(user,HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException ex){
            AuthenticationError error = new AuthenticationError();
            error.setMessage(writerHubUser.getUsername()+" is already exist!!");
            error.setStatusCode(HttpStatus.BAD_REQUEST);
            response = new ResponseEntity<>(error,error.getStatusCode());
        }
        catch (Exception ex){
            System.out.println(ex.getClass());
            AuthenticationError error = new AuthenticationError();
            error.setMessage(ex.getMessage());
            error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response = new ResponseEntity<>(error,error.getStatusCode());
        }
      return  response;
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String refresh_token = JWTUtil.extractToken(request);
            DecodedJWT decoded = JWTUtil.decodeToken(refresh_token);
            String username = decoded.getSubject();
            WriterHubUser user = writerHubUserService.getUser(username);
            String access_token = JWTUtil.getToken(user,request.getServletPath().toString(),false);
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
