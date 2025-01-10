package praktikum;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Credentials {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    public static final String BASE_PATH = "/api";
    public static final String VALID_INGREDIENT = "61c0c5a71d1f82001bdaaa6e";
    public static final String INVALID_INGREDIENT = "sdafsdfsdfsdfsdfsdfsdfsd";

    public RequestSpecification spec() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(BASE_PATH);
    }
}