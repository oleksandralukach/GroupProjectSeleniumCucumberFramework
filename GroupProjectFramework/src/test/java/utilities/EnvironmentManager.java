package utilities;

import org.openqa.selenium.WebDriver;

public class EnvironmentManager {
    //our env will have different urls, user credentials, api endpoints

    public static String baseUrl;
    public static String username;
    public static String password;

    public static void setUpEnvironment() throws Exception {
        switch (ConfigReader.getProperty("env")) {
            case "dev":
                DEV();
                break;
            case "qa":
                QA();
                break;
            case "prod":
                PROD();
                break;
            default:
                throw new Exception("Invalid environment config.property.\n " +
                        "Please select from Qa,Dev,Prod ");
        }
    }

    private static void DEV() {
        //to set up dev variables
        baseUrl = "http://dev-automationpractice.com/index.php";
        username = "TestUserDev";
        password = "Test1234";
    }

    private static void QA() {
        baseUrl = "http://qa-automationpractice.com/index.php";
        username = "testUserQa";
        password = "test";
    }

    private static void PROD() {
        baseUrl = "http://qa-automationpractice.com/index.php";
        username = "fdf@gmail.com";
        password = "Test1234";

    }
}
