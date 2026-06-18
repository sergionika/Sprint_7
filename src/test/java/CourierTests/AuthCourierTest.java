package CourierTests;

import Steps.CourierSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthCourierTest {
    CourierID loginCourier;
    CourierSteps courierSteps = new CourierSteps();

    @Before
    public void seUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        loginCourier = new CourierID("UchihaLogin", "123456");
    }

    @Test
    public void loginCourier() {
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    public void loginCourierIncorrectLogin() {
        loginCourier.setLogin("UchihaLOGIN");
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void loginCourierIncorrectPassword() {
        loginCourier.setPassword("123457");
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void loginCourierWithoutLogin() {
        loginCourier.setLogin(null);
        Response response = courierSteps.loginCourier(loginCourier);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    //Тест в Postman падает с тойже ошибкой
    public void loginCourierWithoutPassword() {
        loginCourier.setPassword(null);
        Response response = courierSteps.loginCourier(loginCourier);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
}
