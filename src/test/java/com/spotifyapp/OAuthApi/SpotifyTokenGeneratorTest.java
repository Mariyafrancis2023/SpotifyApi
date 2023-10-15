package com.spotifyapp.OAuthApi;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SpotifyTokenGeneratorTest {

	WebDriver driver;
	String accessToken;
	String authGrant;

	@Before
	public void beforeTest() {
		WebDriverManager.chromedriver().setup();
	}

	@Test
	public void getPlaylist() {

		Response response = (Response) RestAssured.given().header("Authorization", "Bearer BQCeoA_0rYXAx-w6ffyh66qYuccRN9N7hKwuzdpIguztg8WFoFrWVbpXPfd-Cm2E2L-InBOLnJjhhRaEGbwC8nU-ii-QEFRbiDHOLaTLrqA5zXTS2Ays79ipQ3pg8rvE9ktqog-jFMVZfCG1-8Oq-4pChSNoMdfbKwzSyVImGY90CeuM8fzj7bKbBpF1NBdDiC9qDg").log().all()
				.when().get("https://api.spotify.com/v1/playlists/37i9dQZEVXbMda2apknTqH");

		assertEquals(200, response.statusCode());
	}

	@After
	public void afterTest() {
		driver.quit();
	}
}
