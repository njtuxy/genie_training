package com.kabam.doa.ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "Payment", "regression" })
public class Doa_Payment_Tests extends Doa_Test_BaseFixture {

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

	public void user_should_be_able_to_purchase_9_99_package() {
		doa_common_service.click_buy_rubies_button();
		doa_payment_service.wait_for_payment_page_to_load();
		doa_payment_service.select_package_and_click_buy_now(1);
		Assert.assertEquals(doa_payment_service.get_diamonds_amount_from_trailpay_payment_page(), "65 Rubies");
		Assert.assertEquals(doa_payment_service.get_total_price_from_trailpay_payment_page(), "USD 9.90");
	}

	public void user_should_be_able_to_purchase_19_99_package() {
		doa_common_service.click_buy_rubies_button();
		doa_payment_service.wait_for_payment_page_to_load();
		doa_payment_service.select_package_and_click_buy_now(2);
		Assert.assertEquals(doa_payment_service.get_diamonds_amount_from_trailpay_payment_page(), "155 Rubies");
		Assert.assertEquals(doa_payment_service.get_total_price_from_trailpay_payment_page(), "USD 19.90");
	}

}
