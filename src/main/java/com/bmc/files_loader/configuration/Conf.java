package com.bmc.files_loader.configuration;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {
    @Bean
    public Faker getFaker(){
        return new Faker();
    }

}
