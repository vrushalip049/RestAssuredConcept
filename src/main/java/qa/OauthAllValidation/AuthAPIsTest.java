package qa.OauthAllValidation;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
public class AuthAPIsTest {
	//basic
	//preemptive
	//digestive
	//form
	//Oauth1
	//Oauth2
	//JWT
	
	//basic auth: username and password
	//2
	/*BasicAuth :
	 * 
	 * As discussed above, the basic authentication scheme uses the username and
	 * password in base64 encoded format. The request header needs to contain the
	 * credentials of the user for access to the resource. It is very easy to send
	 * the credentials using the basic auth
	 */@Test
	public void basicAuthTest() {
		RestAssured.baseURI="https://the-internet.herokuapp.com";
		//need to use RestAssured.given()
		RestAssured.given()
		.auth()
			.basic("admin", "admin")
				.when()
					.get("/basic_auth")
						.then().log().all()
							.assertThat()
								.statusCode(200);
							
		
	}
	 
	/*
	 * By default, Rest Assured uses the challenge-response mechanism. This means
	 * that it waits for the server to challenge rather than send the credentials
	 * directly. By using the preemptive directives we can avoid that additional
	 * call that the server makes and hence additional complications. In a way, it
	 * is similar to the basic auth we saw above, the only difference is that an
	 * additional premptive () directive adds after auth ().
	 */
	@Test
	public void preemptiveAuthTest() {
		RestAssured.baseURI = "https://the-internet.herokuapp.com";
		RestAssured.given()
			.auth()
				.preemptive()
					.basic("admin", "admin")
					.when()
					.get("/basic_auth")
						.then().log().all()
							.assertThat()
								.statusCode(200);
							
	}
	
	/*
	 * It is somewhat similar to challenge-based authentication but is more secure
	 * as it uses a digestive key in subsequent requests. If at all it is
	 * intercepted by an eavesdropper, he will get access only to the transaction
	 * performed and not the user password. The transaction might be replayed but a
	 * new transaction cannot be made as the password is not exposed. Its syntax is
	 * similar to basic authentication-
	 */@Test
	public void digestiveAuthTest() {
		RestAssured.baseURI = "https://postman-echo.com";
		RestAssured.given()
			.auth()
				.digest("postman", "password")
				.when()
				.get("/digest-auth")
				.then().log().all()
				.assertThat()
					.statusCode(200)
						.and()
							.body("authenticated", equalTo(true));
				
	}
	
}
