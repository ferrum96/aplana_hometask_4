import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SberbankTest {

    private WebDriver driver;
    private String baseUrl;


    @Before
    public void setUp() throws Exception {

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://www.sberbank.ru/ru/person";
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }



    @Test
    public void Test() throws Exception {

        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);

        String regionTitleXpath = "//div[@class='hd-ft-region__title']/child::span";
        String inputRegionXpath = "//a[@class='kit-link kit-link_m hd-ft-region__city'][contains(text(),'Нижегородская область')]";

        driver.findElement(By.xpath(regionTitleXpath)).click();

        WebElement regionSearch = driver.findElement(By.xpath(inputRegionXpath));
        regionSearch.click();

        assertEquals("Нижегородская область", driver.findElement(By.xpath("//div[@class='hd-ft-region__title']/child::span")).getText());

        WebElement lastElement = driver.findElement(By.xpath("//div[@data-pid='SiteFooter-5058887']"));
        wait.until(ExpectedConditions.elementToBeClickable(lastElement));
        int y = lastElement.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+y+")");

        Assert.assertTrue(driver.findElement(By.cssSelector("span.footer__social_logo.footer__social_fb")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("span.footer__social_logo.footer__social_tw")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("span.footer__social_logo.footer__social_yt")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("span.footer__social_logo.footer__social_ins")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("span.footer__social_logo.footer__social_vk")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("span.footer__social_logo.footer__social_ok")).isDisplayed());

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
