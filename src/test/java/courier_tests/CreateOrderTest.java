package courier_tests;

import steps.OrderSteps;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.NewOrder;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {
    private final List<String> color;
    OrderSteps orderSteps = new OrderSteps();

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColours() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Test
    public void createOrderWithDifferentListColours() {
        NewOrder order = new NewOrder("Naruto", "Uchiha", "Konoha, 142 apt.", 4,
                "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha",
                this.color);
        Response response = orderSteps.createOrder(order);
        response.then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
