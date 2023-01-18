package starter;


import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {
        		"pretty",
        		"json:target/serenity-reports/SerenityTestReport.json",
        		"html:target/serenity-reports/SerenityHtmlReport.htm"
        },
        features = "src/test/resources/features",
        monochrome = true,
        tags = "@poc",
        glue = {"stepdefinitions"}
)

public class CucumberTestSuiteStarter {

}
