package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.pojo.CourierID;
import ru.practicum.pojo.NewCourier;
import static io.restassured.RestAssured.given;
import static ru.practicum.endpoints.*;

public class CourierSteps {

    @Step("Создать курьера")
    public Response createCourier(NewCourier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(createCourier);
    }

    @Step("Авторизовать курьера")
    public Response loginCourier(CourierID courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(loginCourier);
    }

    @Step("Удалить курьера")
    public Response deleteCourier(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(deleteCourier + courierId);
    }
}
