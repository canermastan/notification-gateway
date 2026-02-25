package com.canermastan.notificationgateway;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class NotificationResourceTest {
    @Test
    public void testNotifyEndpointWithInvalidKey() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"apiKey\": \"wrong-key\", \"message\": \"test\", \"severity\": \"INFO\"}")
                .when().post("/v1/notify")
                .then()
                .statusCode(401);
    }
}
