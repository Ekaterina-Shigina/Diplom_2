package ru.yandex.practicum;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.clients.UserClient;
import ru.yandex.practicum.entity.User;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserTest extends BaseTest{

    private UserClient client;


    @Before
    public void setUp() throws InterruptedException {
        client = new UserClient();
        addUser();
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Редактирование данных пользователя авторизованным пользователем")
    @Description("Редактирование данных пользователя авторизованным пользователем. Код ответа")
    public void updateDataAuthorizedUserTest(){

        user.setEmail(faker.internet().emailAddress());
        user.setName(faker.name().firstName());

        client.updateUser(user, accessToken)
                .then()
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()))
                .statusCode(200);

    }
    @Test
    @DisplayName("Редактирование данных пользователя неавторизованным пользователем")
    @Description("Редактирование данных пользователя неавторизованным пользователем. Код ответа 401")
    public void updateDataUnAuthorizedUserTest(){

        //тест
        client.updateUnAuthorizedUser(user)
                .then()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);

    }

    @After
    public void tearDown(){
        //удаление пользователя
        deleteUser();
    }
}
