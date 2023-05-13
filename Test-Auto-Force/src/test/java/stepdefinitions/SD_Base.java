package stepdefinitions;

import org.junit.Assert;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BasePage;

public class SD_Base {
	BasePage basePage;
	
	@Given("I open {string} page")
	public void navUrl(String url) {
		basePage.openApp(url);
	}
	
	@Given("I open {string} application page")
	public void openEnvPage(String appName) {
		basePage.openApp(appName);
	}
	
	@Given("I open application page")
	public void openDefaultPage() {
		basePage.open();
		basePage.setDriver();
		basePage.waitForPageToLoad(15);
	}
	
	@When("I click the link {string} on the {string} page")
	public void clickLink(String linkText,String pageName) {
		basePage.clickWebElement(linkText, pageName);
	}
	
	@Then("The application page with title {string} should be displayed")
	public void verifyTitle(String title) {
		Assert.assertEquals("Application page is displayed", title.trim() , basePage.getTitle().trim());
	}
	
	@AfterAll
	public static void closeBrowser() {
		//basePage.closeAndExitBrowser();
	}
}
