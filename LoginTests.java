package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginTests extends BaseTest {

    @Test(priority = 1)
    public void validLoginTest() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        WebElement dashboard = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
        Assert.assertTrue(dashboard.isDisplayed(), "Valid login failed");
    }

    @Test(priority = 2)
    public void invalidLoginTest() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.findElement(By.name("username")).sendKeys("WrongUser");
        driver.findElement(By.name("password")).sendKeys("WrongPass");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        WebElement error = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class,'alert-content-text')]")));
        Assert.assertTrue(error.isDisplayed(), "Error message not shown for invalid login");
    }
}
