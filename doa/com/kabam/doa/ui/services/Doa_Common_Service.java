package com.kabam.doa.ui.services;

public class Doa_Common_Service extends Doa_Service_BaseFixture {

	// static Doa_UI_Objects doa_ui_ojbects = new Doa_UI_Objects();

	public void click_use_3_rubies_to_reduce_time_by_5_minutes() {
		click(click_use_3_rubies_to_reduce_time_by_5_minutes);
		javaWaitInSeconds(10);
	}

	public void click_speed_button() {
		click(click_speed_button);
	}

	public String get_the_rubies_count() {
		return getTextFromFlashObject(get_rubies_amount);
	}

	public void click_buy_rubies_button() {
		click(click_buy_rubies_button);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> waitting for click buy rubies button");
		javaWaitInSeconds(10);
	}

	public void click_speed_up_button() {
		click(click_speed_up_button);
	}

}
