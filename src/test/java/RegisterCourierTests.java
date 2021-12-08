import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("Тесты на регистрацию курьера")
public class RegisterCourierTests {
    CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Успешное оздание курьера")
    public void registerCourierTest() {
        CourierData courier = CourierData.getRandomCourier();
        Response response = courierClient.courierRegisterResponse(courier);
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        courierClient.courierDeletedResponse(courierClient.getCourierId(CourierCredentials.from(courier)));
    }

    @Test
    @DisplayName("Создание клиента с существующим логином")
    public void createCourierDuplicateLoginTest() {
        CourierData courier = CourierData.getRandomCourier();
        Response responseForFirstCourier = courierClient.courierRegisterResponse(courier);
        Response responseForSecondCourier = courierClient.courierRegisterResponse(courier);
        responseForSecondCourier.then()
                .assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierClient.courierDeletedResponse(courierClient.getCourierId(CourierCredentials.from(courier)));

    }

    @Test
    @DisplayName("Регистарция курьера без обязательного поля (логина)")
    public void registerCourierWithOutRequiredFieldLoginTest() {
        CourierData courier = CourierData.getRandomCourierWithOutLogin();
        Response response = courierClient.courierRegisterResponse(courier);
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Регистарция курьера без обязательного поля (пароля)")
    public void registerCourierWithOutRequiredFieldPasswordTest() {
        CourierData courier = CourierData.getRandomCourierWithOutPassword();
        Response response = courierClient.courierRegisterResponse(courier);
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    //тест падает, так как есть возможность зарегистрировать курьера без пароля
    @Test
    @DisplayName("Регистарция курьера без обязательного поля (имя)")
    public void registerCourierWithOutRequiredFieldNameTest() {
        CourierData courier = CourierData.getRandomCourierWithOutName();
        Response response = courierClient.courierRegisterResponse(courier);
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
