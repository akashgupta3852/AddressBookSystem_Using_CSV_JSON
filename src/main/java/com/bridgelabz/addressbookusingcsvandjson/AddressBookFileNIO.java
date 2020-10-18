package com.bridgelabz.addressbookusingcsvandjson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AddressBookFileNIO {
	public static String ADDRESS_BOOK_PATH = "addressBook.txt";

	// Reading data from file
	public void readFromFile() throws IOException {
		Path path = Paths.get(ADDRESS_BOOK_PATH);
		System.out.println("The contact person's details in the address book file are: ");
		Files.lines(path).map(line -> line.trim()).forEach(line -> System.out.println(line));
	}

	// Writing data into file
	public void writeToFile(Map<String, Set<ContactPerson>> addressBookSystem) throws IOException {
		Path path = Paths.get(ADDRESS_BOOK_PATH);
		if (Files.notExists(path))
			Files.createFile(path);
		StringBuffer adBookBuffer = new StringBuffer();
		List<String> contactList = new ArrayList<String>();
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			List<String> contactDeatilsList = me.getValue().stream()
					.map(contactPerson -> "Name: " + contactPerson.getFirstName() + " " + contactPerson.getLastName()
							+ ", Address: " + contactPerson.getAddress() + ", City: " + contactPerson.getCity()
							+ ", State: " + contactPerson.getState() + ", Zip: " + contactPerson.getZip()
							+ ", PhoneNo: " + contactPerson.getPhoneNo() + ", EmailId: " + contactPerson.getEmailId())
					.collect(Collectors.toList());
			contactList.addAll(contactDeatilsList);
		}
		contactList.forEach(adBook -> {
			String personDetails = adBook.toString().concat("\n");
			adBookBuffer.append(personDetails);
		});
		Files.write(path, adBookBuffer.toString().getBytes());
	}
}