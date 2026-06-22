package courier_tests;

import steps.OrderSteps;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest extends BaseTest{

    OrderSteps orderSteps = new OrderSteps();

    @Test
    public void getOrdersList() {
        Response response = orderSteps.getOrders();
        response.then().assertThat()
                .statusCode(200)
                .body("orders", notNullValue());

    }
}
