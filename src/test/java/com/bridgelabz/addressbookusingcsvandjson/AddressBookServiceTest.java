package com.bridgelabz.addressbookusingcsvandjson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.bridgelabz.addressbookusingcsvandjson.AddressBookService.IOService.*;

public class AddressBookServiceTest {
	AddressBookService addressBookService;

	@Before
	public void initialize() {
		addressBookService = new AddressBookService();
	}

	// UC15 - Retrieving data from address_book_name table
	@Test
	public void givenAddressBookInDB_WhenRetrievedAddressBookName_ShouldMatchExactCount() {
		long countEntries = addressBookService.getAddressBookName(DB_IO);
		Assert.assertEquals(5, countEntries);
	}

	// UC15 - Retrieving data from address_book_type table
	@Test
	public void givenAddressBookInDB_WhenRetrievedAddressBookType_ShouldMatchExactCount() {
		long countEntries = addressBookService.getAddressBookType(DB_IO);
		Assert.assertEquals(3, countEntries);
	}

	// UC15 - Retrieving data from address_details table
	@Test
	public void givenAddressBookInDB_WhenRetrievedAddressDetails_ShouldMatchExactCount() {
		long countEntries = addressBookService.getAddressDetails(DB_IO);
		Assert.assertEquals(12, countEntries);
	}

	// UC15 - Retrieving data from contact_person_details table
	@Test
	public void givenAddressBookInDB_WhenRetrievedContactPersonDetails_ShouldMatchExactCount() {
		long countEntries = addressBookService.getContactPersonDetails(DB_IO);
		Assert.assertEquals(12, countEntries);
	}

	// UC16 - Updating phone number in the database
	@Test
	public void givenNewPhoneNumber_WhenUpdated_ShouldSyncWithDatabase() {
		addressBookService.readContactPersonDetails(DB_IO);
		addressBookService.updatePhoneNoInDB("Akash", "Gupta", "9044589948");
		boolean result = addressBookService.checkContactPersonDetailsInSyncWithDB("Akash", "Gupta");
		Assert.assertTrue(result);
	}
}
