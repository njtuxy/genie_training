package com.kabam.doa.ui.services;

public class Doa_Icon_Bar_Service extends Doa_Service_BaseFixture {
	public boolean isNanoIconPresent() {
		return isPresent(nano_icon);
	}

	public void click_nano_icon() {
		click(nano_icon);
	}
}
