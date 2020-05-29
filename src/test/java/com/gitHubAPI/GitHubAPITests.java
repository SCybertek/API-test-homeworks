package com.gitHubAPI;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.*;
import java.util.List;

public class GitHubAPITests {
    @BeforeAll
    public static void setup() {
        baseURI = "https://api.github.com";

    }
        /**
         *
         Verify organization information
         1. Send a get request to /orgs/:org Request includes : • Path param org with value cucumber
         2. Verify status code 200, content type application/json; charset=utf-8
         3. Verify value of the login field is cucumber
         4. Verify value of the name field is cucumber
         5. Verify value of the id field is 320565
         */


        @Test
        public void organizationInfoTest(){
            given().
                    get("/orgs/{:org}", "cucumber").prettyPeek().
            then().assertThat().
                    statusCode(200).
                    contentType("application/json; charset=utf-8").
                    body("login",is("cucumber")).
                    body("name",is("Cucumber")).body("id",is(320565));
        }

    /**
     * Verify error message
     * 1. Send a get request to /orgs/:org. Request includes : • Header Accept with value application/xml
     * • Path param org with value cucumber
     * 2. Verify status code 415, content type application/json; charset=utf-8
     * 3. Verify response status line include message Unsupported Media Type
     */

    @Test
    public void errorMessageTest() {
        given().
                header("Accept", "application/xml").
                when().get("/orgs/{:org}", "cucumber").prettyPeek().
        then().assertThat().
                statusCode(415).
                contentType(" application/json; charset=utf-8").
                statusLine(containsString("Unsupported Media Type"));
    }

    /**
     * Number of repositories
     * 1. Send a get request to /orgs/:org. Request includes : • Path param org with value cucumber
     * 2. Grab the value of the field public_repos
     * 3. Send a get request to /orgs/:org/repos. Request includes :
     * • Path param org with value cucumber
     * 4. Verify that number of objects in the response is equal to value from step 2
     */

    @Test
    public void repoNumberTest(){ //failing this guy
        Response response = when().
                            get("/orgs/{:org}","cucumber");

        int public_repoValue = response.jsonPath().getInt("public_repos");

        Response response1 = given().queryParam("per_page", 90). //this param is to specify how many items you want to see on each page
                when().get("/orgs/{:org}/repos","cucumber").prettyPeek();
        //how to count number of objects in the body ?
        int actual_repo_Value = response1.jsonPath().getInt("size()");
        //lets try with List :
//        List<Object> all = response1.jsonPath().getList("");
//        System.out.println("size of collection" + all.size() );

        assertEquals(public_repoValue, actual_repo_Value);
    }

    /**
     * Repository id information
     * 1. Send a get request to /orgs/:org/repos. Request includes : • Path param org with value cucumber
     * 2. Verify that id field is unique in every in every object in the response
     * 3. Verify that node_id field is unique in every in every object in the response
     */
    @Test
    public void repoIdInfoTest(){
      Response response = given().
                        accept(ContentType.JSON).
              when().
                        get("/orgs/{:org}/repos", "cucumber").prettyPeek();
        int expected = response.jsonPath().getInt("size()"); //size of the collection

        List<String> allId = response.jsonPath().getList("id");
        System.out.println("allId = " + allId);
        int actual = allId.size(); //size of the ID

        //here we need to make sure that ID and node_id is unique :

        List<String> allNodeId = response.jsonPath().getList("node_id");
        int listSize = allNodeId.size();
        Set<String> allNodeIDSET = new HashSet<>(allNodeId);
        int setSize = allNodeIDSET.size();

        assertEquals(listSize,setSize); //checking if node_id is unique
        assertEquals(expected,actual); //if ID numbers match number of the Objects in response then ID is UNIQUE

    }

    //when do we use pathParam()? we seem to specify path parameters in get()
    //we can add pathParam("id",101). => inside our given() part
    //then in pur when part we add only : spartan/{id}
    //but we can use either our: we can add path param in our get()
    //given().accept(ContentType.JSON).pathParam("org", "cucumber")
    //        .get("/orgs/{org}").then().assertThat().statusCode(200);
    /**
     * Repository owner information
     * 1. Send a get request to /orgs/:org. Request includes : • Path param org with value cucumber
     * 2. Grab the value of the field id
     * 3. Send a get request to /orgs/:org/repos. Request includes :
     * • Path param org with value cucumber
     * 4. Verify that value of the id inside the owner object in every response is equal to value from step 2
     */

    @Test
    @DisplayName("Verify that value of the id after get request")
    public void repoOwnerNameTest(){
        Response response = given().accept(ContentType.JSON).
                when().
                            get("/orgs/{:org}","cucumber");//.prettyPeek();

        String firstID = response.jsonPath().getString("id");
        System.out.println("firstID = " + firstID);

        Response response1 = given().
                accept(ContentType.JSON).
         when().
                get("/orgs/{:org}/repos", "cucumber");
               // prettyPeek();

        List<String> onlyIDsinOwner = response1.jsonPath().getList("owner.findAll{it.id}.id");
        //without .id will show everything that each owner has
        Set<String> removingDuplicates = new HashSet<>(onlyIDsinOwner);
        // removingDuplicates.iterator().next(); => returns first element in set : Integer
        String value = "";
        Iterator myIter = removingDuplicates.iterator();
        while (myIter.hasNext()){
            value += myIter.next();
            System.out.println("myIter.toString() = " + myIter.toString());//some hashcode
        }

        assertEquals(firstID,value) ; //test passes in here
        assertEquals(Integer.valueOf(firstID), removingDuplicates.iterator().next());
        //org.opentest4j.AssertionFailedError: expected: java.lang.String@1b7332a7<320565> but was: java.lang.Integer@77c233af<320565>

    }

    /**
     * Ascending order by full_name sort
     * 1. Send a get request to /orgs/:org/repos. Request includes : • Path param org with value cucumber
     * • Query param sort with value full_name
     * 2. Verify that all repositories are listed in alphabetical order based on the value of the field name
     */
    @Test
    public void ascendingOrderByNameTest(){
        Response response = given().
                                accept(ContentType.JSON).
                                queryParam("sort", "full_name").
                            when().
                                get("/orgs/{:org}/repos", "cucumber").
                                prettyPeek();

        List<String > allNames = response.jsonPath().getList("name");
        System.out.println("allNames = " + allNames);//just to see all the names

        List<String> sortedNames = new ArrayList<>();
        sortedNames.addAll(allNames);
        Collections.sort(sortedNames);

        assertEquals(allNames,sortedNames);

    }

    /**
     * Descending order by full_name sort
     * 1. Send a get request to /orgs/:org/repos. Request includes : • Path param org with value cucumber
     * • Query param sort with value full_name
     * • Query param direction with value desc
     * 2. Verify that all repositories are listed in reverser alphabetical order based on the value of the field
     * name
     */

    @Test
    public void descendingOrderTest(){
        Response response = given().
                accept(ContentType.JSON).
                queryParam("sort", "full_name").
                queryParam("direction","desc").
                when().
                get("/orgs/{:org}/repos", "cucumber").
                prettyPeek();

        List<String > allNames = response.jsonPath().getList("name");
        System.out.println("allNames = " + allNames);//just to see all the names

        List<String> copyNames = new ArrayList<>();
        copyNames.addAll(allNames);

        Collections.sort(allNames); //ascending
        Collections.reverse(allNames); //descending

        assertEquals(allNames,copyNames);
    }

    /**
     * Default sort
     * 1. Send a get request to /orgs/:org/repos. Request includes : • Path param org with value cucumber
     * 2. Verify that by default all repositories are listed in descending order based on the value of the field
     * created_at
     */

    @Test
    public void defaultSort(){
       Response response = given().
                accept(ContentType.JSON).
               get("/orgs/{:org}/repos", "cucumber").prettyPeek();

      //Verify that by default all repositories are listed in descending order based on the value of the field
        //     * created_at ??
        
        List <String > creationDates = response.jsonPath().getList("created_at");
        System.out.println("creationDates = " + creationDates);


        }

    }



