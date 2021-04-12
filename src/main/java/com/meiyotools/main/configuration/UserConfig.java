package com.meiyotools.main.configuration;

import com.meiyotools.main.model.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository pUserRepository) {
        return args -> {};
    }
}
