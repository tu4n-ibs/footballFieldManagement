package org.example.myproject.controller;

import org.example.myproject.dto.JwtResponse;
import org.example.myproject.dto.Response;
import org.example.myproject.model.JwtToken;
import org.example.myproject.model.Role;
import org.example.myproject.model.User;
import org.example.myproject.repository.JwtTokenRepository;
import org.example.myproject.service.JwtService;
import org.example.myproject.service.RoleService;
import org.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private JwtTokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Response("400", "Dữ liệu người dùng không hợp lệ", bindingResult.getFieldErrors()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role roleStaff = roleService.findByName("Role_user");
        user.setRoles(Collections.singletonList(roleStaff));
        user.setStatus(true);
        user.setState(true);

        User userResponse = userService.save(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/registerManage")
    public ResponseEntity<?> registerManage(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Response("400", "Dữ liệu người dùng không hợp lệ", bindingResult.getFieldErrors()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role roleManage = roleService.findByName("Role_manage");
        user.setRoles(Collections.singletonList(roleManage));
        user.setStatus(false);
        user.setState(false);

        User userResponse = userService.save(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            if (jwt.equals("Tài khoản của bạn đã bị chặn")) {
                return ResponseEntity.ok(new Response("203", "Tài khoản của bạn đã bị chặn", null));
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getEmail());
            if (jwt.equals("Tài khoản của bạn chưa được xác nhận")) {
                return ResponseEntity.ok(new Response("202", "Tài khoản của bạn chưa được xác nhận",
                        new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities())));
            }
            return ResponseEntity.ok(new Response("200", "Đăng nhập thành công",
                    new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities())));
        } catch (Exception e) {
            return ResponseEntity.ok(new Response("401", "Tài khoản hoặc mật khẩu không đúng", null));
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader HttpHeaders httpHeaders) {
        String authorization = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            JwtToken jwtToken = tokenRepository.findByTokenEquals(token);

            if (jwtToken != null){
                jwtToken.setValid(false);
                tokenRepository.save(jwtToken);
                return ResponseEntity.ok("đăng xuất thành công");
            }else {
                return ResponseEntity.badRequest().body("token không hợp lệ");
            }
        }else {
            return ResponseEntity.badRequest().body("authorization thiếu hoặc không hợp lệ");
        }
    }

}
