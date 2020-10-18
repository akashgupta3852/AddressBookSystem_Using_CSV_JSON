package com.bridgelabz.addressbookusingcsvandjson;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONService {
	private static final String STRING_ARRAY_SAMPLE = "addressBook.json";

	// Method for write JSON file
	public void writeJson(Map<String, Set<ContactPerson>> addressBookSystem) throws Exception {
		List<ContactPerson> contactList = new ArrayList<ContactPerson>();
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			List<ContactPerson> contactDeatilsList = me.getValue().stream().collect(Collectors.toList());
			contactList.addAll(contactDeatilsList);
		}

		
		Gson gson = new GsonBuilder().create();
		List list = contactList.stream().collect(Collectors.toList());
		String json = gson.toJson(list);
		FileWriter writer = new FileWriter(STRING_ARRAY_SAMPLE);
		writer.write(json);
		writer.close();
		System.out.println("123");
	}

	// Method for Read JSON file
	public int readJson() {
		int count = 0;
		try {
			Reader reader = Files.newBufferedReader(Paths.get(STRING_ARRAY_SAMPLE));
			List<ContactPerson> addresBook = new Gson().fromJson(reader, new TypeToken<List<ContactPerson>>() {
			}.getType());
			Iterator<ContactPerson> jsonIterator = addresBook.iterator();

			while (jsonIterator.hasNext()) {
				count++;
				ContactPerson adressBook = jsonIterator.next();
				System.out.println("First Name : " + adressBook.getFirstName());
				System.out.println("Last Name : " + adressBook.getLastName());
				System.out.println("Address: " + adressBook.getAddress());
				System.out.println("City : " + adressBook.getCity());
				System.out.println("State : " + adressBook.getState());
				System.out.println("Zip : " + adressBook.getZip());
				System.out.println("Number : " + adressBook.getPhoneNo());
				System.out.println("Email : " + adressBook.getEmailId());
				System.out.println("*****************");
			}
			reader.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return count;
	}
}