package utilities;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.*;
import utilities.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

public class SaucelabsDriver {

    //this will be the class to create the driver that will be running on Sauce Labs
    //we need to connect our code to our saucelabs account
    // we need username,access key, url

    private static final String USERNAME = ConfigReader.getProperty("saucelabsUsername");
    private static final String ACCESS_KEY = ConfigReader.getProperty("saucelabsAccesskey");
    private static final String URL = "https://oauth-" + USERNAME + "-b0bcc:" + ACCESS_KEY + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";

    public static WebDriver loadSaucelabsDriver() {
        //we need to provide the info on the configuration to use to run out tests
        //DesiredCapabilities
        //here we will set OS, browser, browser version

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","macOS 12");
        //capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.IOS);
        capabilities.setCapability("browserName", "Safari");
        //capabilities.setCapability(CapabilityType.BROWSER_NAME, Browser.SAFARI);
        capabilities.setCapability("browserVersion", "latest");

        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(URL), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
