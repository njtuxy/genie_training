package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(description = "test user can go through the FTE of DOA", groups = { "FTE", "smokeTest", "regression" })
public class Doa_FTE_Tests extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {

	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	public void user_should_be_able_to_go_through_the_doa_fte() {
		printUserInfo();
		doa_account_service.create_new_account_and_login(userAccount, userPassword);
		doa_fteService.close_browser_alert();
		doa_fteService.init_genie_and_connect_to_flash_game();
		doa_fteService.wait_for_fte_first_next_button_present();

		doa_fteService.click_fte_first_next_button();
		doa_fteService.click_fte_next_button();
		doa_fteService.select_avatar_Solerian();
		// TODO The player name is to long.
		doa_fteService.set_player_name(playerName);
		doa_fteService.click_selecting_avatar_done_button();
		doa_fteService.click_FTE_guide_next_button();

		doa_fteService.click_FTE_navbutton_city();
		doa_fteService.click_FTE_navbutton_first_building();
		doa_fteService.click_FTE_select_homes_to_build();
		doa_fteService.click_FTE_build_button();
		// It takes 10 seconds to finish the building
		doa_fteService.javaWaitInSeconds(15);

		doa_fteService.click_FTE_navbutton_field();
		doa_fteService.click_FTE_navbutton_first_field();
		doa_fteService.click_FTE_select_lumbermill_to_build();
		doa_fteService.click_FTE_build_button();

		// It takes 10 seconds to finish the building
		doa_fteService.javaWaitInSeconds(5);

		doa_fteService.click_FTE_go_to_my_items();
		doa_fteService.javaWaitInSeconds(2);
		doa_fteService.click_FTE_use_item_button();
		doa_fteService.click_FTE_ok_button();
		doa_fteService.click_fte_first_next_button();
		doa_fteService.click_FTE_guide_next_button();

		doa_fteService.click_FTE_quests_button();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_quest_1_button();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_claim_reward();
		doa_fteService.javaWaitInSeconds(2);
		doa_fteService.click_FTE_quest_2_button();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_claim_reward();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_guide_next_button();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_build_first_farm();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_select_farm_to_build();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_build_button();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_guide_next_button();
		doa_fteService.javaWaitInSeconds(1);
		doa_fteService.click_FTE_guide_finish_button();
		doa_fteService.javaWaitInSeconds(4);
		Assert.assertEquals(doa_common_service.get_the_rubies_count(), "10");
	}
}
