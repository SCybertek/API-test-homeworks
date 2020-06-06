package com.UInamesAPI;



import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UINamesTests {

    // documentation = https://github.com/SCybertek/uinames

    @BeforeAll
    public static void setUP(){
        baseURI = "https://cybertek-ui-names.herokuapp.com/api/";

    }

    /**
     * 1 create a request without providing any parameters
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that name, surname, gender, region fields have value
     */
    @Test
    public void noParamsTest(){

        when().
                get(baseURI).prettyPeek().
        then().assertThat().statusCode(200).
                contentType("application/json; charset=utf-8").
                body("name", is (notNullValue()) ).
                body("surname", is (notNullValue())).
                body("gender", is (notNullValue())).
                body("region", is (notNullValue()) );

        //or

        Response response = when().
                get(baseURI).prettyPeek();
        response.then().assertThat().statusCode(200).
                contentType("application/json; charset=utf-8");

        RandomUser user = response.as(RandomUser.class);
        Assertions.assertNotNull(user.getName());
        Assertions.assertNotNull(user.getSurname());
        Assertions.assertNotNull(user.getGender());
        Assertions.assertNotNull(user.getRegion());
    }

    /**
     * Gender test
     * 1. Create a request by providing query parameter: gender, male or female
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that value of gender field is same from step 1
     */

    @Test
    public void genderTest(){
        given().queryParam("gender", "male").
        get(baseURI).prettyPeek().
        then().statusCode(200).contentType("application/json; charset=utf-8").
                body("gender", is("male"));
    }

    /**
     *
     2 params test
     1. Create a request by providing query parameters: a valid region and gender NOTE: Available region values are given in the documentation
     2. Verify status code 200, content type application/json; charset=utf-8
     3. Verify that value of gender field is same from step 1
     4. Verify that value of region field is same from step 1

     */
    @Test
    public void twoParamsTest(){
        given().queryParam("gender", "female").
                queryParam("region", "Russia").
                get(baseURI).prettyPeek().
                then().statusCode(200).contentType("application/json; charset=utf-8").
                body("gender", is("female")).
                body("region", is("Russia"));
    }

    /**
     * Invalid gender test
     * 1. Create a request by providing query parameter: invalid gender
     * 2. Verify status code 400 and status line contains Bad Request
     * 3. Verify that value of error field is Invalid gender
     */
    @Test
    public void invalidGenderTest(){
        given().queryParam("gender", "abc").
                get(baseURI).prettyPeek().
                then().statusCode(400).
                statusLine(containsString("Bad Request")).
                body("error", is("Invalid gender"));
    }
    /**
     * 1. Create a request by providing query parameter: invalid region
     * 2. Verify status code 400 and status line contains Bad Request
     * 3. Verify that value of error field is Region or language not found
     */
    @Test
    public void invalidRegionTest(){
        given().queryParam("region", "abc").
                get(baseURI).prettyPeek().
                then().statusCode(400).
                statusLine(containsString("Bad Request")).
                body("error", is("Region or language not found"));
    }
    /**
     * Create request by providing query parameters: a valid region and amount (must be bigger than 1)
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that all objects have different name+surname combination
     */

    @Test
    public void amountRegionsTest(){
        Response response = given().queryParam("region", "Germany").
                                    queryParam("amount", 350). //boundary value is failing .. but 10 is passing
                            get().prettyPeek();
        //no need to pass baseuri in get () because there is no special path for this request and base runs before each method
        response.then().statusCode(200).
                contentType("application/json; charset=utf-8");

        List<RandomUser> allUsers = response.jsonPath().getList("",RandomUser.class);

        Set<String> fullNames = new HashSet<>();
        for (RandomUser user : allUsers){
            String fullName = user.getName() + " " + user.getSurname();
            fullNames.add(fullName);
        }
        //2nd way
        // stream : pipeline => to create one list from another list..or set from list
        Set<String> fullNamesV2 = allUsers.stream().
                map(user -> user.getName() + " " + user.getSurname() ).
                collect(Collectors.toSet());

        response.then().
                assertThat().
                statusCode(200).
                and().
                header("Content-Type","application/json; charset=utf-8").
                and().
                body("size()",is(fullNames.size()));

    }

    /**
     *  Create a request by providing query parameters: a valid region, gender and amount (must be bigger than 1)
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that all objects the response have the same region and gender passed in step 1
     */
    //from Furkan : need to listen :
    //nextInt () method from Random class will create numbers between 0-499
    //That's why we add + 1
    //in this case my random number will be between 1-500

    int randomAmount = new Random().nextInt(500) + 1;
    List<String> genders = Arrays.asList("male","female");//We will pick random gender for each execution

    public String generateRandomGender() {
        Collections.shuffle(genders);
        return genders.get(0);
    }
    //getProperty("user.dir") will provide project path: C:\Users\1\Desktop\bugbusters\APIHomeworks
    File namesJson = new File(System.getProperty("user.dir") + File.separator + "names.json");
    JsonPath jsonPath = new JsonPath(namesJson);
    List<String>regions = jsonPath.getList("region");

    public String generateRandomRegion() {
        Collections.shuffle(regions);
        return regions.get(0);
    }

    @Test
    public void threeParamsTest(){
        Response response1 = given().queryParam("region", "United States").
                                    queryParam("gender", "female").
                                    queryParam( "amount",20).
                            when().get(baseURI).prettyPeek();
        response1.then().statusCode(200).contentType("application/json; charset=utf-8").and().body("size()",is (20)).
                body("gender",everyItem(is("female"))).
                body("region",everyItem(is("United States")));

//randomly :

        String randomGender = generateRandomGender();
        String randomRegion = generateRandomRegion();

        System.out.println("randomRegion = " + randomRegion);
        System.out.println("randomAmount = " + randomAmount);
        System.out.println("randomGender = " + randomGender);

        Response response =
                given().
                        queryParams("region",randomRegion).
                        queryParams("gender",randomGender).
                        queryParams("amount",randomAmount).
                        when().
                        get().prettyPeek();

        response.then().
                assertThat().
                statusCode(200).
                and().
                contentType("application/json; charset=utf-8").
                and().
                body("size()",is(randomAmount)).
                body("gender",everyItem(is(randomGender))).
                body("region",everyItem(is(randomRegion)));
    }

    /**
     * Amount count test
     * 1. Create a request by providing query parameter: amount (must be bigger than 1)
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that number of objects returned in the response is same as the amount passed in step 1
     */
    @Test
    @DisplayName("Amount count test")
    public void amountCount() {
        System.out.println("randomAmount = " + randomAmount);
        Response response =
                given().
                        queryParams("amount",randomAmount).
                        when().
                        get().prettyPeek();

        response.then().
                assertThat().
                statusCode(200).
                and().
                contentType("application/json; charset=utf-8").
                and().
                body("size()",is(randomAmount));
    }
}
