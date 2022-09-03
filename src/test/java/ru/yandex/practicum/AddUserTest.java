package ru.yandex.practicum;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.clients.UserClient;

import ru.yandex.practicum.entity.User;



import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Enclosed.class)
public class AddUserTest  {



    public static class AddUserWithoutParameterTest extends BaseTest{
        private UserClient client;

        @Before
        public void setUp(){
            client = new UserClient();
        }

        @Test
        @DisplayName("Успешная регистрация пользователя.")
        @Description("Регистрация пользователя с валидными данными. Код ответа 200.")
        public void addValidUserTest(){
            accessToken = client.registerUser(user)
                    .then()
                    .body("user.name", equalTo(user.getName()))
                    .body("user.email", equalTo(user.getEmail()))
                    .body("success", equalTo(true))
                    .statusCode(200)
                    .extract()
                    .path("accessToken");

        }

        @Test
        @DisplayName("Регистрация пользователя, который уже зарегистрирован.")
        @Description("Регистрация пользователя, который уже зарегистрирован. Код ответа 403.")
        public void addDoubleUserTest() throws InterruptedException {
            //подготовка данных - создание пользователя
            addUser();
            Thread.sleep(1000);


            //тест
            client.registerUser(user)
                    .then()
                    .assertThat()
                    .body("success", equalTo(false))
                    .body("message", equalTo("User already exists"))
                    .and()
                    .statusCode(403);
        }


        @After
        public void tearDown(){
            //чистка тестовых данных: удаление пользователя
            deleteUser();

        }

    }

    @RunWith(Parameterized.class)
    public static class AddUserParameterTest{

        UserClient client = new UserClient();

        private final String email;
        private final String password;
        private final String name;

        public AddUserParameterTest(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }

        @Parameterized.Parameters(name = "Iteration #{index}")
        public static Object[][] getUser(){
            return new Object[][]{
                    {null, "123456", "name"},
                    {"123@mail.ru", null, "name"},
                    {"123@mail.ru", "123456", null},

            };
        }

        @Test
        @DisplayName("Регистрация пользователя без обязательного поля.")
        @Description("Регистрация пользователя без обязательного поля. Код ответа 403.")
        public void addIncorrectUserTest() throws InterruptedException {

            User user = new User(email, password, name);


            //тест
            client.registerUser(user)
                    .then()
                    .assertThat()
                    .body("success", equalTo(false))
                    .body("message", equalTo("Email, password and name are required fields"))
                    .and()
                    .statusCode(403);
            Thread.sleep(1000);
        }

    }


}
