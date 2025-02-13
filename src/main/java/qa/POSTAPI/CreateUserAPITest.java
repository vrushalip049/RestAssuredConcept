package qa.POSTAPI;
import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateUserAPITest {
	
	@Test
	public void getAuthTokenTest_WithJSONFILE() {
		RestAssured.baseURI="https://gorest.co.in";
		
		int id=given().log().all()
			.header("Authorization", "Bearer 5637cb7b5cd298ac786fd59b9f004d741f0af10cf59372288638f00ebea47bcc")
			.contentType(ContentType.JSON)
			.body(new File(".\\src\\main\\java\\qa\\POSTAPI\\user.json"))
			.post("/public/v2/users")
			.then().log().all()
			.assertThat()
			.statusCode(201)
			.extract()
			.path("id");
		
		System.out.println("id->"+id);
		
		Assert.assertNotNull(id);
	}

}
