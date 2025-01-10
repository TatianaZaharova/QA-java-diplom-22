package praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;

public class GetOrderTest {

    OrderClient orderClient = new OrderClient();
    OrderChecks orderChecks = new OrderChecks();
    UserClient userClient = new UserClient();
    UserChecks userChecks = new UserChecks();

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
    @DisplayName("Получение заказов конкретного пользователя с авторизацией")
    public void getUserOrdersWithAuth() {
        var user = User.random();
        ValidatableResponse createUserResponse = userClient.createUser(user);
        accessToken = userChecks.checkCreateUser(createUserResponse);
        orderClient.createOrderWithAuth(accessToken);
        ValidatableResponse getOrdersResponse = orderClient.getAllOrders(accessToken);
        orderChecks.checkSuccessGetAllOrders(getOrdersResponse);
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void getUserOrdersWithoutAuth() {
        ValidatableResponse getOrderResponse = orderClient.getUserOrders();
        orderChecks.checkFailedGetOrders(getOrderResponse);
    }

}