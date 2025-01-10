package praktikum.user;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.qameta.allure.model.Parameter.Mode.HIDDEN;


public class UserClient extends praktikum.Credentials {

    // Создание пользователя
    @Step("Создание уникального пользователя")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post("/auth/register")
                .then().log().all();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(UserCredentials user) {
        return spec()
                .body(user)
                .when()
                .post("/auth/login")
                .then().log().all();
    }


    @Step("Изменяем данные пользователя")
    // Добавили аннотацию для аргумента, который нужно скрыть (accessToken)
    public ValidatableResponse updateUser(@Param(mode = HIDDEN) String accessToken, User updateUser) {
        return spec()
                .header("Authorization", accessToken)
                .body(updateUser)
                .when()
                .patch("/auth/user")
                .then().log().all();
    }


    @Step("Удаление пользователя")
    // Добавили аннотацию для аргумента, который нужно скрыть (accessToken)
    public ValidatableResponse deleteUser(@Param(mode = HIDDEN) String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete("/auth/user")
                .then().log().all();
    }
}