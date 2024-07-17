package org.example.myproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.myproject.dto.JwtResponse;
import org.example.myproject.dto.Response;
import org.example.myproject.model.Role;
import org.example.myproject.model.User;
import org.example.myproject.repository.JwtTokenRepository;
import org.example.myproject.service.JwtService;
import org.example.myproject.service.RoleService;
import org.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin("*")
public class LoginController {

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

    @Autowired
    private JwtTokenRepository tokenRepository;

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
        user.setStatus(true);
        user.setState(true);

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

            if ("Tài khoản của bạn đã bị chặn".equals(jwt)) {
                return ResponseEntity.ok(new Response("203", "Tài khoản của bạn đã bị chặn", null));
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getEmail());

            if ("Tài khoản của bạn chưa được xác nhận".equals(jwt)) {
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
    public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            String jwt = jwtService.getJwtFromRequest(request);
            if (jwt != null) {
                jwtService.blacklistToken(jwt);
            }
        }
        return ResponseEntity.ok(new Response("200", "Bạn đã đăng xuất thành công.", null));
    }

}
