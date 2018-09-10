package myprojects.automation.assignment5.model;



public class BuyerData {
    private String name;
    private String surname;
    private String email;
    private String address = "ул.Драйзера, 55";

    public String getAddress() {
        return address;
    }

    public String getPost() {
        return post;
    }

    public String getCity() {
        return city;
    }

    private String post = "02166";
    private String city = "Киев";

    public BuyerData(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public static BuyerData generate() {

        return new BuyerData(
                "NewBuyerName",
                "NewBuyerSurname",
                "email" + System.currentTimeMillis() + "@gmail.com"
        );
    }

}
