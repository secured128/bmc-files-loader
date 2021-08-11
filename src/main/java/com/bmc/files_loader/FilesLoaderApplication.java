package com.bmc.files_loader;

import com.bmc.files_loader.controllers.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FilesLoaderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FilesLoaderApplication.class);
        context.getBean(Controller.class).controlFlow();

    }

}
