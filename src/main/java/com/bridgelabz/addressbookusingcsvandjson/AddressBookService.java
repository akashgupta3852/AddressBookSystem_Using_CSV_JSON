package com.bridgelabz.addressbookusingcsvandjson;

import java.util.ArrayList;
import java.util.List;

public class AddressBookService {
	private List<ContactPersonDB> contactPersonList;
	private AddressBookDBService addressBookDBService;

	public enum IOService {
		DB_IO
	}

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
		contactPersonList = new ArrayList<>();
	}

	public long getAddressBookName(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.readAddressBookName();
		return 0;
	}

	public long getAddressBookType(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.readAddressBookType();
		return 0;
	}

	public long getAddressDetails(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.readAddressDetails();
		return 0;
	}

	public long getContactPersonDetails(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.readContactPersonDetails().size();
		return 0;
	}

	public void readContactPersonDetails(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			contactPersonList = addressBookDBService.readContactPersonDetails();
	}

	public void updatePhoneNoInDB(String firstName, String lastName, String phoneNo) {
		int result = addressBookDBService.updatePhoneNo(firstName, lastName, phoneNo);
		if (result == 0)
			return;
		ContactPersonDB contactPersonDB = this.getContactPersonData(firstName, lastName);
		if (contactPersonDB != null) {
			contactPersonDB.setPhoneNo(phoneNo);
		}
	}

	private ContactPersonDB getContactPersonData(String firstName, String lastName) {
		ContactPersonDB contactPersonDB = this.contactPersonList.stream()
				.filter(contactPersonListItem -> contactPersonListItem.getFirstName().equals(firstName)
						&& contactPersonListItem.getLastName().equals(lastName))
				.findFirst().orElse(null);
		return contactPersonDB;
	}

	public boolean checkContactPersonDetailsInSyncWithDB(String firstName, String lastName) {
		List<ContactPersonDB> contactPersonList = addressBookDBService.getContactPersonData(firstName, lastName);
		if (contactPersonList.size() == 0)
			return false;
		else
			return contactPersonList.get(0).equals(getContactPersonData(firstName, lastName));
	}

	public List<ContactPersonDB> getContactsByDateRange(String fromDate, String toDate) {
		return addressBookDBService.getContactsByDateRange(fromDate, toDate);
	}

	public List<ContactPersonDB> getContactsByCity(IOService ioService, String cityName) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.getContactsByCity(cityName);
		return null;
	}

	public List<ContactPersonDB> getContactsByState(IOService ioService, String stateName) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.getContactsByState(stateName);
		return null;
	}

	public void addAddressBookDataInDB(String firstName, String lastName, String phoneNo, String email, int addressId,
			int typeId, int bookId, String dateAdded, String address, String cityName, String stateName, String zip) {
		contactPersonList.add(addressBookDBService.addAddressBookDataInDB(firstName, lastName, phoneNo, email,
				addressId, typeId, bookId, dateAdded, address, cityName, stateName, zip));
	}
}
