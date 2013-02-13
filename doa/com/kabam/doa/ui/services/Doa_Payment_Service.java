package com.kabam.doa.ui.services;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Doa_Payment_Service extends Doa_Service_BaseFixture {

	public void wait_for_payment_page_to_load() {
		wait_for_html_element_to_present(By.id("game_frame"), 60);
	}

	// public void select_credit_card_as_payment_method() {
	// execute_javascript_on_browser(browser, "document.getElementsByClassName('TrialPay')[0].click();");
	// }
	//
	// public void select_paypal_as_payment_method() {
	// execute_javascript_on_browser(browser, "document.getElementsByClassName('PayPal')[0].click();");
	// }
	//
	// public void select_mobile_as_payment_method() {
	// execute_javascript_on_browser(browser, "document.getElementsByClassName('Boku')[0].click();");
	// }

	// The package index is counted globally cross TrailPay, PayPal, Mobile and other methods. for DOA, the range is [1..20]
	public void select_package_and_click_buy_now(int packageIndexOnUI) {
		get_into_second_level_frame();
		int real_package_index = packageIndexOnUI - 1;
		execute_javascript_on_browser(browser, "document.getElementsByClassName('priceContainer')[" + real_package_index + "].click();");
		execute_javascript_on_browser(browser, "document.getElementById('buyButton').click()");

		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public String get_gold_amount_from_payment_page() {
	// get_into_third_level_frame();
	// return exectue_javaScript_and_return_result(browser, "document.getElementsByClassName('amount')[0].innerHTML");
	//
	// }
	//
	// public String get_price_from_payment_page() {
	// get_into_third_level_frame();
	// // return browser.findElement(By.className("price")).getText();
	// return exectue_javaScript_and_return_result(browser, "document.getElementsByClassName('price')[0].innerHTML");
	// }
	//
	// public String get_total_payment_from_payment_page() {
	// get_into_third_level_frame();
	// // return browser.findElement(By.className("amt")).getText();
	// return exectue_javaScript_and_return_result(browser, "document.getElementsByClassName('amt')[0].innerHTML");
	// }
	//
	// public void go_back_to_package_selector_page() {
	// get_into_second_level_frame();
	// }
}
