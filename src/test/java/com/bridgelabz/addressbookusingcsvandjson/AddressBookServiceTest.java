package com.bridgelabz.addressbookusingcsvandjson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.bridgelabz.addressbookusingcsvandjson.AddressBookService.IOService.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
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
		List<ContactsDB> contactPersonList = addressBookService.getContactsByDateRange("2019-04-01", "2020-11-04");
		Assert.assertEquals(5, contactPersonList.size());
	}

	// Retrieving Contacts from the Database by City
	@Test
	public void givenAddressBookInDB_WhenRetrievedContactsByCity_ShouldReturnExactCount() {
		addressBookService.readContactPersonDetails(DB_IO);
		List<ContactsDB> contactPersonList = addressBookService.getContactsByCity(DB_IO, "Varanasi");
		Assert.assertEquals(3, contactPersonList.size());
	}

	// Retrieving Contacts from the Database by State
	@Test
	public void givenAddressBookInDB_WhenRetrievedContactsByState_ShouldReturnExactCount() {
		addressBookService.readContactPersonDetails(DB_IO);
		List<ContactsDB> contactPersonList = addressBookService.getContactsByState(DB_IO, "U.P.");
		Assert.assertEquals(5, contactPersonList.size());
	}

	// Adding new address book data into database
	@Test
	public void givenAddressBookData_WhenAddedToDB_ShouldBeSyncWithDB() {
		addressBookService.readContactPersonDetails(DB_IO);
		addressBookService.addAddressBookDataInDB("Meera", "Devi", "9415829547", "meera3377@gmail.com", 23, 1, 104,
				"2020-11-05", "Station Road", "Gwalior", "M.P.", "516899");
		boolean result = addressBookService.checkContactPersonDetailsInSyncWithDB("Meera", "Devi");
		Assert.assertTrue(result);
	}

	// Adding multiple address book data using threads and without using threads
	@Test
	public void given3AddressBookData_WhenAddedToDB_ShouldBeSyncWithDB() {
		ContactsDB[] arrayOfContacts = {
				new ContactsDB("Radha", "Gupta", "9487986852", "radha@yahoo.co.in", 24, 2, 101, LocalDate.now(),
						"Rampur", "Surat", "Gujarat", "456987"),
				new ContactsDB("Mohan", "Singh", "9875893678", "mohan@yahoo.co.in", 25, 1, 104, LocalDate.now(),
						"Huda City", "Gurgaon", "Haryana", "217894"),
				new ContactsDB("Golu", "Jee", "8895874391", "golu@yahoo.co.in", 26, 3, 103, LocalDate.now(),
						"Airoli Campus", "Mumbai", "Maharastra", "148979") };
		Instant start = Instant.now();
		addressBookService.readContactPersonDetails(DB_IO);
		addressBookService.addContactsToDB(Arrays.asList(arrayOfContacts));
		Instant end = Instant.now();
		System.out.println("Duration without Thread; " + Duration.between(start, end));
		Instant threadStart = Instant.now();
		addressBookService.addContactsToDBWithThreads(Arrays.asList(arrayOfContacts));
		Instant threadEnd = Instant.now();
		System.out.println("Duration with Thread; " + Duration.between(threadStart, threadEnd));
		Assert.assertEquals(19, addressBookService.countEntries(DB_IO));
	}
}