package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CardDisplayTest {

    @Test
    void displayTenAsString() {
        Card tenOfClubs = new Card(Suit.CLUBS, Rank.TEN);

        assertThat(ConsoleCard.display(tenOfClubs))
                .isEqualTo("[30m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }

    @Test
    void displayNonTenAsString() {
        Card fiveOfSpades = new Card(Suit.SPADES, Rank.FIVE);

        assertThat(ConsoleCard.display(fiveOfSpades))
                .isEqualTo("[30m┌─────────┐[1B[11D│5        │[1B[11D│         │[1B[11D│    ♠    │[1B[11D│         │[1B[11D│        5│[1B[11D└─────────┘");
    }
}
