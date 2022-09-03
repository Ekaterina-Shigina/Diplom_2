package ru.yandex.practicum;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.clients.OrderClient;
import ru.yandex.practicum.clients.UserClient;
import ru.yandex.practicum.entity.Order;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class GetUserOrderTest extends BaseTest{

    private OrderClient clientOrder;

    Order order = new Order(new String[] {"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f"});


    @Before
    public void setUp(){
        clientOrder = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов авторизованным пользователем.")
    @Description("Получение списка заказов авторизованным пользователем. Код ответа 200.")
    public void getOrderAuthorizedUserTest() throws InterruptedException {

        //подготовка данных
        //создание нового пользователя
        addUser();
        Thread.sleep(1000);


        //создание заказа
        clientOrder.addOrderAuthorizedUser(accessToken, order)
                .then()
                .statusCode(200);

        //тест получения списка заказов
        clientOrder.getOrderAuthorizedUser(accessToken)
                .then()
                .body("success", equalTo(true))
                .body("orders.size()", is(1))
                .statusCode(200);

        //удаление созданного пользователя
        deleteUser();

    }

    @Test
    @DisplayName("Получение списка заказов не авторизованным пользователем.")
    @Description("Получение списка заказов не авторизованным пользователем. Код ответа 401.")
    public void getOrderUnAuthorizedUserTest(){

        clientOrder.getOrderUnAuthorizedUser()
                .then()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }

}
