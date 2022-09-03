package ru.yandex.practicum;

import com.github.javafaker.Faker;
import org.junit.Before;
import ru.yandex.practicum.clients.UserClient;
import ru.yandex.practicum.entity.User;

import java.util.Random;

public class BaseTest {

    private UserClient client = new UserClient();

    static Faker faker = new Faker();
    static Random random = new Random();

    static String email = faker.internet().emailAddress();
    static String name = faker.name().firstName();
    static String password = faker.name().firstName() + random.nextInt(100);

    User user = new User(email, password, name);

    String accessToken;

    public void addUser(){

        accessToken = client.registerUser(user)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");

    }

    public void deleteUser(){
        client.deleteUser(accessToken)
                .then()
                .statusCode(202);
    }

}
