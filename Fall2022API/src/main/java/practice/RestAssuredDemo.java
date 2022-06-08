package practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestAssuredDemo {

    public static void main(String[] args) {

        //before we can hit the endpoint we need to have
        // full url,
        // method type,
        // payload

        //we are all hitting our local instances of the app under test(FOOD_DELIVERY)
        String url = "http://localhost:8083/food/cache/add"; //food/cache/add - endpoint

        // test data - payload is the test data
        // domain knowledge
        String payloadBody = "{\n" +
                "    \"description\": \"Wine\",\n" +
                "    \"imageUrl\": \"https:foods.com\",\n" +
                "    \"price\": \"20.00\",\n" +
                "    \"name\": \"Merlot\",\n" +
                "    \"foodType\": \"Beverages\"\n" +
                "}";

        //rest Assured is java http client
        //when testing api you are really testing servers
        //you have to mimic the client via Postman or Automation

        Response response = RestAssured
                //after given() we provide headers and url
                .given()
                //full url
                .baseUri(url)
                //content type is what request payload format this api expects
                .contentType(ContentType.JSON)
                //accept is what response payload format this api returns
                .accept(ContentType.JSON)
                //when is a start if the action we choose the request type(GET, PUT, POST, DELETE)
                .body(payloadBody)
                .when()
                //request return response object. response object will have the info about status code and response payload once the api hits.
                .request("POST");

        System.out.println(response.getStatusCode()); //return status code: 200, 300, 400.....
        System.out.println(response.getBody().toString()); //return body

        //==============================================================================================================

        String listUrl = "https://localhost:8083/food/cache/list";

        Response foodListResponse = RestAssured
                .given()
                .baseUri(listUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .request("GET");

        System.out.println(foodListResponse.getStatusCode());
        System.out.println(foodListResponse.getBody().asPrettyString());

    }
}
