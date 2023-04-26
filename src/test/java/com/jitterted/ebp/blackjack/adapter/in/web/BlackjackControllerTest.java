package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.ShuffledDeck;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BlackjackControllerTest {

    @Test
    void startGameResultsInTwoCardsDealtToPlayer() {
        Game game = new Game(new ShuffledDeck());
        BlackjackController blackjackController = new BlackjackController(game);

        String redirectPage = blackjackController.startGame();

        assertThat(game.playerHand().cards())
                .hasSize(2);
        assertThat(redirectPage)
                .isEqualTo("redirect:/game");
    }

    @Test
    void gameViewPopulatesViewModelWithAllCards() {
        Deck stubDeck = new StubDeck(List.of(new Card(Suit.DIAMONDS, Rank.TEN),
                                             new Card(Suit.HEARTS, Rank.TWO),
                                             new Card(Suit.DIAMONDS, Rank.KING),
                                             new Card(Suit.CLUBS, Rank.THREE)));
        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        String viewName = blackjackController.gameView(model);

        assertThat(viewName)
                .isEqualTo("blackjack");

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getDealerCards())
                .containsExactly("2♥", "3♣");

        assertThat(gameView.getPlayerCards())
                .containsExactly("10♦", "K♦");
    }

    @Test
    public void hitCommandResultsInThirdCardDealtToPlayer() throws Exception {
        Game game = new Game(StubDeck.playerHitsDoesNotBust());
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        String redirectPage = blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game");
        assertThat(game.playerHand().cards())
                .hasSize(3);
    }

    @Test
    public void hitCommandAndPlayerBustsThenRedirectToDonePage() throws Exception {
        Game game = new Game(StubDeck.playerHitsAndGoesBust());
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        String redirectPage = blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");
    }

    @Test
    public void donePageShowsFinalGameStateWithOutcome() throws Exception {
        Fixture fixture = createFixture(StubDeck.playerPushesWithDealer());

        Model model = new ConcurrentModel();
        fixture.blackjackController.doneView(model);

        assertThat(model.containsAttribute("gameView"))
                .isTrue();

        String outcome = (String) model.getAttribute("outcome");
        assertThat(outcome)
                .isNotBlank();
    }

    @Test
    void playerStandsResultsInRedirectToDonePageAndPlayerIsDone() {
        Fixture fixture = createFixture(StubDeck.playerStandsAndBeatsDealer());

        String redirectPage = fixture.blackjackController.standCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");
        assertThat(fixture.game.isPlayerDone())
                .isTrue();
    }

    private Fixture createFixture(Deck deck) {
        Game game = new Game(deck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        return new Fixture(game, blackjackController);
    }

    @Test
    void standResultsInDealerDrawingCardOnTheirTurn() throws Exception {
        Fixture fixture = createFixture(StubDeck.dealerDrawsOneCardOnTheirTurn());

        fixture.blackjackController.standCommand();

        assertThat(fixture.game.dealerHand().cards())
                .hasSize(3);
    }

    private static class Fixture {
        Game game;
        BlackjackController blackjackController;

        public Fixture(Game game, BlackjackController blackjackController) {
            this.game = game;
            this.blackjackController = blackjackController;
        }
    }
}