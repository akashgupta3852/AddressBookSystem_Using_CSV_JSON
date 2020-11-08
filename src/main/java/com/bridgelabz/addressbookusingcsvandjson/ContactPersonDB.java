package com.bridgelabz.addressbookusingcsvandjson;

public class ContactPersonDB {
	private int personId;
	private String firstName, lastName, phoneNo, email;
	private int addressId, typeId, bookId;

	public ContactPersonDB(int personId, String firstName, String lastName, String phoneNo, String email, int addressId,
			int typeId, int bookId) {
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.addressId = addressId;
		this.typeId = typeId;
		this.bookId = bookId;
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

	@Override
	public String toString() {
		return "ContactPersonDB [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", phoneNo=" + phoneNo + ", email=" + email + ", addressId=" + addressId + ", typeId=" + typeId
				+ ", bookId=" + bookId + "]";
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
		if (addressId != other.addressId)
			return false;
		if (bookId != other.bookId)
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
		if (typeId != other.typeId)
			return false;
		return true;
	}
}
