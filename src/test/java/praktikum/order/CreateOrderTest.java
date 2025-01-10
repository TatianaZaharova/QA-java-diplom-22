package praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;

public class CreateOrderTest {

    UserClient userClient = new UserClient();
    UserChecks userChecks = new UserChecks();
    OrderClient orderClient = new OrderClient();
    OrderChecks orderChecks = new OrderChecks();

    private String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            // Проверяем код и тело ответа после удаления
            ValidatableResponse deleteResponse = userClient.deleteUser(accessToken);
            userChecks.checkDeleted(deleteResponse);
        }
    }

    @Test
    @DisplayName("Успешное создание заказа с авторизацией. Переданы ингредиенты")
    public void createOrderWithAuth() {
        var user = User.random();
        ValidatableResponse createUserResponse = userClient.createUser(user);
        accessToken = userChecks.checkCreateUser(createUserResponse);
        ValidatableResponse createOrderResponse = orderClient.createOrderWithAuth(accessToken);
        orderChecks.checkCreateOrderWithAuth(createOrderResponse);
    }

    @Test
    @DisplayName("Неуспешное создание заказа с авторизацией. Ингредиенты не переданы")
    public void createOrderWithAuthNoIngredients() {
        var user = User.random();
        ValidatableResponse createUserResponse = userClient.createUser(user);
        accessToken = userChecks.checkCreateUser(createUserResponse);
        ValidatableResponse createOrderResponse = orderClient.createOrderWithAuthNoIngredients(accessToken);
        orderChecks.checkCreateOrderNoIngredients(createOrderResponse);
    }


    @Test
    @DisplayName("Успешное создание заказа без авторизации")
    public void createOrderWithoutAuth() {
        ValidatableResponse createOrderResponse = orderClient.createOrderWithoutAuth();
        orderChecks.checkCreateOrderWithoutAuth(createOrderResponse);
    }

    @Test
    @DisplayName("Неуспешное создание заказа без авторизации. Ингредиенты не переданы")
    public void createOrderWithoutAuthNoIngredients() {
        ValidatableResponse createOrderResponse = orderClient.createOrderWithoutAuthNoIngredients();
        orderChecks.checkCreateOrderNoIngredients(createOrderResponse);
    }

    @Test
    @DisplayName("Неуспешное создание заказа. Передан невалидный хеш ингредиента")
    public void createOrderWithInvalidIngredients() {
        ValidatableResponse createOrderResponse = orderClient.createOrderWithoutAuthInvalidIngredient();
        orderChecks.checkCreateOrderWithInvalidIngredient(createOrderResponse);
    }
}