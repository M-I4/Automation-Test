package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testLoginSuccess() {
        String payload = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";
        Response response = given().header("Content-Type", "application/json").body(payload)
                .when().post("/login").then().statusCode(200).extract().response();
        Assert.assertNotNull(response.jsonPath().getString("token"));
    }

    @Test
    public void testLoginFailure() {
        String payload = "{\"email\":\"peter@klaven\"}";
        given().header("Content-Type", "application/json").body(payload)
                .when().post("/login").then().statusCode(400);
    }

    @Test
    public void testGetUsers() {
        Response response = given().when().get("/users?page=2").then().statusCode(200).extract().response();
        Assert.assertTrue(response.jsonPath().getList("data").size() > 0);
    }

    @Test
    public void testCreateUser() {
        String payload = "{\"name\":\"John\",\"job\":\"leader\"}";
        Response response = given().header("Content-Type", "application/json").body(payload)
                .when().post("/users").then().statusCode(201).extract().response();
        Assert.assertEquals(response.jsonPath().getString("name"), "John");
    }

    @Test
    public void testCreateUserBadRequest() {
        String payload = "{}";
        given().header("Content-Type", "application/json").body(payload)
                .when().post("/users").then().statusCode(400);
    }

    @Test
    public void testUpdateUser() {
        String payload = "{\"name\":\"John Updated\",\"job\":\"manager\"}";
        Response response = given().header("Content-Type", "application/json").body(payload)
                .when().put("/users/2").then().statusCode(200).extract().response();
        Assert.assertEquals(response.jsonPath().getString("name"), "John Updated");
    }

    @Test
    public void testDeleteUser() {
        given().when().delete("/users/2").then().statusCode(204);
    }

    @Test
    public void testDeleteNonExistentUser() {
        given().when().delete("/users/999999").then().statusCode(204);
    }
}
