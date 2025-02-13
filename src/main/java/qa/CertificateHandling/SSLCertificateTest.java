package qa.CertificateHandling;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;

public class SSLCertificateTest {
	@Test
	public void sslTest() {
		RestAssured.given().log().all()
		.relaxedHTTPSValidation()
		//.relaxedHTTPSValidation()->Caused by: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
		.when()
		.get("https://untrusted-root.badssl.com/")
		.then().log().all()
		.statusCode(200);
	}
	
	@Test
	public void sslTest_With_Config() {
		RestAssured.config = RestAssuredConfig.config()
				.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
		RestAssured.given().log().all()
			.when()
				.get("https://untrusted-root.badssl.com/")
				.then().log().all()
				.statusCode(200);
	}
	
}
