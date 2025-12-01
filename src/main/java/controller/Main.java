package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// 1. Spunem Spring-ului să scaneze și celelalte pachete (foldere) pentru Componente (Service, Controller, etc.)
@ComponentScan(basePackages = {"controller", "service", "repository", "bootstrap"})
// 2. Spunem unde sunt Entitățile JPA (@Entity) - adică folderul 'model'
@EntityScan(basePackages = "model")
// 3. Spunem unde sunt Repository-urile JPA - adică folderul 'repository'
@EnableJpaRepositories(basePackages = "repository")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}