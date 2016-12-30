package uk.co.craigbass.blackjack;

import org.junit.Before;
import org.junit.Test;
import uk.co.craigbass.blackjack.Blackjack.Presenter;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.co.craigbass.blackjack.Card.Suit.*;
import static uk.co.craigbass.blackjack.Card.Value.*;

public class BlackjackTest implements Presenter {
    private GameState gameState;
    private Blackjack blackjack;
    private String cardsReceived = "";
    private Pack pack;

    private class RepeatingTwoCardPack implements Pack {
        private boolean first = true;

        @Override
        public Card next() {
            Card card = first ? new Card(Spade, Ace) : new Card(Diamond, Two);
            first = !first;
            return card;
        }
    }

    @Override
    public void presentHand(Card[] cards) {
        for (Card card : cards) cardsReceived += card.getValue() + " " + card.getSuit() + " ";
        cardsReceived = cardsReceived.trim();
    }

    @Before
    public void setUp() {
        gameState = new GameState();
        pack = new RepeatingTwoCardPack();
        blackjack = new Blackjack(gameState, this, pack);
    }

    @Test
    public void playersCardsAreStored() {
        blackjack.deal();
        List<Card> playerCards = gameState.getPlayerCards();

        Card firstCard = playerCards.get(0);
        Card secondCard = playerCards.get(1);

        assertEquals(2, playerCards.size());

        assertEquals(Spade, firstCard.getSuit());
        assertEquals(Ace, firstCard.getValue());

        assertEquals(Diamond, secondCard.getSuit());
        assertEquals(Two, secondCard.getValue());
    }

    @Test
    public void dealerReceivesTwoCards() {
        blackjack.deal();
        List<Card> dealerCards = gameState.getDealerCards();

        Card firstCard = dealerCards.get(0);
        Card secondCard = dealerCards.get(1);

        assertEquals(2, dealerCards.size());

        assertEquals(Spade, firstCard.getSuit());
        assertEquals(Ace, firstCard.getValue());

        assertEquals(Diamond, secondCard.getSuit());
        assertEquals(Two, secondCard.getValue());
    }

    @Test
    public void playersCardsArePresented() {
        blackjack.deal();
        assertEquals("Ace Spade Two Diamond", cardsReceived);
    }
}
