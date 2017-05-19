package generated;


import java.io.Serializable;

public class Student implements Serializable{

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private int groupId;

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String value) {
        this.firstName = value;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String value) {
        this.lastName = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    public void setGroupId(int value) {
        groupId = value;
    }

    public int getGroupId() {
        return groupId;
    }

}
