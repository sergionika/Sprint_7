package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.pojo.NewOrder;
import static io.restassured.RestAssured.given;
import static ru.practicum.endpoints.createOrder;
import static ru.practicum.endpoints.getOrder;

public class OrderSteps {

    @Step("Создать заказ")
    public Response createOrder(NewOrder order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(createOrder);
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(getOrder);
    }
}

