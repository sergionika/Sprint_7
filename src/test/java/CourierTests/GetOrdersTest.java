package CourierTests;

import Steps.OrderSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest {

    OrderSteps orderSteps = new OrderSteps();

    @Test
    public void getOrdersList() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        Response response = orderSteps.getOrders();
        response.then().assertThat()
                .body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
