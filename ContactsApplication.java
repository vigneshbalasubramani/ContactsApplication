package org.zilker.vigneshb.jdbc;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ContactsApplication {

	static Logger logger = Logger.getLogger(ContactsApplication.class.getName());
	static Scanner scan = new Scanner(System.in);
	static String firstName, lastName, email, mobileCountry = null, mobileNum = null, homeArea = null,
			homeCountry = null, homeNum = null, workExtn = null, workNum = null, mobile, home, work;

	public static void main(String[] args) throws SQLException {
		int choice = 0;
		while (choice < 5) {
			logger.info(
					"Enter your choice \n 1.insert people contacts \n 2.update details \n 3.view details \n 4.query \n 5.exit");
			choice = scan.nextInt();
			switch (choice) {
			case 1:
				insertContact();
				break;
			case 2:
				updateContact();
				break;
			case 3:
				viewContact();
				break;
			case 4:
				queryContact();
				break;
			case 5:
				logger.info("exitting");
				break;
			default:
				logger.info("enter a proper option");
				break;
			}
		}

	}

	static int insertContact() throws SQLException {// inserts a new contact
		String response;
		logger.info("Enter the first name");
		firstName = scan.next();
		logger.info("enter the last name");
		lastName = scan.next();
		logger.info("enter the email id");
		email = scan.next();
		logger.info("enter the mobile country code and mobile number or nil if not present");
		mobile = scan.next();
		if (!mobile.equals("null")) {
			mobileCountry = mobile;
			mobileNum = scan.next();
		}
		logger.info("enter the work extension code and work number or nil if not present");
		work = scan.next();
		if (!work.equals("null")) {
			workExtn = work;
			workNum = scan.next();
		}
		logger.info("enter the home area code, country code and home number or nil if not present");
		home = scan.next();
		if (!home.equals("null")) {
			homeArea = home;
			homeCountry = scan.next();
			homeNum = scan.next();
		}
		response = DAOInstance.insertContacts(firstName, lastName, email, mobileCountry, mobileNum, workExtn, workNum,
				homeArea, homeCountry, homeNum);
		if (response != null)
			logger.info("enter the " + response + " details properly");
		else
			logger.info("entered successfully");
		return 0;
	}

	static int updateContact() throws SQLException {// updates an existing contact
		String fname, lname, field, value, response;
		logger.info("enter the employee first name and last name");
		fname = scan.next();
		lname = scan.next();
		logger.info("enter the field(email/mobile/work/home) and value");
		field = scan.next();
		value = scan.next();
		response = DAOInstance.updateContacts(fname, lname, field, value);
		if (response != null)
			logger.info("enter the " + response + " proplerly");
		else
			logger.info("updated successfully");
		return 0;
	}

	static int viewContact() throws SQLException {// views existing contact
		String fname, lname, response;
		logger.info("enter the first name and last name");
		fname = scan.next();
		lname = scan.next();
		response = DAOInstance.viewContacts(fname, lname);
		if (response != null)
			logger.info("no such contact is present");
		return 0;
	}

	static int queryContact() throws SQLException {// finds contacts with a given number
		String response, value;
		logger.info("enter any number you know(mobile/home/work)");
		value = scan.next();
		response = DAOInstance.queryContacts(value);
		if (response != null)
			logger.info("no contacts");
		return 0;
	}

}
