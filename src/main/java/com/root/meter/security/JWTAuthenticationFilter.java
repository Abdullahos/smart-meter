package com.root.meter.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.root.meter.DTO.UserDTO;
import com.root.meter.model.Users;
import com.root.meter.repo.UserRepo;
import com.root.meter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * adapted from the supporting material
 * can be found at https://classroom.udacity.com/nanodegrees/nd035/parts/ef7ed085-afaf-4e13-924d-94c9746ed954/modules/c48ee8c7-bbce-4e82-8607-650c5acac401/lessons/6937a8f4-aeb7-4aec-ac7b-37840ecf1765/concepts/f4a66010-25d7-41a2-9266-6b96e5da2646
 * date:16/9/2021
 */
@Service
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * try to athenticate the req using basic auth
     * @param req
     * @param res
     * @return go to successfulAuthentication if success, or set the res to appropriate error message
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            Users user = new ObjectMapper().readValue(req.getInputStream(), Users.class);
            System.out.println("pass 1"+user.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());
            System.out.println("pass 2"+user.getPassword());

            if(userDetails!=null) {
                Users retrievedUser = new Users();
                retrievedUser.setName(userDetails.getUsername());
                retrievedUser.setPassword(userDetails.getPassword());
                //compare the stored hashed password with the entered one(BCryptPasswordEncoder manage that for us)
                String retrievedHashedPass = retrievedUser.getPassword();
                String enteredPass = user.getPassword();
                System.out.println("pass 3"+retrievedUser.getPassword());
                //if passwords matches, authenticate
                if(bCryptPasswordEncoder.matches(enteredPass,retrievedHashedPass)) {
                    return authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getName(),
                                    user.getPassword(),
                                    new ArrayList<>()));
                }
                else {
                    Map<String, Object> jsonRes = new HashMap<>();
                    jsonRes.put("message", "wrong username or pass");
                    new ObjectMapper().writeValue(res.getOutputStream(), jsonRes);
                }
            }
            return null;

        } catch (Exception e){
            e.printStackTrace();
            Map<String, Object> jsonRes = new HashMap<>();
            jsonRes.put("message", "wrong username or pass");
            try {
                new ObjectMapper().writeValue(res.getOutputStream(), jsonRes);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        final UserDTO userByName = userService.findByName(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        Map<String, Object> jsonRes = new HashMap<>();
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        jsonRes.put("access_token",SecurityConstants.TOKEN_PREFIX + token);
        jsonRes.put("userId",userByName.getId());
        new ObjectMapper().writeValue(res.getOutputStream(), jsonRes);
        //TODO: try to check if the JWT signature is null or not https://developer.okta.com/blog/2020/12/21/beginners-guide-to-jwt#problems
    }

}