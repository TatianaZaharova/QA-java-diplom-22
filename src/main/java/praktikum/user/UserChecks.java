package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;



public class UserChecks {


    @Step("Получение токена после успешного создания пользователя, проверка статус-кода и тела ответа")
    public String checkCreateUser(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", equalTo(true));
        UserResponse user = createResponse.extract().as(UserResponse.class);
        MatcherAssert.assertThat(user.getUser(),
                notNullValue());
        MatcherAssert.assertThat(user.getAccessToken(), notNullValue());
        MatcherAssert.assertThat(user.getRefreshToken(), notNullValue());
        return user.getAccessToken();
    }

    @Step("Проверка статус-кода и тела ответа при создании уже существующего пользователя")
    public void checkDoubleUser(ValidatableResponse doubleCreateResponse) {
        doubleCreateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Step("Получение токена после успешной авторизации. Проверка статус-кода и тела ответа")
    public String checkSuccessLogin(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", equalTo(true));
        UserResponse user = loginResponse.extract().as(UserResponse.class);
        MatcherAssert.assertThat(user.getUser(), notNullValue());
        MatcherAssert.assertThat(user.getAccessToken(), notNullValue());
        MatcherAssert.assertThat(user.getRefreshToken(), notNullValue());
        return user.getAccessToken();
    }

    @Step("Проверка успешного обновления данных пользователя")
    public void checkSuccessUpdate(ValidatableResponse updateResponse) {
        updateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", equalTo(true));
        UserResponse user = updateResponse.extract().as(UserResponse.class);
        MatcherAssert.assertThat(user.getUser().getEmail(), notNullValue());
        MatcherAssert.assertThat(user.getUser().getName(), notNullValue());
    }

    @Step("Проверка невозможности обновить данные пользователя без авторизации")
    public void checkFailedUpdate(ValidatableResponse updateResponse) {
        updateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Проверка невозможности обновить email пользователя на уже использующийся")
    public void checkUpdateAlreadyExistValue(ValidatableResponse updateResponse) {
        updateResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("User with such email already exists"));

    }

    @Step("Проверка успешного удаления пользователя")
    public void checkDeleted(ValidatableResponse deleteResponse) {
        deleteResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .and()
                .body("success", equalTo(true));
    }
    @Step("Проверка невозможности авторизоваться с неверным логином или паролем")
    public void checkLoginWithoutFields(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Проверка невозможности создания пользователя без обязательных полей")
    public void checkCreateWithoutFields(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}