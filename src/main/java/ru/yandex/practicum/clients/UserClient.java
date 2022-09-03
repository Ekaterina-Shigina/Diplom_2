package ru.yandex.practicum.clients;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.entity.User;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;

public class UserClient extends BaseClient{

    private static final String URL_CLIENT = "/api/auth/";

    @Step("Регистрация пользователя /api/auth/register")
    public Response registerUser(User user){
        return given()
                .spec(requestSpec())
                .body(user)
                .when()
                .post(URL_CLIENT + "register ");
    }

    @Step("Удаление пользователя /api/auth/user")
    public Response deleteUser(String accessToken){
        return given()
                .spec(requestSpec())
                .header("Authorization", accessToken)
                .delete(URL_CLIENT + "user");

    }

    @Step("Авторизация пользователя /api/auth/login")
    public Response loginUser(User user){
        return given()
                .spec(requestSpec())
                .body(user)
                .when()
                .post(URL_CLIENT + "login");

    }

    @Step("Обновление пользоваеля с авторизацией /api/auth/user")
    public Response updateUser(User user, String accessToken){
        return given()
                .spec(requestSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(URL_CLIENT + "user");
    }

    @Step("Обновление пользоваеля с авторизацией /api/auth/user")
    public Response updateUnAuthorizedUser(User user){
        return given()
                .spec(requestSpec())
                .body(user)
                .when()
                .patch(URL_CLIENT + "user");
    }

}
