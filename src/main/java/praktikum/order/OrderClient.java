package praktikum.order;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Credentials;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.model.Parameter.Mode.HIDDEN;

public class OrderClient extends Credentials {

    @Step("Создание заказа с авторизацией. Ингредиенты переданы")
    public ValidatableResponse createOrderWithAuth(@Param(mode = HIDDEN) String accessToken) {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(VALID_INGREDIENT);
        IngredientList ingredientList = new IngredientList(ingredients);
        return spec()
                .header("Authorization", accessToken)
                .body(ingredientList)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Создание заказа с авторизацией. Ингредиенты не переданы")
    public ValidatableResponse createOrderWithAuthNoIngredients(@Param(mode = HIDDEN) String accessToken) {
        List<String> ingredients = new ArrayList<>();
        IngredientList ingredientList = new IngredientList(ingredients);
        return spec()
                .header("Authorization", accessToken)
                .body(ingredientList)
                .when()
                .post("/orders")
                .then().log().all();
    }


    @Step("Создание заказа без авторизации. Ингредиенты переданы")
    public ValidatableResponse createOrderWithoutAuth() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(VALID_INGREDIENT);
        IngredientList ingredientList = new IngredientList(ingredients);
        return spec()
                .body(ingredientList)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Создание заказа без авторизации. Ингредиенты не переданы")
    public ValidatableResponse createOrderWithoutAuthNoIngredients() {
        List<String> ingredients = new ArrayList<>();
        IngredientList ingredientList = new IngredientList(ingredients);
        return spec()
                .body(ingredientList)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Создание заказа без авторизации. Передан невалидный хеш ингредиента")
    public ValidatableResponse createOrderWithoutAuthInvalidIngredient() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(INVALID_INGREDIENT);
        IngredientList ingredientList = new IngredientList(ingredients);
        return spec()
                .body(ingredientList)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Получение заказов конкретного пользователя с авторизацией")
    public ValidatableResponse getAllOrders(@Param(mode = HIDDEN) String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get("/orders")
                .then().log().all();
    }

    @Step("Получение заказов пользователя без авторизации")
    public ValidatableResponse getUserOrders() {
        return spec()
                .when()
                .get("/orders")
                .then().log().all();
    }

}