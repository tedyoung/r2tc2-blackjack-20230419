package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;
import org.junit.jupiter.api.Test;

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
}