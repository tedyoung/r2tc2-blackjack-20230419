package com.jitterted.ebp.blackjack;

public class ConsoleHand {

    // convert (transform) HAND -> String representation for I/O
    static String displayFaceUpCard(Hand hand) {
        return ConsoleCard.display(hand.faceUpCard());
    }
}
