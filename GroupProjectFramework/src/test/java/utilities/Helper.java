package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Helper {
    //this class will store reusable methods

    //actions click - mouse click
    public void mouseClick(WebElement element) {
        //I would first check if my element is clickable and visible, enable
     waitForElementToBeClickable(element);
        //then click with Actions class
        Actions actions = new Actions(Driver.getDriver());
        actions.click(element).perform();

    }

    //javascript click
    public void javascriptClickable(WebElement element) {
        //I would first check if my element is clickable and visible, enable
       waitForElementToBeClickable(element);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    //in case you will be demoing your framework
    public void highlightElement(WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        String style = "border: 4px dashed red";
        js.executeScript("argument[0].setAttribute('style',argument[1];", element, style);
        Thread.sleep(3000);
    }

    //    //in case you will be demoing your framework and first you locating webelement with js
//    public void highlightElement(String id) throws InterruptedException {
//        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
//        String style = "border: 4px dashed red";
//        js.executeScript("document.getElementById('" + id + "').setAttribute('style',argument[1];", style);
//        Thread.sleep(3000);
//    }
    public void waitForElementToBeDisplayed(WebElement element) {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(7)).until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(7)).until(ExpectedConditions.elementToBeClickable(element));
    }

    //I have a custom click method WeClick()
    public void weClick(WebElement element) {
        waitForElementToBeDisplayed(element);
        waitForElementToBeClickable(element);
        element.click();
    }

    public void weMoveToTheElement(WebElement element) {
        waitForElementToBeDisplayed(element);
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }
}
