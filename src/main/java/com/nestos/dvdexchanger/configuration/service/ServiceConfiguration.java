package com.nestos.dvdexchanger.configuration.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = com.nestos.dvdexchanger.service.DvdEchangerService.class)
public class ServiceConfiguration {
}
