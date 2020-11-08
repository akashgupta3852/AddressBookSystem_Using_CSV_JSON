package com.bridgelabz.addressbookusingcsvandjson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
	private PreparedStatement preparedStatement;
	private static AddressBookDBService addressBookDBService;

	private AddressBookDBService() {
	}

	public static AddressBookDBService getInstance() {
		if (addressBookDBService == null)
			addressBookDBService = new AddressBookDBService();
		return addressBookDBService;
	}

	private Connection getConnection() {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String username = "root";
		String password = "473852";
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public long readAddressBookName() {
		String query = "SELECT * FROM address_book_name;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return getCount(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long readAddressBookType() {
		String query = "SELECT * FROM address_book_type;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return getCount(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long readAddressDetails() {
		String query = "SELECT * FROM address_details;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return getCount(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private long getCount(ResultSet resultSet) throws SQLException {
		int count = 0;
		while (resultSet.next())
			count++;
		return count;
	}

	public List<ContactPersonDB> readContactPersonDetails() {
		String query = "SELECT * FROM contact_person_details;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return this.getContactPersonList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ContactPersonDB> getContactPersonData(String firstName, String lastName) {
		try {
			String query = "select * from contact_person_details where first_name = ? and last_name =?;";
			preparedStatement = this.getConnection().prepareStatement(query);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			ResultSet resultSet = preparedStatement.executeQuery();
			return this.getContactPersonList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<ContactPersonDB> getContactPersonList(ResultSet resultSet) {
		List<ContactPersonDB> contactPersonList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("person_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String phoneNo = resultSet.getString("phone_no");
				String email = resultSet.getString("email");
				int addressId = resultSet.getInt("ad_id");
				int typeId = resultSet.getInt("type_id");
				int bookId = resultSet.getInt("book_id");
				LocalDate dateAdded = resultSet.getDate("date_added").toLocalDate();
				contactPersonList.add(new ContactPersonDB(id, firstName, lastName, phoneNo, email, addressId, typeId,
						bookId, dateAdded));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactPersonList;
	}

	public int updatePhoneNo(String firstName, String lastName, String phoneNo) {
		String query = "update contact_person_details set phone_no = ? where first_name = ? and last_name =?;";
		try {
			preparedStatement = this.getConnection().prepareStatement(query);
			preparedStatement.setString(1, phoneNo);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<ContactPersonDB> findContactPersonByDateRange(String fromDate, String toDate) {
		String query = "select * from contact_person_details where date_added between Cast(? as Date) and Cast(? as Date)";
		try {
			preparedStatement = this.getConnection().prepareStatement(query);
			preparedStatement.setString(1, fromDate);
			preparedStatement.setString(2, toDate);
			ResultSet resultSet = preparedStatement.executeQuery();
			return this.getContactPersonList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
