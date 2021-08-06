public class Card
{
    public static final int DIAMOND   = 4;
    public static final int HEART   = 3;
    public static final int CLUB    = 2;
    public static final int SPADE = 1;

    private static final String[] Suit = { "*", "s", "c", "h", "d"};
    private static final String[] Rank = { "*", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private byte cardSuit;
    private byte cardRank;
    private boolean isRed; // red is true; black is false
    private boolean faceDown;

    public Card( int suit, int rank ) {
        cardRank = (byte) rank;

        if (suit > 0 && suit < 3) {
            isRed = false;
        } else if (suit > 2) {
            isRed = true;
        }

        cardSuit = (byte) suit;
        faceDown = true;
    }

    public byte getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(byte cardSuit) {
        this.cardSuit = cardSuit;
    }

    public byte getCardRank() {
        return cardRank;
    }

    public void setCardRank(byte cardRank) {
        this.cardRank = cardRank;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    public void flip() {
        faceDown = !faceDown;
    }

    public void setFaceDown() {
        faceDown = true;
    }

    public void setFaceUp() {
        faceDown = false;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public boolean equals( Card x ) {
        return this.cardSuit == x.cardSuit && this.cardRank == x.cardRank;
    }

    public String toString() {
        if (!faceDown)
            return ( Rank[ cardRank ] + Suit[ cardSuit ] );
        else
            return null;
    }
}