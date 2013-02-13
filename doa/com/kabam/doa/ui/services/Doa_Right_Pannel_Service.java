package com.kabam.doa.ui.services;

public class Doa_Right_Pannel_Service extends Doa_Service_BaseFixture {

	public void open_construction_progress() {
		click(right_pannel_construction_button);
	}

	public String get_construction_progress_build_type() {
		return getTextFromFlashObject(right_pannel_construction_progress_build_type);
	}

	public String get_construction_progress_build_level() {
		return getTextFromFlashObject(right_pannel_construction_progress_build_level);
	}

	public String get_construction_progress_build_city() {
		return getTextFromFlashObject(right_pannel_construction_progress_build_city);
	}

	public void click_construction_speed_button() {
		click(right_pannel_construction_progress_speed_button);
	}

	public void open_traning_progress() {
		click(right_pannel_tranning_button);
	}

	public void click_traninig_speed_button() {
		click(right_pannel_tranning_progress_speed_button);
	}

	public String get_training_troops_type() {
		return getTextFromFlashObject(right_pannel_tranning_progress_troops_type);
	}

	public String get_traning_troops_quantity() {
		return getTextFromFlashObject(right_pannel_tranning_progress_troops_quantity).replace("(", "").replace(")", "");
	}
}
