package com.bridgelabz.addressbookusingcsvandjson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressBookDBService {
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
		String sql = "SELECT * FROM address_book_name;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			return getCount(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long readAddressBookType() {
		String sql = "SELECT * FROM address_book_type;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			return getCount(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long readAddressDetails() {
		String sql = "SELECT * FROM address_details;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			return getCount(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long readContactPersonDetails() {
		String sql = "SELECT * FROM contact_person_details;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
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
}
