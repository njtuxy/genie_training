package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "Accessibility", "regression" })
public class Doa_Accessibility_Tests extends Doa_Test_BaseFixture {

	// @BeforeMethod
	// public void test_level_setup() {
	// login_as_existing_user(doa_account_service.testAccount, doa_account_service.testPassword);
	//

	//
	// // Close Play Lucky Chances Dialog
	// doa_dialog_service.click("FP^RetentionPanel:::SE^DynamicButton:::CH^com.wonderhill.castlemania.view.components::PX^1::PTR^0::IX^3::ITR^1");
	// // doa_dialog_service.close_play_lucky_chances_dialog();
	// // doa_dialog_service.close_tournament_dialog();
	// }

	// @AfterMethod
	// public void test_level_teardown() {
	// tearDown();
	// }

	public void user_should_be_able_to_login_open_the_game() {

		login_as_existing_user(doa_account_service.testAccount, doa_account_service.testPassword);

		// Wait for fortress to show up
		doa_views_city_view_service.waitForFlashObjectToPresentOnScreen("FP^CapitalCityView:::SE^BuildingView:::CH^com.wonderhill.castlemania.view.components.core::PX^0::PTR^0::IX^4::ITR^0", 60);
		// doa_views_city_view_service.wait_for_fortress_to_present();

		// Close Play Lucky Chances Dialog
		doa_dialog_service.click("FP^RetentionPanel:::SE^DynamicButton:::CH^com.wonderhill.castlemania.view.components::PX^1::PTR^0::IX^3::ITR^1");
		// doa_dialog_service.close_play_lucky_chances_dialog();

		doa_dialog_service.click("FP^LoginPromoPanel:::SE^DynamicButton:::CH^com.wonderhill.castlemania.view.components::PX^1::PTR^0::IX^1::ITR^0");

		Assert.assertTrue(doa_views_city_view_service.isPresent("FP^CapitalCityView:::SE^BuildingView:::CH^com.wonderhill.castlemania.view.components.core::PX^0::PTR^0::IX^4::ITR^0"));

		// Assert.assertEquals(doa_dialog_service.get_dialog_title(), "Homes Level 1");
	}

}
