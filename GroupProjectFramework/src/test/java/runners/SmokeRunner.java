package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Cucumber.class)
@CucumberOptions(
// plugin = {"pretty",
//        "html:target//html-reports//index.html",
//       "json:target//cucumber-reports//report.json",  //will generate json report but needs to be run from command-line
//         "junit:target//MyReports//report.xml" //default plugin in ide
// // "usage" - The output from the usage plugin is useful for quickly finding slow parts in your code but it is also a great way to get an overview of your Step Definitions.
// },
         features = {"classpath:features"},
         glue = {"step_defs"},
         tags = "@Smoke",
         //dryRun = false,//In case the value of dry run is set to true, Cucumber will verify individual steps in the Feature file and the implementation code of steps in Feature file within the Step Definition file.
        //monochrome = true, //If the monochrome option is set to false, then the console output is not as readable as it should be.default is false
        publish = true,
        plugin = {"json:target/reports/cucumber.json", "pretty", "html;target/reports"}
        //generating cucumber html report from cucumber.json file

)

public class SmokeRunner {

    @BeforeClass //runs on;y once before anything, change the version of the app under the test to the latest one
    public void oneTimeSetup(){
        System.out.println();
    }
    @AfterClass
    public void oneTimeTeatUp(){
        System.out.println();
    }


    //just for CME company - we have to ssh to qa server where the app is running
    //and run update version (redeploy with new version) command-> app redeploy -u AppName(zelle)(repo-gen)(any app name0

    //Apple has internal app to redeploy new version
    //Options:
    //env -qa, dev, qa2
    //which app - itunes, apay
    //which versions -2, 4.0, 5.6
}
