package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(dependsOnGroups = { "FTE" }, groups = { "Doa_Top_Pannel_Buttons_Tests", "regression" })
public class Doa_Top_Pannel_Buttons_Tests extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {

	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	public void the_buttons_on_the_top_pannel_function_correctly() {
		doa_account_service.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();

		doa_dialog_service.wait_for_play_lucky_chances_window_present();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_top_pannel_service.click_navigate_to_empire_button();
		Assert.assertEquals(doa_dialog_service.get_dialog_title(), "Capital City");
		doa_top_pannel_service.close_empire_dialog();

		doa_top_pannel_service.click_navigate_to_map_button();
		Assert.assertTrue(doa_views_map_view_service.isFirstCityVisible());

		doa_top_pannel_service.click_navigate_to_field_button();
		Assert.assertTrue(doa_views_field_view_service.isFortressPresent());

		doa_top_pannel_service.click_navigate_to_city_button();
		Assert.assertTrue(doa_views_city_view_service.isFortressPresent());
	}

}
