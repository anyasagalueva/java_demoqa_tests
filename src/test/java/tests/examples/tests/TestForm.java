package tests.examples.tests;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestForm {
    @BeforeAll
    static void configureBrowser() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
    }

    // ДЗ п.1 заполнение всех полей формы регистрации
    @Test
    void successfulFillFormTest() {
        open("/automation-practice-form");
        $("#firstName").setValue("Testik");
        $("#lastName").setValue("Testoviy");
        $("#userEmail").setValue("test123@test.ru");
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("9149999999");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("February");
        $(".react-datepicker__year-select").selectOption("1996");
        $(".react-datepicker__day--026:not(.react-datepicker__day--outside-month)").click();
        $("#subjectsInput").setValue("Maths").pressEnter();
        $("#hobbiesWrapper").$(byText("Music")).click();
        $("#uploadPicture").uploadFromClasspath("avatar.png");
        $("#currentAddress").setValue("Saint Petersburg, Nevsky prospekt 102");
        $("#state").scrollTo().click();
        $("#stateCity-wrapper").$(byText("Haryana")).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText("Karnal")).click();
        $("#submit").scrollTo().click();

        $(".modal-dialog").should(appear);
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Testik Testoviy"));
        $(".table-responsive").$(byText("Student Email")).parent().shouldHave(text("test123@test.ru"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Female"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("9149999999"));
        $(".table-responsive").$(byText("Date of Birth")).parent().shouldHave(text("26 February,1996"));
        $(".table-responsive").$(byText("Subjects")).parent().shouldHave(text("Maths"));
        $(".table-responsive").$(byText("Hobbies")).parent().shouldHave(text("Music"));
        $(".table-responsive").$(byText("Picture")).parent().shouldHave(text("avatar.png"));
        $(".table-responsive").$(byText("Address")).parent().shouldHave(text("Saint Petersburg, Nevsky prospekt 102"));
        $(".table-responsive").$(byText("State and City")).parent().shouldHave(text("Haryana Karnal"));
    }

    // ДЗ п.2.1 только обязательные поля
    @Test
    void fillRequiredFieldsTest() {
        open("/automation-practice-form");
        $("#firstName").setValue("Testik");
        $("#lastName").setValue("Testoviy");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("9149999999");
        $("#submit").scrollTo().click();

        $(".modal-dialog").should(appear);
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Testik Testoviy"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Male"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("9149999999"));
    }

    // ДЗ п.2.2  негативный(пустая форма)
    @Test
    void emptyFormTest() {
        open("/automation-practice-form");
        $("#submit").scrollTo().click();

        $("#userForm").shouldHave(cssClass("was-validated"));
        $(".modal-dialog").shouldNot(appear);
    }

    // ДЗ п.2.2 негативный(не выбран пол)
    @Test
    void withoutGenderTest() {
        open("/automation-practice-form");
        $("#firstName").setValue("Testik");
        $("#lastName").setValue("Testoviy");
        $("#userNumber").setValue("9149999999");
        $("#submit").scrollTo().click();

        $("#userForm").shouldHave(cssClass("was-validated"));
        $(".modal-dialog").shouldNot(appear);
    }

    // ДЗ п.2.2 негативный(не заполнена фамилия)
    @Test
    void withoutLastNameTest() {
        open("/automation-practice-form");
        $("#firstName").setValue("Testik");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("9149999999");
        $("#submit").scrollTo().click();

        $("#userForm").shouldHave(cssClass("was-validated"));
        $(".modal-dialog").shouldNot(appear);
    }

    // ДЗ п.2.3 успешная отправка с минимумом полей
    @Test
    void fillOnlyNameTest() {
        open("/text-box");
        $("#userName").setValue("Testik Testoviy");
        $("#submit").scrollTo().click();

        $("#output #name").shouldHave(text("Testik Testoviy"));
    }

    // ДЗ п.2.3 негативный(email без @)
    @Test
    void invalidEmailTest() {
        open("/text-box");
        $("#userName").setValue("Testik Testoviy");
        $("#userEmail").setValue("test1232test.ru");
        $("#submit").scrollTo().click();

        $("#userEmail").shouldHave(cssClass("field-error"));
        $("#output #email").shouldNot(exist);
    }
}


