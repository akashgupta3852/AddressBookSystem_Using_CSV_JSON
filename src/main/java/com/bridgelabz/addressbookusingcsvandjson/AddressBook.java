package com.bridgelabz.addressbookusingcsvandjson;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AddressBook {
	private Set<ContactPerson> addressBook;
	private static Map<String, Set<ContactPerson>> addressBookSystem = new TreeMap<>();

	public Map<String, Set<ContactPerson>> getAddressBookSystem() {
		return addressBookSystem;
	}

	public Set<ContactPerson> getAddressBook() {
		return addressBook;
	}

	public void setAddressBook(Set<ContactPerson> addressBook) {
		this.addressBook = addressBook;
	}

	public void addContactPerson(ContactPerson contactPerson) {
		addressBook.add(contactPerson);
	}

	public void addAddressBookToSystem(String addressBookName, Set<ContactPerson> addressBook) {
		addressBookSystem.put(addressBookName, addressBook);
	}

	// Checking whether address book is present or not in system
	public boolean isPresentAddressBook(String addressBookName) {
		Predicate<String> isPresent = k -> k.equals(addressBookName);
		List<String> nameList = addressBookSystem.keySet().stream().filter(isPresent).collect(Collectors.toList());
		if (nameList.size() == 0)
			return false;
		return true;
	}

	// Editing the contact person details in the address book
	public boolean editContactPersonDetails(String addressBookName, String personName, Scanner input) {
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			String adBookName = me.getKey();
			Set<ContactPerson> addressBook = me.getValue();
			if (adBookName.equals(addressBookName)) {
				for (ContactPerson contactPerson : addressBook) {
					String name = contactPerson.getFirstName() + " " + contactPerson.getLastName();
					if (name.equals(personName)) {
						addressBook.remove(contactPerson);
						ContactPerson contactPerson1 = addContactPersonDetails(input);
						addressBook.add(contactPerson1);
						addAddressBookToSystem(addressBookName, addressBook);
						return true;
					}
				}
			}
		}
		return false;
	}

	// Deleting the contact person details from the address book
	public boolean deleteContactPersonDetails(String addressBookName, String personName) {
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			String adBookName = me.getKey();
			Set<ContactPerson> addressBook = me.getValue();
			if (adBookName.equals(addressBookName)) {
				for (ContactPerson contactPerson : addressBook) {
					String name = contactPerson.getFirstName() + " " + contactPerson.getLastName();
					if (name.equals(personName)) {
						addressBook.remove(contactPerson);
						addAddressBookToSystem(addressBookName, addressBook);
						return true;
					}
				}
			}
		}
		return false;
	}

	// Listing the persons based on the city and state respectively
	public List<String> searchPersonByCityorState(String cityOrState) {
		List<String> personsInCityOrState = new ArrayList<String>();
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			List<String> personsList = me.getValue().stream()
					.filter(contactPerson -> contactPerson.getCity().equals(cityOrState)
							|| contactPerson.getState().equals(cityOrState))
					.map(contactPerson -> contactPerson.getFirstName() + " " + contactPerson.getLastName())
					.collect(Collectors.toList());
			personsInCityOrState.addAll(personsList);
		}
		return personsInCityOrState;
	}

	// Mapping the persons by city or state
	public Map<String, List<String>> viewPersonByCityOrState(String cityOrState) {
		Map<String, List<String>> personCityStateMap = new HashMap<>();
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			List<String> personsInCityOrState = me.getValue().stream()
					.filter(contactPerson -> contactPerson.getCity().equals(cityOrState)
							|| contactPerson.getState().equals(cityOrState))
					.map(contactPerson -> contactPerson.getFirstName() + " " + contactPerson.getLastName())
					.collect(Collectors.toList());
			personCityStateMap.put(me.getKey(), personsInCityOrState);
		}
		return personCityStateMap;
	}

	// Counting the persons by city and state respectively
	public void countPersonByCityorState(String cityOrState) {
		Map<String, List<String>> personCityStateMap = viewPersonByCityOrState(cityOrState);
		personCityStateMap.entrySet().stream().forEach(me -> {
			System.out.println("The no. of persons reside in the " + cityOrState + " is " + me.getValue().size()
					+ ", as given in the " + me.getKey() + ".");
		});
		int count = personCityStateMap.entrySet().stream().map(me -> me.getValue().size()).reduce(0, (a, b) -> a + b);
		System.out.println("There are total " + count + " persons in the " + cityOrState + ".");
	}

	// Printing address book by its name
	public void showAddressBook(String addressBookName) {
		addressBookSystem.entrySet().stream().forEach(me -> {
			if (me.getKey().equals(addressBookName)) {
				if (me.getValue().size() == 0)
					System.out.println("Sorry, there is no contact details in the " + addressBookName + ".");
				else {
					System.out.println("The contact details of the " + addressBookName + ":");
					me.getValue().stream().forEach(contactPerson -> System.out.println(contactPerson));
				}
			}
		});
	}

	// Printing all the address books
	public void showAddressBookSystem() {
		sortedByName();
		if (addressBookSystem.size() == 0)
			System.out.println("There is no address book in the system.");
		else {
			addressBookSystem.entrySet().stream().forEach(me -> {
				System.out.println("The contact details of the " + me.getKey() + ":");
				if (me.getValue().size() == 0)
					System.out.println("Sorry, there is no contact in the " + me.getKey() + ".");
				else
					me.getValue().stream().forEach(contactPerson -> System.out.println(contactPerson));
			});
		}
	}

	// Sorting the address book by name
	public void sortedByName() {
		Map<String, Set<ContactPerson>> personNameSortedMap = new HashMap<>();
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			Set<ContactPerson> personsInCity = me.getValue().stream()
					.sorted((ab1, ab2) -> (ab1.getFirstName() + " " + ab1.getLastName())
							.compareTo(ab2.getFirstName() + " " + ab2.getLastName()))
					.collect(Collectors.toSet());
			personNameSortedMap.put(me.getKey(), personsInCity);
		}
		addressBookSystem = personNameSortedMap;
	}

	// Sorting the address book by city
	public Map<String, List<ContactPerson>> viewSortedByCity() {
		Map<String, List<ContactPerson>> personCitySortedMap = new HashMap<>();
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			List<ContactPerson> personsInCity = me.getValue().stream()
					.sorted((ab1, ab2) -> ab1.getCity().compareTo(ab2.getCity())).collect(Collectors.toList());
			personCitySortedMap.put(me.getKey(), personsInCity);
		}
		return personCitySortedMap;
	}

	// Sorting the address book by state
	public Map<String, List<ContactPerson>> viewSortedByState() {
		Map<String, List<ContactPerson>> personStateSortedMap = new HashMap<>();
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			List<ContactPerson> personsInState = me.getValue().stream()
					.sorted((ab1, ab2) -> ab1.getState().compareTo(ab2.getState())).collect(Collectors.toList());
			personStateSortedMap.put(me.getKey(), personsInState);
		}
		return personStateSortedMap;
	}

	// Sorting the address book by zip
	public Map<String, List<ContactPerson>> viewSortedByZip() {
		Map<String, List<ContactPerson>> personZipSortedMap = new HashMap<>();
		for (Map.Entry<String, Set<ContactPerson>> me : addressBookSystem.entrySet()) {
			List<ContactPerson> personsInZip = me.getValue().stream().sorted((ab1, ab2) -> ab1.getZip() - ab2.getZip())
					.collect(Collectors.toList());
			personZipSortedMap.put(me.getKey(), personsInZip);
		}
		return personZipSortedMap;
	}

	// Printing the sorted map
	public void printSortedMap(Map<String, List<ContactPerson>> sortedMap) {
		if (sortedMap.size() == 0)
			System.out.println("There is no address book in the system.");
		else {
			sortedMap.entrySet().stream().forEach(me -> {
				System.out.println("The contact details of the " + me.getKey() + ":");
				if (me.getValue().size() == 0)
					System.out.println("Sorry, there is no contact in the " + me.getKey() + ".");
				else
					me.getValue().stream().forEach(contactPerson -> System.out.println(contactPerson));
			});
		}
	}

	// Initialising the contact person object
	public ContactPerson addContactPersonDetails(Scanner input) {
		System.out.println("Enter the first name:");
		String firstName = input.next();
		System.out.println("Enter the last name:");
		String lastName = input.next();
		System.out.println("Enter the address:");
		input.nextLine();
		String address = input.nextLine();
		System.out.println("Enter the city:");
		String city = input.nextLine();
		System.out.println("Enter the state:");
		String state = input.nextLine();
		System.out.println("Enter the zip:");
		int zip = input.nextInt();
		System.out.println("Enter the phoneNo:");
		long phoneNo = input.nextLong();
		System.out.println("Enter the email:");
		String emailId = input.next();
		ContactPerson personDetails = new ContactPerson(firstName, lastName, address, city, state, zip, phoneNo,
				emailId);
		return personDetails;
	}
}
