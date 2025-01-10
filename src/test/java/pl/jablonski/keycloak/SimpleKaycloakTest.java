package pl.jablonski.keycloak;


import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class SimpleKaycloakTest {

    @Container
    KeycloakContainer keycloak = new KeycloakContainer()
            .withRealmImportFile("test-realm.json");

    @Test
    void test() {
        System.out.println("Keycloak URL: " + keycloak.getAuthServerUrl());
        Assertions.assertTrue(keycloak.isRunning());
    }
}
