package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

//Singleton design pattern -
// Singleton means that the class set up in a way that only one object can be created in a time –
// if the driver is running no new driver can be created

public class Driver {

    private Driver() {
    }//only through static method of this class allow object creation of this class, only in this class

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            switch (ConfigReader.getProperty("browser").toLowerCase()) { //here everything need to be in lower cases as we're converting our configurations to lower cases
                default:
                    driver = ChromeWebDriver.loadChromeWebDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
                    driver.manage().window().maximize();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(9));
                    driver.manage().window().maximize();
                    break;
                case "saucelabs":
                    driver = SaucelabsDriver.loadSaucelabsDriver();
                    break;
            }
        }
        //if it is not null we simply return existing driver
        return driver;
    }

    public static void closeDriver() {
        try {
            if (driver != null) {
                driver.close();
                driver.quit();
                driver = null;
            }
        } catch (Exception e) { // when we close it manually
            e.printStackTrace();
        }
    }

}
