package com.kabam.doa.ui.services;

public class Doa_Views_Map_View_Service extends Doa_Service_BaseFixture {

	public boolean isLocation_widget_visible() {
		return isPresent(map_view_location_widget);
	}

	public boolean isFirstCityVisible() {
		return isPresent(map_view_first_city);
	}

}
