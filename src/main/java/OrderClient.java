import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends ApiClient{

    private final String baseUrl = "api/v1/orders";

    @Step("Получение заказов")
    public Response getOrdersResponse(int courierId) {
        return given()
                .spec(getBaseSpec())
                .and()
                .queryParam("courierId", courierId)
                .when()
                .get(baseUrl);
    }

    @Step("Создание заказа")
    public Response createOrderResponse(OrderData order){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post(baseUrl);
    }

    @Step("Отмена заказа")
    public Response cancelOrderResponse(int id){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(id)
                .when()
                .put(baseUrl + "/cancel");
    }

    @Step("Получение айди заказа")
    public int getOrderIdByTrack(int trackId){
        Response response =  given()
                .spec(getBaseSpec())
                .and()
                .queryParam("t",trackId)
                .when()
                .get(baseUrl + "/track");
        return response.body().path("order.id");
    }

    @Step("Подтверждение заказа")
    public Response acceptOrderResponse(int orderId, int courierId){
        return given()
                .spec(getBaseSpec())
                .and()
                .queryParam("courierId",courierId)
                .when()
                .put(baseUrl + "/accept/" + orderId);
    }
}
