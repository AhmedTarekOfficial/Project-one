package com.saftyhub.project1;

import com.saftyhub.project1.model.ETRole;
import com.saftyhub.project1.model.Role;
import com.saftyhub.project1.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Project1Application {

    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }
    
    // ✅ Bean جديد: يتم تنفيذه عند تشغيل التطبيق لأول مرة
    @Bean
    public CommandLineRunner initialRoles(RoleRepository roleRepository) {
        return args -> {
            // إذا لم يكن الدور موجوداً، أضفه
            if (roleRepository.findByName(ETRole.ROLE_ADMIN).isEmpty()) {
                roleRepository.save(new Role(ETRole.ROLE_ADMIN));
            }
            if (roleRepository.findByName(ETRole.ROLE_ENGINEER).isEmpty()) {
                roleRepository.save(new Role(ETRole.ROLE_ENGINEER));
            }
            if (roleRepository.findByName(ETRole.ROLE_WORKER).isEmpty()) {
                roleRepository.save(new Role(ETRole.ROLE_WORKER));
            }
        };
    }
}