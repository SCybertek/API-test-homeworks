package com.UInamesAPI;



import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
}
