package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.User;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;
import utils.TestNGListener;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class MyStepdefs {
   static User user;
    int userID;
    ObjectMapper objectMapper = new ObjectMapper();
    Response putResponse,response;
    static User putBody;
   static User postresponseUser,responseUser;
    @Given("creating a user")
    public void User() throws JsonProcessingException {
        //create request
        JSONObject testData = (JSONObject) TestNGListener.data.get("postRequest");
        user = new User((String) testData.get("name"), (String) testData.get("gender"),
                "tenali" + System.currentTimeMillis() + "@15ce.com", (String) testData.get("status"));

        response = given()
                .body(user)
                .when().post(Endpoints.userEndpoint)
                .then().body(matchesJsonSchemaInClasspath("user_schema.json"))
                .statusCode(201).extract().response();

        responseUser = objectMapper.readValue(response.asString(), User.class);
        Assert.assertEquals(user.getEmail(), responseUser.getEmail());
        userID=responseUser.getId();
    }

    @When("updating the user")
    public void updatingTheUser() {
        //put request
        JSONObject testData1 = (JSONObject) TestNGListener.data.get("putRequest");
       putBody = new User((String) testData1.get("name"), (String) testData1.get("gender"),
                "tenali" + System.currentTimeMillis() + "@15ce.com", (String) testData1.get("status"));

        putResponse = given()
                .body(putBody)
                .when().put(Endpoints.userEndpoint + "/" + userID)
                .then().statusCode(200).extract().response();

    }

    @Then("the user is updated")
    public void theUserIsUpdated() throws JsonProcessingException {
        //assertion
        postresponseUser = objectMapper.readValue(putResponse.asString(), User.class);
        Assert.assertEquals(putBody.getGender(), postresponseUser.getGender());
    }

    @Given("creating the user")
    public void creatingTheUser() {
        JSONObject testData = (JSONObject) TestNGListener.data.get("postRequest");
        user = new User((String) testData.get("name"), (String) testData.get("gender"),
                "tenali" + System.currentTimeMillis() + "@15ce.com", (String) testData.get("status"));

        response = given()
                .body(user)
                .when().post(Endpoints.userEndpoint)
                .then().body(matchesJsonSchemaInClasspath("user_schema.json"))
                .statusCode(201).extract().response();
    }

    @Then("the user is created")
    public void theUserIsCreated() throws JsonProcessingException {

        responseUser = objectMapper.readValue(response.asString(), User.class);
        Assert.assertEquals(user.getEmail(), responseUser.getEmail());
    }

    @When("^updating the user with invalid input \"(.*)\" and \"(.*)\"$")
    public void updatingTheUserWithInvalidInputAnd(String arg0, String arg1) {
        System.out.println(arg0);
        System.out.println(arg1);
    }

    @Then("the user not to be updated")
    public void theUserNotToBeUpdated() {
    }
}
