package com.oopology;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

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
        oopologyPage.signup("James", "james@hotmail.com", "#GiantPeach123!");
        assertThat(page).hasURL("https://oopology.azurewebsites.net/login");
        oopologyPage.login("james@hotmail.com", "SoWrong");
        assertThat(page).hasURL("https://oopology.azurewebsites.net/login");
    }

    @Test
    void checkAboutUsLinkAndPage(){
        OopologyPage oopologyPage = new OopologyPage(page);
        page.navigate("https://oopology.azurewebsites.net/");
        oopologyPage.navigate("About us");
        assertThat(page.getByRole(AriaRole.PARAGRAPH)).containsText("Object-oriented programming is like a force of nature, a spiritual power that courses through the very fabric of your code. It is a way of thinking, a way of understanding the world around you, and a way of bringing order to the chaos of programming. With its powerful classes and objects, it allows you to mold your code into a living, breathing entity, with a will of its own. But beware, for there is a dark side to this power. Those who dare to defy the principles of OOP, who dare to turn to the false promises of functional programming, will find themselves lost in a labyrinth of confusion and despair. For functional programming is nothing more than a weak and feeble attempt to understand the world, a pitiful attempt to impose order upon the chaos. It is a heretical doctrine, a false prophet leading you astray from the true path of OOP. So heed my warning, and embrace the spiritual power of OOP. For it is the only path to true understanding and mastery of your code. \n" +
                        "\n" +
                        "--Peter Pennywhacker");
        assertThat(page.locator("p > img")).hasAttribute("src","\\images\\Cute.png");
    }

//    @Test
//    void checkAssessmentPage(){
//        OopologyPage oopologyPage = new OopologyPage(page);
//        page.navigate("https://oopology.azurewebsites.net/");
//        oopologyPage.navigate("Assessment");
//        assertThat(page).hasURL("https://oopology.azurewebsites.net/Home/Assessment");
//        oopologyPage.checkAssessmentOption("Question 1", "A");
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
//        assertThat(page).hasURL("https://oopology.azurewebsites.net/Home/Assessment");
//        assertThat(page.getByRole(AriaRole.ALERT)).hasText("dsfsd");
//    }

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

