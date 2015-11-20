package com.example.utd.edu;

/**
 * Created by Swathi and Ranjani on 11/3/2015.
 * Class Description: Class for creating value objects. It contains the getters and setters.
 * Have also implemented the Comparable interface and have overridden the compareTo method to compare ContactManagerVO objects.
 */
public class ContactManagerVO implements Comparable<ContactManagerVO> {
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "ContactManagerVO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                '}';
    }
    @Override
    public int compareTo(ContactManagerVO contactObject) {
        String firstName = ( contactObject.getFirstName()).toUpperCase();
        String firstNameCurrent = this.firstName.toUpperCase();
        int i = firstNameCurrent .compareTo(firstName);
        return i;
    }

}
