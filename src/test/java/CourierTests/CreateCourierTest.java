package CourierTests;

import Steps.CourierSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import pojo.NewCourier;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    NewCourier courier;
    CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void addCourier() {
        courier = new NewCourier("UzumakiLogin", "12345", "Naruto");
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    public void doubleAddCourier() {
        courier = new NewCourier("UzumakiLogin", "12345", "Naruto");
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    public void addCourierWithoutLogin() {
        courier = new NewCourier("UzumakiLogin2", "12345", "Naruto2");
        courier.setLogin(null);
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    @Test
    public void addCourierWithoutPassword() {
        courier = new NewCourier("UzumakiLogin3", "12345", "Naruto3");
        courier.setPassword(null);
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    @Test
    //Тест в Postman падает с тойже ошибкой
    public void addCourierWithoutFirstName() {
        courier = new NewCourier("UzumakiLogin4", "12345", "Naruto4");
        courier.setFirstName(null);
        Response response = courierSteps.createCourier(courier);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
