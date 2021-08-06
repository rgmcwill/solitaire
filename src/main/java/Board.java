import java.util.Stack;

public class Board {
    private ColOfCards[] mainBoard;
    private AceStacksOfCards[] aceBoard;
    private Stack<Card> deck;
    private Stack<Card> dealtDeck;
    private Stack<Card> bDeck;
    private Stack<Card> fDeck;
    private Stack<Card> moveStack;
    private boolean o;

    private String drawMainBoard[][];

    public Board(DeckOfCards deck) {
        this.mainBoard = new ColOfCards[7];
        this.aceBoard = new AceStacksOfCards[4];
        this.bDeck = new Stack<>();
        this.fDeck = new Stack<>();
        this.moveStack = new Stack<>();
        this.o = false;
        this.deck = null;
        this.dealtDeck = null;
        this.drawMainBoard = new String[21][7];

        this.deck = this.fDeck;
        this.dealtDeck = this.bDeck;

        for (int i = 0; i < 4; i++) {
            aceBoard[i] = new AceStacksOfCards();
        }

        while (deck.cardsLeft()) {
            this.deck.push(deck.deal());
        }

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
                Card card = this.deck.pop();
                if (mainBoard[i].size() == i) {
                    card.flip();
                }
                    mainBoard[i].push(card);
            }
            i++;
        }
    }

    public Card deal() {
        Card dealt = null;
        if (bDeck.size() <= 0) {
            o = false;
            deck = fDeck;
            dealtDeck = bDeck;
        } else if (bDeck.size() <= 0) {
            o = true;
            deck = bDeck;
            dealtDeck = fDeck;
        }

        if (!o) {
            dealt = bDeck.push(fDeck.pop());
        } else {
            dealt = fDeck.push(bDeck.pop());
        }

        dealt.flip();
        return dealt;
    }

    public boolean moveFromDeal(int to) {
        Card cardToMove = deck.peek();
        Card movedCard = null;
        if (to < 7) {
            movedCard = mainBoard[to].pushValidate(cardToMove);
        } else if (to >= 7) {
            movedCard = aceBoard[cardToMove.getCardSuit()-1].pushValidate(cardToMove);
        }
        if (movedCard != null) {
            deck.pop();
            return true;
        } else {
            return false;
        }
    }

    public boolean moveToAceStacks(int from) {
        Card cardToMove = mainBoard[from].peek();
        Card movedCard = aceBoard[cardToMove.getCardSuit()-1].pushValidate(cardToMove);
        if (movedCard == null) {
            return false;
        }
        mainBoard[from].pop();
        mainBoard[from].peek().setFaceUp();
        return true;
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
        }

        if (mainBoard[from].size() != 0)
            mainBoard[from].peek();

        printBoard();
        return canMove;
    }

    public void printBoard() {
        String dealtCard;
        String spades;
        String clubs;
        String heats;
        String diamonds;
        if (!dealtDeck.empty())
            dealtCard = make3Length(dealtDeck.peek().toString());
        else
            dealtCard = "   ";
        if (!aceBoard[0].empty())
            spades = make3Length(aceBoard[0].peek().toString());
        else
            spades = "   ";
        if (!aceBoard[1].empty())
            clubs = make3Length(aceBoard[1].peek().toString());
        else
            clubs = "   ";
        if (!aceBoard[2].empty())
            heats = make3Length(aceBoard[2].peek().toString());
        else
            heats = "   ";
        if (!aceBoard[3].empty())
            diamonds = make3Length(aceBoard[3].peek().toString());
        else
            diamonds = "   ";

        System.out.println("┌---┐ ┌---┐       ┌---┐ ┌---┐ ┌---┐ ┌---┐\n" +
                           "|-*-| |"+dealtCard+"|       |"+spades+"| |"+clubs+"| |"+heats+"| |"+diamonds+"|\n" +
                           "└---┘ └---┘       └---┘ └---┘ └---┘ └---┘\n");

        for (int i = 0; i < mainBoard.length; i++) {
            ColOfCards col = mainBoard[i];
            int size = col.size();
            for (int j = 0; j < size; j++) {
                moveStack.push(col.pop());
            }

            for (int j = 0; j < size; j++) {
                String toAdd = col.push(moveStack.pop()).toString();

                if (toAdd == null) {
                    toAdd = "|---|";
                } else {
                    toAdd = "|"+make3Length(toAdd)+"|";
                }

                drawMainBoard[j][i] = toAdd;
            }
        }

        boolean returnLine = false;
        for (int i = 0;i < drawMainBoard.length;i++) {
            String[] subDrawMainBoard = drawMainBoard[i];
            for (int j = 0;j < subDrawMainBoard.length;j++) {
                String toDraw = drawMainBoard[i][j];
                if (toDraw != null) {
                    returnLine = true;
                    System.out.print(toDraw+" ");
                } else {
                    System.out.print("      ");
                }
            }
            if (returnLine)
                System.out.println("");
            returnLine = false;
        }
        System.out.println("");

//        printMainBoard();

        /*
        |---| ┌---┐       ┌---┐ ┌---┐ ┌---┐ ┌---┐
        |---| |10c|       |Ac | |As | |Ah | |Ad |
        |---| └---┘       └---┘ └---┘ └---┘ └---┘

        |Kh-| |---| |---| |---| |---| |---| |---|
              |3s-| |---| |---| |---| |---| |---|
                    |8c-| |---| |---| |---| |---|
                          |9d-| |---| |---| |---|
                                |Jc-| |---| |---|
                                      |5c-| |---|
                                            |4d-|
         */
    }

    private String make3Length(String string) {
        if (string.length() == 2) {
            string = string + " ";
        }
        return string;
    }

    public void printMainBoard() {
        for (ColOfCards cards : mainBoard) {
            System.out.println(cards.toString());
        }
    }

    public void printAceBoard() {
        for (AceStacksOfCards cards : aceBoard) {
            System.out.println(cards.toString());
        }
    }

    public void printTempStack() {
        System.out.println(moveStack.toString());
    }
}
