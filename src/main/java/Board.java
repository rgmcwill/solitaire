import java.util.Stack;

public class Board {
    private ColOfCards[] mainBoard;
    private AceStacksOfCards[] aceBoard;
    private DeckOfCards deck;

    public Board(DeckOfCards deck) {
        this.deck = deck;
        mainBoard = new ColOfCards[7];
        aceBoard = new AceStacksOfCards[4];

        int i = 0;
        int j = 0;
        while (j <= 7) {
            if (i == 7) {
                i = 0;
                j++;
            }
            if (mainBoard[i] == null) {
                mainBoard[i] = new ColOfCards();
            }
            if (mainBoard[i].size() < i+1) {
                Card card = deck.deal();
                if (mainBoard[i].size() == i) {
                    card.flip();
                }
                    mainBoard[i].push(card);
            }
            i++;
        }
    }

    public boolean moveToCol(int to, int from) {
        boolean didMove = false;

        Card cardToMove = mainBoard[from].pop();
        mainBoard[to].push(cardToMove);

        mainBoard[from].peek();
        printBoard();
        return didMove;
    }

    public void printBoard() {
        for (StackOfCards cards : mainBoard) {
            System.out.println(cards.toString());
        }
    }
}
