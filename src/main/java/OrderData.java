import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class OrderData {

    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final String[] color;

    public OrderData(String firstName, String lastName, String address, String metroStation, String phone, int rentTime,
                 String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static OrderData getRandomOrder(String whatColor){
        Faker faker = new Faker();
        Random random = new Random();
        OrderData order = null;
        final String firstName = faker.superhero().name();
        final String lastName = faker.superhero().power();
        final String address = faker.address().fullAddress();
        final String metroStation = faker.country().name();
        final String phone = faker.phoneNumber().cellPhone();
        final int rentTime = random.nextInt(10)+1;
        final String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        final String comment = faker.shakespeare().hamletQuote();
        if(whatColor.equals("ALL")) {
            final String[] color = {"BLACK", "GRAY"};
            order = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        }
        else if(whatColor.equals("BLACK")){
            final String[] color = {"BLACK"};
            order = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        }
        else if(whatColor.equals("GRAY")){
            final String[] color = {"GRAY"};
            order = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        }
        else if(whatColor.equals("NONE")){
            final String[] color = null;
            order = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        }
        return order;
    }
}
