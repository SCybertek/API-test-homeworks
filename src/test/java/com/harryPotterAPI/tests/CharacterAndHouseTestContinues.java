package com.harryPotterAPI.tests;

import com.google.gson.reflect.TypeToken;
import com.harryPotterAPI.pojos.Character;

import com.harryPotterAPI.pojos.House;
import com.harryPotterAPI.pojos.Members;
import io.restassured.response.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

//NOTE : you cannot use $ in rest assured when there is not collection
//aslo add equals method and hascode in my pojo

public class CharacterAndHouseTestContinues {


        private static final String apiKey = "$2a$10$zJSd2AyX3C9hNa35beYuGufyekC0D96EG.T1rFcuJd0ZQladAiQfa";

        static {

        baseURI = "https://www.potterapi.com/v1";

    }

    /**
     * Verify all character information
     * 1. Send a get request to /characters. Request includes : • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Select name of any random character
     * 4. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json • Query param key with value {{apiKey}}
     * • Query param name with value from step 3
     * 5. Verify that response contains the same character information from step 3. Compare all fields.
     */
    @Test
    @DisplayName("Verify all character information")
    public void characterInfoTest(){
        Response response = given().
                                    header("Accept", " application/json").
                                    queryParam("key", apiKey ).
                when().get("/characters");//prettyPeek();

        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");

        List<Character> allCharacters = response.jsonPath().getList("", Character.class);

        System.out.println("allCharacters = " + allCharacters);

        Random random = new Random();
        int randomName = random.nextInt(allCharacters.size());

        String ourCharacter = allCharacters.get(randomName).getName();
        System.out.println("ourChar = " + ourCharacter); //Salazar Slytherin
        Character expectedCharacterInfo = allCharacters.get(randomName); //Character{id='5a12327c0f5ae10021650d94', name='Salazar Slytherin', role='Founder, Hogwarts', house='Slytherin', school='Hogwarts School of Witchcraft and Wizardry', alias='null', wand='null', boggart='null', patronus='null', v=0, ministryOfMagic=false, orderOfThePhoenix=false, dumbledoresArmy=false, deathEater=false, bloodStatus='pure-blood', species='human'}

        Response response1 = given().header("Accept", "application/json").
                                    queryParam("key",apiKey).
                                    queryParam("name", ourCharacter).
                            when().get("/characters").prettyPeek();
        List<Character> actualCharacter = response1.jsonPath().getList("", Character.class);

        assertEquals(actualCharacter.get(0).toString(), expectedCharacterInfo.toString());

    }



    /**
     * Verify name search
     * 1. Send a get request to /characters. Request includes : • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * • Query param name with value Harry Potter
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify name Harry Potter
     * 4. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json • Query param key with value {{apiKey}}
     * • Query param name with value Marry Potter
     * 5. Verify status code 200, content type application/json; charset=utf-8
     * 6. Verify response body is empty
     */
    @Test
    public void nameSearchTest(){
       Response response = given().header("Accept", "application/json").
                queryParam("key",apiKey).
                queryParam("name", "Harry Potter").
                when().get("/characters");//prettyPeek();

        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");

//        List<Character> harryPotter = response.body().as(List.class);
//        System.out.println("harryPotter = " + harryPotter);
        //System.out.println("harryPotter = " + harryPotter);//; as(Map.class) ; // [ { ...
        //Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 2 path $
        //System.out.println("harryPotter = " + harryPotter);
        //assertEquals(harryPotter.get(0).getName()

        List<Map<String, Object>> characterAsMap = response.body().as(List.class);
        System.out.println("characterAsMap = " + characterAsMap);
        List<String> expected = response.jsonPath().get("name");
        assertEquals(expected.get(0), "Harry Potter");
        
        Response response1 = given().header("Accept", "application/json").
                queryParam("key",apiKey).
                queryParam("name", "Marry Potter").
                when().get("/characters").prettyPeek();

        response1.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");

        assertTrue(response1.body().as(List.class).isEmpty());

    }
    //second way of doing from Furkan :
    @Test
    @DisplayName("Verify name search")
    public void nameSearch() {

        Response response =
                given().
                        header("Accept","application/json").
                        queryParams("key",apiKey).
                        queryParams("name", "Harry Potter").
                        when().
                        get("/characters").prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                body("[0].name",is("Harry Potter"));

        //with pojo
        Character hpCharacter = response.jsonPath().getObject("[0]",Character.class);
        assertEquals("Harry Potter",hpCharacter.getName());

        Response response2 =
                given().
                        header("Accept","application/json").
                        queryParams("key",apiKey).
                        queryParams("name", "Marry Potter").
                        when().
                        get("/characters").prettyPeek();

        response2.then().assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                body("[0]",isEmptyOrNullString()).
                body("size()",is(0));


    }

    /**
     * Verify house members
     * 1. Send a get request to /houses. Request includes : • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Capture the id of the Gryffindor house
     * 4. Capture the ids of the all members of the Gryffindor house
     * 5. Send a get request to /houses/:id. Request includes :
     * • Header Accept with value application/json • Query param key with value {{apiKey}}
     * • Path param id with value from step 3
     * 6. Verify that response contains the same member ids as the step 4
     */

    @Test
    public void houseMembersTest(){
        Response response = given().header("Accept", "application/json").
                                    queryParam("key",apiKey).
                            when().get("/houses").prettyPeek();//prettyPeek();

        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");
        String id = response.body().jsonPath().getString("find{it.name == 'Gryffindor'}._id");
        System.out.println("id = " + id);

        List<String> allIDs = response.body().jsonPath().getList("find{it.name == 'Gryffindor'}.members");
        System.out.println("allIDs = " + allIDs);
        System.out.println("allIDs.size() = " + allIDs.size());

        Response response1 = given().header("Accept", "application/json").
                                    queryParam("key",apiKey).
                             when().get("/houses/{:id}",id).prettyPeek();

        List<String>memberIDsFrom2ndResponse = response1.jsonPath().getList("[0].members._id");
        System.out.println("memberIDsFrom2ndResponse = " + memberIDsFrom2ndResponse);

        assertEquals(allIDs,memberIDsFrom2ndResponse);

//my way longer :
//       List <List<Map<String, String>>> allMembers = response1.jsonPath().getList("members");
//        System.out.println("allMembers = " + allMembers);
//        System.out.println("allMembers.get(0).get(0) = " + allMembers.get(0).get(0)); //{_id=5a0fa648ae5bc100213c2332, name=Katie Bell}
//
//        allMembers.get(0).forEach(map -> assertTrue(allIDs.contains(map.get("_id"))));

        //POJO is not working!!! why?? our second response give members as Object thats why it did not work ..incorporate our Members object in here
         //List<House> house = response1.body().as( List.class);
        List<House> house = response1.jsonPath().getList("");
        House house1 = response1.jsonPath().getObject("[0]", House.class);
        System.out.println("house1 = " + house1);
        System.out.println("house1.getId() = " + house1.getId());

        // List<House> house = response.jsonPath().getObject("[0]",Character.class);
        System.out.println("house = " + house);
        System.out.println("house.get(0) = " + house.get(0) );//getMembers() ); //.getId());
        System.out.println("house.size() = " + house.size());
       // System.out.println("house.get(0).getName() = " + house.get(0).getMembers());

        //java.lang.ClassCastException: class com.google.gson.internal.LinkedTreeMap cannot be cast to class com.harryPotterAPI.pojos.House (com.google.gson.internal.LinkedTreeMap and com.harryPotterAPI.pojos.House are in unnamed module of loader 'app')
//google solution: ???
//Type typeMyType = new TypeToken<ArrayList<House>>(){}.getType();
//        System.out.println("typeMyType = " + typeMyType);
//
//ArrayList<House> myObject = response1.fromJson(" ", House.class);



    }

    /**
     * Verify house members again
     * 1. Send a get request to /houses/:id. Request includes : • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * • Path param id with value 5a05e2b252f721a3cf2ea33f
     * 2. Capture the ids of all members
     * 3. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json • Query param key with value {{apiKey}}
     * • Query param house with value Gryffindor
     * 4. Verify that response contains the same member ids from step 2
     */
    @Test
    public void houseMembersTest2(){
        Response response = given().header("Accept", "application/json").
                queryParam("key",apiKey).
                when().get("/houses/{id}", "5a05e2b252f721a3cf2ea33f").prettyPeek();
        
//        List<String> allId = response.jsonPath().get("members.findAll{it._id}._id");
//        System.out.println("allId = " + allId);

        Response response1 = given().
                header("Accept", "application/json").
                queryParam("key",apiKey).
                queryParam("house","Gryffindor").
                when().get("/characters").prettyPeek();

        List<Members>memberList = response.jsonPath().getList("[0].members",Members.class);
        System.out.println("memberList = " + memberList);

        //with pojo
        List<Character>hpCharactersList = response1.jsonPath().getList("",Character.class);

        System.out.println("hpCharactersList = " + hpCharactersList.size());
        System.out.println("memberList = " + memberList.size());
        assertEquals(memberList.size(),hpCharactersList.size());
        //sizes are different though
//5a1223ed0f5ae10021650d70 member id is forgotten to put in list

    }
    /**
     * Verify house with most members
     * 1. Send a get request to /houses. Request includes : • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that Gryffindor house has the most members
     */

    @Test
    public void houseWithMostMembersTest(){
        Response response = given().
                header("Accept", "application/json").
                queryParam("key",apiKey).when().get("/houses").prettyPeek();
        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");
        List<List<String>> gryffindorHouse = response.jsonPath().get("findAll{it.name == 'Gryffindor'}.members"); //[ [
        System.out.println("gryffindorHouse.get(0).size() = " + gryffindorHouse.get(0).size());
        List<List<String>> hufflepuffHouse = response.jsonPath().get("findAll{it.name == 'Hufflepuff'}.members");
        List<List<String>> lytherinHouse = response.jsonPath().get("findAll{it.name == 'Slytherin'}.members");
        List<List<String>> ravenclawHouse = response.jsonPath().get("findAll{it.name == 'Ravenclaw'}.members");

       assertTrue(gryffindorHouse.get(0).size() > hufflepuffHouse.get(0).size() && gryffindorHouse.get(0).size() > lytherinHouse.get(0).size() && gryffindorHouse.get(0).size() > ravenclawHouse.get(0).size());


       //second way of assertion :  response.then().
        //                                      assertThat().
        //                                      body("max{it.members.size()}.name",is("Gryffindor"));


        //with POJO-- changed private List<String> members; in House pojo
        List<House>houseList = response.jsonPath().getList("",House.class);
        String houseHasTheMostMembers = "";
        int max = 0;
        for (House hpHouse:houseList) {
            int sizeOFMembers = hpHouse.getMembers().size();
            if(sizeOFMembers > max){
                max = sizeOFMembers;
                houseHasTheMostMembers = hpHouse.getName();
            }
        }

        assertEquals("Gryffindor", houseHasTheMostMembers);

    }
}
