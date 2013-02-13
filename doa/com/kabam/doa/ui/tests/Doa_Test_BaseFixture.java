package com.kabam.doa.ui.tests;

import com.kabam.doa.ui.services.Doa_Admin_Tool_Service;
import com.kabam.doa.ui.services.Doa_Chat_Service;
import com.kabam.doa.ui.services.Doa_Common_Service;
import com.kabam.doa.ui.services.Doa_Dialogs_Service;
import com.kabam.doa.ui.services.Doa_FTE_Service;
import com.kabam.doa.ui.services.Doa_Facebook_Service;
import com.kabam.doa.ui.services.Doa_Google_Plus_Service;
import com.kabam.doa.ui.services.Doa_Icon_Bar_Service;
import com.kabam.doa.ui.services.Doa_Kongregate_Service;
import com.kabam.doa.ui.services.Doa_Left_Pannel_Service;
import com.kabam.doa.ui.services.Doa_Marketing_Page_Service;
import com.kabam.doa.ui.services.Doa_Payment_Service;
import com.kabam.doa.ui.services.Doa_Right_Pannel_Service;
import com.kabam.doa.ui.services.Doa_Shop_Service;
import com.kabam.doa.ui.services.Doa_Tooltip_Service;
import com.kabam.doa.ui.services.Doa_Top_Pannel_Service;
import com.kabam.doa.ui.services.Doa_Views_City_View_Service;
import com.kabam.doa.ui.services.Doa_Views_Field_View_Service;
import com.kabam.doa.ui.services.Doa_Views_Map_View_Service;

public abstract class Doa_Test_BaseFixture {
	public static Doa_FTE_Service doa_fteService = new Doa_FTE_Service();
	public static Doa_Common_Service doa_common_service = new Doa_Common_Service();
	public static Doa_Payment_Service doa_payment_service = new Doa_Payment_Service();
	public static Doa_Chat_Service doa_chat_service = new Doa_Chat_Service();
	public static Doa_Top_Pannel_Service doa_top_pannel_service = new Doa_Top_Pannel_Service();
	public static Doa_Dialogs_Service doa_dialog_service = new Doa_Dialogs_Service();
	public static Doa_Views_Map_View_Service doa_views_map_view_service = new Doa_Views_Map_View_Service();
	public static Doa_Views_Field_View_Service doa_views_field_view_service = new Doa_Views_Field_View_Service();
	public static Doa_Views_City_View_Service doa_views_city_view_service = new Doa_Views_City_View_Service();
	public static Doa_Right_Pannel_Service doa_right_pannel_service = new Doa_Right_Pannel_Service();
	public static Doa_Admin_Tool_Service doa_admin_tool_service = new Doa_Admin_Tool_Service();
	public static Doa_Left_Pannel_Service doa_left_pannel_service = new Doa_Left_Pannel_Service();
	public static Doa_Shop_Service doa_shop_service = new Doa_Shop_Service();
	public static Doa_Tooltip_Service doa_tooltip_service = new Doa_Tooltip_Service();
	public static Doa_Icon_Bar_Service doa_icon_bar_servcie = new Doa_Icon_Bar_Service();
	public static Doa_Account_Service doa_account_service = new Doa_Account_Service();
	public static Doa_Marketing_Page_Service doa_marketing_page_service = new Doa_Marketing_Page_Service();
	public static Doa_Facebook_Service doa_facebook_service = new Doa_Facebook_Service();
	public static Doa_Kongregate_Service doa_kongregate_service = new Doa_Kongregate_Service();
	public static Doa_Google_Plus_Service doa_google_plus_service = new Doa_Google_Plus_Service();

	public void tearDown() {
		doa_fteService.close_browser_and_stop_genie();
	}

	public final static String userAccount = doa_fteService.generate_unique_email_address();
	public final static String playerName = doa_fteService.generate_unique_user_name();
	public final static String userPassword = "123456789";

	public void printUserInfo() {
		System.out.println(">>>>>>>>>********** the user account this time:" + userAccount + ">>>>>>>>>**********");
		System.out.println(">>>>>>>>>********** the user name this time:" + playerName + ">>>>>>>>>**********");
	}

	public void login_as_existing_user() {
		doa_account_service.login_with_exisiting_account(userAccount, userPassword);

	}

	public void login_as_existing_user(String userName, String userPassword) {
		doa_account_service.login_with_exisiting_account(userName, userPassword);
	}

}
