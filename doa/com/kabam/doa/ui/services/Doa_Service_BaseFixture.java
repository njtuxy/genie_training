package com.kabam.doa.ui.services;

import genie_tests_driver.Genie_Selenium_Driver;

public class Doa_Service_BaseFixture extends Genie_Selenium_Driver implements IDoa_UI_Objects {

	// Load the properties file before run any service and test
	public Doa_Service_BaseFixture() {
		super("resources/doa/production.runtime.properties");
	}
}
