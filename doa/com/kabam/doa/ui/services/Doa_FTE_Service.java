package com.kabam.doa.ui.services;

public class Doa_FTE_Service extends Doa_Service_BaseFixture {

	public void wait_for_fte_first_next_button_present() {
		waitForFlashObjectToPresentOnScreen(FTE_first_next_button, 120);
	}

	public boolean is_fte_first_next_button_present() { 
		return isPresent(FTE_first_next_button);
	}

	public void click_fte_first_next_button() {
		click(FTE_first_next_button);
	}

	public void click_fte_next_button() {
		click(FTE_next_button);
	}

	public void select_avatar_Solerian() {
		click(FTE_select_avatar_Solerian);
	}

	public String get_player_name() {
		return getTextFromFlashObject(FTE_player_name);
	}

	public String get_city_name() {
		return getTextFromFlashObject(FTE_city_name);
	}

	public void clear_player_name_inputBox() {
		remove_all_text_in_inputBox(FTE_player_name, 0, 10);
	}

	public void set_player_name(String playerName) {
		clear_player_name_inputBox();
		inputTextFor(FTE_player_name, playerName);
	}

	public void click_selecting_avatar_done_button() {
		click(FTE_done_button);
	}

	public void click_FTE_guide_next_button() {
		click(FTE_guide_next_button);
	}

	public void click_FTE_navbutton_city() {
		click(FTE_navbutton_city);
	}

	public void click_FTE_navbutton_first_building() {
		click(FTE_navbutton_first_building_slot);
	}

	public void click_FTE_select_homes_to_build() {
		click(FTE_select_homes_to_build);
	}

	public void click_FTE_build_button() {
		click(FTE_build_button);
	}

	public void click_FTE_navbutton_field() {
		System.out.println("CLICKING THE FTE NAV BUTTON FIELD!!");
		click(FTE_navbutton_field);
	}

	public void click_FTE_navbutton_first_field() {
		click(FTE_navbutton_first_field);
	}

	public void click_FTE_select_lumbermill_to_build() {
		click(FTE_select_lumbermill_to_build);
		// javaWait(5000);
	}

	public void click_FTE_go_to_my_items() {
		click(FTE_go_to_my_items);
	}

	public void click_FTE_use_item_button() {
		click(FTE_use_item_button);
	}

	public void click_FTE_ok_button() {
		click(FTE_ok_button);
	}

	public void click_FTE_shop_next_button() {
		click(FTE_shop_next_button);
	}

	public void click_FTE_next_go_back_to_city() {
		click(FTE_next_go_back_to_city);
	}

	public void click_FTE_quests_button() {
		click(FTE_quests_button);
	}
	
	
	public void click_FTE_quest_1_button() {
		click(FTE_quest_1);
	}
	
	public void click_FTE_quest_2_button() {
		click(FTE_quest_2);
	}

	public void click_FTE_start_loggin_button() {
		click(FTE_start_loggin_button);
	}

	public void click_FTE_claim_reward() {
		click(FTE_claim_reward);
	}

	public void click_FTE_build_first_farm() {
		click(FTE_build_first_farm);
	}

	public void click_FTE_select_farm_to_build() {
		click(FTE_select_farm_to_build);
	}

	public void click_FTE_guide_finish_button() {
		click(FTE_guide_finish_button);
	}
}
