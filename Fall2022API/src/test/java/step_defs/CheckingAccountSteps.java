package step_defs;

import beans.CreateAccountPayload;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class CheckingAccountSteps extends BaseDigitalBankSteps{

    //we made it as a base class and extended it here
//    @Given("^the admin user is authenticated$") //post method to get auth token
//    public void the_admin_user_is_authenticated() {
//        String authenticationURI = "http://3.129.60.236:8080/bank/api/v1/auth";
//        authResponse = RestAssured
//                .given()
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .baseUri(authenticationURI)
//                .queryParam("username", "admin@demo.io")
//                .and()
//                .queryParam("password", "Demo123!")
//                .when()
//                .request("POST");
//        System.out.println(authResponse.getBody().asPrettyString());
//    }

    @When("^I send a following create account payload$")
    public void i_send_a_following_create_account_payload(List<CreateAccountPayload> createAccountPayload) {
        //JsonPath is a way to extract just the values from a JSON file. JsonPath ia an alternative to using xpath
        //JsonPath jsonPath = response.jsonPath(); //dont need it as created method inside BasicSteps class
       // System.out.println(jsonPath.getString("authToken"));

        String URI = "http://3.129.60.236:8080/bank/api/v1/user/75/account";
        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(URI)
                //.pathParam("id", "75")//pathParams - in uri - in {}
                .header("Authorization", "Bearer " + the_admin_user_is_authenticated())
                //.header("Authorization", "Bearer" + jsonPath.getString("authToken"))
                .body(createAccountPayload.get(0))
                .when()
                .request("POST");
    }

    @Then("^following should be the response payload$")
    public void following_should_be_the_response_payload(List<CreateAccountPayload> expecteddCreateAccountResponsePayload) {
        //assert each field
        //this how you get expected result (first part of Assert - reading from datatable from your cucumber feature)
       // Assert.assertEquals(createAccountResponsePayload.get(0).get("name"), response.jsonPath().getString("name"));
        //Assert.assertEquals(createAccountResponsePayload.get(0).get("account number"), response.jsonPath().getString("account number"));

        String jsonResponse = response.getBody().asPrettyString();
        CreateAccountPayload actualResponseBody = gson.fromJson(jsonResponse, CreateAccountPayload.class);
        Assert.assertEquals(expecteddCreateAccountResponsePayload.get(0).getAccountName(),actualResponseBody.getAccountName());
        Assert.assertEquals(expecteddCreateAccountResponsePayload.get(0).getOpeningDeposit(),actualResponseBody.getOpeningDeposit());
        Assert.assertEquals(expecteddCreateAccountResponsePayload.get(0).getOwnerTypeCode(),actualResponseBody.getOwnerTypeCode());
        Assert.assertEquals(expecteddCreateAccountResponsePayload.get(0).getAccountTypeCode(),actualResponseBody.getAccountTypeCode());

    }

    @Then("^the following should be the accountType payload$")
    public void the_following_should_be_the_accountType_payload(List<Map<String, String>> accountTypeResponsePayload) {
        Assert.assertEquals(accountTypeResponsePayload.get(0).get("code"), response.getBody().jsonPath().get("accountType.code"));
        Assert.assertEquals(accountTypeResponsePayload.get(0).get("category"), response.getBody().jsonPath().get("accountType.category"));
        Assert.assertEquals(accountTypeResponsePayload.get(0).get("name"), response.getBody().jsonPath().get("accountType.name"));
        Assert.assertEquals(accountTypeResponsePayload.get(0).get("minDeposit"), response.getBody().jsonPath().getString("accountType.minDeposit"));
        Assert.assertEquals(accountTypeResponsePayload.get(0).get("overdraftLimit"), response.getBody().jsonPath().getString("accountType.overdraftLimit"));
        Assert.assertEquals(accountTypeResponsePayload.get(0).get("overdraftFee"), response.getBody().jsonPath().getString("accountType.overdraftFee"));
    }

    @Then("^the following should be the ownershipTYpe payload$")
    public void the_following_should_be_the_ownershipTYpe_payload(List<Map<String, String>> accountOwnershipTypeResponsePayload) {
        Assert.assertEquals(accountOwnershipTypeResponsePayload.get(0).get("code"), response.getBody().jsonPath().getString("ownershipType.code"));
        Assert.assertEquals(accountOwnershipTypeResponsePayload.get(0).get("name"), response.getBody().jsonPath().getString("ownershipType.name"));
    }

    @Then("^the following should be accountStanding payload$")
    public void the_following_should_be_accountStanding_payload(List<Map<String, String>> accountStandingResponsePayload) {
        Assert.assertEquals(accountStandingResponsePayload.get(0).get("code"), response.getBody().jsonPath().getString("accountStanding.code"));
        Assert.assertEquals(accountStandingResponsePayload.get(0).get("name"), response.getBody().jsonPath().getString("accountStanding.name"));
    }
    //======================================================================================================================

    @When("^User looks up for the owner of '(\\d+)' account$")
    public void user_looks_up_for_the_owner_of_account(int userId) throws Throwable {
        String baseUri = "http://3.129.60.236:8080/bank/api/v1/account/" + userId + "/owner";

        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + the_admin_user_is_authenticated())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(baseUri)
                .when()
                .request("GET");
    }

    @Then("^the following should be the response payload of GetOwner endpoint$")
    public void the_following_should_be_the_response_payload_of_GetOwner_endpoint(List<Map<String, String>> expectedOwnerList) throws Throwable {
        Assert.assertEquals(expectedOwnerList.get(0).get("id"), response.getBody().jsonPath().getString("id"));
        Assert.assertEquals(expectedOwnerList.get(0).get("username"), response.getBody().jsonPath().getString("username"));
        Assert.assertEquals(expectedOwnerList.get(0).get("enabled"), response.getBody().jsonPath().getString("enabled"));
        Assert.assertEquals(expectedOwnerList.get(0).get("accountNonExpired"), response.getBody().jsonPath().getString("accountNonExpired"));
        Assert.assertEquals(expectedOwnerList.get(0).get("accountNonLocked"), response.getBody().jsonPath().getString("accountNonLocked"));
        Assert.assertEquals(expectedOwnerList.get(0).get("credentialsNonExpired"), response.getBody().jsonPath().getString("credentialsNonExpired"));

    }

    @Then("^the following should be userProfile response$")
    public void the_following_should_be_userProfile_response(List<Map<String, String>> expectedUserProfileList) throws Throwable {
        Assert.assertEquals(expectedUserProfileList.get(0).get("id"), response.getBody().jsonPath().getString("userProfile.id"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("firstName"), response.getBody().jsonPath().getString("userProfile.firstName"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("lastName"), response.getBody().jsonPath().getString("userProfile.lastName"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("title"), response.getBody().jsonPath().getString("userProfile.title"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("gender"), response.getBody().jsonPath().getString("userProfile.gender"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("ssn"), response.getBody().jsonPath().getString("userProfile.ssn"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("dob"), response.getBody().jsonPath().getString("userProfile.dob"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("dom"), response.getBody().jsonPath().getString("userProfile.dom"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("emailAddress"), response.getBody().jsonPath().getString("userProfile.emailAddress"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("homePhone"), response.getBody().jsonPath().getString("userProfile.homePhone"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("mobilePhone"), response.getBody().jsonPath().getString("userProfile.mobilePhone"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("workPhone"), response.getBody().jsonPath().getString("userProfile.workPhone"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("address"), response.getBody().jsonPath().getString("userProfile.address"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("locality"), response.getBody().jsonPath().getString("userProfile.locality"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("region"), response.getBody().jsonPath().getString("userProfile.region"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("postalCode"), response.getBody().jsonPath().getString("userProfile.postalCode"));
        Assert.assertEquals(expectedUserProfileList.get(0).get("country"), response.getBody().jsonPath().getString("userProfile.country"));
    }

    @Then("^response status code should be '(\\d+)'$")
    public void response_status_code_should_be(int expectedStatusCode) {
        Assert.assertEquals(response.getBody().asPrettyString(), expectedStatusCode, response.getStatusCode());
    }

    @When("^I send a following update account payload$")
    public void iSendAValidUpdateAccountPayload(List<CreateAccountPayload> createAccountPayload) {
        String URI = "http://3.129.60.236:8080/bank/api/v1/user/75/account";
        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(URI)
                //.pathParam("id", "75")//pathParams - in uri - in {}
                .header("Authorization", "Bearer " + the_admin_user_is_authenticated())
                //.header("Authorization", "Bearer" + jsonPath.getString("authToken"))
                .body(createAccountPayload.get(0))
                .when()
                .request("PUT");
    }
}
