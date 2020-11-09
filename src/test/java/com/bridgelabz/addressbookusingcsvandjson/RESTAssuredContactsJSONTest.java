package com.bridgelabz.addressbookusingcsvandjson;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.bridgelabz.addressbookusingcsvandjson.AddressBookService.IOService.*;

public class RESTAssuredContactsJSONTest {

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	// Getting the list of contact persons from the Json server
	public ContactPerson[] getContactPersonList() {
		Response response = RestAssured.get("/contacts");
		ContactPerson[] arrayOfContactPersons = new Gson().fromJson(response.asString(), ContactPerson[].class);
		return arrayOfContactPersons;
	}

	// Getting response object after adding a person's contact to the Json server
	public Response addContactsToJsonServer(ContactPerson contactPerson) {
		String contactPersonJson = new Gson().toJson(contactPerson);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactPersonJson);
		return request.post("/contacts");
	}

	// Reading the persons contact from Json server
	@Test
	public void givenPersonsContactInJsonServer_WhenRetrieved_ShouldMatchTheCount() {
		ContactPerson[] arrayOfEmps = getContactPersonList();
		AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfEmps));
		long entries = addressBookService.countEntries(REST_IO);
		Assert.assertEquals(2, entries);
	}

	// Writing the person's contact to the Json server
	@Test
	public void givenNewPersonsContact_WhenAdded_ShouldMatch201ResponseAndCount() {
		ContactPerson[] arrayOfContactPersons = getContactPersonList();
		AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfContactPersons));
		ContactPerson contactPerson = null;
		contactPerson = new ContactPerson("Abhishek", "Gupta", "Brahmasthan", "Rasra", "U.P.", 221712, 7275339746l,
				"abhish123@gmail.com");
		Response response = addContactsToJsonServer(contactPerson);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		contactPerson = new Gson().fromJson(response.asString(), ContactPerson.class);
		addressBookService.addContactPerson(contactPerson, REST_IO);
		long entries = addressBookService.countEntries(REST_IO);
		Assert.assertEquals(3, entries);
	}

	// Writing the multiple person's contacts to the Json server
	@Test
	public void givenListOfContacts_WhenAdded_ShouldMatch201ResponseAndCount() {
		ContactPerson[] arrayOfContactPersons = getContactPersonList();
		AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfContactPersons));
		ContactPerson[] arrOfcontacts = { 
				new ContactPerson("Meera", "Gupta", "Gudari Bazar", "Rasra", "U.P.", 221712, 9475986231l, "meera3377@gmail.com"),
				new ContactPerson("Radha", "Gupta", "Kakadeo", "Kanpur", "U.P.", 141891, 8932339795l, "radha007@gmail.com"),
				new ContactPerson("Khusbu", "Gupta", "Gudari Bazar", "Rasra", "U.P.", 221712, 7789346593l, "khusbu89563@gmail.com")
				};
		for (ContactPerson contactPerson : arrOfcontacts) {
			Response response = addContactsToJsonServer(contactPerson);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(201, statusCode);
			contactPerson = new Gson().fromJson(response.asString(), ContactPerson.class);
			addressBookService.addContactPerson(contactPerson, REST_IO);
		}
		long entries = addressBookService.countEntries(REST_IO);
		Assert.assertEquals(6, entries);
	}
	
	// Updating the person's contact on the Json server
	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldMatch200Response() {
		ContactPerson[] arrayOfContactPersons = getContactPersonList();
		AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfContactPersons));
		addressBookService.updateZip("Radha", "Gupta", 230178);
		ContactPerson contactPerson = addressBookService.getContactPersonDataFromJson("Radha", "Gupta");
		String contactPersonJson = new Gson().toJson(contactPerson);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactPersonJson);
		Response response = request.put("/contacts/" + contactPerson.getId());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
	}
}
