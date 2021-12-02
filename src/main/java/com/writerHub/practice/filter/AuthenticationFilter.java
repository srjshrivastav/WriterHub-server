package com.writerHub.practice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.writerHub.practice.Util.JWTUtil;
import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.service.WriterHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final WriterHubUserService writerHubUserService;

    public AuthenticationFilter(AuthenticationManager authenticationManager,WriterHubUserService userService) {
        this.authenticationManager = authenticationManager;
        this.writerHubUserService = userService;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        WriterHubUser appUser = writerHubUserService.getUser(user.getUsername());
        String access_token = JWTUtil.getToken(appUser,request.getServletPath().toString(),false);
        String refresh_token = JWTUtil.getToken(appUser,request.getServletPath().toString(),true);
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("access_token",access_token);
        responseBody.put("refresh_token",refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),responseBody);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }
}
