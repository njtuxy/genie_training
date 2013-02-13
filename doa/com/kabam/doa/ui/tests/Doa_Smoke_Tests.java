package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.kabam.doa.ui.services.Doa_Left_Pannel_Service;

public class Doa_Smoke_Tests extends Doa_Test_BaseFixture {
	@BeforeMethod
	public void test_level_setup() {

	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	/*
	// @Test(description = "test user can go through the FTE of DOA", groups = { "FTE", "smokeTest" })
	public void user_should_be_able_to_go_through_the_doa_fte() {
		doa_account_service.create_new_account_and_login(userAccount, userPassword);
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();

		doa_fteService.click_fte_first_next_button();
		doa_fteService.click_fte_next_button();
		doa_fteService.select_avatar_Solerian();
		doa_fteService.set_player_name(playerName);
		doa_fteService.click_selecting_avatar_done_button();
		doa_fteService.click_FTE_guide_next_button();

		doa_fteService.click_FTE_navbutton_city();
		doa_fteService.click_FTE_navbutton_first_building();
		doa_fteService.click_FTE_select_homes_to_build();
		doa_fteService.click_FTE_build_button();

		doa_fteService.click_FTE_navbutton_field();
		doa_fteService.click_FTE_navbutton_first_field();
		doa_fteService.click_FTE_select_lumbermill_to_build();
		doa_fteService.click_FTE_build_button();

		doa_fteService.click_FTE_go_to_my_items();
		doa_fteService.click_FTE_use_item_button();
		doa_fteService.click_FTE_ok_button();
		doa_fteService.click_fte_first_next_button();
		doa_fteService.click_FTE_guide_next_button();

		doa_fteService.click_FTE_quests_button();
		doa_fteService.click_FTE_start_loggin_button();
		doa_fteService.click_FTE_claim_reward();
		doa_fteService.click_FTE_start_loggin_button();
		doa_fteService.click_FTE_claim_reward();
		doa_fteService.click_FTE_guide_next_button();
		doa_fteService.click_FTE_build_first_farm();
		doa_fteService.click_FTE_select_farm_to_build();
		doa_fteService.click_FTE_build_button();
		doa_fteService.click_FTE_guide_next_button();
		doa_fteService.click_FTE_guide_finish_button();
		Assert.assertEquals(doa_common_service.get_the_rubies_count(), "10");
	}

	// @Test(dependsOnMethods = { "user_should_be_able_to_go_through_the_doa_fte" }, description = "test user should be able to go through the doa FTE")
	public void user_should_be_able_to_login_with_the_new_account_and_build_new_buildings() {
		doa_account_sevcie.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_views_city_view_service.click_empty_slot1();
		// doa_common_service.select_homes_to_build();
		// doa_common_service.click_build_button();

		// It takes 10 seconds to finish building the homes
		doa_common_service.javaWaitInSeconds(12000);

		doa_views_city_view_service.click_empty_slot2();
		// doa_common_service.select_garrison_to_build();
		// doa_common_service.click_build_button();
		doa_right_pannel_service.open_construction_progress();
		doa_right_pannel_service.click_construction_speed_button();
		doa_common_service.click_use_3_rubies_to_reduce_time_by_5_minutes();

		// Verify the building is on screen
		doa_views_city_view_service.click_slot1_with_building();
		Assert.assertEquals(doa_dialog_service.get_dialog_title(), "Homes Level 1");
		doa_dialog_service.close_building_details_dialog();

		// Verify the building is on screen
		doa_views_city_view_service.click_slot2_with_building();
		Assert.assertEquals(doa_dialog_service.get_dialog_title(), "Garrison Level 1");
		doa_dialog_service.close_building_details_dialog();
	}

	// @Test(dependsOnMethods = { "user_should_be_able_to_login_with_the_new_account_and_build_new_buildings" }, description = "test user can train troops")
	public void users_are_able_to_train_troops() {
		doa_account_sevcie.login_with_exisiting_account(userAccount, userPassword);
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

	// @Test(dependsOnMethods = { "user_should_be_able_to_login_with_the_new_account_and_build_new_buildings" }, description = "test user can upgrade dragon's keep")
	public void users_should_be_able_to_upgrade_dragons_keep() {
		doa_account_sevcie.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_views_city_view_service.click_dragons_keep();
		doa_dialog_service.click_upgrade_dragons_keep();
		doa_right_pannel_service.open_construction_progress();
		Assert.assertEquals(doa_right_pannel_service.get_construction_progress_build_type(), "Dragon's Keep");
	}

	// @Test(dependsOnMethods = { "user_should_be_able_to_go_through_the_doa_fte" }, description = "test user should be able to change the tax rate")
	public void user_should_be_able_to_update_the_tax_rate() {
		doa_account_sevcie.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_views_city_view_service.click_fortress();
		doa_dialog_service.set_tax_rate_on_fortress_dialog("20");
		doa_dialog_service.click_change_tax_rate_button();

		Assert.assertEquals(doa_dialog_service.getConfirmationDialogMessage(), "You have successfully changed the tax rate");
		Assert.assertEquals(doa_dialog_service.getConfirmationDialogTitle(), "Taxes Changes");
		doa_dialog_service.click_ok_on_confirmation_dialog();
		Assert.assertEquals(doa_dialog_service.get_current_tax_rate(), "20");
	}

	// @Test(dependsOnMethods = { "user_should_be_able_to_login_with_the_new_account_and_build_new_buildings" }, description = "test user should be able to destroy exsisting buildings")
	public void user_should_be_able_to_destory_existing_buildings() {
		doa_admin_tool_service.add_rubies_for_player(playerName, "1000");
		doa_account_sevcie.login_with_exisiting_account(userAccount, userPassword);
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

	// @Test(dependsOnMethods = { "user_should_be_able_to_destory_existing_buildings" }, description = "test user should be able to buy and use harvest nanos")
	public void user_can_buy_and_use_harvest_nanos() {
		doa_account_sevcie.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_left_pannel_service.click_shop_button();
		doa_shop_service.click_tab_production();
		doa_shop_service.select_production_item1_to_buy();
		doa_shop_service.go_to_my_items();
		doa_shop_service.select_production_item1_to_use();
		doa_shop_service.close_shop_dialog();
		Assert.assertTrue(doa_icon_bar_servcie.isNanoIconPresent());
	}

	// @Test(dependsOnMethods = { "user_should_be_able_to_go_through_the_doa_fte" }, description = "test user should be able to go open payment page")
	public void user_should_be_able_to_open_payment_page() {
		doa_account_sevcie.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();

		doa_common_service.click_buy_rubies_button();
		doa_payment_service.wait_for_payment_page_to_load();
		doa_payment_service.select_package_and_click_buy_now(1);
		Assert.assertEquals(doa_payment_service.get_diamonds_amount_from_payment_page(), "65 Gold");
		Assert.assertEquals(doa_payment_service.get_total_price_from_payment_page(), "USD 9.99");
	}

	// @Test(dependsOnMethods = { "user_should_be_able_to_go_through_the_doa_fte" }, description = "the buttons in the top pannel function correctly")
	public void the_buttons_on_the_top_pannel_function_correctly() {
		doa_account_sevcie.login_with_exisiting_account(userAccount, userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
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

	public void temp() {
		doa_admin_tool_service.add_gold_for_player("njtuxy", "1000");
		doa_admin_tool_service.add_rubies_for_player("28952f", "1000");
	}

	// The "ENTER" key doesn't work
	public void user_should_be_able_to_chat_and_view_chat_history() {
		String chat_message = "this is automation testing chat message!";
		doa_account_sevcie.login_with_exisiting_account("615316qaautomation@kabam.com", userPassword);
		doa_fteService.click_play_now_button();
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_dialog_service.close_play_lucky_chances_dialog();

		doa_chat_service.input_chat_message(chat_message);
		Assert.assertTrue(doa_chat_service.get_chat_message_1().contains(chat_message));
	}
	
	*/

}
