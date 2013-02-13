package com.kabam.doa.ui.services;

import com.adobe.genie.executor.exceptions.StepFailedException;
import com.adobe.genie.executor.exceptions.StepTimedOutException;

public class Doa_Dialogs_Service extends Doa_Service_BaseFixture {

	public void wait_for_play_lucky_chances_window_present() {
		waitForFlashObjectToPresentOnScreen(close_play_lucky_chances_dialog, 120);
	}

	public void close_play_lucky_chances_dialog() {
		if (isPresent(close_play_lucky_chances_dialog))
			click(close_play_lucky_chances_dialog);
	}

	public void close_tournament_dialog() {
		if (isPresent(close_tournament_dialog))
			click(close_tournament_dialog);
	}

	public String get_dialog_title() {
		return getTextFromFlashObject(Dialog_title);
	}

	public void close_building_details_dialog() {
		click(close_building_details_dialog);
	}

	public void click_train_troops_on_garrison_dialog() {
		click(Garrison_dialog_train_toops);
	}

	public void select_porter_to_train_on_train_troops_dialog() {
		click(train_troops_dialog_select_porter);
	}

	public void set_quantity_of_troops_to_train_on_train_troops_dialog(String troopsQuantity) {
		remove_all_text_in_inputBox(train_troops_dialog_set_troops_amount_to_train, 0, 2);
		inputTextFor(train_troops_dialog_set_troops_amount_to_train, troopsQuantity);
	}

	public void click_begin_training() {
		click(train_troops_dialog_begin_tranning);
	}

	public void click_upgrade_dragons_keep() {
		click(upgrade_dragons_keep);
	}

	public void set_tax_rate_on_fortress_dialog(String taxRate) {
		remove_all_text_in_inputBox(Fortress_dialog_tax_rate, 0, 3);
		inputTextFor(Fortress_dialog_tax_rate, taxRate);
	}

	public void click_change_tax_rate_button() {
		click(Fortress_dialog_change_tax_rate_button);
	}

	public String get_current_tax_rate() {
		return getTextFromFlashObject(Fortress_dialog_tax_rate);
	}

	public String getConfirmationDialogTitle() {
		return getTextFromFlashObject(confirmation_dialog_title);
	}

	public String getConfirmationDialogMessage() {
		return getTextFromFlashObject(confirmation_dialog_message);
	}

	public void click_ok_on_confirmation_dialog() {
		click(confirmation_dialog_ok_button);
	}

	public void click_disintegrate_button() {
		click(click_disintegrate_button);
	}

	public void click_use_a_mass_nullifier_to_destroy_building() {
		click(click_use_a_mass_nullifier_to_destroy_building);
	}

	public void click_disintegrate_button_on_confirm_dialog() {
		click(click_disintegrate_button_on_confirm_dialog);
	}

	public void close_speed_items_dialog() {
		click(click_disintegrate_button_on_confirm_dialog);
	}

	public void close_choose_building_dialog() {
		String chooseBuildingDialogClassName = "com.wonderhill.castlemania.view.dialog::ChooseBuildingDialog";
		click_by_index_1(chooseBuildingDialogClassName, 2);
	}

	// The index of building type start from 3
	public void choose_building_type_by_index(int index) {
		// String bulding_selector = "com.wonderhill.castlemania.view.dialog::BuildingSelector";
		String chooseBuildingDialogClassName = "com.wonderhill.castlemania.view.dialog::ChooseBuildingDialog";
		try {
			get_children_by_index(chooseBuildingDialogClassName, 1).getChildAt(index).getChildAt(3).click();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void close_upgrade_building_dialog() {
		String upgrade_building_dialog_class = "com.wonderhill.castlemania.view.dialog::UpgradeBuildingDialog";
		click_by_index_1(upgrade_building_dialog_class, 2);
	}

	public void click_build_button_on_upgrade_building_dialog() {
		String upgrade_building_dialog_class = "com.wonderhill.castlemania.view.dialog::UpgradeBuildingDialog";
		click_by_index_1(get_children_by_index(upgrade_building_dialog_class, 1), 6);
	}

	// public void click_item_on_choose_building_dialog_by_text(String text) {
	//
	// String chooseBuildingDialogClassName = "com.wonderhill.castlemania.view.dialog::ChooseBuildingDialog";
	// GenieComponent chooseBuildingDialog = findByClassName(chooseBuildingDialogClassName, 0);
	//
	// if (chooseBuildingDialog.getGenieID().equals("")) {
	// chooseBuildingDialog = findByClassName(chooseBuildingDialogClassName, 1);
	// }
	//
	// clickGenieComponent(chooseBuildingDialog);
	//
	// clickGenieComponent(findChildrenByLabelText(chooseBuildingDialog, text, 0));
	//
	// }

	public void select_homes_to_build() {
		choose_building_type_by_index(3);
	}

	public void select_garrison_to_build() {
		choose_building_type_by_index(4);
	}

	public void click_build_button() {
		click_build_button_on_upgrade_building_dialog();
	}

	public void click_speed_up_button() {
		click(click_speed_up_button);
	}

}
