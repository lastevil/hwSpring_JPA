package com.hw.spring.auth.controllers;


import com.hw.spring.auth.aspect.AspectLoging;
import com.hw.spring.auth.dto.JwtRequest;
import com.hw.spring.auth.dto.JwtResponse;
import com.hw.spring.auth.dto.UserDto;
import com.hw.spring.auth.exceptions.AppError;
import com.hw.spring.auth.services.UserService;
import com.hw.spring.auth.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final AspectLoging aspectLoging;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Wrong username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()) == null
                || userService.findByEmail(userDto.getEmail()) == null) {
            return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), "User exist"), HttpStatus.CONFLICT);
        } else {
            userService.createUser(userDto);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

}
