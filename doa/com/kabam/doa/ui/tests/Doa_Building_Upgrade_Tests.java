package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "Doa_Building_Upgrade" , "regression"})


public class Doa_Building_Upgrade_Tests extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {
		login_as_existing_user("njtuxy@hotmail.com", "5225653");
	}

	
	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

}
