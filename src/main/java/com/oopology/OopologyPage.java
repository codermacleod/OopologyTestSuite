package com.oopology;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class OopologyPage {
    private final Page page;
//    private Locator john;
    private String jane;

    public OopologyPage(Page page) {
        this.page = page;
//        this.toDoField = page.getByPlaceholder("What needs to be done?");
    }

    public void navigate(String pageLink){
        //Home, Sign Up, Login, Courses
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(pageLink)).click();
    }
    public void signup(String name, String email, String password){
        page.getByLabel("Name:").fill(name);
        page.getByLabel("Email:").fill(email);
        page.getByLabel("Password:").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
    }

    public void signUpAndLogin(String name, String email, String password){
        signup(name, email, password);
        page.getByLabel("Email:").fill(email);
        page.getByLabel("Password:").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
    }

    public void login(String email, String password){
        page.getByLabel("Email:").fill(email);
        page.getByLabel("Password:").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
    }
//    public void checkAssessmentOption(String question, String option){
//        page.getByText(question).locator("input."+ option).click();
//    }
}
