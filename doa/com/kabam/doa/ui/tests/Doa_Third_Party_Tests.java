package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "Third_Party_Tests", "regression" })
public class Doa_Third_Party_Tests extends Doa_Test_BaseFixture {

	@BeforeMethod
	public void test_level_setup() {
	}

	@AfterMethod
	public void test_level_teardown() {
		tearDown();
	}

	public void user_can_register_from_marketing_page() {
		doa_marketing_page_service.open_marketing_page();
		doa_marketing_page_service.fill_out_the_new_user_form_and_sumbit(userAccount, userPassword);
		doa_marketing_page_service.init_genie_and_connect_to_flash_game();
		String doaGameURL = "https://www.kabam.com/games/dragons-of-atlantis/play";
		Assert.assertEquals(doa_marketing_page_service.get_current_browser_url(), doaGameURL);
		doa_fteService.wait_for_fte_first_next_button_present();
		Assert.assertTrue(doa_fteService.is_fte_first_next_button_present());

	}

	public void user_should_be_able_to_launch_game_via_facebook() {
		doa_facebook_service.open_doa_facebook_url();
		doa_facebook_service.loginFacebookAsWaylon();
		doa_facebook_service.init_genie_and_connect_to_flash_game();
		doa_views_city_view_service.wait_for_fortress_to_present();
		Assert.assertTrue(doa_views_city_view_service.isFortressPresent());
	}

	// public void user_should_be_able_to_launch_game_via_kongregate() {
	// doa_kongregate_service.open_kongregate();
	// doa_kongregate_service.loginKongregateAsXY();
	// doa_kongregate_service.open_doa_kongregate_url();
	// doa_kongregate_service.init_genie_and_connect_to_flash_game();
	// doa_views_city_view_service.wait_for_fortress_to_present();
	// Assert.assertTrue(doa_views_city_view_service.isFortressPresent());
	// }

	public void user_should_be_able_to_launch_doa_via_google_plus() {
		doa_google_plus_service.open_godfather_google_plus_page();
		doa_google_plus_service.loginGooglePlusAsTestAccount();
		doa_google_plus_service.init_genie_and_connect_to_flash_game();
		doa_google_plus_service.javaWaitInSeconds(20);
		doa_fteService.wait_for_fte_first_next_button_present();
		Assert.assertTrue(doa_fteService.is_fte_first_next_button_present());
	}
}
