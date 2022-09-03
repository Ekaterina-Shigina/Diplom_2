package ru.yandex.practicum;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.clients.OrderClient;
import ru.yandex.practicum.clients.UserClient;
import ru.yandex.practicum.entity.Order;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Enclosed.class)
public class AddOrderTest {

    @RunWith(Parameterized.class)
    public static class AddOrderParameterTest extends BaseTest{

        private OrderClient clientOrder;
        Order order;


        private final  String[] ingredients;
        private final int responseCode;
        private final boolean success;
        private final String body;
        private final String text;

        public AddOrderParameterTest(String[] ingredients, boolean success, int responseCode, String body, String text) {
            this.ingredients = ingredients;
            this.success = success;
            this.responseCode = responseCode;
            this.body = body;
            this.text = text;
        }

        @Parameterized.Parameters(name = "Iteration #{index}")
        public static Object[][] addIngridients(){
            return new Object[][]{
                    {new String []{"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f"}, true, 200, "name", "Бессмертный флюоресцентный бургер"},
                    {new String []{}, false, 400, "message", "Ingredient ids must be provided"},
            };
        }


        @Before
        public void setUp(){
            clientOrder = new OrderClient();
            order = new Order(ingredients);
        }


        @Test
        @DisplayName("Создание заказа без авторизации.")
        @Description("Создание заказа без авторизации.")
        public void addOrderUnAuthorizedUserWithIngredientsTest() throws InterruptedException {
            clientOrder.addOrderUnAuthorizedUser(order)
                    .then()
                    .body("success", equalTo(success))
                    .body(body, equalTo(text))
                    .statusCode(responseCode);
            Thread.sleep(1000);
        }


        @Test
        @DisplayName("Создание заказа с авторизацей.")
        @Description("Создание заказа с авторизацей.")
        public void addOrderAuthorizedUserWithIngredientsTest() throws InterruptedException {

            //подготовка данных
            UserClient client = new UserClient();
            //создание пользователя
            addUser();
            Thread.sleep(1000);

            //тест с авторизацией
            clientOrder.addOrderAuthorizedUser(accessToken, order)
                    .then()
                    .body("success", equalTo(success))
                    .body(body, equalTo(text))
                    .statusCode(responseCode);

            //удаление созданного пользователя
            deleteUser();
        }

    }
    public static class AddOrderWithoutParameterTest {

        OrderClient clientOrder = new OrderClient();

        private String[] ingredients = {"dfgdfgdfg"};
        Order order = new Order(ingredients);


        @Test
        @DisplayName("Создание заказа без авторизации. Неверный хеш ингридиента.")
        @Description("Создание заказа без авторизации. Неверный хеш ингридиента. Код ответа 500")
        public void addOrderAuthorizedUserWithIngredientsTest(){

            clientOrder.addOrderUnAuthorizedUser(order)
                    .then()
                    .statusCode(500);
        }
    }

}
