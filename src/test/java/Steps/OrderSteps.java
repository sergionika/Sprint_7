package Steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.NewOrder;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создать заказ")
    public Response createOrder(NewOrder order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
    }
}

