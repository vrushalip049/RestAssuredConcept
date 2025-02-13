package qa.OauthAllValidation;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class OAuth2APITest {
	private String accessToken;
	
	
	@BeforeMethod
	public void getAccessToken() {
		RestAssured.baseURI = "https://test.api.amadeus.com/v1/security/oauth2/token";
		
		Response response = RestAssured.given()
					.contentType(ContentType.URLENC)
					.formParam("grant_type", "client_credentials")
					.formParam("client_id", "VXBJs37GsjZLfpjh7VqJrNdFLV0uryvV")
					.formParam("client_secret", "QO9X6yaeB1HfANf7")
					.when()
						.post();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		response.prettyPrint();
		
		accessToken = response.jsonPath().getString("access_token");
	}

	/*
	 * here are cases when we need to generate an access token for a user session.
	 * This access token performs various transactions and helps maintain the user
	 * session. While using OAuth 2.0 you need to directly pass the access token
	 * generated when the user login using the below syntax-
	 * 
	 * given().auth().oauth2("Access token").get("your end point URL") Using the
	 * access token you can easily request any of the resources secured using the
	 * OAuth scheme.
	 */
	@Test
	public void getFlightDetailsTest_1() {
		//RestAssured.baseURI = "https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200";

		
		Response response = RestAssured.given()
			.header("Authorization", "Bearer " + accessToken)
			.when()
			.get("https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200");
			
		Assert.assertEquals(response.getStatusCode(), 200);
		response.prettyPrint();

	}
	
	
	@Test
	public void getFlightDetailsTest_2() {
		
		Response response = RestAssured.given()
				.auth().oauth2(accessToken)
			.when()
			.get("https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200");
			
		Assert.assertEquals(response.getStatusCode(), 200);
		response.prettyPrint();

	}
	
	
	
}
