package com.kabam.doa.ui.services;

public class Doa_Chat_Service extends Doa_Service_BaseFixture {

	public void input_chat_message(String message) {
		inputTextFromKeyBoard(chat_input_box, message);
	}

	public String get_chat_message_1() {
		return getTextFromFlashObject(chat_message_1);
	}

	public String get_chat_user_name_1() {
		return getTextFromFlashObject(chat_box_userName_1);
	}

}
