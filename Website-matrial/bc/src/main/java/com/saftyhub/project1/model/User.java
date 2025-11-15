package com.saftyhub.project1.model; // تأكدي من تعديل اسم الحزمة

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor // مهم لـ JPA
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = true ) 
    private int job_id  ;

    @Column(nullable = false) 
    private String join_date ;

    
    // سيتم تخزينها مشفرة

    
    // Constructor أساسي للتسجيل
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}