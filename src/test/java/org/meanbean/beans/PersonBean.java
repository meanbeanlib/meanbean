package org.meanbean.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PersonBean /* implements Person */{

	private String firstName;
	private String lastName;
	private Gender gender;
	private boolean isEmployed;
	private Date dateOfBirth;
	private Address address;
	private double favouriteNumber;
	private Set<String> phoneNumbers;
	private List<Double> magicNumbers;
	private Map<Long, Double> index;
	private Collection<Long> collection;
	private ArrayList<String> subscriptions;
	
	public PersonBean() {
		// Do nothing
	}

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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public boolean isEmployed() {
		return isEmployed;
	}

	public void setEmployed(boolean isEmployed) {
		this.isEmployed = isEmployed;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public double getFavouriteNumber() {
		return favouriteNumber;
	}

	public void setFavouriteNumber(double favouriteNumber) {
		this.favouriteNumber = favouriteNumber;
	}

	public Set<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(Set<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<Double> getMagicNumbers() {
		return magicNumbers;
	}

	public void setMagicNumbers(List<Double> magicNumbers) {
		this.magicNumbers = magicNumbers;
	}

	public Map<Long, Double> getIndex() {
		return index;
	}

	public void setIndex(Map<Long, Double> index) {
		this.index = index;
	}

	public Collection<Long> getCollection() {
		return collection;
	}
	
	public void setCollection(Collection<Long> collection) {
		this.collection = collection;
	}
	
	public ArrayList<String> getSubscriptions() {
		return subscriptions;
	}
	
	public void setSubscriptions(ArrayList<String> subscriptions) {
		this.subscriptions = subscriptions;
	}
	
}
