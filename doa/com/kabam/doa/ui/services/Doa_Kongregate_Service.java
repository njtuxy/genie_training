package com.kabam.doa.ui.services;

public class Doa_Kongregate_Service extends Doa_Service_BaseFixture {

	public void open_kongregate() {
		openURL("http://www.kongregate.com");
	}

	public void open_doa_kongregate_url() {
		openURL("http://www.kongregate.com/games/kabam/dragons-of-atlantis");
	}

	public void loginKongregateAsXY() {
		loginKongregate("tahoe2012", "123456789");
		javaWaitInSeconds(5);
	}

}
