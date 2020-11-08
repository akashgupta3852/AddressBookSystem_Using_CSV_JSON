package com.bridgelabz.addressbookusingcsvandjson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

	public List<ContactsDB> readContactPersonDetails() {
		String query = "SELECT * FROM contact_person_details;";
		try (Connection con = this.getConnection();) {
			preparedStatement = con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return this.getContactPersonList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ContactsDB> getContactPersonData(String firstName, String lastName) {
		String query = "select * from contact_person_details where first_name = ? and last_name =?;";
		try (Connection con = this.getConnection();) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			ResultSet resultSet = preparedStatement.executeQuery();
			return this.getContactPersonList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<ContactsDB> getContactPersonList(ResultSet resultSet) {
		List<ContactsDB> contactPersonList = new ArrayList<>();
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
				contactPersonList.add(
						new ContactsDB(id, firstName, lastName, phoneNo, email, addressId, typeId, bookId, dateAdded));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactPersonList;
	}

	public int updatePhoneNo(String firstName, String lastName, String phoneNo) {
		String query = "update contact_person_details set phone_no = ? where first_name = ? and last_name =?;";
		try (Connection con = this.getConnection();) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, phoneNo);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<ContactsDB> getContactsByDateRange(String fromDate, String toDate) {
		String query = "select * from contact_person_details where date_added between Cast(? as Date) and Cast(? as Date)";
		try (Connection con = this.getConnection();) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, fromDate);
			preparedStatement.setString(2, toDate);
			ResultSet resultSet = preparedStatement.executeQuery();
			return this.getContactPersonList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ContactsDB> getContactsByCity(String cityName) {
		String query = "select * from contact_person_details c, address_details a where c.ad_id = a.address_id and city = ?;";
		try (Connection con = this.getConnection();) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, cityName);
			ResultSet resultSet = preparedStatement.executeQuery();
			return this.getContactPersonList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ContactsDB> getContactsByState(String stateName) {
		String query = "select * from contact_person_details c, address_details a where c.ad_id = a.address_id and state = ?;";
		try (Connection con = this.getConnection();) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, stateName);
			ResultSet resultSet = preparedStatement.executeQuery();
			return this.getContactPersonList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ContactsDB addAddressBookDataInDB(String firstName, String lastName, String phoneNo, String email,
			int addressId, int typeId, int bookId, String dateAdded, String address, String cityName, String stateName,
			String zip) {
		String query = "insert into contact_person_details (first_name, last_name, phone_no, email, ad_id, type_id, book_id, date_added) values (?, ?, ?, ?, ?, ?, ?, Cast(? as Date));";
		Connection connection = this.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, phoneNo);
			preparedStatement.setString(4, email);
			preparedStatement.setInt(5, addressId);
			preparedStatement.setInt(6, typeId);
			preparedStatement.setInt(7, bookId);
			preparedStatement.setString(8, dateAdded);
			preparedStatement.executeUpdate();
			String sql = "SELECT person_id FROM contact_person_details where first_name = ? and last_name= ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int personId = resultSet.getInt("person_id");
			addAddressDetails(addressId, address, cityName, stateName, zip, connection);
			return new ContactsDB(personId, firstName, lastName, phoneNo, email, addressId, typeId, bookId,
					LocalDate.parse(dateAdded, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void addAddressDetails(int addressId, String address, String cityName, String stateName, String zip,
			Connection connection) {
		try {
			String query = "insert into address_details values (?, ?, ?, ?, ?);";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, addressId);
			preparedStatement.setString(2, address);
			preparedStatement.setString(3, cityName);
			preparedStatement.setString(4, stateName);
			preparedStatement.setString(5, zip);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}