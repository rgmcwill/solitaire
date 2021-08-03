import java.util.Stack;

public class Board {
    private Stack<Card>[] mainBoard;
    private Stack<Card>[] aceBoard;
    private DeckOfCards deck;

    public Board(DeckOfCards deck) {
        this.deck = deck;
        mainBoard = new Stack[7];
        aceBoard = new Stack[4];

        int i = 0;
        int j = 0;
        while (j <= 7) {
            if (i == 7) {
                i = 0;
                j++;
            }
            if (mainBoard[i] == null) {
                mainBoard[i] = new Stack<>();
            }
            if (mainBoard[i].size() < i+1) {
                mainBoard[i].push(deck.deal());
            }
            i++;
        }

        System.out.println(mainBoard[6]);
    }
}
