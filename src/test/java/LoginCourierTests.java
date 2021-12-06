import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        step("Выбираем нового курьера", () -> {
            CourierData courier = CourierData.getRandomCourier();
            step("Регистрируем курьера", () -> {
                courierClient.courierRegisterResponse(courier);
                step("Вызвать сервис логина курьера с данными", () -> {
                    Response loginResponse = courierClient.courierLoginResponse(CourierCredentials.from(courier));
                    Assertions.assertAll(
                            () -> assertEquals(200, loginResponse.getStatusCode()),
                            () -> assertNotNull(loginResponse.getBody())
                    );
                    int courierId = loginResponse.path("id");
                    courierClient.courierDeletedResponse(courierId);
                });
            });
        });
    }

    @Test
    @DisplayName("Неуспешный тест - вызов логина курьера без обязательного параметра (логина)")
    public void incorrectLoginCourierWithOutLoginTest() {
        step("Для запроса логина не указываем логин крьера", () -> {
            CourierCredentials courierCredentials = new CourierCredentials(null, RandomStringUtils.randomAlphabetic(10));
            step("Вызвать сервис логина курьера с данными", () -> {
                Response loginResponse = courierClient.courierLoginResponse(courierCredentials);
                loginResponse
                        .then()
                        .assertThat()
                        .statusCode(400)
                        .and()
                        .body("message", equalTo("Недостаточно данных для входа"));
            });
        });
    }

    @Test
    @DisplayName("Тест для проверки нессответсвия пары логин-пароль")
    public void incorrectLoginCourierWithPasswordAndLoginTest() {
        step("Выбираем нового курьера", () -> {
            CourierData courier = CourierData.getRandomCourier();
            step("Регистрируем курьера", () -> {
                courierClient.courierRegisterResponse(courier);
                step("генерируем пароль", () -> {
                    courier.login = RandomStringUtils.randomAlphabetic(10);
                    step("Вызвать сервис логина курьера с данными", () -> {
                        Response loginResponse = courierClient.courierLoginResponse(CourierCredentials.from(courier));
                        loginResponse.then()
                                .assertThat()
                                .statusCode(404)
                                .and()
                                .body("message", equalTo("Учетная запись не найдена"));
                    });
                });
            });
        });
    }

    @Test
    @DisplayName("Вызов сервиса для логина несуществующего курьера")
    public void nonExistentCourierLoginTest() {
        step("Выбираем нового курьера", () -> {
            CourierData courier = CourierData.getRandomCourier();
            step("Вызвать сервис логина курьера с данными", () -> {
                Response loginResponse = courierClient.courierLoginResponse(CourierCredentials.from(courier));
                loginResponse.then()
                        .assertThat()
                        .statusCode(404)
                        .and()
                        .body("message", equalTo("Учетная запись не найдена"));
            });
        });
    }

}
