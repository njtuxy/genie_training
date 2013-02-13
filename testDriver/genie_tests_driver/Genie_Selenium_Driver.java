package genie_tests_driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import org.browsermob.proxy.ProxyServer;
import org.browsermob.core.har.Har;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import com.adobe.genie.executor.Genie;
import com.adobe.genie.executor.GenieLocatorInfo;
import com.adobe.genie.executor.LogConfig;
import com.adobe.genie.executor.components.GenieComponent;
import com.adobe.genie.executor.components.GenieDisplayObject;
import com.adobe.genie.executor.components.GenieTextInput;
import com.adobe.genie.executor.exceptions.ConnectionFailedException;
import com.adobe.genie.executor.exceptions.StepFailedException;
import com.adobe.genie.executor.exceptions.StepTimedOutException;
import com.adobe.genie.executor.uiEvents.UIGenieID;
import com.adobe.genie.executor.uiEvents.UIKeyBoard;
import com.adobe.genie.executor.uiEvents.UILocal;
import com.adobe.genie.genieCom.SWFApp;

public class Genie_Selenium_Driver extends Account_Creation{

	// properties file property
	public static Properties testRunTimeProperties = null;
	public static Properties uiObjectsProperties = null;

	// Genie services parameters
	public static SWFApp swfGameApp = null;
	public static Genie genie = null;
	public static String gameName = null;
	public static UIGenieID uiGenieID = null;

	// Selenium properties
	public static ProxyServer server = null;
	public static Proxy proxy = null;
	public static WebDriver browser = null;
	public static Actions action = null;
	public static String browserTypeDefinedInConfigFile = null;
	public static String siteDomain = null;
	public static String testAccount = null;
	public static String testPassword = null;
	public static String testPlayerName = null;

	// Genie properties
	public static int flashAreaStartX;
	public static int flashAreaStartY;
	
	//For accessibility tets
	public static String game_genie_id = null;

	protected static String gameURL;
	

	public Genie_Selenium_Driver(String testRunTimePropertiesFilePath) {
		// Load test runtine properties file and keep one instance
		load_test_runtime_properties(testRunTimePropertiesFilePath);
	}

	// Method to init Genie driver and connect to Flash game. Used after browser is launched
	public boolean init_genie_and_connect_to_flash_game() {
		// Initialize Genie and set the log folder
		if (genie == null) {
			try {
				LogConfig logConfig = new LogConfig();
				// logConfig.setNoLogging(false);
				if (isWindows()) {
					logConfig.setLogFolder(testRunTimeProperties.getProperty("GenieLogDirOnWindows"));
				} else {
					logConfig.setLogFolder(testRunTimeProperties.getProperty("GenieLogDirOnOtherOS"));
				}
				genie = Genie.init(logConfig);
				// genie.EXIT_ON_FAILURE = true;
				// genie.CAPTURE_SCREENSHOT_ON_FAILURE = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Connect to Flash game application with Genie, the timeout of waiting is 65 seconds
		if (swfGameApp == null) {
			try {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Connecting to the Flash Game! --> " + gameName);
				swfGameApp = genie.waitForAppToConnect(gameName, 65);
			} catch (ConnectionFailedException e) {
				e.printStackTrace();
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Failed to connect to the game! --> " + gameName);
				return false;
			}
		}

		// Set the flashArea start coordinates
		if (uiGenieID == null) {
			uiGenieID = new UIGenieID(swfGameApp);
			uiGenieID.setFlashAreaStartCoordinates(flashAreaStartX, flashAreaStartY);
		}		
		
		return true;
	}

	public boolean proxy_toggled() {
		return Boolean.parseBoolean(System.getProperty("proxy.server").toLowerCase());
	}

	// Method to start proxy server
	public void start_proxy_server() {
		// start the proxy
		server = new ProxyServer(9090);
		try {
			server.start();
		} catch(Exception e) {
			e.printStackTrace();
		}

		// get the Selenium proxy object
		try {
			proxy = server.seleniumProxy();
		} catch(java.net.UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void stop_proxy_server() {
		if(server != null) {
			try {
				server.stop();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Har create_har(String label) {
		// // create a new HAR with a label
		return server.newHar(label);
	}

	public Har get_har() {
		return get_har("Selenium_test");
	}

	public Har get_har(String file) {
		// // get the HAR data
		Har har = server.getHar();
		String strFilePath = "test-output/har/" + file + ".har";
		try {
			FileOutputStream fos = new FileOutputStream(strFilePath);
			har.writeTo(fos);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return har;
	}

	// Method to open browser and maximize the window for testing by SELENIUM
	public void start_selenium_browser_and_maxmize_the_browser_window(String browserType) {
		if (browser == null) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Launching the browser! <<<<<<<<<<<<<<<<");

			if (browserType.equals("Firefox")) {
				// Setup Firefox profile to allow Selenium Webdriver to access
				// the elements inside the Iframe
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("capability.policy.default.Window.QueryInterface", "allAccess");
				firefoxProfile.setPreference("capability.policy.default.Window.frameElement.get", "allAccess");
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
				if (proxy != null) {
					capabilities.setCapability(CapabilityType.PROXY, proxy);
				}
				browser = new FirefoxDriver(capabilities);
			}

			if (browserType.equals("IE")) {
				File file = new File("C:/Selenium/IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				if (proxy != null) {
					capabilities.setCapability(CapabilityType.PROXY, proxy);
				}
				browser = new InternetExplorerDriver(capabilities);
			}

			if (browserType.equals("Chrome")) {
				if (isWindows()) {
					File file = new File("C:/Selenium/chromedriver.exe");
					System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				} else {
					System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
				}
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
				if (proxy != null) {
					capabilities.setCapability(CapabilityType.PROXY, proxy);
				}
				browser = new ChromeDriver(capabilities);
			}
			// Maximize the browser window
			browser.manage().window().maximize();

			// Allow implicit waits for elements to be found
			browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Create object to handle actions such as mouse movement and hover
			action = new Actions(browser);
		}
	}

	// Use SELENIUM to open the target URL
	public void openURL(String url) {
		start_selenium_browser_and_maxmize_the_browser_window(browserTypeDefinedInConfigFile);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Opening the expected URL! <<<<<<<<<<<<<<<<");
		browser.get(url);
	}

	// Use Selenium to close the browser alert(warning) by clicking 'continue'
	// on it. If there is no alert present, just skip this method.
	public void close_browser_alert() {
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Closing the browser alert! <<<<<<<<<<<<<<<<");
			Alert alert = browser.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {

		}
	}

	public void loginKabamFromHeaderLogin(String email, String password) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Logging in with valid user email and password! <<<<<<<<<<<<<<<<");
		browser.findElement(By.id("login-email")).sendKeys(email);
		browser.findElement(By.id("login-password")).sendKeys(password);
		browser.findElement(By.id("header-login-submit")).click();
		waitForAjaxToBeFinished();
	}
	
	public void loginKabamFromMainPage(String email, String password){
		browser.findElement(By.id("login-email-modal")).sendKeys(email);
		browser.findElement(By.id("login-password-modal")).sendKeys(password);
		browser.findElement(By.id("login-submit-modal")).click();
				
	}

	public void loginFB(String email, String password) {
		wait_for_html_element_to_present(By.id("email"), 5);
		browser.findElement(By.id("email")).sendKeys(email);
		browser.findElement(By.id("pass")).sendKeys(password);
		browser.findElement(By.id("pass")).sendKeys(Keys.ENTER);
	}

	// Close the browser and reset all the properties
	public void close_browser_and_stop_genie() {
		if (genie != null) {
			genie.stop();
			genie = null;
		}

		if (browser != null) {
			browser.quit();
			browser = null;
		}

		if (swfGameApp != null) {
			swfGameApp = null;
		}

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop_genie() {
		if (genie != null) {
			genie.stop();
			genie = null;
		}

		if (swfGameApp != null) {
			swfGameApp = null;
		}
	}

	public String get_current_browser_url() {
		return browser.getCurrentUrl();
	}

	public void close_invite_friends_window() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Close the invitation window! <<<<<<<<<<<<<<<<");
		wait_for_html_element_to_present(By.id("modal"), 5);
		browser.findElement(By.className("simplemodal-close")).click();
	}

	protected void wait_for_html_element_to_present(By htmlElementLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(browser, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(htmlElementLocator));
	}

	protected boolean does_element_exist(By htmlElementLocation, int timeout) {
		WebDriverWait wait = new WebDriverWait(browser, timeout);
		javaWaitInSeconds(5);
		if (browser.findElements(htmlElementLocation).size() != 0)
			return true;
		else
			return false;
	}

	// ******* Iframe + Selenium operations start
	public void wait_for_frame_to_be_available_and_switch_to_it(String frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(browser, timeout);
		wait.until(xyframeToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void wait_for_frame_to_be_available_and_switch_to_it(int frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(browser, timeout);
		wait.until(xyframeToBeAvailableAndSwitchToIt(frameLocator));
	}

	public static ExpectedCondition<WebDriver> xyframeToBeAvailableAndSwitchToIt(final String frameLocator) {
		return new ExpectedCondition<WebDriver>() {
			public WebDriver apply(WebDriver from) {
				try {
					return from.switchTo().frame(frameLocator);
				} catch (NoSuchFrameException e) {
					return null;
				}
			}
		};
	}

	public static ExpectedCondition<WebDriver> xyframeToBeAvailableAndSwitchToIt(final int frameLocator) {
		return new ExpectedCondition<WebDriver>() {
			public WebDriver apply(WebDriver from) {
				try {
					return from.switchTo().frame(frameLocator);
				} catch (NoSuchFrameException e) {
					return null;
				}
			}
		};
	}

	// ******* Iframe + Selenium operations end

	// Detect OS type by Java:
	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
	}

	// load config.properties file
	public void load_test_runtime_properties(String testRunTimePropertiesFilePath) {
		if (testRunTimeProperties == null) {
			try {
				testRunTimeProperties = new Properties();
				testRunTimeProperties.load(new FileInputStream(testRunTimePropertiesFilePath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// read and set the Selenium properties
		browserTypeDefinedInConfigFile = testRunTimeProperties.getProperty("Browser");
		if(browserTypeDefinedInConfigFile == null) {
			browserTypeDefinedInConfigFile = System.getProperty("browser.type");
		}
		siteDomain = testRunTimeProperties.getProperty("SiteDomain");
		gameURL = testRunTimeProperties.getProperty("gameURL");
		testAccount = testRunTimeProperties.getProperty("Test_Account");
		testPassword = testRunTimeProperties.getProperty("Test_Account_password");
		testPlayerName = testRunTimeProperties.getProperty("Test_Player_Name");

		// read and set the Genie properties
		flashAreaStartX = Integer.parseInt(testRunTimeProperties.getProperty("FlashAreaStartX"));
		flashAreaStartY = Integer.parseInt(testRunTimeProperties.getProperty("FlashAreaStartY"));
		gameName = testRunTimeProperties.getProperty("GameName");
		
		if (testRunTimeProperties.getProperty("game_loadded_genie_id") != null){
			game_genie_id = testRunTimeProperties.getProperty("game_loadded_genie_id");	
		}		
		
	}

	// load uiObjects.properties file
	public void load_test_ui_object_propoerties(String uiObjectsPropertiesFilePath) {
		if (uiObjectsProperties == null) {
			try {
				uiObjectsProperties = new Properties();
				uiObjectsProperties.load(new FileInputStream(uiObjectsPropertiesFilePath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Wrap up genie methods:

	public void click(String genieID) {
		try {
			new GenieDisplayObject(genieID, swfGameApp).click();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void click(String genieID, String genieObjectName) {
		try {
			new GenieDisplayObject(genieID, swfGameApp).click();
		} catch (StepFailedException e) {
			System.out.println("Cannot find this Flash object on page: " + genieObjectName);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO Fix double click method
	public void doubleClick(String genieID, int x, int y) {
		try {

			GenieDisplayObject object = new GenieDisplayObject(genieID, swfGameApp);
			double x_coord = object.getLocalCoordinates().getX();
			double y_coord = object.getLocalCoordinates().getY();
			// System.out.println("Local X: " + x_coord + "Local Y: " + y_coord);
			object.doubleClick(x_coord, y_coord);

		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void click_inside(String genieIDkey, int x, int y) {
		try {
			uiGenieID.click(genieIDkey, x, y);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void click_inside(String genieIDkey, String x, String y) {
		int x_in_int = Integer.parseInt(x);
		int y_in_int = Integer.parseInt(x);
		try {
			uiGenieID.click(genieIDkey, x_in_int, y_in_int);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTextFromFlashObject(String genieID) {
		String textValue = null;
		try {
			textValue = (new GenieDisplayObject(genieID, swfGameApp)).getValueOf("text");
		} catch (StepFailedException e) {
			System.out.println("another error!!");
			// e.printStackTrace();
		} catch (StepTimedOutException e) {
			System.out.println("Cannot find the UI object on Screen!!");
			// e.printStackTrace();

		}
		return textValue;
	}

	public String getPropertyValueOfFlashObject(String genieIDKey, String propertyName) {
		String propertyValue = null;
		try {
			propertyValue = (new GenieDisplayObject(genieIDKey, swfGameApp)).getValueOf(propertyName);
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyValue;
	}

	public boolean isPresent(String genieIDKey) {
		Boolean isPresent = false;
		isPresent = (new GenieDisplayObject(genieIDKey, swfGameApp)).isPresent();
		return isPresent;
	}

	public static boolean isVisible(String genieIDKey) {
		Boolean isVisible = false;
		try {
			isVisible = (new GenieDisplayObject(genieIDKey, swfGameApp)).isVisible();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isVisible;
	}

	public void inputKey(String genieIDKey, String key) {
		try {
			new GenieTextInput(genieIDKey, swfGameApp).type(key);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inputTextFor(String genieIDKey, String text) {
		try {
			(new GenieTextInput(genieIDKey, swfGameApp)).input(text);
			javaWaitInSeconds(1);
			(new GenieTextInput(genieIDKey, swfGameApp)).type("ENTER");
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inputTextFromKeyBoard(String genieID, String text) {
		try {
			(new GenieTextInput(genieID, swfGameApp)).click();
			(new UIKeyBoard()).typeText(text);
			(new UIKeyBoard()).typeKey("VK_ENTER");
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remove_all_text_in_inputBox(String genieID, int startIndex, int endIndex) {
		try {
			(new GenieTextInput(genieID, swfGameApp)).selectText(startIndex, endIndex);
			(new GenieTextInput(genieID, swfGameApp)).type("DELETE", "true");
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void waitForFlashObjectToPresentOnScreen(String genieID, int timeoutInSeconds) {
		long timeToWait;
		timeToWait = System.currentTimeMillis() + (timeoutInSeconds * 1000L);
		boolean isPresent = false;

		while (isPresent == false && System.currentTimeMillis() < timeToWait) {
			isPresent = isPresent(genieID);
			javaWaitInMilliSeconds(200);
		}
	}

	public void waitForPropertyValue(String genieIDKey, String property, String value, int timeout) {
		try {
			(new GenieComponent(genieIDKey, swfGameApp)).waitForPropertyValue(property, value, timeout);
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// In order to generate unique email address
	// public String generate_unique_email_address() {
	// int sixdDigitsRandomInt = (int) (Math.random() * 9000000) + 10;
	// return Integer.toString(sixdDigitsRandomInt) + "qaautomation@kbautomation.com";
	// }

	// In order to generate unique username
	// public String generate_unique_user_name() {
	// return "bbk" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
	//
	// }

	public void javaWaitInSeconds(int timeoutInSeconds) {
		try {
			Thread.sleep(timeoutInSeconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void javaWaitInMilliSeconds(int timeoutInMilliSeconds) {
		try {
			Thread.sleep(timeoutInMilliSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dragTo(int fromX, int fromY, int toX, int toY) {
		try {
			(new UILocal(swfGameApp)).drag(fromX, fromY, toX, toY);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resizeWindow(int x, int y) {
		Dimension dimension = new Dimension(x, y);
		browser.manage().window().setSize(dimension);
	}

	/**
	 * Waits for an Ajax command to finish by polling the jQuery.active property until it returns 0
	 */
	public void waitForAjaxToBeFinished() {
		Long complete = 1L;
		while (true) // Handle timeout somewhere
		{
			try {
				complete = (Long) ((JavascriptExecutor) browser).executeScript("return jQuery.active");
			} catch (WebDriverException e) {
				if (e.getMessage().contains("jQuery is not defined")) {
					complete = 0L;
				} else {
					throw e;
				}
			}
			if (complete == 0L)
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String[] getSubItemGenieIDsOfOneClass(String className) {
		GenieComponent[] comps = getGenieComponentsByQualifiedClassName(className);
		String[] slotGenieIDs = new String[comps.length];
		for (int i = 0; i < slotGenieIDs.length; i++) {
			slotGenieIDs[i] = comps[i].getGenieID();
		}
		return slotGenieIDs;
	}

	public GenieComponent[] getGenieComponentsByQualifiedClassName(String qualifiedClassName) {
		GenieLocatorInfo info = new GenieLocatorInfo(); // creates the GenieLocatorInfo Object
		info.qualifiedClassName = qualifiedClassName;
		GenieComponent[] comps = null;
		try {
			comps = (new GenieComponent("", swfGameApp)).getChildren(info, true);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comps;
	}

	public String[] getItemsWhoseLabelIs(String text) {

		GenieLocatorInfo info = new GenieLocatorInfo(); // creates the GenieLocatorInfo Object
		info.text = text;
		String[] genieIDs = null;
		GenieComponent[] comps = null;
		try {
			comps = (new GenieComponent("", swfGameApp)).getChildren(info, true);
			genieIDs = new String[comps.length];
			for (int i = 0; i < genieIDs.length; i++) {
				genieIDs[i] = comps[i].getGenieID();
			}

		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return genieIDs;
	}

	public String[] getChildrenOfGivenObjectByClassName(String GenieID, String qualifiedClassName) {
		GenieLocatorInfo info = new GenieLocatorInfo(); // creates the GenieLocatorInfo Object
		info.qualifiedClassName = qualifiedClassName;
		GenieComponent[] comps = null;
		try {
			comps = (new GenieComponent(GenieID, swfGameApp)).getChildren(info, true);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getGenieIDFromComponents(comps);
	}

	public void clickGenieComponent(GenieComponent genieComponent) {
		try {
			genieComponent.click();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// find flash object by its class name, will return an array, because multiple objects can share one class name
	public GenieComponent[] findByClassName(String qualifiedClassName) {
		GenieLocatorInfo info = new GenieLocatorInfo(); // creates the GenieLocatorInfo Object
		info.qualifiedClassName = qualifiedClassName;
		GenieComponent[] comps = null;
		try {
			comps = (new GenieComponent("", swfGameApp)).getChildren(info, true);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comps;
	}

	// find flash object by its class name, will return an array, because multiple objects can share one class name
	public GenieComponent findByClassName(String qualifiedClassName, int index) {
		GenieLocatorInfo info = new GenieLocatorInfo(); // creates the GenieLocatorInfo Object
		info.qualifiedClassName = qualifiedClassName;
		GenieComponent[] comps = null;
		try {
			comps = (new GenieComponent("", swfGameApp)).getChildren(info, true);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comps[index];
	}

	// find by Children label text, will return an array.
	public GenieComponent findChildrenByLabelText(GenieComponent parentGeineComponent, String label, int index) {
		GenieLocatorInfo info = new GenieLocatorInfo(); // creates the GenieLocatorInfo Object
		info.text = label;
		GenieComponent[] comps = null;
		try {
			comps = (new GenieComponent(parentGeineComponent.getGenieID(), swfGameApp)).getChildren(info, true);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comps[index];
	}

	// find by Children index number, only return on children
	public GenieComponent findChildrenByIndex(GenieComponent parentGeineComponent, int index) {
		GenieLocatorInfo info = new GenieLocatorInfo(); // creates the GenieLocatorInfo Object
		info.index = index;
		GenieComponent comps = null;
		try {
			comps = (new GenieComponent(parentGeineComponent.getGenieID(), swfGameApp)).getChildAt(index);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comps;
	}

	// find by Children by class name, will return an array
	public GenieComponent findChildrenByClassName(GenieComponent parentGeineComponent, String qualifiedClassName, int index) {
		GenieLocatorInfo info = new GenieLocatorInfo(); // creates the GenieLocatorInfo Object
		info.qualifiedClassName = qualifiedClassName;
		GenieComponent[] comps = null;
		try {
			comps = (new GenieComponent(parentGeineComponent.getGenieID(), swfGameApp)).getChildren(info, true);
		} catch (StepTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comps[index];
	}

	public String[] getGenieIDFromComponents(GenieComponent[] comps) {

		String[] genieIDStringArray = new String[comps.length];
		for (int i = 0; i < genieIDStringArray.length; i++) {
			genieIDStringArray[i] = comps[i].getGenieID();
		}
		return genieIDStringArray;
	}

	/****************** Payment page methods start ******************/
	/****************** Keep the debugging message until the methods become stable ******************/
	public void get_into_second_level_frame() {
		javaWaitInSeconds(10);
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>getting to main window");
			String windowHandel = browser.getWindowHandle();
			browser.switchTo().window(windowHandel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>getting to frame 1");
			wait_for_frame_to_be_available_and_switch_to_it(0, 60);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>getting to frame 2");
			wait_for_frame_to_be_available_and_switch_to_it(0, 60);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void get_into_third_level_frame() {
		javaWaitInSeconds(10);
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>getting to main window");
			browser.switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>getting to frame 1");
			wait_for_html_element_to_present(By.id("game_frame"), 60);
			wait_for_frame_to_be_available_and_switch_to_it(0, 60);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>getting to frame 2");
			wait_for_html_element_to_present(By.id("paymentFrame"), 60);
			wait_for_frame_to_be_available_and_switch_to_it(0, 60);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>getting to frame 3");
			wait_for_html_element_to_present(By.className("provider_iframe"), 60);
			wait_for_frame_to_be_available_and_switch_to_it(0, 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* TrialPay methods start */
	public void select_trialpay_as_payment_method() {
		get_into_second_level_frame();
		String select_credit_card_as_payment_method = "//div[@id='providerContainer']/ul/li/div";
		wait_for_html_element_to_present(By.xpath(select_credit_card_as_payment_method), 60);
		browser.findElement(By.xpath(select_credit_card_as_payment_method)).click();
		// execute_javascript_on_browser(browser, "document.getElementsByClassName('TrialPay')[0].click();");
	}

	public String get_diamonds_amount_from_trailpay_payment_page() {
		get_into_third_level_frame();
		String diamonds_amount = "id('submit_form')/div[2]/div[1]/table/tbody/tr[1]/td[1]";
		wait_for_html_element_to_present(By.xpath(diamonds_amount), 60);
		return browser.findElement(By.xpath(diamonds_amount)).getText();
	}

	public String get_total_price_from_trailpay_payment_page() {
		get_into_third_level_frame();
		String total_price = "id('submit_form')/div[2]/div[1]/table/tbody/tr[2]/td[2]/span";
		wait_for_html_element_to_present(By.xpath(total_price), 60);
		return browser.findElement(By.xpath(total_price)).getText();
	}

	/* Trial pay payment page methods end */

	/* PayPal methods start */
	public void select_paypal_as_payment_method() {
		get_into_second_level_frame();
		execute_javascript_on_browser(browser, "document.getElementsByClassName('PayPal')[0].click();");
	}

	public String get_diamonds_amount_from_paypal_payment_page() {
		return browser.findElement(By.id("showname0")).getText();
	}

	public String get_grand_total_price_from_paypal_payment_page() {
		return browser.findElement(By.className("grandTotal")).getText();
	}

	public String get_price_from_paypal_payment_page() {
		return browser.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/div/form[3]/div/div/div/div[2]/div/div[2]/div/ol/li/ul/li/ul/li/span[2]")).getText();
	}

	/* PayPalpayment page methods end */

	public void go_back_to_package_selector_page() {
		get_into_second_level_frame();
		browser.findElement(By.xpath("/html/body/div/div/div")).click();
	}

	public void refresh_browser_page() {
		browser.navigate().refresh();
	}

	/* JavaScript executor methods start */
	protected void execute_javascript_on_browser(WebDriver browser, String javaScript) {
		((JavascriptExecutor) browser).executeScript(javaScript);
	}

	protected String exectue_javaScript_and_return_result(WebDriver browser, String javaScript) {
		return (String) ((JavascriptExecutor) browser).executeScript("return" + javaScript);
	}

	/* JavaScript executor methods end */

	/* Mobile payment methods start */
	public void select_mobile_as_payment_method() {
		get_into_second_level_frame();
		execute_javascript_on_browser(browser, "document.getElementsByClassName('Boku')[0].click();");
	}

	/* Mobile payment methods end */

	/******************* Payment page methods end *******************/

	/******************* Login methods start *******************/
	public void create_new_account_and_login(String userAccount, String userPassword) {
		openURL(siteDomain);
		wait_for_html_element_to_present(By.id("header-reg"), 60);
		browser.findElement(By.id("header-reg")).click();
		wait_for_html_element_to_present(By.id("register-email"), 60);
		browser.findElement(By.id("register-email")).sendKeys(userAccount);
		browser.findElement(By.id("register-password")).sendKeys(userPassword);
		browser.findElement(By.id("register-confirm-password")).sendKeys(userPassword);
		Select month = new Select(browser.findElement(By.id("register_birthdate_2i")));
		month.selectByVisibleText("May");
		Select day = new Select(browser.findElement(By.id("register_birthdate_3i")));
		day.selectByVisibleText("10");
		Select year = new Select(browser.findElement(By.id("register_birthdate_1i")));
		year.selectByVisibleText("1970");
		browser.findElement(By.id("register-submit")).click();
		waitForAjaxToBeFinished();
	}

	// Login an existing account with SELENIUM
	public void login_with_exisiting_account(String userAccount, String userPassword) {
		openURL(siteDomain);
		loginKabamFromHeaderLogin(userAccount, userPassword);
		click_play_now_button();
		close_browser_alert();
		init_genie_and_connect_to_flash_game();
	}

	public void click_play_now_button() {
		wait_for_html_element_to_present(By.id("home-playnow"), 60);
		browser.findElement(By.id("home-playnow")).click();
	}

	/******************* Login methods end *******************/

	public void loginFacebook(String emailAddress, String userPassword) {
		browser.findElement(By.id("email")).sendKeys(emailAddress);
		browser.findElement(By.id("pass")).sendKeys(userPassword);
		browser.findElement(By.name("login")).click();
	}

	public void loginKongregate(String userName, String userPassword) {
		browser.findElement(By.id("welcome_username")).sendKeys(userName);
		browser.findElement(By.id("welcome_password")).sendKeys(userPassword);
		browser.findElement(By.id("welcome_box_sign_in_button")).click();
	}

	public boolean switchToWindowByTitle(String titleOfWindow) {
		String currentWindow = browser.getWindowHandle();
		Set<String> availableWindows = browser.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				if (browser.switchTo().window(windowId).getTitle().equals(titleOfWindow)) {
					return true;
				} else {
					browser.switchTo().window(currentWindow);
				}
			}
		}
		return false;
	}

	public void switchToNewWindow() {
		Set<String> availableWindows = browser.getWindowHandles();
		for (String windowId : availableWindows) {
			browser.switchTo().window(windowId);
		}
	}

	public void wait_for_new_window_to_be_available(int timeout) {
		WebDriverWait wait = new WebDriverWait(browser, timeout);
		wait.until(waitForNewWindowPresent(1));
	}

	public static ExpectedCondition<Boolean> waitForNewWindowPresent(final int windowsBefore) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver browser) {
				return browser.getWindowHandles().size() == windowsBefore + 1;
			}
		};
	}

}
