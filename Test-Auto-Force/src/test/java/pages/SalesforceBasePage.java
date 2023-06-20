package pages;

import java.time.Duration;

import net.thucydides.core.annotations.Step;
import utam.aura.pageobjects.Body;
import utam.core.framework.base.RootPageObject;
import utam.core.framework.consumer.UtamLoader;
import utam.core.framework.consumer.UtamLoaderConfig;
import utam.core.framework.consumer.UtamLoaderConfigImpl;
import utam.core.framework.consumer.UtamLoaderImpl;
import utam.global.pageobjects.AppLauncherMenu;
import utam.global.pageobjects.AppNav;
import utam.global.pageobjects.RecordActionWrapper;
import utam.helpers.pageobjects.Login;
import utam.navex.pageobjects.DesktopLayoutContainer;
import utam.records.pageobjects.LwcRecordLayout;

public class SalesforceBasePage extends BasePage {
	protected static Login loginPage;
	protected static UtamLoader loader;
	protected static DesktopLayoutContainer layoutContainer;
	protected static AppLauncherMenu appLauncherMenu;
	protected static RecordActionWrapper recordFormModal;
	protected static Body body;
	
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
		open();
		loginPage = from(Login.class);
	}

	@Step("Get Navigation Bar")
	public AppNav getNavBar() {
		layoutContainer = from(DesktopLayoutContainer.class);
		return layoutContainer.getAppNav();
	}

	@Step("Launch App")
	public void launchApp(String appName) {
		getNavBar().getAppLauncherHeader().getButton().click();
		appLauncherMenu = from(AppLauncherMenu.class);
		appLauncherMenu.getSearchBar().getLwcInput().setText(appName);
		appLauncherMenu.getItems().stream().findAny().ifPresent(appLauncherMenuItem -> appLauncherMenuItem.getRoot().click());
	}

	@Step("Load Record Form Modal")
	public LwcRecordLayout loadRecordFormModal(){
		recordFormModal = from(RecordActionWrapper.class);
		return recordFormModal.getRecordForm().getRecordLayout();
	}

	@Step("Load Aura Body Class")
	public Body loadBody() {
		body = from(Body.class);
		return body;
	}

	@Step("Navigate to Tab")
	public void navToTab(String objectName){
		try{
			if(getNavBar().getAppNavBar().getNavItem(objectName) ==null){
				getNavBar().getAppNavBar().getNavItem(objectName).clickAndWaitForUrl(objectName);
			}
		}catch(NullPointerException npe) {
			getNavBar().getAppNavBar().getShowMoreMenuButton().expand();
			getNavBar().getAppNavBar().getShowMoreMenuButton().getMenuItemByText(objectName).clickAndWaitForUrl(objectName);
		}
	}

}
