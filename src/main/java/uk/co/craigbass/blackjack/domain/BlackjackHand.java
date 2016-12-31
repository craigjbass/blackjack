package uk.co.craigbass.blackjack.domain;

import uk.co.craigbass.playingcards.Card;
import uk.co.craigbass.playingcards.Hand;

import java.util.List;
import java.util.stream.Stream;

import static uk.co.craigbass.playingcards.Card.Value.Ace;

public class BlackjackHand extends Hand {
    private static final int ADDITIONAL_VALUE_OF_HIGH_ACE = 10;

    public BlackjackHand(List<Card> cards) {
        super(cards);
    }

    public int getValue() {
        int value = getCardsStream()
                .mapToInt(Card::getFaceValue)
                .sum();

        if (hasAnAce() && canHaveHighAce(value)) value += ADDITIONAL_VALUE_OF_HIGH_ACE;
        return value;
    }

    private boolean canHaveHighAce(int lowAceHandValue) {
        int handValueWithHighAce = lowAceHandValue + 10;
        return isNotBust(handValueWithHighAce);
    }

    private boolean isNotBust(int handTotal) {
        return handTotal < 22;
    }

    private boolean hasAnAce() {
        long numberOfAces = getCardsStream()
                .filter(this::isAnAce)
                .count();

        return numberOfAces > 0;
    }

    private boolean isAnAce(Card card) {
        return card.isAn(Ace);
    }

    private Stream<Card> getCardsStream() {
        return cards.stream();
    }
}
