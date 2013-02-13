package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(dependsOnGroups = { "Doa_Building_Upgrade" }, groups = { "Doa_Building_Disintegration", "regression" })
public class Doa_Building_Disintegration_Tests extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {

	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	public void user_should_be_able_to_destory_existing_buildings() {
		doa_admin_tool_service.add_rubies_for_player(playerName, "1000");
		doa_account_service.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_views_city_view_service.click_slot1_with_building();
		doa_dialog_service.click_disintegrate_button();
		doa_dialog_service.click_use_a_mass_nullifier_to_destroy_building();
		doa_dialog_service.click_disintegrate_button_on_confirm_dialog();
		Assert.assertTrue(doa_views_city_view_service.isEmptySlot1Present());
	}

}
