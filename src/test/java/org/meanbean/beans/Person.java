package org.meanbean.beans;

import java.util.Date;

public interface Person {

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    Gender getGender();

    void setGender(Gender gender);

    boolean isEmployed();

    void setEmployed(boolean isEmployed);

    Date getDateOfBirth();

    void setDateOfBirth(Date dateOfBirth);

    Address getAddress();

    void setAddress(Address address);

}