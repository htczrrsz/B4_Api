package apiTest.day06;

import api_POJO_Templates.PetStoreUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class POJO_Deserialization {
    @BeforeClass
    public void beforeClass() {
        baseURI = "https://petstore.swagger.io/v2";
    }


    @Test
    public void oneUserPetStore(){
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("username", "Miky")
                .when()
                .get("/user/{username}");

        System.out.println("response.statusCode() = " + response.statusCode());

        //JSON to our petStore object
        PetStoreUser oneUser= response.body().as(PetStoreUser.class);
        //print all information
        System.out.println("oneUser.getId() = " + oneUser.getId());
        System.out.println("oneUser.getUserStatus() = " + oneUser.getUserStatus());
        System.out.println("oneUser.getUsername() = " + oneUser.getUsername());
        System.out.println("oneUser.getFirstname() = " + oneUser.getFirstName());
        System.out.println("oneUser.getLastname() = " + oneUser.getLastName());
        System.out.println("oneUser.getEmail() = " + oneUser.getEmail());
        System.out.println("oneUser.getPhone() = " + oneUser.getPhone());
        System.out.println("oneUser.getPassword() = " + oneUser.getPassword());

        //verify all information
        Assert.assertEquals(oneUser.getId(),9.223372036854776E18);
        Assert.assertEquals(oneUser.getUserStatus(),0.0);
        Assert.assertEquals(oneUser.getEmail(),"mike@gmail.com");
        Assert.assertEquals(oneUser.getFirstName(),"mike");
        Assert.assertEquals(oneUser.getLastName(),"masters");
        Assert.assertEquals(oneUser.getPhone(),"55512345");
        Assert.assertEquals(oneUser.getPassword(),"Test1234");
    }

}
