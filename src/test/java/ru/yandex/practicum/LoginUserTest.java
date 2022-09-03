package ru.yandex.practicum;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.clients.UserClient;
import ru.yandex.practicum.entity.User;


import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest extends  BaseTest{

    private UserClient client;


    @Before
    public void setUp(){
        client = new UserClient();
    }

    @Test
    @DisplayName("Авторизация с валидными данными")
    @Description("Авторизация с валидными данными. Код ответа ")
    public void loginValidUserTest() throws InterruptedException {

        //подготовка данных - создание пользователя
        addUser();
        Thread.sleep(1000);

        //тест авторизации
        client.loginUser(user)
                .then()
                .body("user.name", equalTo(user.getName()))
                .body("user.email", equalTo(user.getEmail()))
                .statusCode(200);

        //удаление созданного пользователя
        deleteUser();

    }

    @Test
    @DisplayName("Авторизация с неверными данными")
    @Description("Авторизация под несуществующими данными. Код ответа ")
    public void loginNonValidUserTest(){

        User user = new User("dfdaf@mail.ru", "1563", null);

        client.loginUser(user)
                .then()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(401);
    }

}
