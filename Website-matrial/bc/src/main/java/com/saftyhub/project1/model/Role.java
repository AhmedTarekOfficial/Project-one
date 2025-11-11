package com.saftyhub.project1.model; // تأكدي من تعديل اسم الحزمة

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // استخدام الـ Enum الذي أنشأناه للتو
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ETRole name;
    
    // هذا Constructor مهم لإنشاء Role جديدة
    public Role(ETRole name) {
        this.name = name;
    }
}