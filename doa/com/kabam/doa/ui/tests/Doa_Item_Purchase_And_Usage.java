package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(dependsOnGroups = { "FTE" }, groups = { "Doa_Item_Purchase_And_Usage", "regression" })
public class Doa_Item_Purchase_And_Usage extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {

	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	public void user_can_buy_and_use_harvest_nanos() {
		doa_admin_tool_service.add_rubies_for_player(playerName, "1000");
		doa_account_service.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();

		doa_dialog_service.wait_for_play_lucky_chances_window_present();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_left_pannel_service.click_shop_button();
		doa_shop_service.click_tab_production();
		doa_shop_service.select_production_item1_to_buy();
		doa_shop_service.go_to_my_items();
		doa_shop_service.select_production_item1_to_use();
		doa_shop_service.close_shop_dialog();
		Assert.assertTrue(doa_icon_bar_servcie.isNanoIconPresent());
	}

}
