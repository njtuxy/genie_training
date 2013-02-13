package com.kabam.doa.ui.services;

import genie_tests_driver.Genie_Selenium_Driver;

import org.openqa.selenium.By;

import com.adobe.genie.executor.components.GenieComponent;

public class Doa_Service_BaseFixture extends Genie_Selenium_Driver implements IDoa_UI_Objects {

	protected static String platform = System.getProperty("game.platform");

	// Load the properties file before run any service and test

	public Doa_Service_BaseFixture() {
		super(String.format("resources/doa/%s.runtime.properties", platform));
	}

	public void click_by_index(String parentClassName, int index) {

		if (findByClassName(parentClassName, 0).getGenieID().equals("")) {
			clickGenieComponent(findChildrenByIndex(findByClassName(parentClassName, 1), index));
		}

		else {
			clickGenieComponent(findChildrenByIndex(findByClassName(parentClassName, 0), index));
		}
	}

	public void click_by_index_1(String parentClassName, int index) {

		GenieComponent[] temp = findByClassName(parentClassName);

		// why I am doing this: sometimes the findByClassName will return 2 children and the first one is null, so add this statement here.
		// will look into it to identify whether it's Genie issue of game issue. but good enough for testing now. -- Yan

		if (temp[0].getGenieID().equals("")) {
			clickGenieComponent(findChildrenByIndex(temp[1], index));
		}

		else {
			clickGenieComponent(findChildrenByIndex(temp[0], index));
		}
	}

	public void click_by_index_1(GenieComponent parent, int index) {
		clickGenieComponent(findChildrenByIndex(parent, index));
	}

	public GenieComponent get_children_by_index(String parentClassName, int index) {
		GenieComponent[] temp = findByClassName(parentClassName);

		// why I am doing this: sometimes the findByClassName will return 2 children and the first one is null, so add this statement here.
		// will look into it to identify whether it's Genie issue of game issue. but good enough for testing now. -- Yan

		if (temp[0].getGenieID().equals("")) {
			return findChildrenByIndex(temp[1], index);
		}

		else {
			return findChildrenByIndex(temp[0], index);
		}

	}

}
