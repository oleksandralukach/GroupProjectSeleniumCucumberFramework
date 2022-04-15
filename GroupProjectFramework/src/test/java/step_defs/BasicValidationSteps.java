package step_defs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import utilities.Driver;
import utilities.EnvironmentManager;

import java.util.Locale;

public class BasicValidationSteps {

    WebDriver driver = Driver.getDriver();

    @When("^the user navigates to google$")
    public void the_user_navigates_to_google() {

        driver.navigate().to("https://www.google.com/");
        //driver.navigate().to(EnvironmentManager.baseUrl); later
    }

    @When("^the user searches for \"([^\"]*)\"$")
    public void the_user_searches_for(String searchCriteria) {
        driver.findElement(By.name("q")).sendKeys(searchCriteria + Keys.ENTER);
    }

    @Then("^verify \"([^\"]*)\" is in the title of the page$")
    public void verify_is_in_the_title_of_the_page(String searchCriteria) {
        Assert.assertTrue(driver.getTitle().toLowerCase().contains(searchCriteria));
    }
}
