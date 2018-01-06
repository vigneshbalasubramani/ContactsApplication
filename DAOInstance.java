package org.zilker.vigneshb.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

public class DAOInstance {
	static Connection connection;
	static Statement statement;
	static ResultSet resultSet;
	static Logger logger = Logger.getLogger(ContactsApplication.class.getName());

	public static int contactCount;// to get a new id for each contact

	public static int connect() {// connects to database and gets no:of rows present
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root", "zilkeradmin");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select count(*) from contact.people_Details");
			resultSet.next();
			contactCount = resultSet.getInt("count(*)");
		} catch (SQLException e) {
			logger.info("cannot establish a connection");
		}
		return 0;
	}

	public static int close() throws SQLException {// closes a made connection, statement and resultset
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		if (connection != null)
			connection.close();
		return 0;
	}

	public static String insertContacts(String first_name, String last_name, String email_id, String mobile_country,
			String mobile_num, String work_extn, String work_num, String home_area, String home_country,
			String home_num) throws SQLException {
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		connect();
		ContactBean contactBean = new ContactBean();
		if (contactBean.setFirstName(first_name) == -1)
			return "first name";
		if (contactBean.setLastName(last_name) == -1)
			return "last name";
		if (contactBean.setEmail(email_id) == -1)
			return "email";
		contactBean.setMobileCountry(mobile_country);
		contactBean.setMobileNum(mobile_num);
		contactBean.setWorkExtn(work_extn);
		contactBean.setWorkNum(work_num);
		contactBean.setHomeArea(home_area);
		contactBean.setHomeCountry(home_country);
		contactBean.setHomeNum(home_num);
		try {
			contactCount++;
			statement = connection.createStatement();
			statement.execute("insert into contact.people_details values(" + contactCount + ",'"
					+ contactBean.getFirstName() + "','" + contactBean.getLastName() + "','" + contactBean.getEmail()
					+ "','vignesh','" + timeStamp + "','vignesh','" + timeStamp + "')");
			statement.execute("insert into contact.phone_details values(" + contactCount + ",'"
					+ contactBean.getWorkExtn() + "','" + contactBean.getWorkNum() + "','"
					+ contactBean.getMobileCountry() + "','" + contactBean.getMobileNum() + "','"
					+ contactBean.getHomeArea() + "','" + contactBean.getHomeCountry() + "','"
					+ contactBean.getHomeNum() + "','vignesh','" + timeStamp + "','vignesh','" + timeStamp + "')");
		} catch (Exception e) {
			logger.info("cannot be inserted");
		} finally {
			close();
		}
		return null;
	}

	public static String updateContacts(String fname, String lname, String field, String value) throws SQLException {// updates
																														// existing
																														// contacts
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		connect();
		try {
			statement = connection.createStatement();
			if (field.equals("email"))
				statement.executeUpdate("update people_details set email_id ='" + value + "',updated_at='" + timeStamp
						+ "' where first_name='" + fname + "' and last_name='" + lname + "'");
			else
				statement.executeUpdate("update phone_details set " + field + "='" + value + "',updated_at='"
						+ timeStamp + "' where contact_id=(select contact_id from people_details where first_name='"
						+ fname + "' and last_name='" + lname + "')");
			return null;
		} catch (Exception e) {
			logger.info("enter the field names properly");
		} finally {
			close();
		}
		return null;
	}

	public static String viewContacts(String fname, String lname) throws SQLException {// views existing contacts
		connect();
		try {
			resultSet = statement.executeQuery("select email_id from people_details where first_name='" + fname
					+ "' and last_name='" + lname + "'");
			resultSet.next();
			logger.info("1");
			ContactBean contactBean = new ContactBean();
			contactBean.setFirstName(fname);
			contactBean.setLastName(lname);
			contactBean.setEmail(resultSet.getString("email_id"));
			resultSet = statement.executeQuery(
					"select * from phone_details where contact_id=(select contact_id from people_details where first_name='"
							+ contactBean.getFirstName() + "' and last_name='" + contactBean.getLastName() + "')");
			if (resultSet == null)
				return "nil";
			resultSet.next();
			contactBean.setMobileCountry(resultSet.getString("mobile_country"));
			contactBean.setMobileNum(resultSet.getString("mobile_num"));
			contactBean.setWorkExtn(resultSet.getString("office_extn"));
			contactBean.setWorkNum(resultSet.getString("office_num"));
			contactBean.setHomeArea(resultSet.getString("home_area"));
			contactBean.setHomeCountry(resultSet.getString("home_country"));
			contactBean.setHomeNum(resultSet.getString("home_number"));
			String mobileString = "\n mobile number: " + contactBean.getMobileCountry() + " "
					+ contactBean.getMobileNum();
			String homeString = "\n home number: " + contactBean.getHomeArea() + " " + contactBean.getHomeCountry()
					+ " " + contactBean.getHomeNum();
			String workString = "\n work number: " + contactBean.getWorkExtn() + " " + contactBean.getWorkNum();

			logger.info("The contact details are \nFirst name" + contactBean.getFirstName() + "\n last name:"
					+ contactBean.getLastName() + "\n email:" + contactBean.getEmail() + mobileString + workString
					+ homeString);
		} catch (Exception e) {
			logger.info("enter the names properly");
		} finally {
			close();
		}
		return null;
	}

	public static String queryContacts(String value) throws SQLException {// queries contacts using known numbers
		connect();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"Select * from contact.people_details where contact_id in (select contact_id from contact.phone_details where office_num='"
							+ value + "' or mobile_num='" + value + "' or home_number='" + value + "')");
			if (resultSet == null)
				return "empty";
			while (resultSet.next()) {
				logger.info("\n First name: " + resultSet.getString("first_name") + "\n Last name:"
						+ resultSet.getString("last_name") + "\n email: " + resultSet.getString("email_id"));
			}
		} catch (Exception e) {
			logger.info("no such contact exists");
		} finally {
			close();
		}
		return null;
	}

}
