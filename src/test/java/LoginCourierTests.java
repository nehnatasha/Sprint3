import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Авторизация курьера")
public class LoginCourierTests {
    CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Успешный тест - логина курьера")
    public void correctLoginCourierTest() {
        CourierData courier = CourierData.getRandomCourier();
        courierClient.courierRegisterResponse(courier);
        Response loginResponse = courierClient.courierLoginResponse(CourierCredentials.from(courier));
        loginResponse
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(notNullValue());
        int courierId = loginResponse.path("id");
        courierClient.courierDeletedResponse(courierId);
    }

    @Test
    @DisplayName("Неуспешный тест - вызов логина курьера без обязательного параметра (логина)")
    public void incorrectLoginCourierWithOutLoginTest() {
        CourierCredentials courierCredentials = new CourierCredentials(null, RandomStringUtils.randomAlphabetic(10));
        Response loginResponse = courierClient.courierLoginResponse(courierCredentials);
        loginResponse
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест для проверки нессответсвия пары логин-пароль")
    public void incorrectLoginCourierWithPasswordAndLoginTest() {
        CourierData courier = CourierData.getRandomCourier();
        courierClient.courierRegisterResponse(courier);
        courier.login = RandomStringUtils.randomAlphabetic(10);
        Response loginResponse = courierClient.courierLoginResponse(CourierCredentials.from(courier));
        loginResponse.then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Вызов сервиса для логина несуществующего курьера")
    public void nonExistentCourierLoginTest() {
        CourierData courier = CourierData.getRandomCourier();
        Response loginResponse = courierClient.courierLoginResponse(CourierCredentials.from(courier));
        loginResponse.then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
