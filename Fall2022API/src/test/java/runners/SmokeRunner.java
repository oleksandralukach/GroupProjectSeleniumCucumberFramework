package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "step_defs",
        tags = "@Smoke",
        plugin = {"pretty",
                "html:target/default-cucumber-reports",
                "json:target/cucumber.json"//will generate json report but needs to be run from command-line
        }
)
public class SmokeRunner {

}
