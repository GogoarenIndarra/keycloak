package pl.jablonski.keycloak;

import org.springframework.boot.SpringApplication;

public class TestKeycloakApplication {

	public static void main(String[] args) {
		SpringApplication.from(KeycloakApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
