package ru.netology.cardDelivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;

public class Tests {

    private String dateGenerator(int addedDays, String pattern) {
        return LocalDate.now().plusDays(addedDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    private String planDate = dateGenerator(3, "dd.MM.yyyy");

    @Test
    public void shouldBeBooked() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planDate);
        $("[data-test-id='name'] input").setValue("Гарсия Хосе");
        $("[data-test-id='phone'] input").setValue("+77007007070");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planDate));
    }
}
