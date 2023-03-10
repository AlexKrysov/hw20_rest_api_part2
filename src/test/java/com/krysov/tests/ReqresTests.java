package com.krysov.tests;

import com.krysov.models.lombok.CreateBodyLombokModel;
import com.krysov.models.lombok.CreateResponseLombokModel;
import com.krysov.models.pojo.CreateBodyModel;
import com.krysov.models.pojo.CreateResponse;
import org.junit.jupiter.api.Test;

import static com.krysov.helpers.CustomApiListener.withCustomTemplates;
import static com.krysov.specs.CreateSpecs.CreateResponseSpec;
import static com.krysov.specs.CreateSpecs.createRequestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresTests {

    @Test
    void createTest() {
        CreateBodyModel data = new CreateBodyModel();
        data.setName("morpheus");
        data.setJob("leader");
        CreateResponse response = given()
                        .log().all()
                        .contentType(JSON)
                        .body(data)
                        .when()
                        .post("https://reqres.in/api/users")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(201)
                        .extract().as(CreateResponse.class);
        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("leader");
    }

    @Test
    void CreateLombokTest() {
        CreateBodyLombokModel data = new CreateBodyLombokModel();
        data.setName("morpheus");
        data.setJob("leader");

        CreateResponseLombokModel response = given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateResponseLombokModel.class);
        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("leader");
    }

    @Test
    void createWithCustomListenerTest() {
        CreateBodyLombokModel data = new CreateBodyLombokModel();
        data.setName("morpheus");
        data.setJob("leader");

        CreateResponseLombokModel response = given()
                .log().uri()
                .log().headers()
                .log().body()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateResponseLombokModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("leader");
    }

    @Test
    void createWithSpecsTest() {
        CreateBodyLombokModel data = new CreateBodyLombokModel();
        data.setName("morpheus");
        data.setJob("leader");

        CreateResponseLombokModel response = given(createRequestSpec)
                .body(data)
                .when()
                .post("/users")
                .then()
                .spec(CreateResponseSpec)
                .extract().as(CreateResponseLombokModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("leader");
    }
}
