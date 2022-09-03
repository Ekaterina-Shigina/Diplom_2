package ru.yandex.practicum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.entity.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {

    private static final String URL_ORDER = "/api/orders";

    @Step("Создание заказа с авторизацией")
    public Response addOrderAuthorizedUser(String accessToken, Order order){
        return given()
                .spec(requestSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(URL_ORDER);
    }

    @Step("Создание заказа без авторизации")
    public Response addOrderUnAuthorizedUser(Order order){
        return given()
                .spec(requestSpec())
                .body(order)
                .when()
                .post(URL_ORDER);
    }

    @Step("Получение заказов пользователя с авторизацией")
    public Response getOrderAuthorizedUser(String accessToken){
        return given()
                .spec(requestSpec())
                .header("Authorization", accessToken)
                .when()
                .get(URL_ORDER);
    }

    @Step("Получение списка заказов без авторизации")
    public Response getOrderUnAuthorizedUser(){
        return given()
                .spec(requestSpec())
                .when()
                .get(URL_ORDER);
    }

}
