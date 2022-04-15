package utilities;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
@RunWith(Parameterized.class)
public class SauceLabsParallel {

    private static final String USERNAME = ConfigReader.getProperty("saucelabsUsername");
    private static final String ACCESS_KEY = ConfigReader.getProperty("saucelabsAccesskey");
    private static final String URL = "https://oauth-" + USERNAME + "-b0bcc:" + ACCESS_KEY + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";

    private String browser;
    private String version;
    private String os;

    public SauceLabsParallel(String browser,String version, String os){
        this.browser = browser;
        this.version = version;
        this.os = os;
    }
    //when we are trying to run a test in a parallel we need to make sure
    //we set up our driver to be thread local(do not interfere with each other)
    //Thread - line of execution / 1 test = 1 thread

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    //when we are running in parallel we need to keep track of sessions
    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    private WebDriver createDriver(String browser, String version, String os) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("browserVersion", version);
        capabilities.setCapability("platformName", os);

        driver.set(new RemoteWebDriver(new URL(URL), capabilities));  //set() to add new object to threadLocal and get() - to get an object from ThreadLocal
        // I need to get a session id and store it in sessionId collection
        String id = ((RemoteWebDriver)driver.get()).getSessionId().toString();
        sessionId.set(id);

        return  driver.get();
    }

    //now we need to store all the configurations for the browser that will be using
    //data provider for previous method

    @Parameterized.Parameters
    public static Collection sauceLabDataProvider(){
        return Arrays.asList(new Object[][]{
                {"chrome", "latest","windows 10"},
                {"firefox", "latest", "macOs 10.14"},
                //{"edge", "92", "windows 10"}
        });
    }

//    @Test
//    public void testPromp() throws MalformedURLException {
//
//        WebDriver driver = createDriver(browser,version, os);
//        System.out.println(Thread.currentThread().getId());
//        driver.navigate().to("https://demoqa.com/alerts");
//
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("window.scrollBy(0,500)");
//        driver.findElement(By.id("promtButton")).click();
//        Alert alert = driver.switchTo().alert();
//
//        //since its a promt alert I need to type here:
//        String name = "Sofia";
//        alert.sendKeys(name);
//        alert.accept();
//        WebElement result = driver.findElement(By.id("promptResult"));
//        Assert.assertTrue(result.getText().contains(name));
//    }
//    @Test
//    public void testConfirmationAlert() throws MalformedURLException {
//        System.out.println(Thread.currentThread().getId());
//        WebDriver driver = createDriver(browser,version, os);
//        driver.navigate().to("https://demoqa.com/alerts");
//        driver.findElement(By.id("confirmButton")).click();
//
//        Alert alert = driver.switchTo().alert();
//        alert.dismiss();
//
//        WebElement resultMessage = driver.findElement(By.id("confirmResult"));
//        Assert.assertTrue(resultMessage.getText().endsWith("Cancel"));
//    }
//    @After
//    public void tearDown(){
//        driver.get().close();
//    }
}
