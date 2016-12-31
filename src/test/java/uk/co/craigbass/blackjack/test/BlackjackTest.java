package uk.co.craigbass.blackjack.test;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.craigbass.blackjack.Blackjack;
import uk.co.craigbass.blackjack.Blackjack.Presenter;
import uk.co.craigbass.playingcards.Card;
import uk.co.craigbass.blackjack.InMemoryTable;
import uk.co.craigbass.blackjack.Pack;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static uk.co.craigbass.playingcards.Card.Suit.*;
import static uk.co.craigbass.playingcards.Card.Value.*;

@RunWith(HierarchicalContextRunner.class)
public class BlackjackTest implements Presenter {
    private InMemoryTable inMemoryGameState;
    private Blackjack blackjack;
    private String cardsReceived = "";
    private ConfigurableLoopingPack pack;
    private int handValue;

    private class ConfigurableLoopingPack implements Pack {
        private Queue<Card> cards = new LinkedList<>();

        public void add(Card card) {
            cards.add(card);
        }

        @Override
        public Card next() {
            Card card = cards.remove();
            cards.add(card);
            return card;
        }
    }

    @Override
    public void presentHand(PresentableHand hand) {
        handValue = hand.value;
        for (Card card : hand.cards) cardsReceived += card.getValue() + " " + card.getSuit() + " ";
        cardsReceived = cardsReceived.trim();
    }

    @Before
    public void setUp() {
        inMemoryGameState = new InMemoryTable();
        pack = new ConfigurableLoopingPack();



        blackjack = new Blackjack(inMemoryGameState, this, pack);
    }

    public class WhenPackRepeatsCardsAceTwoFourQueen {
        @Before
        public void setUp() {
            pack.add(new Card(Spade, Ace));
            pack.add(new Card(Diamond, Two));
            pack.add(new Card(Heart, Four));
            pack.add(new Card(Club, Queen));
        }
        @Test
        public void playersCardsAreStored() {
            blackjack.deal();
            Card[] playerCards = inMemoryGameState.getPlayersHand().getCards();

            Card firstCard = playerCards[0];
            Card secondCard = playerCards[1];

            assertEquals(2, playerCards.length);

            assertEquals(Spade, firstCard.getSuit());
            assertEquals(Ace, firstCard.getValue());

            assertEquals(Diamond, secondCard.getSuit());
            assertEquals(Two, secondCard.getValue());
        }

        @Test
        public void dealerReceivesTwoCards() {
            blackjack.deal();
            List<Card> dealerCards = inMemoryGameState.getDealerCards();

            Card firstCard = dealerCards.get(0);
            Card secondCard = dealerCards.get(1);

            assertEquals(2, dealerCards.size());

            assertEquals(Heart, firstCard.getSuit());
            assertEquals(Four, firstCard.getValue());

            assertEquals(Club, secondCard.getSuit());
            assertEquals(Queen, secondCard.getValue());
        }

        @Test
        public void playersHandIsPresented() {
            blackjack.deal();
            assertEquals("Ace Spade Two Diamond", cardsReceived);
            assertEquals(13, handValue);
        }
    }

    @Test
    public void GivenTwoTens_ThenHandValueIs20() {
        pack.add(new Card(Spade, Ten));
        blackjack.deal();
        assertEquals(20, handValue);
    }

    @Test
    public void GivenTwoAces_ThenHandValueIs12() {
        pack.add(new Card(Spade, Ace));
        pack.add(new Card(Spade, Ace));
        blackjack.deal();
        assertEquals(12, handValue);
    }
}
