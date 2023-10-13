package starter.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.pages.CheckerPage;

import static starter.pages.CheckerPage.PAGE_TITLE;

public class CheckerStepDefinitions {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    CheckerPage checkerPage;

    @Given("I am on the Games for the Brain checkers page")
    public void iAmOnTheGamesForTheBrainCheckersPage() {
        checkerPage.open();
    }

    @And("the site is active")
    public void theSiteIsActive() {
        String actualTitle = checkerPage.getTitle();
        Assert.assertEquals("Verification Failed - An incorrect title is displayed on the web page.",
                PAGE_TITLE,actualTitle);
    }

    @When("I start a game as orange")
    public void iStartAGameAsOrange() {
        LOGGER.info("Message is: " + checkerPage.message.getText());
        Assert.assertTrue(checkerPage.message.getText().contains("orange"));
    }

    @And("make five moves including taking a blue piece with {string} confirmations")
    public void makeFiveMovesIncludingTakingABluePieceWithConfirmations(String makeMoveText) {
        checkerPage.selectCheckerBySpaceNumber(6,2);
        checkerPage.moveToSpace(7,3);
        checkerPage.selectCheckerBySpaceNumber(2,2);
        checkerPage.moveToSpace(3,3);
        checkerPage.selectCheckerBySpaceNumber(0,2);
        checkerPage.moveToSpace(2,4);
        checkerPage.selectCheckerBySpaceNumber(7,1);
        checkerPage.moveToSpace(6,2);
        checkerPage.selectCheckerBySpaceNumber(6,2);
        checkerPage.moveToSpace(5,3);
    }

    @Then("I restart the game")
    public void iRestartTheGame() {
        checkerPage.scrollElementIntoViewCenter(checkerPage.restartButton);
        checkerPage.withAction().moveToElement(checkerPage.restartButton).click().perform();
        checkerPage.waitForPageLoad(5);
    }

    @And("confirm the board is reset with no previous moves")
    public void confirmTheBoardIsResetWithNoPreviousMoves() {
        iStartAGameAsOrange();
    }
}
