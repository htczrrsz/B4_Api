package apiTest.day03;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class D_06_PathMethod {
    @BeforeClass
    public void beforeClass(){
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }


    @Test
    public void testWithPathMethod(){
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 111)
                .when()//.log().all()
                .get("/allusers/getbyid/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=UTF-8");

        // print each value
        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("name").toString());
        System.out.println("response.body().path(\"email\").toString() = " + response.body().path("email").toString());
        System.out.println("response.body().path(\"id\").toString() = " + response.body().path("id").toString());
        System.out.println("response.body().path(\"job\").toString() = " + response.body().path("job").toString());

        int id=response.path("id[0]");
        String name=response.path("name[0]");
        String email=response.path("email[0]");

        //verify each value
        assertEquals(id,111);
        assertEquals(name,"Thomas Eduson");
        assertEquals(email,"thomas@test.com");



    }


    @Test
    public void testAllUsersWithPathMth(){
        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .when()//.log().all()
                .when()//.log().all()
                .get("/allusers/alluser");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=UTF-8");
        assertEquals(response.getHeader("Content-Type"),"application/json; charset=UTF-8");


        int id= response.path("id[0]");
        System.out.println("id = " + id);
        assertEquals(id,1);

        String name= response.path("name[0]");
        System.out.println("name = " + name);
        assertEquals(name,"MercanS");

        int lastId= response.path("id[-1]");
        System.out.println("lastId = " + lastId);
        String lastname= response.path("name[-1]");
        System.out.println("lastname = " + lastname);
        assertEquals(lastId,102);
        assertEquals(lastname,"GHAN");

//        skills[0][1] --> MercanS's skills --> java
//        education[0].school[0] --> MercanS's school --> School or BootCamp

    }





}
