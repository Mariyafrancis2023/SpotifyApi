package com.spotifyapp.OAuthApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

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
