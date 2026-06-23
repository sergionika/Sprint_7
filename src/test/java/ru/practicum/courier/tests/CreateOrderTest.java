package ru.practicum.courier.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import ru.practicum.steps.OrderSteps;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.pojo.NewOrder;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {
    private final List<String> color;
    OrderSteps orderSteps = new OrderSteps();

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getColours() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    @Description("Проверка создания заказа для различных комбинаций цветов самоката")
    public void createOrderWithDifferentListColours() {
        NewOrder order = new NewOrder("Naruto", "Uchiha", "Konoha, 142 apt.", 4,
                "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha",
                this.color);
        Response response = orderSteps.createOrder(order);
        response.then()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}
