package co.ninja.qa.api.beans;

public class Contact {
    private String name;
    private String phone_number;
    private String email;
    private Address address;

    public Contact(String name, String phone_number, String email, Address address) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
