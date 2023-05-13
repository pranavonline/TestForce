package pages;

import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchShadowRootException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import common.FileUtil;
import net.serenitybdd.core.Serenity;
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
		setData();
		open();
	}
	
	public final void setDriver() {
		driver = Serenity.getDriver();
		setData();
	}
	
	@Step("Load Data")
	public void setData() {
		global_OR_Data = FileUtil.loadOR();
	}
	
	// Common Methods for this page
    public String getXPATH_OR(String or_variable) {
        return global_OR_Data.getAsJsonObject(CLASS_NAME).get(or_variable).getAsString();
    }
	
	@Step("Set Application Page")
	public void openApp(String var) {
		if(driver==null)
			setupDriver();
		if(environmentVariables.getProperty("environments." +var.toLowerCase().trim()+ ".webdriver.base.url")!=null)
			driver.get(environmentVariables.getProperty("environments.test.webdriver.base.url"));
		else
			driver.get(var);
	}
	
	public void closeOldTab() {
        String currentWindow = getDriver().getWindowHandle();
        if (getDriver().getWindowHandles().size() <= 1)
            return;
        for (String win : getDriver().getWindowHandles()) {
            if (!win.contentEquals(currentWindow)) {
                getDriver().switchTo().window(win);
                break;
            } else
                getDriver().close();
        }
    }
	
	
	
	@Step("Get the title of the application page")
	public String getTitle() {
		return Serenity.getDriver().getTitle();
	}
	
	@Step("Close browser")
	public void closeAndExitBrowser() {
		
//		// Close the browser
//		Serenity.getWebdriverManager().getCurrentDriver().close();

		// Quit the browser
		Serenity.getWebdriverManager().getCurrentDriver().quit();
	}
	
	@Step("Wait in seconds")
	public void waitInSeconds(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
		
	@Step("Wait for page to load")
	public void waitForPageToLoad(int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		JavascriptExecutor jsExecuter = (JavascriptExecutor) driver;
		wait.until(driver -> "complete".equals(jsExecuter.executeScript("return document.readyState")));
	}
	
	@Step("Click WebElement")
	public void clickWebElement(String label,String pageName) {
		// Is page class exists
		AtomicBoolean flag = new AtomicBoolean(true);
		try {
			Class.forName(pageName);// if not exists then pick the element from base class [Go to catch block]
			// if exists then pick the element from that class
			//Class.forName(CLASS_NAME).getClass().getField(pageName).get(By.xpath(pageName))
		} catch (ClassNotFoundException e) {
			if($$(By.xpath(getXPATH_OR("CLICK_ELEMENT").replace("textToReplace", label))).size()<1) {
				flag.set(false);
			} else{
				$$(By.xpath(getXPATH_OR("CLICK_ELEMENT").replace("textToReplace", label))).forEach(ele ->
				{
					if (ele.isClickable()) {
						ele.click();
						flag.set(true);
					}
				});
			}
		} finally {
			if(!flag.get()){
				//verify the shadow

				for(WebElement ele : driver.findElements(By.xpath("//*"))) {
					SearchContext shadowRoot = null;
					try {
					shadowRoot = ele.getShadowRoot();
					}catch(NoSuchShadowRootException e) {
						continue;
					}
					WebElement finalElement = getElement_ShadowDOM(shadowRoot, By.linkText(label));
					if (finalElement==null){
						finalElement = getElement_ShadowDOM(shadowRoot, By.cssSelector("button[title=\""+ label +"\"]"));
							if(finalElement == null)
								continue;
					}
					if(Objects.equals(finalElement.getText(), label)){
						finalElement.click();
						flag.set(true);
					}
					
				}
				//SearchContext shadowHost = driver.findElements(By.xpath(getXPATH_OR("CLICK_ELEMENT").replace("textToReplace", label))).getShadowRoot();
			}
			if(!flag.get()) {
				$$(By.xpath(getXPATH_OR("CLICK_ELEMENT").replace("textToReplace", label))).forEach(ele ->
				{
					if (ele.isClickable()) {
						ele.click();
						flag.set(true);
					}
				});
			}
			assertTrue("Element No Found", flag.get());
		}
	}

	public WebElement getElement_ShadowDOM(SearchContext searchContext,By locator){

//		// Add code to handle different type of locators
//		if(driver.findElement(By.cssSelector(locatorValue)).isDisplayed()){
//			return driver.findElement(By.cssSelector(locatorValue));
//		}
		WebElement finalElement;
		try {
			finalElement = searchContext.findElement(locator);
			return finalElement;
		}catch (Exception e){
			finalElement=null;
		}
		if(searchContext.findElements(By.cssSelector("*")).size()>0){
			for(WebElement ele : searchContext.findElements(By.cssSelector("*"))){
				SearchContext shadowRoot;
				try {
					shadowRoot = ele.getShadowRoot();
				}catch(NoSuchShadowRootException e) {
					continue;
				}
				if(shadowRoot != null) {
					try {
						if (shadowRoot.findElement(locator) != null) {
							return shadowRoot.findElement(locator);
						}
					}catch(Exception e){
						continue;
						//return null;
					}
					finalElement = getElement_ShadowDOM(shadowRoot,locator);
				}
			}
		}else{
			return null;
		}
//		WebElement finalElement=null;
//		List<WebElement> elements = shadowHost.findElements(By.xpath("//*"));
//		for(WebElement ele : elements){
//			SearchContext shadowRoot = null;
//			try {
//				shadowRoot = ele.getShadowRoot();
//			}catch(NoSuchShadowRootException e) {
//				continue;
//			}
//			if(shadowRoot != null) {
//				if(shadowRoot.findElement(By.cssSelector(locatorValue)).isDisplayed()){
//					return shadowRoot.findElement(By.cssSelector(locatorValue));
//				}
////				for(WebElement ele_s:shadowRoot.findElements(By.xpath("//*")){
////
////				}
//			}
//		}
//
		return finalElement;
	}

    @Step("Click Event by JS")
    public void clickElement_JS(String elementLabel) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", $(By.xpath(getXPATH_OR("CLICK_ELEMENT").replace("textToReplace", elementLabel))).getElement());
    }

}
