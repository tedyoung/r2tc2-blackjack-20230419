package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    void playerHitsAndGoesBustThenOutcomeIsPlayerLoses() {
        Game game = new Game(StubDeck.createPlayerHitsAndGoesBust());
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
    }

    @Test
    void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() {
        Game game = new Game(StubDeck.createPlayerStandsAndBeatsDealer());
        game.initialDeal();

        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    void playerDealtHandWithSameValueAsDealerThenPlayerPushesDealer() {
        Game game = new Game(StubDeck.createPlayerPushesDealer());
        game.initialDeal();

        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_PUSHES);
    }

    @Test
    void playerDealtBlackjackUponInitialDealThenWinsBlackjack() {
        Deck playerDealtBlackjackDealerNotDealtBlackjack = new StubDeck(Rank.ACE, Rank.NINE,
                                                 Rank.JACK, Rank.QUEEN);
        Game game = new Game(playerDealtBlackjackDealerNotDealtBlackjack);

        game.initialDeal();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_DEALT_BLACKJACK);
    }

    @Test
    void playerWithHandValueOf21And3CardsIsNotBlackjack() {
        Deck twentyOneWithThreeCards = new StubDeck(Rank.EIGHT, Rank.NINE,
                                                    Rank.THREE, Rank.EIGHT,
                                                    Rank.TEN);
        Game game = new Game(twentyOneWithThreeCards);
        game.initialDeal();

        game.playerHits();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    void noBlackjackDealtPlayerIsNotDone() {
        Deck notDealtBlackjack = new StubDeck(Rank.EIGHT, Rank.NINE,
                                              Rank.THREE, Rank.EIGHT);
        Game game = new Game(notDealtBlackjack);

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isFalse();
    }

}