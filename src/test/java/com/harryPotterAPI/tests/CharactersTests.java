package com.harryPotterAPI.tests;

import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.Callable;

import com.harryPotterAPI.pojos.Character;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CharactersTests {

    final String apiKey = "$2a$10$zJSd2AyX3C9hNa35beYuGufyekC0D96EG.T1rFcuJd0ZQladAiQfa";

@BeforeAll
    public static void setUP(){
    baseURI = "https://www.potterapi.com/v1";
    //added for pojo below but not sure if needed
    config = config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON));

}

    /**
     * 1. Send a get request to /characters. Request includes : • Header Accept with value application/json
     * • Query param key with value invalid
     * 2. Verify status code 401, content type application/json; charset=utf-8
     * 3. Verify response status line include message Unauthorized
     * 4. Verify that response body says "error": "API Key Not Found"
     */
    @Test
    public void badKeyTest(){
       given().
               header("Accept", "application/json").
               queryParam("key", "invalid").
       when().
               get("/characters").prettyPeek().
       then().
               assertThat().statusCode(401).
               contentType("application/json; charset=utf-8").
               statusLine(containsString("Unauthorized")).
               body("error", is("API Key Not Found"));
    }

    /**
     * Send a get request to /characters. Request includes : • Header Accept with value application/json
     * 2. Verify status code 409, content type application/json; charset=utf-8
     * 3. Verify response status line include message Conflict
     * 4. Verify that response body says "error": "Must pass API key for request"
     */
    @Test
    public void noKeyTest(){
        given().
                header("Accept", "application/json").
        when().
                get("/characters").prettyPeek().
        then().assertThat().statusCode(409).
                contentType("application/json; charset=utf-8").
                statusLine(containsString("Conflict")).
                body("error", is("Must pass API key for request"));

    }

    /**
     * Verify number of characters
     * 1. Send a get request to /characters. Request includes : • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify response contains 194 characters
     */

    @Test
    public void numberOfCharactersTest(){
        Response response = given().
                                    header("Accept", "application/json").
                                    queryParam("key",apiKey ).
                            when().
                                    get("/characters");//prettyPeek();

        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8").
                body("size()",is(195));

        List<Character> all = response.jsonPath().getList("", Character.class);
        System.out.println(" all = " + all.size() ); //195
    }

    /**
     *  Send a get request to /characters. Request includes : • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify all characters in the response have id field which is not empty
     * 4. Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response
     * 5. Verify value of the house in all characters in the response is one of the following:
     * "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */

    @Test
    public void characterIdAndHouseTest(){
        Response response = given().
                                    header("Accept", "application/json").
                                    queryParam("key",apiKey ).
                            when().
                                    get("/characters").prettyPeek();//prettyPeek();
        response.
                then().assertThat().
                statusCode(200).contentType("application/json; charset=utf-8").
                body("_id",everyItem(not(isEmptyString()))).
                body("dumbledoresArmy",everyItem(is(instanceOf(Boolean.class)))).
                body("house",everyItem(is(oneOf("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff",null))));;


                //second way of assertion :

        response.body().jsonPath().getList("_id").forEach(value-> assertNotNull(value));
        response.body().jsonPath().getList("dumbledoresArmy").forEach(army-> assertTrue(army instanceof Boolean));

        //third way :
        //List<Map<String, Object> >characterAsMap = response.body().as(List.class);  // cover full all json  body to list.
        //for (Map <String, Object>  each : characterAsMap){
        //    String id= (String) each.get("_id");
        //    System.out.println("id = " + id);
        //    assertNotNull(id);
        //}
        //for (Map <String, Object>  each : characterAsMap) {
        //    Boolean dumbledoresArmy = (Boolean) each.get("dumbledoresArmy");
        //    assertTrue(dumbledoresArmy instanceof Boolean);
        //    System.out.println("dumbledoresArmy = " + dumbledoresArmy);
        //}

        List <String> expectedHouses = new ArrayList<>(Arrays.asList("Gryffindor", "Ravenclaw" , "Slytherin", "Hufflepuff"));
        System.out.println("expectedHouses = " + expectedHouses);
        List<String> allHouses = response.jsonPath().getList("house")  ;
        Set<String  > actualHouses = new HashSet<>(allHouses);
        System.out.println("actualHouses = " + actualHouses);

        assertTrue(actualHouses.containsAll(expectedHouses)) ;


    }




}
