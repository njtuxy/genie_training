package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "Accessibility", "regression" })
public class Doa_Accessibility_Tests extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {
		login_as_existing_user(doa_account_service.testAccount, doa_account_service.testPassword);
		doa_views_city_view_service.wait_for_fortress_to_present();
		doa_dialog_service.close_play_lucky_chances_dialog();
		doa_dialog_service.close_tournament_dialog();

	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	public void user_should_be_able_to_login_and_check_existing_buildings() {
		doa_views_city_view_service.click_slot_on_city_view_by_slot_index(3);
		Assert.assertEquals(doa_dialog_service.get_dialog_title(), "Homes Level 1");
	}

}
