public class DeckOfCards {
    public static final int NCARDS = 52;

    private final Card[] deckOfCards;         // Contains all 52 cards
    private int currentCard;            // deal THIS card in deck

    public DeckOfCards( )    // Constructor
    {
        deckOfCards = new Card[ NCARDS ];

        int i = 0;

//        System.out.println(Card.SPADE);
        for ( int suit = Card.DIAMOND; suit >= Card.SPADE; suit-- ) {
//            System.out.println(suit);
            for (int rank = 1; rank <= 13; rank++) {
                Card card = new Card(suit, rank);
//                System.out.println(card);
                deckOfCards[i++] = card;
            }
        }
        currentCard = 0;
    }

    public int getNcards() {
        return deckOfCards.length - currentCard;
    }

    /* ---------------------------------
      shuffle(n): shuffle the deck
      --------------------------------- */
    public void shuffle(int n) {
        int i, j, k;

        for ( k = 0; k < n; k++ )
        {
            i = (int) ( NCARDS * Math.random() );  // Pick 2 random cards
            j = (int) ( NCARDS * Math.random() );  // in the deck

   	     /* ---------------------------------
   		swap these randomly picked cards
   		--------------------------------- */
            Card tmp = deckOfCards[i];
            deckOfCards[i] = deckOfCards[j];
            deckOfCards[j] = tmp;
        }

        currentCard = 0;   // Reset current card to deal
    }
    /* -------------------------------------------
      deal(): deal deckOfCards[currentCard] out
      ------------------------------------------- */
    public Card deal() {
        if ( cardsLeft() )
        {
            return ( deckOfCards[ currentCard++ ] );
        }
        else
        {
            System.out.println("Out of cards error");
            return ( null );  // Error;
        }
    }

    public boolean cardsLeft() {
        return currentCard < NCARDS;
    }

    public void flip() {
        for (Card card : deckOfCards) {
            card.flip();
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int k;

        k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++)
                s.append(deckOfCards[k++]).append(" ");

            s.append("\n");
        }
        return (s.toString());
    }
}