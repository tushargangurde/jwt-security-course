package com.tushar.securitycourse.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tushar.securitycourse.dto.RoleToUserForm;
import com.tushar.securitycourse.model.AppUser;
import com.tushar.securitycourse.model.Role;
import com.tushar.securitycourse.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        List<AppUser> appUsers = userService.getUsers();
        return new ResponseEntity<List<AppUser>>(appUsers, HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUsers(@RequestBody AppUser user) {
        AppUser savedUsers = userService.saveUser(user);
        return new ResponseEntity<AppUser>(savedUsers, HttpStatus.CREATED);
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        Role savedRole = userService.saveRole(role);
        return new ResponseEntity<Role>(savedRole, HttpStatus.CREATED);
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> saveRole(@RequestBody RoleToUserForm role) {
        userService.addRoleToUser(role.getUsername(), role.getRoleName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType("application/json");
                response.setStatus(HttpStatus.OK.value());
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("JWT Related Exception: " + exception.getMessage());
                Map<String, String> error = new HashMap<>();
                error.put("errorCode", HttpStatus.UNAUTHORIZED.toString());
                error.put("errorMessage", "JWT Related Exception: " + exception.getMessage());
                response.setContentType("application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            log.error("Authorization header is missing or does not starts with Bearer");
            Map<String, String> error = new HashMap<>();
            error.put("errorCode", HttpStatus.UNAUTHORIZED.toString());
            error.put("errorMessage", "Authorization header is missing or does not starts with Bearer");
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

}
