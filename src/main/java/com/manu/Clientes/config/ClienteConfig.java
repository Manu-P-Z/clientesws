package com.manu.Clientes.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ClienteConfig {
    @Configuration
    public class ShopConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .components(new Components())
                    .info(new Info().title("Clientes API")
                            .description("Ejemplo de API REST")
                            .contact(new Contact()
                                    .name("Manu Portolés")
                                    .email("manu.portoles.zagala@gmail.com")
                                    .url("https://github.com/Manu-P-Z"))
                            .version("1.0"));
        }
    }
}
