package com.kabam.doa.ui.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.util.EntityUtils;

public class Doa_Admin_Tool_Service {

	public static String userID = null;
	public static String playerID = null;

	DefaultHttpClient httpClient = new DefaultHttpClient();
	public static boolean isLoggedIn = false;

	public void add_rubies_for_player(String playerName, String rubiesQuantity) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		login_doa_admin_tool(httpClient);
		search_by_doa_player_name_and_get_user_id_and_player_id(httpClient, playerName);
		doa_add_rubies(httpClient, userID, rubiesQuantity);
		httpClient.getConnectionManager().shutdown();
	}

	public void add_gold_for_player(String playerName, String godQuantity) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		login_doa_admin_tool(httpClient);
		search_by_doa_player_name_and_get_user_id_and_player_id(httpClient, playerName);
		doa_add_gold(httpClient, playerID, godQuantity);
		httpClient.getConnectionManager().shutdown();
	}

	private void doa_add_rubies(HttpClient httpClient, String userId, String rubiesQuantity) {
		System.out.println("\n" + "===== Grant Rubies =====================================================================");
		String doa_admin_tool_grant_rubies = "http://c1.staging-qa.castle.wonderhill.com:7236/hamburgers/users/" + userId + "/grant?grant%5Brubies_quantity%5D=" + rubiesQuantity + "&undefined=undefined";
		HttpPost httpost = new HttpPost(doa_admin_tool_grant_rubies);
		try {
			httpClient.execute(httpost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpost.releaseConnection();

	}

	private void doa_add_gold(HttpClient httpClient, String playerID, String godQuantity) {
		System.out.println("\n" + "===== Add Gold! =====================================================================");
		String doa_admin_tool_add_gold = "http://c1.staging-qa.castle.wonderhill.com:7236/hamburgers/players/" + playerID + "/resources?resource%5Bamount%5D=" + godQuantity + "&resource%5Bcity_id%5D=562&resource%5Btype%5D=gold&undefined=undefined";
		HttpPost httpost = new HttpPost(doa_admin_tool_add_gold);
		try {
			httpClient.execute(httpost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpost.releaseConnection();
	}

	private <T extends HttpUriRequest> T setBasicAuthentication(T request) {
		String username = "admin@kabam.com";
		String password = "admin";
		String authKey = null;
		try {
			authKey = Base64.encodeBase64String((username + ":" + password).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		authKey = authKey.substring(0, authKey.length() - 1);

		if (authKey != null) {
			request.addHeader("Authorization", "Basic " + authKey);
		}

		return request;
	}

	private void printTheHtmlContent(HttpEntity entity) {
		System.out.println("\n" + "===== HTML Content =====================================================================");
		if (entity != null) {
			long len = entity.getContentLength();
			if (len != -1 && len < 8000) {
				System.out.println("entity's length: " + len);
				try {

					// System.out.println("--------------------HTML content start-------------------------");
					System.out.println(EntityUtils.toString(entity));
					EntityUtils.consume(entity);
					// System.out.println("--------------------HTML content end ----------------------------");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				System.out.println("entity is too long or too short: " + len);
		}

		else
			System.out.println("entity is null!!");
	}

	private void printJson(HttpEntity entity) {
		System.out.println("\n" + "===== Json Content =====================================================================");

		if (entity != null) {
			InputStream is = null;
			try {
				is = entity.getContent();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				String line = br.readLine();
				String text = null;
				StringBuffer sb = new StringBuffer();
				while (line != null) {
					sb.append(line + "\n");
					line = br.readLine();
				}

				System.out.println(sb.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private HttpEntity sendAGetRequest(HttpClient httpClient, String url) {
		System.out.println("\n" + "===== GetMethod =====================================================================");
		// System.out.println("--------------------Get Request start-------------------------");
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpget);
			System.out.println("Get method status:" + response.getStatusLine());
			System.out.println("Get method headers: " + response.getHeaders("Location"));

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		return entity;
	}

	public void getCookiesInfo(DefaultHttpClient httpClient) {
		System.out.println("\n" + "===== Get Cookies =====================================================================");
		// System.out.println("\n" + "--------------------get cookies start-------------------------");
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println("- " + cookies.get(i).toString());
			}
		}
		// System.out.println("\n" + "--------------------get cookies stop-------------------------");
	}

	// It is one post request to the doa login form url
	private void login_doa_admin_tool(HttpClient httpClient) {
		if (isLoggedIn == false) {
			String doa_admin_tool_login_form = "http://realm2.c1.staging-qa.castle.wonderhill.com:7236/hamburgers/session/";
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("email", "admin@kabam.com"));
			nvps.add(new BasicNameValuePair("password", "admin"));
			sendAPostRequest(httpClient, doa_admin_tool_login_form, nvps);
			isLoggedIn = true;
		}
	}

	private void search_by_doa_player_name_and_get_user_id_and_player_id(HttpClient httpClient, String playerName) {
		if (userID == null) {
			String doa_search_by_player_name = "http://c1.staging-qa.castle.wonderhill.com:7236/hamburgers/users?player_name=" + playerName + "&realm_id=2&commit=Search";
			HttpGet httpget = new HttpGet(doa_search_by_player_name);
			BasicHttpContext localContext = new BasicHttpContext();
			try {
				httpClient.execute(httpget, localContext);
				// HttpHost currentHost = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				HttpUriRequest currentReq = (HttpUriRequest) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
				// String currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost.toURI() + currentReq.getURI());

				userID = currentReq.getURI().toString().split("/")[3].split("[?]")[0];
				playerID = currentReq.getURI().toString().split("/")[3].split("[?]")[1].replaceAll("[^\\d]", "");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			httpget.releaseConnection();
		}
	}

	private void sendAPostRequest(HttpClient httpClient, String url, List<NameValuePair> nvps) {
		System.out.println("\n" + "===== Post metod =====================================================================");
		HttpPost httpost = new HttpPost(url);
		HttpResponse response = null;
		httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

		httpost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7");
		httpost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpost.setHeader("Content-Language", "en-US");
		// httpost = setBasicAuthentication(httpost);

		try {
			response = httpClient.execute(httpost);
			httpost.releaseConnection();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Post method status: " + response.getStatusLine());
		System.out.println("Post method headers: " + response.getAllHeaders());
		// httpost.releaseConnection();
		// System.out.println("\n" + "--------------------Post method stop-------------------------");
	}
}

// httpost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7");
// httpost.setHeader("Content-Type", "application/x-www-form-urlencoded");
// httpost.setHeader("Content-Language", "en-US");