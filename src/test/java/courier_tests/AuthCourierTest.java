package courier_tests;

import steps.CourierSteps;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierID;
import org.junit.After;
import pojo.NewCourier;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthCourierTest extends BaseTest {
    NewCourier courier;
    CourierID loginCourier;
    CourierSteps courierSteps = new CourierSteps();
    Integer courierId;

    @Before
    public void seUp() {
        courier = new NewCourier("UchihaLogin" + System.currentTimeMillis(), "123456", "Sasuke");
        loginCourier = new CourierID(courier.getLogin(), courier.getPassword());

        Response response = courierSteps.createCourier(courier);

        if (response.statusCode() == 201) {
            Response loginResponse = courierSteps.loginCourier(loginCourier);
            courierId = loginResponse.path("id");
        }
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierSteps.deleteCourier(courierId);
        }
    }

    @Test
    public void loginCourier() {
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void loginCourierIncorrectLogin() {
        loginCourier.setLogin("UchihaLOGIN");
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierIncorrectPassword() {
        loginCourier.setPassword("123457");
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWithoutLogin() {
        loginCourier.setLogin(null);
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    //Тест в Postman падает с тойже ошибкой
    public void loginCourierWithoutPassword() {
        loginCourier.setPassword(null);
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
