package com.selenium.test.testng.tests;

import com.selenium.test.webtestsbase.WebDriverFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.testng.annotations.*;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 */
public class TestOnliner {
    @DataProvider(name = "user")
    public static Object[][] User() {
        return new Object[][] {
                {"n.rusak@a1qa.com","32243224","nika1qa"}
        };
    }

    @BeforeTest
    public void beforeTest() {
        WebDriverFactory.startBrowser(true);
    }

    @Test
    public void onlinerOpenStartPageTest() {
        WebDriverFactory.getDriver().get("http://onliner.by");
        Assert.assertEquals("http://www.onliner.by/", WebDriverFactory.getDriver().getCurrentUrl());
    }

    @Test(dependsOnMethods = {"onlinerOpenStartPageTest"},dataProvider = "user")
    public void onlinerAuthorizationTest(String email, String password, String username){
        WebElement signInButton = WebDriverFactory.getDriver().findElement(By.xpath("//*[@id=\"userbar\"]/div[2]/div[1]"));
        signInButton.click();
        WebElement userNameField = WebDriverFactory.getDriver().findElement(By.cssSelector("#auth-container__forms > div > div.auth-box__field > form > div:nth-child(1) > div:nth-child(1) > input"));
        userNameField.sendKeys(email);
        WebElement passwordField = WebDriverFactory.getDriver().findElement(By.xpath("//*[@id=\"auth-container__forms\"]/div/div[2]/form/div[1]/div[2]/input"));
        passwordField.sendKeys(password);
        WebElement confirmationButton = WebDriverFactory.getDriver().findElement(By.xpath("//*[@id=\"auth-container__forms\"]/div/div[2]/form/div[4]/div/button"));
        confirmationButton.click();
        WebElement profileName = WebDriverFactory.getDriver().findElement(By.xpath("//*[@id=\"userbar\"]/div[1]/p/a"));
        Assert.assertEquals(username, profileName.getText());
    }

    @Test(dependsOnMethods = {"onlinerOpenStartPageTest"})
    public void onlinerPopularThemeTest() {
        WebElement mostPopular = WebDriverFactory.getDriver().findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div[1]/ul/li[1]/a"));
        String mostPopularExpectedName = mostPopular.getText();
        mostPopular.click();
        String mostPopularRealName = WebDriverFactory.getDriver().findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div[2]/div[1]/div[2]/h1")).getText();
        Assert.assertEquals(mostPopularExpectedName, mostPopularRealName);
    }

    @Test(dependsOnMethods = {"onlinerOpenStartPageTest","onlinerAuthorizationTest"})
    public void onlinerLogoutTest() {
        WebElement logout = WebDriverFactory.getDriver().findElement(By.xpath("//*[@id=\"userbar\"]/div[1]/a"));
        logout.click();
        WebElement signInButton = WebDriverFactory.getDriver().findElement(By.xpath("//*[@id=\"userbar\"]/div[2]/div[1]"));
        Assert.assertEquals("MODELS.AuthController.showModalAuth()",signInButton.getAttribute("OnClick"));
    }

    @AfterTest
    public void afterTest() {
        WebDriverFactory.finishBrowser();
    }

}
