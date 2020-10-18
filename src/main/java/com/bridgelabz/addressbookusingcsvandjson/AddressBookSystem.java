package com.bridgelabz.addressbookusingcsvandjson;

import java.io.IOException;
import java.util.*;

public class AddressBookSystem {
	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Program");

		AddressBook addressBookMain = new AddressBook();
		AddressBookFileNIO addBookFileNIO = new AddressBookFileNIO();
		OpenCSVService openCSVService = new OpenCSVService();
		Scanner input = new Scanner(System.in);

		while (true) {
			System.out.println("Choose the option between 1 - 9");
			System.out.println("1. Add address book to the system");
			System.out.println("2. Edit contact details of the address book");
			System.out.println("3. Delete contact details of the address book");
			System.out.println("4. Show particular address book by its name");
			System.out.println("5. Show address book system");
			System.out.println("6. Search person by the city or sate");
			System.out.println("7. View person by the city or state");
			System.out.println("8. Count by city or sate");
			System.out.println("9. Sort the address book by City");
			System.out.println("10. Sort the address book by State");
			System.out.println("11. Sort the address book by Zip");
			System.out.println("12. Writing data to file");
			System.out.println("13. Reading data from file");
			System.out.println("14. Writing data to CSV");
			System.out.println("15. Reading data from CSV");
			System.out.println("16. Exit");

			String option = input.next();

			// Adding address book to the system
			if (option.equals("1")) {
				System.out.println("Number of Address Book to be added:");
				int noOfAddressBook = input.nextInt();

				for (int i = 0; i < noOfAddressBook; i++) {
					System.out.println("Enter the name of the Address Book");
					input.nextLine();
					String addressBookName = input.nextLine();

					System.out.println(
							"Enter the number of person's details to be added in address book: " + addressBookName);
					int noOfPerson = input.nextInt();

					Set<ContactPerson> phoneBook = new HashSet<>();
					addressBookMain.setAddressBook(phoneBook);
					for (int j = 0; j < noOfPerson; j++) {
						System.out.println("Enter the details of the Contact Person");
						ContactPerson contactPerson = addressBookMain.addContactPersonDetails(input);
						addressBookMain.addContactPerson(contactPerson);
						String name = contactPerson.getFirstName() + " " + contactPerson.getLastName();
						System.out.println("The details of the " + name + " is added to the Address Book: "
								+ addressBookName + " successfully.");
					}

					Set<ContactPerson> addressBook = addressBookMain.getAddressBook();
					addressBookMain.addAddressBookToSystem(addressBookName, addressBook);

					System.out.println("Address Book: " + addressBookName + " is successfully added to the system.");
				}
				continue;
			}

			// Editing contact details of the address book
			if (option.equals("2")) {
				System.out.println("Enter the name of the address book of which person's details to be edited:");
				input.nextLine();
				String addressBookName = input.nextLine();
				if (addressBookMain.isPresentAddressBook(addressBookName)) {
					System.out.println("Enter the name of the person whose details to be edited:");
					String personName = input.nextLine();
					if (addressBookMain.editContactPersonDetails(addressBookName, personName, input))
						System.out.println("The contact details of the " + personName + " from " + addressBookName
								+ " is edited.");
					else
						System.out.println("Sorry, the contact details of the " + personName + " is not found in "
								+ addressBookName + ". We can't proceed to edit.");
				} else
					System.out.println("Sorry, the address book: " + addressBookName
							+ " is not found in the system. We can't proceed to edit.");
				continue;
			}

			// Deleting contact details of the address book
			if (option.equals("3")) {
				System.out.println("Enter the name of the address book from which person's details to be deleted:");
				input.nextLine();
				String addressBookName = input.nextLine();
				if (addressBookMain.isPresentAddressBook(addressBookName)) {
					System.out.println("Enter the name of the person whose details to be deleted:");
					String personName = input.nextLine();
					if (addressBookMain.deleteContactPersonDetails(addressBookName, personName))
						System.out.println("The contact details of the " + personName + " from " + addressBookName
								+ " is deleted.");
					else
						System.out.println("Sorry, the contact details of the " + personName + " is not found in "
								+ addressBookName + ". We can't proceed to delete.");
				} else
					System.out.println("Sorry, the address book: " + addressBookName
							+ " is not found in the system. We can't proceed to delete.");
				continue;
			}

			// Showing particular address book by its name
			if (option.equals("4")) {
				System.out.println("Enter the name of the address book:");
				input.nextLine();
				String addressBookName = input.nextLine();
				if (addressBookMain.isPresentAddressBook(addressBookName))
					addressBookMain.showAddressBook(addressBookName);
				else
					System.out.println("Sorry, Address Book: " + addressBookName + " is not present in the system.");
				continue;
			}

			// Show address book system
			if (option.equals("5")) {
				addressBookMain.showAddressBookSystem();
				continue;
			}

			// Searching person by the city or sate
			if (option.equals("6")) {
				System.out.println("Enter the state/city name to search the persons:");
				input.nextLine();
				String cityOrStateName = input.nextLine();
				List<String> personsInCityOrState = addressBookMain.searchPersonByCityorState(cityOrStateName);
				if (personsInCityOrState.size() == 0)
					System.out.println("Sorry, there is no person in the " + cityOrStateName + ".");
				else {
					System.out.println("The list of persons in the " + cityOrStateName + ":");
					personsInCityOrState.stream().forEach(personName -> System.out.println(personName));
				}
				continue;
			}

			// View person by the city or state
			if (option.equals("7")) {
				System.out.println("Enter the state/city name to view the persons:");
				input.nextLine();
				String cityOrStateName = input.nextLine();
				Map<String, List<String>> personCityStateMap = addressBookMain.viewPersonByCityOrState(cityOrStateName);
				if (personCityStateMap.size() == 0)
					System.out.println("Sorry, there is no any details.");
				else {
					System.out.println("The mapping of city/state and persons:");
					System.out.println(personCityStateMap);
				}
				continue;
			}

			// Count by city or sate
			if (option.equals("8")) {
				System.out.println("Enter the state/city name to count the persons:");
				input.nextLine();
				String cityOrStateName = input.nextLine();
				List<String> personsInCityOrState = addressBookMain.searchPersonByCityorState(cityOrStateName);
				if (personsInCityOrState.size() == 0)
					System.out.println("Sorry, there is no person in the " + cityOrStateName + ".");
				else {
					addressBookMain.countPersonByCityorState(cityOrStateName);
				}
				continue;
			}

			// Sort person details by the city or state
			if (option.equals("9")) {
				Map<String, List<ContactPerson>> personCitySortedMap = addressBookMain.viewSortedByCity();
				if (personCitySortedMap.size() == 0)
					System.out.println("Sorry, there is no any details.");
				else {
					System.out.println("The address books are sorted by city:");
					addressBookMain.printSortedMap(personCitySortedMap);
				}
				continue;
			}

			// Sort person details by the city or state
			if (option.equals("10")) {
				Map<String, List<ContactPerson>> personStateSortedMap = addressBookMain.viewSortedByState();
				if (personStateSortedMap.size() == 0)
					System.out.println("Sorry, there is no any details.");
				else {
					System.out.println("The address books are sorted by state:");
					addressBookMain.printSortedMap(personStateSortedMap);
				}
				continue;
			}

			// Sort person details by the city or state
			if (option.equals("11")) {
				Map<String, List<ContactPerson>> personZipSortedMap = addressBookMain.viewSortedByZip();
				if (personZipSortedMap.size() == 0)
					System.out.println("Sorry, there is no any details.");
				else {
					System.out.println("The address books are sorted by zip:");
					addressBookMain.printSortedMap(personZipSortedMap);
				}
				continue;
			}

			// Writing data into file
			if (option.equals("12")) {
				try {
					addBookFileNIO.writeToFile(addressBookMain.getAddressBookSystem());
				} catch (IOException e) {
					e.getMessage();
				}
				continue;
			}

			// Reading data from file
			if (option.equals("13")) {
				try {
					addBookFileNIO.readFromFile();
				} catch (IOException e) {
					e.getMessage();
				}
				continue;
			}

			// Writing data into CSV
			if (option.equals("14")) {
				try {
					openCSVService.writetoCsv(addressBookMain.getAddressBookSystem());
				} catch (IOException e) {
					e.getMessage();
				}
				continue;
			}

			// Reading data from CSV
			if (option.equals("15")) {
				try {
					 openCSVService.readCsv();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				continue;
			}

			// Exiting from the address book system
			if (option.equals("16")) {
				System.out.println("Thank you.");
				break;
			} else
				System.out.print("You entered the invalid option. Please, ");
		}
	}
}
