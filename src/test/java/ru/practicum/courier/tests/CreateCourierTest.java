package ru.practicum.courier.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import ru.practicum.pojo.CourierID;
import ru.practicum.steps.CourierSteps;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.pojo.NewCourier;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;

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
    @DisplayName("Создание курьера")
    @Description("Проверка успешного создания нового курьера")
    public void addCourier() {
        Response response = courierSteps.createCourier(courier);
        courierId = courierSteps.loginCourier(
                        new CourierID(courier.getLogin(), courier.getPassword()))
                        .path("id");
        response.then()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка ошибки при создании второго курьера с одинаковыми данными")
    public void doubleAddCourier() {
        courierSteps.createCourier(courier);
        courierId = courierSteps.loginCourier(
                        new CourierID(courier.getLogin(), courier.getPassword()))
                        .path("id");
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка ошибки при создании курьера без заполненого поля login")
    public void addCourierWithoutLogin() {
        courier.setLogin(null);
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка ошибки при создании курьера без заполненого поля password")
    public void addCourierWithoutPassword() {
        courier.setPassword(null);
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Проверка ошибки при создании курьера без заполненого поля firstName")
    public void addCourierWithoutFirstName() {
        courier.setFirstName(null);
        Response response = courierSteps.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
