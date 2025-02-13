package qa.OauthAllValidation;
import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;

import io.restassured.authentication.FormAuthConfig;

public class FormAuthentication {
	/*
	 * If you use this approach then Rest Assured will first have to parse through
	 * the HTML response to find the fields for input and then send the form
	 * parameters. However, there is a high possibility that this approach might
	 * fail if the webpage is complex. Additionally, it would also fail if the
	 * context path is not included in the action attribute of the service. To
	 * optimize it to handle such cases, you may use the below format where you
	 * explicitly pass the required fields by providing the FormAuthConfig()-
	 */
	//given().auth().form("your username", "your password", new FormAuthConfig("/perform_signIn","user","password
	@Test
	public void basic_auth_form_API_Test(){
		
		given().log().all()
		.auth()
			.form("admin", "admin", new FormAuthConfig("https://classic.freecrm.com/system/authenticate.cfm", "username", "password"))
		.when().log().all()
			.get("https://classic.freecrm.com/system/authenticate.cfm")
		.then().log().all()
			.assertThat()
				.statusCode(200);
	}
}
