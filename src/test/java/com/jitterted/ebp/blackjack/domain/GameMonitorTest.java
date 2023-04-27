package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class GameMonitorTest {

    @Test
    void playerStandsThenGameIsOverAndResultsSentToMonitor() {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.playerStandsAndBeatsDealer(), gameMonitorSpy);
        game.initialDeal();

        game.playerStands();

        // verify that the roundCompleted method was called with the specific Game we're using
        verify(gameMonitorSpy).roundCompleted(game);
    }

    @Test
    void playerHitsAndGoesBustThenGameResultsSentToMonitor() {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.playerHitsAndGoesBust(), gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        // verify that the roundCompleted method was called with the specific Game we're using
        verify(gameMonitorSpy).roundCompleted(game);
    }

    @Test
    void playerHitsAndDoesNotBustThenNoGameResultsSentToMonitor() {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.playerHitsDoesNotBust(), gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        // verify that the roundCompleted method was NOT called
        verify(gameMonitorSpy, never()).roundCompleted(game);
    }

    @Test
    void playerDealtBlackjackThenGameResultsSentToMonitor() {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.playerDealtBlackjackDealerNotDealtBlackjack(), gameMonitorSpy);

        game.initialDeal();

        verify(gameMonitorSpy).roundCompleted(game);
    }


}