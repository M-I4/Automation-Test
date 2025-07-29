package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployeeCRUDTests extends BaseTest {

    @Test(priority = 1)
    public void testAddEmployee() throws InterruptedException {
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        driver.findElement(By.id("txtPassword")).sendKeys("admin123");
        driver.findElement(By.id("btnLogin")).click();

        driver.findElement(By.id("menu_pim_viewPimModule")).click();
        driver.findElement(By.id("btnAdd")).click();
        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("btnSave")).click();

        String empName = driver.findElement(By.id("personal_txtEmpFirstName")).getAttribute("value");
        Assert.assertEquals(empName, "John", "Employee was not added successfully.");
    }

    @Test(priority = 2)
    public void testEditEmployee() throws InterruptedException {
        driver.findElement(By.id("menu_pim_viewPimModule")).click();
        driver.findElement(By.linkText("John")).click();
        driver.findElement(By.id("btnSave")).click();
        WebElement lastNameField = driver.findElement(By.id("personal_txtEmpLastName"));
        lastNameField.clear();
        lastNameField.sendKeys("Smith");
        driver.findElement(By.id("btnSave")).click();
        String updatedName = driver.findElement(By.id("personal_txtEmpLastName")).getAttribute("value");
        Assert.assertEquals(updatedName, "Smith", "Employee was not updated successfully.");
    }

    @Test(priority = 3)
    public void testDeleteEmployee() throws InterruptedException {
        driver.findElement(By.id("menu_pim_viewPimModule")).click();
        driver.findElement(By.id("ohrmList_chkSelectAll")).click();
        driver.findElement(By.id("btnDelete")).click();
        driver.findElement(By.id("dialogDeleteBtn")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElements(By.linkText("John")).isEmpty(), "Employee was not deleted successfully.");
    }
}
