package uk.co.craigbass.blackjack.domain;

import uk.co.craigbass.playingcards.Card;
import uk.co.craigbass.playingcards.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uk.co.craigbass.playingcards.Card.Value.Ace;

public class BlackjackHand extends Hand {
    private static final int ADDITIONAL_VALUE_OF_HIGH_ACE = 10;

    public BlackjackHand(List<Card> cards) {
        super(cards);
    }

    public static BlackjackHand handFromCards(List<Card> cards) {
        return new BlackjackHand(cards.stream().collect(Collectors.toList()));
    }

    public Card firstCard() {
        return getCardsStream().findFirst().get();
    }

    public int getValue() {
        int value = getCardsStream()
                .mapToInt(this::getBlackjackFaceValue)
                .sum();

        if (hasAnAce() && canHaveHighAce(value)) value += ADDITIONAL_VALUE_OF_HIGH_ACE;
        return value;
    }

    private int getBlackjackFaceValue(Card card) {
        switch(card.getValue()) {
            case King:
            case Queen:
            case Jack:
                return 10;
            default:
                return card.getFaceValue();
        }
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
