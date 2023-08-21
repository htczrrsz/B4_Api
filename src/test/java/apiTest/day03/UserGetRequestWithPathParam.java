package apiTest.day03;

import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.baseURI;

public class UserGetRequestWithPathParam {

    @BeforeClass
    public void beforeClass(){
        baseURI="https://www.krafttechexlab.com";
    }
}
