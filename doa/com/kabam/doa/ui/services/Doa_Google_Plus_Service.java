package com.kabam.doa.ui.services;

import org.openqa.selenium.By;

public class Doa_Google_Plus_Service extends Doa_Service_BaseFixture {

	public void open_godfather_google_plus_page() {
		openURL("https://plus.google.com/games/659749063556");
	}

	public void loginGooglePlusAsTestAccount() {
		loginGooglePlus("gcline.kabam@gmail.com", "test1234kabam");
	}

	public void loginGooglePlus(String emailAddress, String userPassword) {
		browser.findElement(By.id("Email")).sendKeys(emailAddress);
		browser.findElement(By.id("Passwd")).sendKeys(userPassword);
		browser.findElement(By.name("signIn")).click();
	}

}
