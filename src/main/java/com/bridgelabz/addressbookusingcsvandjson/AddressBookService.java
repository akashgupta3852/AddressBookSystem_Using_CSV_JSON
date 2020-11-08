package com.bridgelabz.addressbookusingcsvandjson;

import java.util.List;

public class AddressBookService {
	private List<ContactPersonDB> contactPersonList;
	private AddressBookDBService addressBookDBService;

	public enum IOService {
		DB_IO
	}

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public long getAddressBookName(IOService ioService) {
		return addressBookDBService.readAddressBookName();
	}

	public long getAddressBookType(IOService ioService) {
		return addressBookDBService.readAddressBookType();
	}

	public long getAddressDetails(IOService ioService) {
		return addressBookDBService.readAddressDetails();
	}

	public long getContactPersonDetails(IOService ioService) {
		return addressBookDBService.readContactPersonDetails().size();
	}

	public void readContactPersonDetails(IOService ioService) {
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
		return contactPersonList.get(0).equals(getContactPersonData(firstName, lastName));
	}
}
