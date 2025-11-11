package com.saftyhub.project1.services; // **عدلي هذا السطر ليتناسب مع اسم الحزمة لديكِ**

import com.saftyhub.project1.model.User;
import com.saftyhub.project1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // هذا يجعل Spring يتعرف عليها كخدمة
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository; // نستخدم الـ Repository لجلب البيانات

    @Override
    @Transactional
    // هذه الدالة هي ما تستخدمه Spring Security عند محاولة الدخول
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ابحثي عن المستخدم في الداتا بيز باسم المستخدم
        User user = ((Object) userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // إذا وجدناه، نحوله إلى الصيغة الآمنة (UserDetailsImpl) ونرجعه
        return UserDetailsImpl.build(user);
    }
}