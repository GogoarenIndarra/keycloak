package pl.jablonski.keycloak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.testcontainers.containers.GenericContainer;
import org.keycloak.representations.idm.GroupRepresentation;



public class KeycloakTest {

    private static GenericContainer<?> keycloakContainer;
    private static Keycloak keycloak;

    @BeforeAll
    static void setup() {
        keycloakContainer = new GenericContainer<>("quay.io/keycloak/keycloak:21.1.1")
                .withEnv("KEYCLOAK_ADMIN", "admin")
                .withEnv("KEYCLOAK_ADMIN_PASSWORD", "admin")
                .withEnv("KC_DB", "dev-mem")
                .withExposedPorts(8080)
                .withCommand("start-dev"); // Start Keycloak in development mode

        keycloakContainer.start();

        String serverUrl = "http://" + keycloakContainer.getHost() + ":" + keycloakContainer.getMappedPort(8080);

        keycloak = Keycloak.getInstance(
                serverUrl,
                "master",
                "admin",
                "admin",
                "admin-cli"
        );
    }


    @Test
    void testCreateGroup_return_409_Conflict_whenGroupAlreadyExists() {
        GroupRepresentation group = new GroupRepresentation();
        group.setName("test-group");

        keycloak.realm("master").groups().add(group);

        var temp = keycloak.realm("master").groups().groups().stream()
                .filter(groupRepresentation -> groupRepresentation.getName().equals("test-group"))
                .findAny();

        Assertions.assertEquals("test-group", temp.get().getName());

        var temp2 = keycloak.realm("master").groups().add(group);
        Assertions.assertEquals(409, temp2.getStatus());

    }
}


