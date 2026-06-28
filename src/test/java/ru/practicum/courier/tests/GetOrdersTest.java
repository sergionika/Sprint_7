package ru.practicum.courier.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import ru.practicum.steps.OrderSteps;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.SC_OK;

public class GetOrdersTest extends BaseTest{

    OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получения списка заказов без courierID")
    public void getOrdersList() {
        Response response = orderSteps.getOrders();
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());

    }
}
