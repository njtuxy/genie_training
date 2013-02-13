package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(dependsOnGroups = { "FTE" }, groups = { "Doa_Building_Construction", "regression" })
public class Doa_Building_Construction_Tests extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {
		doa_account_service.login_with_exisiting_account(userAccount, userPassword);
		// doa_admin_tool_service.add_rubies_for_player("014bd9", "1000");
		// doa_fteService.login_with_exisiting_account("757102qaautomation@kabam.com", userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.wait_for_play_lucky_chances_window_present();
		doa_dialog_service.close_play_lucky_chances_dialog();
	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	public void user_should_be_able_to_login_with_the_new_account_and_build_new_buildings() {
		doa_views_city_view_service.click_slot_on_city_view_by_slot_index(5);
		doa_dialog_service.select_homes_to_build();
		doa_dialog_service.click_build_button();
		

		// It takes 10 seconds to finish building the homes
		doa_common_service.javaWaitInSeconds(12000);
		doa_views_city_view_service.click_slot_on_city_view_by_slot_index(6);
		doa_dialog_service.select_garrison_to_build();
		doa_dialog_service.click_build_button_on_upgrade_building_dialog();
		doa_right_pannel_service.open_construction_progress();
		doa_right_pannel_service.click_construction_speed_button();
		// doa_common_service.click_use_3_rubies_to_reduce_time_by_5_minutes();

		// Verify the building is on screen
		doa_views_city_view_service.click_slot_on_city_view_by_slot_index(5);
		Assert.assertEquals(doa_dialog_service.get_dialog_title(), "Homes Level 1");
		doa_dialog_service.close_building_details_dialog();

		// Verify the building is on screen
		doa_views_city_view_service.click_slot_on_city_view_by_slot_index(6);
		Assert.assertEquals(doa_dialog_service.get_dialog_title(), "Garrison Level 1");
		doa_dialog_service.close_building_details_dialog();
	}
}
