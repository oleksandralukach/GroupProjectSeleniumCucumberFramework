package step_defs;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.DBUtils;
import utilities.Driver;
import utilities.EnvironmentManager;

public class Hooks {

    @Before
    public void setUp() throws Exception {
        //to set up environment
        //we will have environment manager that will store all the env variables
        //and our before hook will call the environment manager to set up all
        //the variables correctly depending on the env we are running our tests against
        //EnvironmentManager.setUpEnvironment();
        EnvironmentManager.setUpEnvironment();

        //set up remote execution sauce lab
    }

    @After
    public void tearDown(Scenario scenario) {
        // I can attach a screenshot to a failing scenario
        try {
            if (scenario.isFailed()) {
                //we will take a screenshot
                final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
                //add screenshot to html report
                Thread.sleep(3000);
                scenario.attach(screenshot, "image/png", "My screenshot");
            }
        } catch (Exception e) {
            System.out.println("Error happened while taking screenshot");
            e.getMessage();
        }

        DBUtils.close(); //close connection to DB
        Driver.closeDriver();
    }
}
