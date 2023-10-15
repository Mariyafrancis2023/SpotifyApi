package com.spotifyapp.OAuthApi;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SpotifyTokenGenerator {

	WebDriver driver;
	String accessToken;
	String authGrant;

	@Before
	public void beforeTest() {
		WebDriverManager.chromedriver().setup();
		authGrant = getCode1();
		accessToken = getCode2();
	}

	public String getCode1() {

		driver = new ChromeDriver();

		driver.get(
				"https://accounts.spotify.com/authorize?response_type=code&client_id=<client_id>&redirect_uri=https://pivotcoachingacademy.ca/about-us");
		driver.manage().window().maximize();
		driver.findElement(By.id("login-username")).sendKeys("francismariapadayatty@gmail.com");
		driver.findElement(By.id("login-password")).sendKeys("Spotify2023");
		driver.findElement(By.id("login-button")).click();

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.cssSelector("button[data-testid='auth-accept']"))));
			   
		foo.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[text()='Copyright © 2021 PIVOT COACHING ACADEMY - All Rights Reserved.']")));

		System.out.println("Pivot page displayed :" + driver
				.findElement(
						By.xpath("//span[text()='Copyright © 2021 PIVOT COACHING ACADEMY - All Rights Reserved.']"))
				.isDisplayed());

		String url = driver.getCurrentUrl();
		authGrant = url.substring(url.indexOf("=") + 1).trim();
		return authGrant;
	}

	public String getCode2() {

		Response response = (Response) RestAssured.given().header("Authorization",
				"Basic Y2Y3NGE5MTBlYWYzNDlhY2IzN2ZhNWQyMDc2MzM2MjE6ZTYzOTUxOTRjMDc4NGE4NGE0YjFkZDI2NmZhNWNkMDA=")
				.contentType("application/x-www-form-urlencoded")
				.formParam("grant_type", "authorization_code")
				.formParam("code",authGrant)
				.formParam("redirect_uri", "https://pivotcoachingacademy.ca/about-us").log().all().when().
				post("https://accounts.spotify.com/api/token");

		assertEquals(200, response.statusCode());
		accessToken = response.jsonPath().getString("access_token");
		return accessToken;
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
