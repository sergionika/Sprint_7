package ru.practicum.courier.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import ru.practicum.steps.CourierSteps;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.pojo.CourierID;
import org.junit.After;
import ru.practicum.pojo.NewCourier;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

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

        if (response.statusCode() == SC_CREATED) {
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
    @DisplayName("Авторизация курьера с корректными данными")
    @Description("Проверка успешной авторизации существующего курьера")
    public void loginCourier() {
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным логином")
    @Description("Проверка ошибки авторизации с неверным логином")
    public void loginCourierIncorrectLogin() {
        loginCourier.setLogin("UchihaLOGIN");
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным паролем")
    @Description("Проверка ошибки авторизации с неверным паролем")
    public void loginCourierIncorrectPassword() {
        loginCourier.setPassword("123457");
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка ошибки авторизации с пустым полем login")
    public void loginCourierWithoutLogin() {
        loginCourier.setLogin(null);
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка ошибки авторизации с пустым полем password")
    public void loginCourierWithoutPassword() {
        loginCourier.setPassword(null);
        Response response = courierSteps.loginCourier(loginCourier);
        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
