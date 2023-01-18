package pages;

import java.time.Duration;

import net.thucydides.core.annotations.Step;
import utam.core.framework.base.RootPageObject;
import utam.core.framework.consumer.UtamLoader;
import utam.core.framework.consumer.UtamLoaderConfig;
import utam.core.framework.consumer.UtamLoaderConfigImpl;
import utam.core.framework.consumer.UtamLoaderImpl;
import utam.helpers.pageobjects.Login;

public class SalesforceBasePage extends BasePage {
	protected static Login loginPage;
	protected static UtamLoader loader;
	
	/**
	 * Helper method to load any root Page Object
	 * @param rootPageObjectType type of the page object to load
	 * @param <T> generic bound
	 * @return instance of the loaded page object
	 * 
	 */
	public final<T extends RootPageObject> T from (Class<T> rootPageObjectType) {
		if(loader == null) {
			throw new NullPointerException("Utam loader is not set, pls use setDriver method first");
		}
		return loader.load(rootPageObjectType);
	}
	
	@Step("Set UTAM driver instance")
	final UtamLoader setUtam() {
		UtamLoaderConfig config = new UtamLoaderConfigImpl();
		config.setExplicitTimeout(Duration.ofSeconds(60));
		config.setImplicitTimeout(Duration.ZERO);
		loader = new UtamLoaderImpl(config, driver);
		return loader;
	}
	
	@Step("Set Salesforce Login Page")
	public void setSFDCLoginPage() {
		openApp();
		loginPage = from(Login.class);
	}
	
	
	

}
