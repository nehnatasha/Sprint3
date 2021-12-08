import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Тест на получение списка заказов")
public class GetOrdersListTests {

    OrderClient orderClient;
    CourierClient courierClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Тест для получения списка заказа")
    public void positiveGetOrdersListTest() {
        CourierData courier = CourierData.getRandomCourier();
        OrderData order = OrderData.getRandomOrder(ColorsOfScooter.ALL);
        Response responseCreateOrder = orderClient.createOrderResponse(order);
        int orderId = orderClient.getOrderIdByTrack(responseCreateOrder.body().path("track"));
        Response response = courierClient.courierRegisterResponse(courier);
        int courierId = courierClient.getCourierId(CourierCredentials.from(courier));
        orderClient.acceptOrderResponse(orderId, courierId);
        Response responseGetOrder = orderClient.getOrdersResponse(courierId);
        responseGetOrder.then()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
