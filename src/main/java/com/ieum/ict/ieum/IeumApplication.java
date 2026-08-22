package com.ieum.ict.ieum;

import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.domain.UserRole;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class IeumApplication {
    public static void main(String[] args) {
        SpringApplication.run(IeumApplication.class, args);
    }

    @Bean
    CommandLineRunner seedAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> userRepository.findByEmail("admin@ieum.com").ifPresentOrElse(
                user -> {
                    if (user.getRole() != UserRole.ADMIN) {
                        user.updateRole(UserRole.ADMIN);
                        userRepository.save(user);
                    }
                },
                () -> {
                    User admin = new User(
                            "admin@ieum.com",
                            passwordEncoder.encode("admin1234!"),
                            "ieum_admin"
                    );
                    admin.updateRole(UserRole.ADMIN);
                    userRepository.save(admin);
                }
        );
    }
}
