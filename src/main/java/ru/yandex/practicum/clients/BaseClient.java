package ru.yandex.practicum.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseClient {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    public RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

}
