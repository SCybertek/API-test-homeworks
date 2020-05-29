package com.harryPotterAPI.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SortingHat {

    @BeforeAll
    public static void setUP(){
        baseURI = "https://www.potterapi.com/v1/";

    }

    //no authentication needed for this request

    /**
     * Verify sorting hat
     * 1. Send a get request to /sortingHat. Request includes :
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that response body contains one of the following houses:
     * "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */

    @Test
    public void sortingHatTest(){

        List<String> allHats = new ArrayList<>(Arrays.asList("\"Gryffindor\"", "\"Ravenclaw\"" , "\"Slytherin\"", "\"Hufflepuff\""));
        //{"Gryffindor","Ravenclaw", "Slytherin", "Hufflepuff"};
        Response response = given().
                                    get("/sortingHat").prettyPeek();

        response.then().statusCode(200).contentType("application/json; charset=utf-8");
       // String hat = response.prettyPrint();

        assertTrue(allHats.contains(response.body().asString()));

        //https://stackoverflow.com/questions/2099900/difference-between-tostring-and-as-string-in-c-sharp


    }

}
