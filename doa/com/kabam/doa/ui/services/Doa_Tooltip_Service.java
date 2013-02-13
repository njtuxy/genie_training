package com.kabam.doa.ui.services;

public class Doa_Tooltip_Service extends Doa_Service_BaseFixture {

	public String get_tooltip_content(){
		return getTextFromFlashObject(tooltip);
	}
}
