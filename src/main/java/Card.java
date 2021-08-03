public class Card
{
    public static final int SPADE   = 4;
    public static final int HEART   = 3;
    public static final int CLUB    = 2;
    public static final int DIAMOND = 1;

    private static final String[] Suit = { "*", "d", "c", "h", "s"};
    private static final String[] Rank = { "*", "*", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private byte cardSuit;
    private byte cardRank;
    private boolean faceDown;

    public Card( int suit, int rank ) {
        if ( rank == 1 )
            cardRank = 14;     // Give Ace the rank 14
        else
            cardRank = (byte) rank;

        cardSuit = (byte) suit;
        faceDown = true;
    }

//    public int suit() {
//        return ( cardSuit );         // This is a shorthand for:
//        //   this.cardSuit
//    }
//
//    public String suitStr() {
//        return( Suit[ cardSuit ] );  // This is a shorthand for:
//        //   this.Suit[ this.cardSuit ]
//    }
//
//    public int rank() {
//        return ( cardRank );
//    }
//
//    public String rankStr() {
//        return ( Rank[ cardRank ] );
//    }

    public void flip() {
        if (faceDown)
            faceDown = false;
        else
            faceDown = true;
    }

    public void setFaceDown() {
        faceDown = false;
    }

    public void setFaceUp() {
        faceDown = false;
    }

    public boolean equals( Card x ) {
        if ( this.cardSuit == x.cardSuit && this.cardRank == x.cardRank )
            return ( true );
        else
            return ( false );
    }

    public String toString() {
        if (faceDown == false)
            return ( Rank[ cardRank ] + Suit[ cardSuit ] );
        else
            return "Unknown";
    }
}