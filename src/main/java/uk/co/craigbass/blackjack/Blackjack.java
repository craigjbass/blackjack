package uk.co.craigbass.blackjack;

import uk.co.craigbass.playingcards.Card;
import uk.co.craigbass.blackjack.domain.BlackjackHand;

public class Blackjack {
    private Table table;
    private Presenter presenter;
    private Pack pack;

    public Blackjack(Table table, Presenter presenter, Pack pack) {
        this.table = table;
        this.presenter = presenter;
        this.pack = pack;
    }

    public void deal() {
        table.givePlayerCard(pack.next());
        table.givePlayerCard(pack.next());

        table.giveDealerCard(pack.next());
        table.giveDealerCard(pack.next());

        presentHand();
    }

    private void presentHand() {
        BlackjackHand blackjackHand = table.getPlayersHand();

        Card[] cards = blackjackHand.getCards();

        Presenter.PresentableHand presentableHand = new Presenter.PresentableHand();
        presentableHand.cards = cards;
        presentableHand.value = blackjackHand.getValue();

        presenter.presentHand(presentableHand);
    }

    public interface Table {
        void givePlayerCard(Card card);
        void giveDealerCard(Card card);
        BlackjackHand getPlayersHand();
    }
    public interface Presenter {
        void presentHand(PresentableHand cards);
        class PresentableHand {
            public int value;
            public Card[] cards;
        }
    }
}
