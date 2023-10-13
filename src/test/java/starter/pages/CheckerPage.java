package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;

@DefaultUrl("https://www.gamesforthebrain.com/game/checkers/")
public class CheckerPage extends PageObject {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    public final static String PAGE_TITLE = "Checkers - Games for the Brain";
    private static final String CHECKER_IMAGE_SRC_HIGHLIGHTED = "you2.gif";

    public final static org.openqa.selenium.By GRAY_GIF = By.xpath("//img[@src='https://www.gamesforthebrain.com/game/checkers/gray.gif']");

    private static final String MAKE_A_MOVE_MESSAGE = "Make a move.";
    private static final String PLEASE_WAIT_MESSAGE = "Please wait.";

    @FindBy(css = "#message")
    public WebElementFacade message;

    @FindBy(xpath = "//a[normalize-space()='Restart...']")
    public WebElementFacade restartButton;


    public void selectCheckerBySpaceNumber(int x, int y) {
        String spaceName = String.format("space%d%d", x, y);
        LOGGER.info("WebElement attribute is: " + spaceName);

        WebElementFacade checkerElement = find(By.name(spaceName));
        LOGGER.info("Find Element: " + checkerElement);
        withAction().moveToElement(checkerElement).click().perform();

        withTimeoutOf(5, ChronoUnit.SECONDS)
                .waitFor(ExpectedConditions
                        .domAttributeToBe(checkerElement,"src",CHECKER_IMAGE_SRC_HIGHLIGHTED));
    }

    public void moveToSpace(int x, int y) {
        String spaceName = String.format("space%d%d", x, y);

        WebElementFacade targetSpaceElement = find(By.name(spaceName));
        withAction().moveToElement(targetSpaceElement).click().perform();

        withTimeoutOf(5,ChronoUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(GRAY_GIF));
        textToBePresentInElement(message,MAKE_A_MOVE_MESSAGE,PLEASE_WAIT_MESSAGE);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void textToBePresentInElement(WebElementFacade element, String text1, String text2) {
        withTimeoutOf(5, ChronoUnit.SECONDS).waitFor(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElement(element, text1),
                ExpectedConditions.textToBePresentInElement(element, text2)
        ));
    }

    public void waitForPageLoad(int timeOutInSeconds) {
        withTimeoutOf(timeOutInSeconds, ChronoUnit.SECONDS).waitFor(ExpectedConditions
                .jsReturnsValue("return document.readyState === 'complete'"));
    }

    public void scrollElementIntoViewCenter(WebElementFacade element) {
        String script = "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});";
        evaluateJavascript(script, element);
    }

    public CheckerPage(WebDriver driver) {
        super(driver);
    }


}
