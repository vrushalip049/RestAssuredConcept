package qa.POSTAPI;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//Post request is used to send or post the data to the server.

//Post request mostly results in creating a new record in the database. It can also update the existing record in the database.

//Rest Assured uses a post () method to make HTTP POST requests.

//We usually send the JSON data along with the request object and then POST it to the server.

//The content-type parameter is "application/JSON" in the above example. The data sent to the server by POST request can also be XML(content-type: application/XML).
public class AuthAPITest {
	@Test
	public void getAuthTokenTest_WithJSON() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		String token = given().log().all().contentType(ContentType.JSON)
				.body("{\"username\":\"admin\"," + "\"password\":\"password123\"}").when().log().all().post("/auth")
				.then().assertThat().statusCode(200).extract().path("token");

		System.out.println("token->" + token);
		Assert.assertNotNull(token);

	}

	@Test
	public void getAuthTokenTest_WithJSONFILE() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		String tokenId = given().log().all().contentType(ContentType.JSON)
				.body(new File(".\\src\\main\\java\\qa\\POSTAPI\\auth.json")).when().log().all().post("/auth").then()
				.log().all().assertThat().statusCode(200).extract().path("token");

		System.out.println("tokenId ==>" + tokenId);
		Assert.assertNotNull(tokenId);
	}

	// pojo --> json: serialization
	// json--->pojo: de-serialization
	// jackson-databind/gson/yasson

	@Test
	public void getAuthTokenTest_WithPOJOClass() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Credentials cred = new Credentials("admin", "password123");
		String tokenId = given().log().all().contentType(ContentType.JSON).body(cred).when().log().all().post("/auth")
				.then().log().all().assertThat().statusCode(200).extract().path("token");

		System.out.println("tokenId ==>" + tokenId);
		Assert.assertNotNull(tokenId);
	}
}
