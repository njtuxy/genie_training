package com.kabam.doa.ui.services;

public class Doa_Shop_Service extends Doa_Service_BaseFixture {

	public void click_tab_production() {
		click(shop_production_tab);
	}

	public void select_production_item1_to_buy() {
		click(shop_select_production_item1_to_buy);
	}

	public void select_production_item1_to_use() {
		click(shop_select_production_item1_to_use);
	}

	public void go_to_my_items() {
		click(shop_go_to_my_items);
	}

	public void close_shop_dialog() {
		click(shop_dialog_close_button);
	}

}
