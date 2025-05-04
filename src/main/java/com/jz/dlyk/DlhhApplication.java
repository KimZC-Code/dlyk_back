package com.jz.dlyk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.FilterChainProxy;

@MapperScan(basePackages = {"com.jz.dlhh.mapper"})
@SpringBootApplication
public class DlhhApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlhhApplication.class, args);
    }
    @Bean
    public CommandLineRunner printFilters(FilterChainProxy filterChainProxy) {
        return args -> {
            System.out.println("===== Security Filters =====");
            filterChainProxy.getFilterChains().forEach(chain -> {
                chain.getFilters().forEach(filter -> {
                    System.out.println(filter.getClass().getName());
                });
            });
        };
    }

}
