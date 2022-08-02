package step_defs;

import beans.CreateAccountPayload;
import gherkin.deps.com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class BaseDigitalBankSteps {
    private static String authenticationUri = "http://3.129.60.236:8080/bank/api/v1/auth";

    protected static String authString;
    protected static Response response;
    public static Gson gson = new Gson();

    public static String the_admin_user_is_authenticated() {
        //
        Response authResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(authenticationUri)
                .queryParam("username", "admin@demo.io")
                .queryParam("password", "Demo123!")
                .when()
                .request("POST");

        authString = authResponse.getBody().jsonPath().getString("authToken");
        return authString;
    }

    public static void validateAccount(List<CreateAccountPayload> expectedResponsePayload, List<CreateAccountPayload> actualResponsePayload) {

        for (CreateAccountPayload expectedAccount : expectedResponsePayload) {
            for (CreateAccountPayload actualAccount : actualResponsePayload) {
                Assert.assertEquals("Name doesn't match", expectedAccount.getAccountName(), actualAccount.getAccountName());
                Assert.assertEquals(" doesn't match", expectedAccount.getOwnerTypeCode(), actualAccount.getOwnerTypeCode());
                Assert.assertEquals(" doesn't match", expectedAccount.getOpeningDeposit(), actualAccount.getOpeningDeposit());
                Assert.assertEquals(" doesn't match", expectedAccount.getAccountTypeCode(), actualAccount.getAccountTypeCode());
            }
        }
    }
}

