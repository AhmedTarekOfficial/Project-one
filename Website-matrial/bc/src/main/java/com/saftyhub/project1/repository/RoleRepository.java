package com.saftyhub.project1.repository; // تأكدي من تعديل اسم الحزمة

import com.saftyhub.project1.model.ETRole;
import com.saftyhub.project1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// RoleRepository للتفاعل مع جدول الصلاحيات
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ETRole name);
}