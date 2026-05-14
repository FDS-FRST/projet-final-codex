package org.foodshare.api.service;

import org.foodshare.api.entity.Role;
import org.foodshare.api.entity.User;
import org.foodshare.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new User("offreur@test.com", passwordEncoder.encode("123"), "RestoU", Role.OFFREUR));
            userRepository.save(new User("etudiant@test.com", passwordEncoder.encode("123"), "Alice", Role.ETUDIANT));
            System.out.println("Utilisateurs ajoutés : offreur id=1, étudiant id=2");
        }
    }
}