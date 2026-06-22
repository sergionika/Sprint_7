package courier_tests;

import org.junit.After;
import pojo.CourierID;
import steps.CourierSteps;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import pojo.NewCourier;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends BaseTest {
    NewCourier courier;
    CourierSteps courierSteps = new CourierSteps();
    Integer courierId;

    @Before
    public void setUp() {
        courier = new NewCourier("UzumakiLogin", "12345", "Naruto");
    }

    @After
    public void tearDown(){
        if (courierId != null) {
            courierSteps.deleteCourier(courierId);
        }
    }

    @Test
    public void addCourier() {
        Response response = courierSteps.createCourier(courier);
        courierId = courierSteps.loginCourier(
                        new CourierID(courier.getLogin(), courier.getPassword()))
                        .path("id");
        response.then()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void doubleAddCourier() {
        courierSteps.createCourier(courier);
        courierId = courierSteps.loginCourier(
                        new CourierID(courier.getLogin(), courier.getPassword()))
                        .path("id");
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void addCourierWithoutLogin() {
        courier.setLogin(null);
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void addCourierWithoutPassword() {
        courier.setPassword(null);
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    //Тест в Postman падает с тойже ошибкой
    public void addCourierWithoutFirstName() {
        courier.setFirstName(null);
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
