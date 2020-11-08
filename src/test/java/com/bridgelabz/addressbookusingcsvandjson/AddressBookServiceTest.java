package com.bridgelabz.addressbookusingcsvandjson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.bridgelabz.addressbookusingcsvandjson.AddressBookService.IOService.*;

import java.util.List;

public class AddressBookServiceTest {
	AddressBookService addressBookService;

	@Before
	public void initialize() {
		addressBookService = new AddressBookService();
	}

	// Retrieving data from address_book_name table
	@Test
	public void givenAddressBookInDB_WhenRetrievedAddressBookName_ShouldReturnExactCount() {
		long countEntries = addressBookService.getAddressBookName(DB_IO);
		Assert.assertEquals(5, countEntries);
	}

	// Retrieving data from address_book_type table
	@Test
	public void givenAddressBookInDB_WhenRetrievedAddressBookType_ShouldReturnExactCount() {
		long countEntries = addressBookService.getAddressBookType(DB_IO);
		Assert.assertEquals(3, countEntries);
	}

	// Retrieving data from address_details table
	@Test
	public void givenAddressBookInDB_WhenRetrievedAddressDetails_ShouldReturnExactCount() {
		long countEntries = addressBookService.getAddressDetails(DB_IO);
		Assert.assertEquals(12, countEntries);
	}

	// Retrieving data from contact_person_details table
	@Test
	public void givenAddressBookInDB_WhenRetrievedContactPersonDetails_ShouldReturnExactCount() {
		long countEntries = addressBookService.getContactPersonDetails(DB_IO);
		Assert.assertEquals(12, countEntries);
	}

	// Updating phone number in the database
	@Test
	public void givenNewPhoneNumber_WhenUpdated_ShouldSyncWithDatabase() {
		addressBookService.readContactPersonDetails(DB_IO);
		addressBookService.updatePhoneNoInDB("Akash", "Gupta", "9044589948");
		boolean result = addressBookService.checkContactPersonDetailsInSyncWithDB("Akash", "Gupta");
		Assert.assertTrue(result);
	}

	// Retrieving Contacts from the Database that were added in a particular period
	@Test
	public void givenDateRange_WhenRetrieved_ShouldReturnExactCount() {
		List<ContactPersonDB> contactPersonList = addressBookService
				.findContactPersonByDateRange("2019-04-01", "2020-11-04");
		Assert.assertEquals(5, contactPersonList.size());
	}
}
