import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class RgsTest {

    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://www.rgs.ru/";
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }



    @Test
    public void Test() throws Exception {

        String menuXpath = "//a[@class='hidden-xs' and contains(text(),'Меню')]";
        String DMSXpath = "//a[contains(text(),'ДМС')]";
        String okXpath = "//div[contains(text(),'ок')]";
        String DMSHeaderXpath = "//h4[@class='modal-title']";
        String sendButtonXpath = "//a[contains(text(),'Отправить заявку')]";

        WebElement okBtn = driver.findElement(By.xpath(okXpath));
        okBtn.click();

        WebElement menuLink = driver.findElement(By.xpath(menuXpath));
        menuLink.click();

        WebElement DMSLink = driver.findElement(By.xpath(DMSXpath));
        DMSLink.click();

        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'Отправить заявку')]"))));

        WebElement sendBtn = driver.findElement(By.xpath(sendButtonXpath));
        sendBtn.click();

        WebElement DMSHeaderLink = driver.findElement(By.xpath(DMSHeaderXpath));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h4[@class='modal-title']"))));

        assertEquals("Заявка на добровольное медицинское страхование", DMSHeaderLink.getText());


        fillField(By.name("LastName"), "Иванов");
        fillField(By.name("FirstName"), "Иван");
        fillField(By.name("MiddleName"), "Иванович");

        new Select(driver.findElement(By.name("Region"))).selectByVisibleText("Москва");

        WebElement phone = driver.findElement(By.xpath("//*[contains(text(),'Телефон')]/..//input"));
        phone.clear();
        phone.click();
        phone.sendKeys("1234567890");

        fillField(By.name("Email"), "qwertyqwerty");

        WebElement contactDate = driver.findElement(By.name("ContactDate"));
        contactDate.sendKeys(LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        contactDate.sendKeys(Keys.ENTER);

        fillField(By.name("Comment"), "test");

        driver.findElement(By.cssSelector("input.checkbox")).click();
        driver.findElement(By.id("button-m")).click();

        assertEquals("Иванов", driver.findElement(By.name("LastName")).getAttribute("value"));
        assertEquals("Иванович", driver.findElement(By.name("MiddleName")).getAttribute("value"));
        assertEquals("Иван", driver.findElement(By.name("FirstName")).getAttribute("value"));
        assertEquals("+7 (123) 456-78-90", driver.findElement(By.xpath("//*[contains(text(),'Телефон')]/..//input")).getAttribute("value"));
        assertEquals("qwertyqwerty", driver.findElement(By.name("Email")).getAttribute("value"));
        assertEquals("test", driver.findElement(By.name("Comment")).getAttribute("value"));
        assertEquals("Москва", new Select(driver.findElement(By.name("Region"))).getAllSelectedOptions().get(0).getText());

        assertEquals("Введите адрес электронной почты", driver.findElement(By.xpath("//*[text()='Эл. почта']/..//span[@class='validation-error-text']")).getText());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    private void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

}
