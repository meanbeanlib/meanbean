package org.meanbean.test.beans;

import java.util.Date;

/**
 * A bean with properties of differing types. It has a business method (which does nothing). It has a read only getter.
 * It has a write-only ID property. The equals and hashCode methods are correctly implemented and do not consider ID.
 * This class should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class ComplexBean {

	private long id; // write-only

	private String firstName;// read/write

	private String lastName;// read/write

	private Date dateOfBirth;// read-write

	private long favouriteNumber;// read/write

	// also has a read-only property asString (getAsString)

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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public long getFavouriteNumber() {
		return favouriteNumber;
	}

	public void setFavouriteNumber(long favouriteNumber) {
		this.favouriteNumber = favouriteNumber;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAsString() {
		return toString();
	}

	public void doSomeBusinessLogic() {
		// Do nothing
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + (int) (favouriteNumber ^ (favouriteNumber >>> 32));
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexBean other = (ComplexBean) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (favouriteNumber != other.favouriteNumber)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComplexBean [id=").append(id).append(", firstName=").append(firstName).append(", lastName=")
		        .append(lastName).append(", dateOfBirth=").append(dateOfBirth).append(", favouriteNumber=")
		        .append(favouriteNumber).append("]");
		return builder.toString();
	}
}