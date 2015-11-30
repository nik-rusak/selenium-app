package com.selenium.test.testng.tests;

import com.selenium.test.webtestsbase.WebDriverFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 */
public class TestOnliner {

    public WebElement findElementByXpathWithExplicitWait(final String xPath,WebDriver driver) {
        return (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.xpath(xPath));
            }
        });
    }

    @DataProvider(name = "user")
    public static Object[][] User() {
        return new Object[][] {
                {"n.rusak@a1qa.com","32243224","nika1qa",1},
                {"nik-rusak@rambler.ru","10051005","1721211",2}
        };
    }

    @BeforeTest
    public void beforeTest() {
        WebDriverFactory.startBrowser(true);
    }

    @Test(dataProvider = "user")
    public void onlinerTest(String email, String password, String username, int categoryNum) {
        WebDriver driver =  WebDriverFactory.getDriver();
        WebDriverFactory.getDriver().get("http://onliner.by");
        Assert.assertEquals("http://www.onliner.by/", WebDriverFactory.getDriver().getCurrentUrl());
        WebElement signInButton = findElementByXpathWithExplicitWait(".//*[@id='userbar']//div[@onclick='MODELS.AuthController.showModalAuth()']", driver);
    //    WebElement signInButton = WebDriverFactory.getDriver().findElement(By.xpath(".//*[@id='userbar']//div[@onclick='MODELS.AuthController.showModalAuth()']"));
        signInButton.click();
    //    WebElement userNameField = WebDriverFactory.getDriver().findElement(By.xpath(".//*[@id='auth-container__forms']//input[contains(@data-bind,'login.data.login')]"));
        WebElement userNameField = findElementByXpathWithExplicitWait(".//*[@id='auth-container__forms']//input[contains(@data-bind,'login.data.login')]",driver);
        userNameField.sendKeys(email);
     //   WebElement passwordField = WebDriverFactory.getDriver().findElement(By.xpath(".//*[@id='auth-container__forms']//input[contains(@data-bind,'login.data.password')]"));
        WebElement passwordField = findElementByXpathWithExplicitWait(".//*[@id='auth-container__forms']//input[contains(@data-bind,'login.data.password')]", driver);
        passwordField.sendKeys(password);
      //  WebElement submitKey = WebDriverFactory.getDriver().findElement(By.xpath(".//*[@id='auth-container__forms']//button[contains(@data-bind,'login.isProcessing')]"));
        WebElement submitKey = findElementByXpathWithExplicitWait(".//*[@id='auth-container__forms']//button[contains(@data-bind,'login.isProcessing')]",driver);
        submitKey.click();
     //   WebElement profileName = WebDriverFactory.getDriver().findElement(By.xpath(".//*[@id='userbar']//a[contains(@data-bind,'currentUser.nickname')]"));
        WebElement profileName = findElementByXpathWithExplicitWait(".//*[@id='userbar']//a[contains(@data-bind,'currentUser.nickname')]", driver);
        Assert.assertEquals(username, profileName.getText());
        List <WebElement> mostPopular = WebDriverFactory.getDriver().findElements(By.xpath(".//ul[@class='catalog-bar__list']//a"));
        String mostPopularExpectedName = mostPopular.get(categoryNum).getText();
        mostPopular.get(categoryNum).click();
     //   String mostPopularRealName = WebDriverFactory.getDriver().findElement(By.xpath(".//div[@id='schema-order']/following-sibling::h1")).getText();
        String mostPopularRealName = findElementByXpathWithExplicitWait(".//div[@id='schema-order']/following-sibling::h1", driver).getText();
        Assert.assertEquals(mostPopularExpectedName, mostPopularRealName);
     //   WebElement logout = WebDriverFactory.getDriver().findElement(By.xpath(".//*[@id='userbar']//a[contains(@data-bind,'currentUser.logout_key')]"));
        WebElement logout = findElementByXpathWithExplicitWait(".//*[@id='userbar']//a[contains(@data-bind,'currentUser.logout_key')]", driver);
        logout.click();
        List<WebElement> signInButtonAfterLogout = WebDriverFactory.getDriver().findElements(By.xpath(".//*[@id='userbar']//div[@onclick='MODELS.AuthController.showModalAuth()']"));
        Assert.assertEquals(1,signInButtonAfterLogout.size());
    }


    @AfterTest
    public void afterTest() {
        WebDriverFactory.finishBrowser();
    }

}
