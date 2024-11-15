package com.siakadtpq_server.tpq_server.config;

import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (userRepository.count() == 0) {
                User user = new User(null, "admin", passwordEncoder.encode("admin"), "kepala", null,
                        null);

                userRepository.save(user);

                System.out.println("Default users have been added.");
            } else {
                System.out.println("Users already exist, no need to add default users.");
            }
        };
    }
}
