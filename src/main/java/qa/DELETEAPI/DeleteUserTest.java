package qa.DELETEAPI;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
public class DeleteUserTest {
	
	
	//The "delete" method deletes a resource from the server. It is quite similar to the rm UNIX command.
	//The deletion of a resource is based on the server implementation and the response received is non-cacheable.
	//The delete request returns any of the three types of response codes, i.e., 202, 204, and 200.
	//The delete() method used with "path" or "pathParams" deletes the request.
	//To verify the deletion, you may use assertion or any other relevant code as required.
	
	//202(Accepted): The server accepts the request but does not enact.
	//204(No Content)- A status code of 204 on the HTTP delete request method denotes successful enactment of the delete request without any content in the response.
	//200(OK)- The action was successful and the response message includes representation with the status.
	//404(Not Found) - When the server can't find the resource. The reason could either does not exist or previously deleted.
	
	public static String getRandomEmailid()
	{
		return "apiautomation"+System.currentTimeMillis()+"@mail.com";
	}
	
	
	@Test
	public void deleteUserTest() {
		RestAssured.baseURI="https://gorest.co.in";
		
		
		//POST-Create User
		User user=new User("Naveen",getRandomEmailid(),"male","active");
		
		Response response=RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer 5637cb7b5cd298ac786fd59b9f004d741f0af10cf59372288638f00ebea47bcc")
				.body(user)
				.when().log().all()
				.post("/public/v2/users");
		response.prettyPrint();
		Integer userId=response.jsonPath().get("id");	
		System.out.println("userId->"+userId);
		
		System.out.println("-------------------------");
		
		//2. Delete - deleter user
		RestAssured.given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
		.when().log().all()
			.delete("/public/v2/users/"+userId)
				.then().log().all()
					.assertThat()
						.statusCode(204);
		//3. Get the user -- GET:
				RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
				.when()
					.get("/public/v2/users/"+userId)
						.then().log().all()
							.assertThat()
								.statusCode(404)
									.and()
										.assertThat()
											.body("message", equalTo("Resource not found"));
	}

}
