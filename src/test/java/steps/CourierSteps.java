package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.CourierID;
import pojo.NewCourier;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Создать курьера")
    public Response createCourier(NewCourier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Авторизовать курьера")
    public Response loginCourier(CourierID courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удалить курьера")
    public Response deleteCourier(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}
