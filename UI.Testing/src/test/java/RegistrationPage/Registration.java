package RegistrationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;  
import java.io.File;
import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Registration {
	WebDriver driver;
	WebDriverWait wait;
	@BeforeSuite
	void instantiateWebdriver()
	{
		WebDriverManager.chromedriver().setup();
		ChromeOptions options =new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addExtensions(new File("./Extensions/AdBlock.crx"));
		driver=new ChromeDriver(options);
		String URL="http://practice.automationtesting.in/";
		driver.get(URL);
	}
	@Test
	public void registrationSignIn() {
		driver.findElement(By.linkText("My Account")).click();
		wait=new WebDriverWait(driver, Duration.ofSeconds(2));
		String userName="abc93982243@gmail.com";
		String password="Admin@123Admin1";
		driver.findElement(By.id("reg_email")).sendKeys(userName);
		driver.findElement(By.id("reg_password")).sendKeys(password,Keys.ENTER);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p/strong"))));
		String user=driver.findElement(By.xpath("//p/strong")).getText();
		String ActUser[]=userName.split("@");
		Assert.assertEquals(ActUser[0],user);
	}
	
	@Test
	public void invalidEmail() {
		driver.findElement(By.linkText("My Account")).click();
		String userName="abc9398223@Test";
		String password="Admin@123Admin1afsedf";
		driver.findElement(By.id("reg_email")).sendKeys(userName);
		driver.findElement(By.id("reg_password")).sendKeys(password,Keys.ENTER);
		String Error=driver.findElement(By.xpath("//ul[@class='woocommerce-error']/li")).getText();
		Assert.assertEquals(Error, "Error: Please provide a valid email address.");
	}
	@Test
	public void emptyEmail() {
		driver.findElement(By.linkText("My Account")).click();
		String userName="";
		driver.findElement(By.id("reg_email")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@name='register']")).click();
		String Error=driver.findElement(By.xpath("//ul[@class='woocommerce-error']/li")).getText();
		Assert.assertEquals(Error, "Error: Please provide a valid email address.");
		
	}

}
