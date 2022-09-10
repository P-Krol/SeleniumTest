package pl.coderslab.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pl.coderslab.model.HotelLoginPage;
import pl.coderslab.model.HotelMainPage;
import pl.coderslab.model.HotelMyAccountPage;

import java.time.Duration;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class HotelSteps {

    private final static String PERMANENT_LOGIN = "pktest@mailg.com";
    private final static String PERMANENT_PASSWORD = "haslo123";
    private WebDriver driver;

    @Given("an open browser with {}")
    public void openBrowser(String url) {
        //        Ustaw gdzie jest chromedriver -> STEROWNIK
        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/drivers/chromedriver");
        //        Otworz przegladarke
        this.driver = new ChromeDriver();
        //        Jesli test nie przechodzi poprawnie, to pewnie za wolno laduje sie strona -> Dodaj czekanie.
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        this.driver.get(url);
    }

    @When("new user register")
    public void newUserRegister() {
        String email = new Random().nextInt(100000000) + "TEA26@test.com";

        // Wyszukaj przycisk do logowania
        WebElement signInButton = this.driver.findElement(By.className("user_login"));
        signInButton.click(); // Kliknij na przycisk SignIn

        WebElement registerEmailInput = this.driver.findElement(By.id("email_create"));
        registerEmailInput.clear(); // Wyczysc pole tekstowe przed wpisaniem
        registerEmailInput.sendKeys(email);
        registerEmailInput.submit(); // Potwierdz email do rejestracji

        // Wyszukaj podstawowe elementy do zalozenia uzytkownika
        WebElement customerFirstNameInput = this.driver.findElement(By.id("customer_firstname"));
        WebElement customerLastNameInput = this.driver.findElement(By.id("customer_lastname"));
//        WebElement customerEmail = driver.findElement(By.id("email"));
        WebElement customerPassword = this.driver.findElement(By.id("passwd"));

        // Wypelnij brakujace informacje o uzytkowniku
        customerFirstNameInput.clear();
        customerFirstNameInput.sendKeys("Janusz");
        customerLastNameInput.clear();
        customerLastNameInput.sendKeys("Januszewski");

        customerPassword.clear();
        customerPassword.sendKeys("12345");

        // Zaloz konto
        WebElement submitButton = this.driver.findElement(By.id("submitAccount"));
        submitButton.click();
    }


    @When("a user with {} and {} is registered")
    public void newUserRegister(String name, String lastName) {
        String email = new Random().nextInt(100000000) + "TEA26@test.com";

        WebElement signInButton = this.driver.findElement(By.className("user_login"));
        signInButton.click(); // Kliknij na przycisk SignIn

        WebElement registerEmailInput = this.driver.findElement(By.id("email_create"));
        registerEmailInput.clear(); // Wyczysc pole tekstowe przed wpisaniem
        registerEmailInput.sendKeys(email);
        registerEmailInput.submit(); // Potwierdz email do rejestracji

        // Wyszukaj podstawowe elementy do zalozenia uzytkownika
        WebElement customerFirstNameInput = this.driver.findElement(By.id("customer_firstname"));
        WebElement customerLastNameInput = this.driver.findElement(By.id("customer_lastname"));
//        WebElement customerEmail = driver.findElement(By.id("email"));
        WebElement customerPassword = this.driver.findElement(By.id("passwd"));

        // Wypelnij brakujace informacje o uzytkowniku
        customerFirstNameInput.clear();
        customerFirstNameInput.sendKeys(name);
        customerLastNameInput.clear();
        customerLastNameInput.sendKeys(lastName);

        customerPassword.clear();
        customerPassword.sendKeys("12345");

        // Zaloz konto
        WebElement submitButton = this.driver.findElement(By.id("submitAccount"));
        submitButton.click();
    }

    @Then("an account is created")
    public void accountIsCreated() {
        String expectedAlertText = "Your account has been created.";

        // Sprawdz czy konto poprawnie zalozone
        WebElement successAlertField = this.driver.findElement(By.className("alert-success"));

        // Pobierz informacje o poprawnym zalozeniu konta
        String alertText = successAlertField.getText();

        // Zweryfikuj komunikat.
        assertEquals(expectedAlertText, alertText);
    }

    @And("close browser")
    public void closeBrowser() {
        this.driver.quit();
    }

    @When("existing user logged into the account")
    public void logInExistingUser() {
        HotelMainPage hotelMainPage = new HotelMainPage(this.driver);
        hotelMainPage.clickSignIn();

        HotelLoginPage hotelLoginPage = new HotelLoginPage(this.driver);
        hotelLoginPage.signInWithCredentials(PERMANENT_LOGIN, PERMANENT_PASSWORD);

    }

    @Then("new address can be added to the account")
    public void addNewAddress() {

        WebElement addMyFirstAddress = this.driver.findElement(By.xpath("//*[@id=\"center_column\"]/div/div/ul/li[1]/a"));
        addMyFirstAddress.click();
        WebElement address = this.driver.findElement(By.id("address1"));
        address.sendKeys("Uli 123");
        WebElement postCode = this.driver.findElement(By.id("postcode"));
        postCode.sendKeys("14-400");
        WebElement city = this.driver.findElement(By.id("city"));
        city.sendKeys("14-400");
        WebElement country = this.driver.findElement(By.id("id_country"));
        country.sendKeys("Poland");
        WebElement phone = this.driver.findElement(By.id("phone"));
        phone.sendKeys("123456789");
        WebElement mobilePhone = this.driver.findElement(By.id("phone_mobile"));
        mobilePhone.sendKeys("123456789");
        WebElement alias = this.driver.findElement(By.id("alias"));
        alias.sendKeys("address 1");
        WebElement submitAddress = this.driver.findElement(By.id("submitAddress"));
        submitAddress.click();
        WebElement backToAccount = this.driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[1]/a"));
        backToAccount.click();
        WebElement goToMyAddresses = this.driver.findElement(By.xpath("//*[@id=\"center_column\"]/div/div/ul/li[3]/a"));
        goToMyAddresses.click();
        WebElement deleteMyAddress = this.driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/div/div/ul/li[11]/a[2]"));
        deleteMyAddress.click();

        Alert alert = driver.switchTo().alert();
        alert.accept();

    }
}
