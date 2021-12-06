import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient extends ApiClient{

    private final String baseUrl = "api/v1/courier";
    private final String baseUrlLogin = "api/v1/courier/login";

    @Step("Регистрация курьера")
    public Response courierRegisterResponse(CourierData courier){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .when()
                .post(baseUrl);
    }

    @Step("Удаление курьера")
    public void courierDeletedResponse(int id){
        given()
                .spec(getBaseSpec())
                .when()
                .delete(baseUrl + id);
    }

    @Step("Логин курьера")
    public Response courierLoginResponse(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courierCredentials)
                .when()
                .post(baseUrlLogin);
    }

    @Step("Получение айди курьера")
    public int getCourierId(CourierCredentials courierCredentials){
        int courierId = 0;
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(courierCredentials)
                .when()
                .post(baseUrlLogin);

        if (response.statusCode() == 200) {
            JsonPath path = response.jsonPath();
            courierId = path.get("id");
        }
        return courierId;
    }
}
