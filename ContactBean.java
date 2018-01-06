package org.zilker.vigneshb.jdbc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactBean {// contains all fields, their setters and getters
	private String firstName, lastName, email, mobileCountry, mobileNum, workExtn, workNum, homeArea, homeCountry,
			homeNum;
	Pattern emailPattern = Pattern.compile("[a-z0-9A-Z]+@[a-zA-Z0-9]+\\.([a-z0-9]{3}|[a-z0-9]{2})");

	public int setFirstName(String fName) {
		if (fName != null)
			this.firstName = fName;
		else
			return -1;
		return 0;
	}

	public int setLastName(String lName) {
		if (lName != null)
			this.lastName = lName;
		else
			return -1;
		return 0;
	}

	public int setEmail(String email) {
		Matcher matcher = emailPattern.matcher(email);
		if (matcher.matches())
			this.email = email;
		else
			return -1;
		return 0;
	}

	public int setMobileCountry(String mobileCountry) {
		if (mobileCountry.length() == 2)
			this.mobileCountry = mobileCountry;
		else
			return -1;
		return 0;
	}

	public int setMobileNum(String mobileNum) {
		if (mobileNum.length() == 10)
			this.mobileNum = mobileNum;
		else
			return -1;
		return 0;
	}

	public int setWorkExtn(String workExtn) {
		if (workExtn.length() == 3)
			this.workExtn = workExtn;
		else
			return -1;
		return 0;
	}

	public int setWorkNum(String workNum) {
		if (workNum.length() == 8)
			this.workNum = workNum;
		else
			return -1;
		return 0;
	}

	public int setHomeArea(String homeArea) {
		if (homeArea.length() == 3)
			this.homeArea = homeArea;
		else
			return -1;
		return 0;
	}

	public int setHomeCountry(String homeCountry) {
		if (homeCountry.length() == 2)
			this.homeCountry = homeCountry;
		else
			return -1;
		return 0;
	}

	public int setHomeNum(String homeNum) {
		if (homeNum.length() == 8)
			this.homeNum = homeNum;
		else
			return -1;
		return 0;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getMobileCountry() {
		return mobileCountry;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public String getWorkExtn() {
		return workExtn;
	}

	public String getWorkNum() {
		return workNum;
	}

	public String getHomeArea() {
		return homeArea;
	}

	public String getHomeCountry() {
		return homeCountry;
	}

	public String getHomeNum() {
		return homeNum;
	}
}
