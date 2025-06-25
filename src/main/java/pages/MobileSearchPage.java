package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class MobileSearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MobileSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://www.mobile.bg/");
    }

    public void navigateToSearchPage() throws InterruptedException {

        WebElement bannerAcceptBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cookiescript_accept")));
        bannerAcceptBtn.click();

        WebElement category = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.showcats div.a1")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", category);

        WebElement locationDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#locatBG")));
        locationDropdown.click();

    }

    public void searchVolkswagenGolf4x4() throws InterruptedException {
        selectAutocompleteOption("marka","VW");

        selectModel("model_show","Golf");
        driver.findElement(By.cssSelector("div.moreFilters")).click();
        WebElement fourWheelDrive = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'4x4')]")));
        fourWheelDrive.click();
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.SEARCH_btn")));
        submitBtn.click();
    }

    private void selectModel(String inputName,String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(inputName)));
        WebElement modelDropDown = driver.findElement(By.cssSelector("input[name='"+inputName+"']"));
        modelDropDown.click();
        WebElement element = driver.findElement(By.cssSelector("input[data-value='"+value+"']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);


    }

    private void selectAutocompleteOption(String inputName, String valueToSelect) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(inputName)));
        input.clear();
        input.sendKeys(valueToSelect);
        WebElement element = driver.findElement(By.xpath("//span[contains(text(),'" + valueToSelect + "')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public int countAllResults() {
        int totalResults = 0;

        while (true) {
            List<WebElement> resultsOnPage = driver.findElements(By.cssSelector(".item"));

            for (WebElement result : resultsOnPage) {
                String classes = result.getAttribute("class");
                if (!classes.contains("fakti")) {
                    totalResults++;
                }
            }

            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Напред')]")));
                nextButton.click();

                wait.until(ExpectedConditions.stalenessOf(resultsOnPage.get(0)));
            } catch (TimeoutException | ElementClickInterceptedException e) {
                try {
                    WebElement firstPageBtn = driver.findElement(By.xpath("//a[@class='saveSlink ' and text()='1']"));
                    firstPageBtn.click();
                } catch (Exception ex) {
                    System.out.println("Не може да се върне на първа страница: " + ex.getMessage());
                }
                break;
            }
        }

        return totalResults;
    }
    
    public int countPromo(String promoClass) {
        return driver.findElements(By.cssSelector(promoClass)).size();
    }
}