package com.bridgelabz.addressbookusingcsvandjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// Reading contacts from the database
	public void readContactPersonDetails(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			contactPersonList = addressBookDBService.readContactPersonDetails();
	}

	// Updating the phone number in database
	public void updatePhoneNoInDB(String firstName, String lastName, String phoneNo) {
		int result = addressBookDBService.updatePhoneNo(firstName, lastName, phoneNo);
		if (result == 0)
			return;
		ContactPersonDB contactPersonDB = this.getContactPersonData(firstName, lastName);
		if (contactPersonDB != null) {
			contactPersonDB.setPhoneNo(phoneNo);
		}
	}

	// Retrieving contacts from database
	private ContactPersonDB getContactPersonData(String firstName, String lastName) {
		ContactPersonDB contactPersonDB = this.contactPersonList.stream()
				.filter(contactPersonListItem -> contactPersonListItem.getFirstName().equals(firstName)
						&& contactPersonListItem.getLastName().equals(lastName))
				.findFirst().orElse(null);
		return contactPersonDB;
	}

	// Checking whether contacts are sync or not after adding to DB
	public boolean checkContactPersonDetailsInSyncWithDB(String firstName, String lastName) {
		List<ContactPersonDB> contactPersonList = addressBookDBService.getContactPersonData(firstName, lastName);
		if (contactPersonList.size() == 0)
			return false;
		else
			return contactPersonList.get(0).equals(getContactPersonData(firstName, lastName));
	}

	// Retrieving all the contacts which were added in a particular period
	public List<ContactPersonDB> getContactsByDateRange(String fromDate, String toDate) {
		return addressBookDBService.getContactsByDateRange(fromDate, toDate);
	}

	// Retrieving all the contacts by city name
	public List<ContactPersonDB> getContactsByCity(IOService ioService, String cityName) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.getContactsByCity(cityName);
		return null;
	}

	// Retrieving all the contacts by state name
	public List<ContactPersonDB> getContactsByState(IOService ioService, String stateName) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.getContactsByState(stateName);
		return null;
	}

	// Adding a address book data to database
	public void addAddressBookDataInDB(String firstName, String lastName, String phoneNo, String email, int addressId,
			int typeId, int bookId, String dateAdded, String address, String cityName, String stateName, String zip) {
		contactPersonList.add(addressBookDBService.addAddressBookDataInDB(firstName, lastName, phoneNo, email,
				addressId, typeId, bookId, dateAdded, address, cityName, stateName, zip));
	}

	// Adding multiple address book data without using threads
	public void addContactsToDB(List<ContactPersonDB> contactsList) {
		contactsList.forEach(contactPersonData -> {
			this.addAddressBookDataInDB(contactPersonData.getFirstName(), contactPersonData.getLastName(),
					contactPersonData.getPhoneNo(), contactPersonData.getEmail(), contactPersonData.getAddressId(),
					contactPersonData.getTypeId(), contactPersonData.getBookId(),
					contactPersonData.getDateAdded().toString(), contactPersonData.getAddress(),
					contactPersonData.getCityName(), contactPersonData.getStateName(), contactPersonData.getZip());
		});
	}

	// Adding multiple address book data using threads
	public void addaddContactsToDBWithThreads(List<ContactPersonDB> contactsList) {
		Map<Integer, Boolean> contactsAdditionStatus = new HashMap<Integer, Boolean>();
		contactsList.forEach(contactPersonData -> {
			Runnable task = () -> {
				contactsAdditionStatus.put(contactPersonData.hashCode(), false);
				this.addAddressBookDataInDB(contactPersonData.getFirstName(), contactPersonData.getLastName(),
						contactPersonData.getPhoneNo(), contactPersonData.getEmail(), contactPersonData.getAddressId(),
						contactPersonData.getTypeId(), contactPersonData.getBookId(),
						contactPersonData.getDateAdded().toString(), contactPersonData.getAddress(),
						contactPersonData.getCityName(), contactPersonData.getStateName(), contactPersonData.getZip());
				contactsAdditionStatus.put(contactPersonData.hashCode(), true);
			};
			Thread thread = new Thread(task);
			thread.start();
		});
		while (contactsAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
