package com.kabam.doa.ui.services;

import org.openqa.selenium.By;

public class Doa_Marketing_Page_Service extends Doa_Service_BaseFixture {

	public void open_marketing_page() {
		openURL("http://www.dragonsofatlantis.com");
	}

	public void fill_out_the_new_user_form_and_sumbit(String emailAddress, String userPassword) {
		browser.findElement(By.id("lbform_emailaddress")).sendKeys(emailAddress);
		browser.findElement(By.id("lbform_password")).sendKeys(userPassword);
		browser.findElement(By.id("lbform_year")).sendKeys("1970");
		browser.findElement(By.id("lbform_month")).sendKeys("August");
		browser.findElement(By.id("lbform_day")).sendKeys("10");
		if (browser.findElement(By.id("opt_in")).isSelected()) {
			browser.findElement(By.id("opt_in")).click();
		}
		browser.findElement(By.id("lbform_submit")).click();
	}

}
