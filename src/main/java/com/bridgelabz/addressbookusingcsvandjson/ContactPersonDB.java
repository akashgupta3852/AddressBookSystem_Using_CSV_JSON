package com.bridgelabz.addressbookusingcsvandjson;

import java.time.LocalDate;

public class ContactPersonDB {
	private int personId;
	private String firstName, lastName, phoneNo, email;
	private int addressId, typeId, bookId;
	private LocalDate dateAdded;
	private String address, cityName, stateName, zip;

	public ContactPersonDB(int personId, String firstName, String lastName, String phoneNo, String email, int addressId,
			int typeId, int bookId, LocalDate dateAdded) {
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.addressId = addressId;
		this.typeId = typeId;
		this.bookId = bookId;
		this.dateAdded = dateAdded;
	}

	public ContactPersonDB(String firstName, String lastName, String phoneNo, String email, int addressId, int typeId,
			int bookId, LocalDate dateAdded, String address, String cityName, String stateName, String zip) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.addressId = addressId;
		this.typeId = typeId;
		this.bookId = bookId;
		this.dateAdded = dateAdded;
		this.address = address;
		this.cityName = cityName;
		this.stateName = stateName;
		this.zip = zip;
	}

	public int getPersonId() {
		return personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public int getAddressId() {
		return addressId;
	}

	public int getTypeId() {
		return typeId;
	}

	public int getBookId() {
		return bookId;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public String getAddress() {
		return address;
	}

	public String getCityName() {
		return cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public String getZip() {
		return zip;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return "ContactPersonDB [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", phoneNo=" + phoneNo + ", email=" + email + ", addressId=" + addressId + ", typeId=" + typeId
				+ ", bookId=" + bookId + ", dateAdded=" + dateAdded + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + addressId;
		result = prime * result + bookId;
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((dateAdded == null) ? 0 : dateAdded.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + personId;
		result = prime * result + ((phoneNo == null) ? 0 : phoneNo.hashCode());
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
		result = prime * result + typeId;
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		ContactPersonDB other = (ContactPersonDB) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (addressId != other.addressId)
			return false;
		if (bookId != other.bookId)
			return false;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		if (dateAdded == null) {
			if (other.dateAdded != null)
				return false;
		} else if (!dateAdded.equals(other.dateAdded))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (personId != other.personId)
			return false;
		if (phoneNo == null) {
			if (other.phoneNo != null)
				return false;
		} else if (!phoneNo.equals(other.phoneNo))
			return false;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		if (typeId != other.typeId)
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
}
