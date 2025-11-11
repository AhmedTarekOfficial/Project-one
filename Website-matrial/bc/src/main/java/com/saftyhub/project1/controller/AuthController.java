package com.saftyhub.project1.controller; // **عدلي هذا السطر ليطابق اسم حزمتكِ**

import com.saftyhub.project1.model.ETRole;
import com.saftyhub.project1.model.Role;
import com.saftyhub.project1.model.User;
import com.saftyhub.project1.repository.RoleRepository;
import com.saftyhub.project1.repository.UserRepository;
import com.saftyhub.project1.security.jwt.JwtUtils;
import com.saftyhub.project1.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600) // مهم للتعامل مع الـ Frontend
@RestController
@RequestMapping("/api/auth") // كل المسارات ستبدأ بـ /api/auth
public class AuthController {

    @Autowired AuthenticationManager authenticationManager;
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired PasswordEncoder encoder;
    @Autowired JwtUtils jwtUtils;

    // 1. مسار تسجيل الدخول (Login)
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // يتم تمرير طلب الدخول لـ Spring Security للتحقق من كلمة المرور
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // توليد الـ JWT Token باستخدام الـ Utility الذي أنشأناه
        String jwt = jwtUtils.generateJwtToken((UserDetailsImpl) authentication.getPrincipal());
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        // استخراج الصلاحيات (Roles)
        String role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.joining(","));


        // إرجاع الرد النهائي الذي يحتوي على التوكن
        return ResponseEntity.ok(new JwtResponse());
    }

    // 2. مسار التسجيل (Register)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // التحقق من أن اسم المستخدم غير مأخوذ
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // إنشاء مستخدم جديد وتشفير كلمة المرور
        User user = new User(registerRequest.getUsername(), 
                             encoder.encode(registerRequest.getPassword()));

        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();
        
        // تعيين الدور (Role) بناءً على ما أرسله المستخدم
        if (strRoles == null || strRoles.isEmpty()) {
            // الدور الافتراضي: WORKER
            Role defaultRole = roleRepository.findByName(ETRole.ROLE_WORKER)
                    .orElseThrow(() -> new RuntimeException("Error: Role WORKER is not found."));
            roles.add(defaultRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ETRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role ADMIN is not found."));
                        roles.add(adminRole);
                        break;
                    case "engineer":
                        Role engRole = roleRepository.findByName(ETRole.ROLE_ENGINEER)
                                .orElseThrow(() -> new RuntimeException("Error: Role ENGINEER is not found."));
                        roles.add(engRole);
                        break;
                    default:
                        Role workerRole = roleRepository.findByName(ETRole.ROLE_WORKER)
                                .orElseThrow(() -> new RuntimeException("Error: Role WORKER is not found."));
                        roles.add(workerRole);
                }
            });
        }
        
        // ** التعديل الهام: لكي يعمل الـ setRoles حتى لو لم يولدها Lombok بعد: **
        user.setRoles(roles); 
        
        // ** تأكدنا من أن userRepository.save() يعمل أيضاً **
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}