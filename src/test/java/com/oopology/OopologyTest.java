package com.oopology;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class OopologyTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    Locator loginHeading, dashboardHeading;


    @Test
    void shouldNavigateToHomepage(){
        page.navigate("https://oopology.azurewebsites.net/");
        assertThat(page).hasURL("https://oopology.azurewebsites.net/");
    }

//    @Test
//    void shouldCheckThatVideoIsNotPlayingAutomaticallyOnPageLoad(){
//        page.navigate("https://oopology.azurewebsites.net/");
//        assertThat(page).hasURL("https://oopology.azurewebsites.net/");
//
//    }

    @Test
    void shouldSignUpWithValidUsernameAndPassword(){
        var oopologyPage = new OopologyPage(page);
        page.navigate("https://oopology.azurewebsites.net/");
        page.pause();
        assertThat(page).hasURL("https://oopology.azurewebsites.net/");
        oopologyPage.navigate("Sign Up");
        assertThat(page).hasURL("https://oopology.azurewebsites.net/signup");
        oopologyPage.signup("João", "joao@gmail.com", "#Johnny5!");
        loginHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Login"));
        assertThat(loginHeading).isVisible();
    }

    @Test
    void shouldLoginWithAlreadyExistingUser(){
        OopologyPage oopologyPage = new OopologyPage(page);
        page.navigate("https://oopology.azurewebsites.net/signup");
        oopologyPage.signUpAndLogin("Ryan", "ryan@gmail.com", "#Awesomesauce23!");
        assertThat(page).hasURL("https://oopology.azurewebsites.net/dashboard");
        dashboardHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Ryan's Dashboard"));
        assertThat(dashboardHeading).isVisible();
    }

    @Test
    void shouldNotLoginWithNewUser() {
        OopologyPage oopologyPage = new OopologyPage(page);
        page.navigate("https://oopology.azurewebsites.net/login");
        loginHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Login"));
        assertThat(loginHeading).isVisible();
        oopologyPage.login("johnny@yahoo.com", "#Johnny5codesagain!");
        assertThat(page).hasURL("https://oopology.azurewebsites.net/login");
    }

    @Test
    void shouldNotLoginWithValidUserAndWrongPassword() {
        OopologyPage oopologyPage = new OopologyPage(page);
        page.navigate("https://oopology.azurewebsites.net/signup");
        page.pause();
        oopologyPage.signup("James", "james@hotmail.com", "#GiantPeach123!");
        assertThat(page).hasURL("https://oopology.azurewebsites.net/login");
        oopologyPage.login("james@hotmail.com", "SoWrong");
        assertThat(page).hasURL("https://oopology.azurewebsites.net/login");

    }




    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions() // or firefox, webkit
                .setHeadless(false)
                .setSlowMo(500));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}

