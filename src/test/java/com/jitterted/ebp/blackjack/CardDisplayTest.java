package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CardDisplayTest {

    @Test
    void displayTenAsString() {
        Card tenOfClubs = new Card(Suit.CLUBS, Rank.TEN);

        assertThat(tenOfClubs.display())
                .isEqualTo("[30m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }
}
