package com.kabam.doa.ui.services;

public class Doa_Views_City_View_Service extends Doa_Service_BaseFixture {

	public void wait_for_fortress_to_present() {
		waitForFlashObjectToPresentOnScreen(city_view_fortress, 60);
	}

	public boolean isFortressPresent() {
		return isPresent(city_view_fortress);
	}

	public void click_fortress() {
		click(city_view_fortress);
	}

	public void click_empty_slot1() {
		click(city_view_slot_1_empty);
	}

	public boolean isEmptySlot1Present() {
		return isPresent(city_view_slot_1_empty);
	}

	public void click_slot1_with_building() {
		click(city_view_slot_1_with_build);
	}

	public void click_empty_slot2() {
		click(city_view_slot_2_empty);
	}

	public boolean isEmptySlot2Present() {
		return isPresent(city_view_slot_2_empty);
	}

	public void click_slot2_with_building() {
		click(city_view_slot_2_with_build);
	}

	public void click_empty_slot3() {
		click(city_view_slot_3_empty);
	}

	public void click_slot3_with_building() {
		click(city_view_slot_3_with_build);
	}

	public void click_empty_slot4() {
		click(city_view_slot_4_empty);
	}

	public void click_slot4_with_building() {
		click(city_view_slot_4_with_build);
	}

	public void click_dragons_keep() {
		click(city_view_dragons_keep);
	}

}
