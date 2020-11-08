package com.bridgelabz.addressbookusingcsvandjson;

public class AddressBookService {
	private AddressBookDBService addressBookDBService;

	public enum IOService {
		DB_IO
	}

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public long getAddressBookNameFromDB(IOService ioService) {
		return addressBookDBService.readAddressBookName();
	}

	public long getAddressBookTypeFromDB(IOService ioService) {
		return addressBookDBService.readAddressBookType();
	}

	public long getAddressDetailsFromDB(IOService ioService) {
		return addressBookDBService.readAddressDetails();
	}

	public long getContactPersonDetailsFromDB(IOService ioService) {
		return addressBookDBService.readContactPersonDetails();
	}
}
