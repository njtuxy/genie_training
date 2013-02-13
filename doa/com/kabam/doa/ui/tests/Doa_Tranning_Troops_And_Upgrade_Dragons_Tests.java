package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(dependsOnGroups = { "FTE" }, groups = { "Doa_Tranning_Troops_And_Upgrade_Dragons_Tests", "regression" })
public class Doa_Tranning_Troops_And_Upgrade_Dragons_Tests extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {

	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	public void users_are_able_to_train_troops() {
		doa_account_service.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_views_city_view_service.click_slot2_with_building();
		doa_dialog_service.click_train_troops_on_garrison_dialog();
		doa_dialog_service.select_porter_to_train_on_train_troops_dialog();
		doa_dialog_service.set_quantity_of_troops_to_train_on_train_troops_dialog("1");
		doa_dialog_service.click_begin_training();

		doa_right_pannel_service.open_traning_progress();
		Assert.assertEquals(doa_right_pannel_service.get_training_troops_type(), "Porter", "the troops in traning should be Porter!");
		Assert.assertEquals(doa_right_pannel_service.get_traning_troops_quantity(), "1", "the quantity in traning should be 1!");
	}

	public void users_should_be_able_to_upgrade_dragons_keep() {
		doa_account_service.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_views_city_view_service.click_dragons_keep();
		doa_dialog_service.click_upgrade_dragons_keep();
		doa_right_pannel_service.open_construction_progress();
		Assert.assertEquals(doa_right_pannel_service.get_construction_progress_build_type(), "Dragon's Keep");
	}

}
