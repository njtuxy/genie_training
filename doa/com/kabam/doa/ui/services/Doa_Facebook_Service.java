package com.kabam.doa.ui.services;

public class Doa_Facebook_Service extends Doa_Service_BaseFixture {

	public void open_doa_facebook_url() {
		openURL("http://apps.facebook.com/dragonsofatlantis/");
	}

	public void loginFacebookAsJeff() {
		loginFacebook("jschenk@heroforce.com", "123456");
	}

	public void loginFacebookAsWaylon() {
		loginFacebook("wstipes@kabam.com", "test123");
	}

}
