package com.nestos.dvdexchanger.application;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.nestos.dvdexchanger.configuration")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return configureAppication(builder);
    }

    public static void main(String[] args) {
        configureAppication(new SpringApplicationBuilder()).run(args);
    }

    private static SpringApplicationBuilder configureAppication(SpringApplicationBuilder builder) {
        return builder.sources(Application.class).bannerMode(Banner.Mode.OFF);
    }
}
