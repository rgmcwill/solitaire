import java.util.Stack;

public class Board {
    private ColOfCards[] mainBoard;
    private AceStacksOfCards[] aceBoard;
    private DeckOfCards deck;
    private Stack<Card> moveStack;

    public Board(DeckOfCards deck) {
        this.deck = deck;
        mainBoard = new ColOfCards[7];
        aceBoard = new AceStacksOfCards[4];
        moveStack = new Stack<>();

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

    public boolean moveToColAtDepth(int from, int to, int depth) {
        boolean canMove = false;

        for (int i = 0; i < depth; i++) {
            Card popped  = mainBoard[from].pop();
            moveStack.push(popped);
        }
        Card cardToMove = moveStack.peek();
        Card cardMoved = mainBoard[to].pushValidate(cardToMove);
        if (cardMoved != null) {
            canMove = true;
            moveStack.pop();
            while (moveStack.size() != 0) {
                mainBoard[to].push(moveStack.pop());
            }
        } else {
            mainBoard[from].peek().flip();
            for (int i = 0; i < depth; i++) {
                Card popped  = moveStack.pop();
                mainBoard[from].push(popped);
            }
            System.out.println("You DUMB MOTHERFUCKER..... You cant do that! :)");
        }

        if (mainBoard[from].size() != 0)
            mainBoard[from].peek();

        printBoard();
        return canMove;
    }

    public boolean moveToCol(int from, int to) {
        boolean canMove = false;

        Card cardToMove = mainBoard[from].peek();
        Card cardMoved = mainBoard[to].pushValidate(cardToMove);
        if (cardMoved != null) {
            canMove = true;
            mainBoard[from].pop();
        } else {
            System.out.println("You DUMB MOTHERFUCKER..... You cant do that! :)");
        }

        if (mainBoard[from].size() != 0)
            mainBoard[from].peek();

        printBoard();
        return canMove;
    }

    public void printBoard() {
        for (ColOfCards cards : mainBoard) {
            System.out.println(cards.toString());
        }
    }
}
