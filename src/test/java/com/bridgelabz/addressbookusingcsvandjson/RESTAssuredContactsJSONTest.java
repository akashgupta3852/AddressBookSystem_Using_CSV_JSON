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
		System.out.println("EMPLOYEE PAYROLL ENTRIES IN JSONServer:\n" + response.asString());
		ContactPerson[] arrayOfContactPersons = new Gson().fromJson(response.asString(), ContactPerson[].class);
		return arrayOfContactPersons;
	}

	// Getting response object after adding a person's contact to the Json server
	public Response addEmployeeToJsonServer(ContactPerson contactPerson) {
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
		AddressBookService employeePayrollService;
		employeePayrollService = new AddressBookService(Arrays.asList(arrayOfEmps));
		long entries = employeePayrollService.countEntries(REST_IO);
		Assert.assertEquals(2, entries);
	}

	// Writing the persons contact to Json server
	@Test
	public void givenNewPersonsContact_WhenAdded_ShouldMatch201ResponseAndCount() {
		ContactPerson[] arrayOfContactPersons = getContactPersonList();
		AddressBookService addressBookService;
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContactPersons));
		ContactPerson contactPerson = null;
		contactPerson = new ContactPerson("Abhishek", "Gupta", "Brahmasthan", "Rasra", "U.P.", 221712, 7275339746l,
				"abhish123@gmail.com");
		Response response = addEmployeeToJsonServer(contactPerson);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		contactPerson = new Gson().fromJson(response.asString(), ContactPerson.class);
		addressBookService.addContactPerson(contactPerson, REST_IO);
		long entries = addressBookService.countEntries(REST_IO);
		Assert.assertEquals(3, entries);
	}
}
