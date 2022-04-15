package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features"},
        glue = {"step_defs"},
        tags = "@Regression",
        //dryRun = false,//generate step defs for all scenarios, it doesn't have an idea if any scenario
        // has it or not. So it skips all the steps
        // In case the value of dry run is set to true, Cucumber will generate the implementation code of steps in Feature file within the Step Definition file.
        //monochrome = true, //If the monochrome option is set to false, then the console output is not as readable as it should be.default is false
        publish = true,
        plugin = {"json:target/reports/cucumber.json", "pretty", "html:target/reports"}
        //generating cucumber html report from cucumber.json file
)
public class RegressionRunner {
}
