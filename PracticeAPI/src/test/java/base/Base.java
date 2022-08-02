package base;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;

import java.util.logging.Logger;

public class Base {
    public static RequestSpecification httpRequest;
    public static Response response;
    public Logger logger;
    public Gson gson = new Gson();

//    @BeforeClass
//    public void setUp() {
//        logger = Logger.getLogger("");
//        PropertyConfigurator.configure("Log4j.properties"); //added logger
//        //logger.setLevel(Level.DEBUG);
//
//    }
}
