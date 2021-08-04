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
        ColOfCards fromCol = mainBoard[from];
        ColOfCards toCol = mainBoard[to];

        //Checks if can even delve that deep into the colstack
        if (fromCol.size() < depth)
            return false;

        //Checks if it can move the cards, as in, if they are flipped over and moves them to the move stack
        for (int i = 0; i < depth; i++) {
            Card poppedCard = fromCol.pop();
            if (!poppedCard.isFaceDown()) {
                moveStack.push(poppedCard);
            } else {
                fromCol.push(poppedCard);
            }
        }

        Card movedPoppedCard = moveStack.pop();
        Card validatedCard = toCol.pushValidate(movedPoppedCard);

        if (validatedCard == null) {
            fromCol.push(movedPoppedCard);
            while (!moveStack.empty()) {
                fromCol.push(moveStack.pop());
            }
            printBoard();
            System.out.println("----------------------------------------------------------------------------------");
            return false;
        } else {
            while (!moveStack.empty()) {
                toCol.push(moveStack.pop());
            }
            if (!fromCol.empty())
                fromCol.peek().setFaceUp();
            printBoard();
            System.out.println("----------------------------------------------------------------------------------");
            return true;
        }
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

    public void printTempStack() {
        System.out.println(moveStack.toString());
    }
}
