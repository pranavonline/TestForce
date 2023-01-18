package pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.google.gson.JsonObject;

import common.FileUtil;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;

public class BasePage extends PageObject {
	protected static WebDriver driver;
	EnvironmentVariables environmentVariables;
	static final String CLASS_NAME="BASE_PAGE";
	public static JsonObject global_OR_Data;
	public static Map<Object, Object> globalVariables;
	
	
	/**
	 * create driver
	 */
	public final void setupDriver() {
		driver = getDriver();
		open();
	}
	
	@Step("Load Data")
	public void setData() {
		global_OR_Data = FileUtil.loadOR();
	}
	
	@Step("Set Application Page")
	public void openApp( ) {
		if(driver==null)
			setupDriver();
		driver.get(environmentVariables.getProperty("environments.test.webdriver.base.url"));
	}
	
	
}
