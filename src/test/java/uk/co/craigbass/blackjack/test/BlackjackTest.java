package uk.co.craigbass.blackjack.test;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.craigbass.blackjack.Blackjack;
import uk.co.craigbass.blackjack.Blackjack.Presenter;
import uk.co.craigbass.blackjack.InMemoryTable;
import uk.co.craigbass.playingcards.Card;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.co.craigbass.playingcards.Card.Suit.*;
import static uk.co.craigbass.playingcards.Card.Value.*;

@RunWith(HierarchicalContextRunner.class)
public class BlackjackTest implements Presenter {
    private InMemoryTable table;
    private Blackjack blackjack;
    private String cardsReceived = "";
    private ConfigurableLoopingPack pack;
    private int handValue;
    private Boolean playerHasWon = null;

    @Override
    public void presentHand(PresentableHand hand) {
        handValue = hand.value;
        for (Card card : hand.cards) cardsReceived += card.getValue() + " " + card.getSuit() + " ";
        cardsReceived = cardsReceived.trim();
    }

    @Override
    public void gameOver(Ending ending) {
        playerHasWon = ending.equals(Ending.PLAYER_WINS);
    }

    @Before
    public void setUp() {
        table = new InMemoryTable();
        pack = new ConfigurableLoopingPack();
        blackjack = new Blackjack(table, this, pack);
    }

    @Test
    public void GivenTwoKings_ThenHandValueIs20() {
        pack.add(new Card(Spade, King));
        blackjack.deal();
        assertEquals(20, handValue);
    }

    @Test
    public void GivenAJackAndAQueen_ThenHandValueIs20() {
        pack.add(new Card(Spade, Jack));
        pack.add(new Card(Spade, Queen));
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
            Card[] playerCards = table.getPlayersHand().getCards();

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
            List<Card> dealerCards = table.getDealerCards();

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


    public class GivenPlayerWinsNaturally {
        private void winningHand() {
            pack.add(new Card(Diamond, Ace));
            pack.add(new Card(Heart, Jack));
        }

        @Before
        public void setUp() {
            winningHand();
        }

        public class AndDealerGets17 {
            private void handOf17() {
                pack.add(new Card(Spade, Two));
                pack.add(new Card(Diamond, Three));
                pack.add(new Card(Diamond, Five));
                pack.add(new Card(Diamond, Seven));
            }

            @Before
            public void setUp() {
                handOf17();
            }

            @Test
            public void ThenThePlayerHasWon() {
                blackjack.deal();
                blackjack.stick();

                assertTrue(playerHasWon);
            }
        }
    }
}
