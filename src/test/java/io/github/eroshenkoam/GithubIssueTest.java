package io.github.eroshenkoam;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class GithubIssueTest {

    @BeforeEach
    public void initDriver() throws IOException {
//        final String url = " 172.23.0.1:4444/wd/hub";
//        WebDriver driver = new RemoteWebDriver(new URL(url), DesiredCapabilities.chrome());
//        driver.manage().window().setSize(new Dimension(1920,1024));
//        WebDriverRunner.setWebDriver(driver);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("90.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("resolution", "1920x1024");
        capabilities.acceptInsecureCerts();
        capabilities.setAcceptInsecureCerts(true);

        WebDriver driver = new RemoteWebDriver(
                new URL("http://192.168.0.16:4444/wd/hub"),
                capabilities
        );
        driver.manage().window().maximize();
        WebDriverRunner.setWebDriver(driver);
        com.codeborne.selenide.Configuration.timeout = 7000;

    }

    @Test
    @Issue("DE-2")
    @DisplayName("Открываем сщуствующую Issue")
    public void testIssue() {
        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Открываем страницу с репозиторием", () -> {
            $x("//*[contains(@class, 'header-search-input')]").click();
            $x("//*[contains(@class, 'header-search-input')]").sendKeys("eroshenkoam/allure-example");
            $x("//*[contains(@class, 'header-search-input')]").submit();
            $x("//a[@href='/eroshenkoam/allure-example']").click();
        });
//        step("Открываем страницу с задачами в репозитории", () -> {
//            $x("//a[contains(@data-selected-links, 'repo_issues')]").click();
//        });
//        step("Проверяем title", () -> {
//            $x("//a[@aria-label=Page 2']").click();
//            $x("//a[@id='issue_12_link']").click();
//            $x("//h1[contains(@class, 'gh-header-title')]").should(Condition.text("Hello, World!"));
//        });

    }

    @AfterEach
    public void stopDriver() {
        Optional.ofNullable(WebDriverRunner.getWebDriver()).ifPresent(WebDriver::quit);
    }

}
