package pages;

import com.google.gson.JsonObject;
import common.FileUtil;
import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Map;

public class BasePage extends PageObject {
	protected static WebDriver driver;
	EnvironmentVariables environmentVariables;
	static final String CLASS_NAME="BASE_PAGE";
	public static JsonObject global_OR_Data;
	public static Map<Object, Object> globalVariables;

	private static final String APP = "https://github.com/cloudgrey-io/the-app/releases/download/v1.9.0/TheApp-v1.9.0.apk";
	private static final String APPIUM = "http://localhost:4723/wd/hub";

	public AndroidDriver androidDriver;

//	@Before
	public void setUp() throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "9");
		caps.setCapability("deviceName", "Android Emulator");
		caps.setCapability("automationName", "UiAutomator2");
		caps.setCapability("app", APP);
		driver = new AndroidDriver(new URL(APPIUM), caps);
	}

//	@After
//	public void tearDown() {
//		if (driver != null) {
//			driver.quit();
//		}
//	}
	
	/**
	 * create driver
	 */
	public final void setupDriver(String driverType) {
		driver = getDriver();
		open();
	}
	
	@Step("Load Data")
	public void setData() {
		global_OR_Data = FileUtil.loadOR();
	}
	
	@Step("Set Application Page")
	public void openApp() {

		if(driver==null)
			setupDriver(driverType);
		driver.get(environmentVariables.getProperty("environments.test.webdriver.base.url"));
	}
	
	
}
