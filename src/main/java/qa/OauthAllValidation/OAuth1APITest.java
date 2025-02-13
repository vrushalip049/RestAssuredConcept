package qa.OauthAllValidation;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OAuth1APITest {
	// Oauth1.0: you need to add extra dependency in your pom.xml
	// name: scribejava-core
	// version: old version: 2.5.3
	
	/*
	 * You can download the below jars from Maven repository for you oauth
	 * authentication
	 * 
	 * com.github.scribejava scribejava-apis 2.5.3
	 */
	
	/*
	 * Secured resources built using OAuth 1.0 requires passing consumer key,
	 * secret, access token, and token secret. The syntax it follows is -
	 * 
	 * given().auth().oauth(consumerKey, consumerSecret, accessToken,tokenSecret).get("your end point URL") 
	 * 
	 * OAuth parameters read the required
	 * user input dynamically.
	 */
	@Test
	public void flickAPITest() {
		RestAssured.baseURI = "https://www.flickr.com";
		RestAssured.baseURI = "https://www.flickr.com";

		Response response = RestAssured.given().auth()
				.oauth("a110d09788adcf0a3f867e4958a0a3ef", 
						"ecb0b3f9ab7d9f8c", 
						"72157720929991962-e1c25ed42b7c024c",
						"0f4a1d7993ecac89")
				.queryParam("nojsoncallback", 1).
				queryParam("format", "json").
				queryParam("method", "flickr.test.login")
				.when().get("/services/rest").then().assertThat().statusCode(200).extract().response();

		response.prettyPrint();
	}
}
