import org.apache.commons.lang3.RandomStringUtils;

public class CourierData {

    public String login;
    public String password;
    public String firstName;

    public CourierData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static CourierData getRandomCourier() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierData(login, password, firstName);
    }

    public static CourierData getRandomCourierWithOutName() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierData(login, password, null);
    }

    public static CourierData getRandomCourierWithOutPassword(){
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierData(login, null, firstName);
    }

    public static CourierData getRandomCourierWithOutLogin(){
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierData(null, password, firstName);
    }
}
