package ar.com.crivelli.facturas;

import ar.com.crivelli.facturas.application.MenuConsola;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FacturasApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                FacturasApplication.class,
                args);
    }

    @Bean
    @ConditionalOnProperty(
            name = "aplicacion.menu.habilitado",
            havingValue = "true")
    public CommandLineRunner ejecutarMenu(
            MenuConsola menuConsola) {

        return args -> menuConsola.mostrarMenu();
    }
}