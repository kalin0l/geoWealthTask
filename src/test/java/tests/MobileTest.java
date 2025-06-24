package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.MobileSearchPage;
import utils.LoggerUtil;

import java.time.Duration;

public class MobileTest {
    private WebDriver driver;
    private MobileSearchPage searchPage;



    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        searchPage = new MobileSearchPage(driver);
    }

    @Test
    public void testVolkswagenGolf4x4Search() throws InterruptedException {
        searchPage.open();
        searchPage.navigateToSearchPage();
        searchPage.searchVolkswagenGolf4x4();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".item")));
        int total = searchPage.countAllResults();
        int vip = searchPage.countPromo("div.VIP");
        int top = searchPage.countPromo("div.TOP");
        int best = searchPage.countPromo("div.BEST");

        LoggerUtil.log("Общ брой VW Golf 4x4: " + total);
        LoggerUtil.log("VIP обяви: " + vip);
        LoggerUtil.log("TOP обяви: " + top);
        LoggerUtil.log("BEST обяви: " + best);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}