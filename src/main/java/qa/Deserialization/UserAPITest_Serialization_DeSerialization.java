package qa.Deserialization;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserAPITest_Serialization_DeSerialization {
	public static String getEmailId()
	{
		return "Automation"+System.currentTimeMillis()+"@mail.com";
	}
	
	@Test
	public void createUserWith_Lombok() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		User user=new User (null, "Automation",getEmailId(),"female","active");
		
		/*
		 * or //1. create a user : POST 
		 * User user = new User.UserBuilder()
		 * .name("Kavya") 
		 * .email("kavya1237@gmail.com") 
		 * .status("active")
		 * .gender("female") 
		 * .build();
		 */
		
		Response response = given().log().all()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer 5637cb7b5cd298ac786fd59b9f004d741f0af10cf59372288638f00ebea47bcc")	
				.body(user) //auto serialization : pojo/lombok to json
				.when()
					.post("/public/v2/users");
		
		response.prettyPrint();
		Integer userId = response.jsonPath().get("id");
		System.out.println("User id : " + userId);
		
		System.out.println("===================");
		
		//2. get a user using the userId : GET
		Response getResponse = given().log().all()
			.header("Authorization", "Bearer 5637cb7b5cd298ac786fd59b9f004d741f0af10cf59372288638f00ebea47bcc")	
			.when()
				.get("/public/v2/users/" + userId);
				
		getResponse.prettyPrint();
		
		//De-Serialization: JSON to POJO
		ObjectMapper mapper = new ObjectMapper();
		try {
			User userRes = mapper.readValue(getResponse.getBody().asString(), User.class);
			
			System.out.println(userRes.getId() + " " + userRes.getName() + " " + userRes.getGender() + " " + userRes.getStatus() + " " + userRes.getEmail() );
			Assert.assertEquals(userRes.getId(), userId);
			Assert.assertEquals(userRes.getEmail(), user.getEmail());
			Assert.assertEquals(userRes.getGender(), user.getGender());
			Assert.assertEquals(userRes.getStatus(), user.getStatus());
			Assert.assertEquals(userRes.getName(), user.getName());

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			new File("sasa");
			e.printStackTrace();
		}
	}
}
