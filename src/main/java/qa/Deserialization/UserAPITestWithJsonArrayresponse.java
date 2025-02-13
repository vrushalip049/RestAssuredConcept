package qa.Deserialization;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserAPITestWithJsonArrayresponse {
	@Test
	public void createUserWith_Lombok() {
		RestAssured.baseURI="https://gorest.co.in";
		
		//Get all users
		
		Response response=given().log().all()
			.header("Authorization","Bearer")
			.get("/public/v2/users");
		
		response.prettyPrint();
		
		ObjectMapper mapper =new ObjectMapper();
		try {
			User[] user=mapper.readValue(response.getBody().asString(), User[].class);
			
			for(User u : user) {
				System.out.println("ID : " + u.getId());
				System.out.println("NAME : " + u.getName());
				System.out.println("EMAIL : " + u.getEmail());
				System.out.println("STATUS : " + u.getStatus());
				System.out.println("GENDER : " + u.getGender());
				
				System.out.println("----------------------");
			
			}
		}
		catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
		
		
	}
}
