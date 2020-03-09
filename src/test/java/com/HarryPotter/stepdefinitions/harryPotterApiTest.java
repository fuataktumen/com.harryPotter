package com.HarryPotter.stepdefinitions;

import com.HarryPotter.utilities.ConfigurationReader;
import com.google.gson.Gson;
import gherkin.ast.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import  static io.restassured.RestAssured.*;

public class harryPotterApiTest {
        String key ="$2a$10$eGAQ9xfhpFY3JdWfdAsYbOdNg4SIkvQbhB7cStp2./ghuZbs/988O";
    String uri =ConfigurationReader.get("potter.uri");
        Response response;
    Random rn = new Random();
    int name = rn.nextInt(195)+1;
    String randomName;
    @Given("Send a get request to {string}")
    public void send_a_get_request_to(String string) {
           response=given().contentType(ContentType.JSON)
                   .when().get(uri+string);

    }

    @When("Verify status code {int}, content type {string}")
    public void verify_status_code_content_type(int statusCode, String contentType) {

        assertEquals(statusCode,response.statusCode());
        assertEquals(contentType,response.contentType());
    }

    @Then("that response body contains one of the following houses:")
    public void that_response_body_contains_one_of_the_following_houses(List<String> namesHose) {

        String body = response.body().prettyPrint().substring(1,response.body().prettyPrint().length()-1);
        boolean flag=false;
        for (int i = 0; i <namesHose.size() ; i++) {
            if (namesHose.get(i).equals(body)){

                flag=true;
            }
        }
assertEquals(true,flag);
//---------------------------------------------------------------------------------------------
    }

    @Given("Header Accept with value application json")
    public void header_Accept_with_value_application_json() {

        given().accept(ContentType.JSON);

    }

    @Given("Query param key with value invalid")
    public void query_param_key_with_value_invalid() {

    String url = ConfigurationReader.get("potter.uri")+"characters";

     response= given().queryParam("key","invalid")
              .when().get(url);



    }



    @Then("Verify response status line include message {string}")
    public void verify_response_status_line_include_message(String string) {

        assertTrue(response.statusLine().contains(string));
    }

    @Then("Verify that response body says {string} {string}")
    public void verify_that_response_body_says(String error, String errorMessage) {

        assertTrue(response.body().prettyPrint().contains(error));
        assertEquals(errorMessage,response.body().path("error"));

    }
//--------------------------------------------------------------------------------------

    @Given("Query param key with value apiKey")
    public void query_param_key_with_value_apiKey() {
        response=given().accept(ContentType.JSON).and()
                .queryParam("key",key)
                .when().get(uri+"characters");

    }


    @Then("Verify response contains {int} characters")
    public void verify_response_contains_characters(int numberOfChar) {
        List<Map<String ,Object>> numberOf= response.body().path("_id");

        System.out.println(numberOf.size());

        assertEquals(numberOfChar,numberOf.size());

    }

    @Then("Verify all characters in the response have id field which is not empty")
    public void verify_all_characters_in_the_response_have_id_field_which_is_not_empty() {

        List<Map<String,Object>> listOfİd = response.body().as(List.class);
        for (int i = 0; i <listOfİd.size() ; i++) {
            assertTrue(listOfİd.get(i).containsKey("_id"));
            assertFalse(listOfİd.get(i).isEmpty());
        }


    }

    @Then("Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response")
    public void verify_that_value_type_of_the_field_dumbledoresArmy_is_a_boolean_in_all_characters_in_the_response() {
        boolean flag = false;
        List<Map<String,Object>> listOfİd = response.body().as(List.class);
        for (int i = 0; i <listOfİd.size() ; i++) {
            assertTrue(listOfİd.get(i).get("dumbledoresArmy").equals(true)||listOfİd.get(i)
            .get("dumbledoresArmy").equals(false));
        }
    }

    @Then("Verify value of the house in all characters in the response is one of the following:")
    public void verify_value_of_the_house_in_all_characters_in_the_response_is_one_of_the_following(List<String> HOUSElİST) {

        List<Map<String, Object>> characters = response.body().as(List.class);
        System.out.println("characters.size() = " + characters.size());

        for (Map<String, Object> character : characters) {
            if(character.containsKey("house")){
                assertTrue("house",HOUSElİST.contains(character.get("house")));
            }

        }


    }
//------------------------------------------------------------------------------------------------


    @When("Select name of any random character")
    public void select_name_of_any_random_character() {


        List<Map<String ,Object>> names = response.body().as(List.class);
        randomName = response.body().path("name["+name+"]");
        System.out.println(randomName);

    }

    @Then("Query param name with value from step {int}")
    public void query_param_name_with_value_from_step(int int1) {
        List<Map<String ,Object>> names = response.body().as(List.class);

        response = given().
                and().queryParam("key", key)
                .and().queryParam("name",randomName)
                .when().get(uri + "characters");






    }

    @Then("Verify that response contains the same character information from step {int}. Compare all fields.")
    public void verify_that_response_contains_the_same_character_information_from_step_Compare_all_fields(int int1) {
        List<Map<String ,Object>> names = response.body().as(List.class);
        response = given().
                and().queryParam("key", key)
                .and().queryParam("name",randomName)
                .when().get(uri + "characters");
        System.out.println(randomName);

        assertTrue(response.body().asString().contains(randomName));


    }
//----------------------------------------------------------------------------------
@Given("Query param name with value Harry Potter")
public void query_param_name_with_value_Harry_Potter() {


    response = given().
            and().queryParam("key", key)
            .and().queryParam("name","Harry Potter")
            .when().get(uri + "characters");
}

    @When("Verify name Harry Potter")
    public void verify_name_Harry_Potter() {


        String harry = response.body().path("name[0]");
        assertEquals("Harry Potter",harry);

    }

    @When("Query param name with value Marry Potter")
    public void query_param_name_with_value_Marry_Potter() {
        response = given().
                and().queryParam("key", key)
                .and().queryParam("name","Marry Potter")
                .when().get(uri + "characters");
    }

    @Then("Verify response body is empty")
    public void verify_response_body_is_empty() {

        String responseBody = response.body().asString();

        List<String> response2Body = response.body().as(List.class);
        assertEquals(response2Body.size(),0);

    }
//---------------------------------------------------------------------------------------------------------------------
    @When("Capture the id of the {string} house")
    public void capture_the_id_of_the_house(String house) {

response=given().accept(ContentType.JSON).
        and().pathParam("key",key)
        .when().get(uri+"houses");




    }

    @When("Capture the ids of the all members of the {string} house")
    public void capture_the_ids_of_the_all_members_of_the_house(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("Send a get request to \\/houses\\/:id")
    public void send_a_get_request_to_houses_id() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("Path param id with value from step {int}")
    public void path_param_id_with_value_from_step(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("Verify that response contains the same member ids as the step {int}")
    public void verify_that_response_contains_the_same_member_ids_as_the_step(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }




    }

