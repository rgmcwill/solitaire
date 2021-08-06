import java.util.Arrays;
import java.util.Stack;

public class Board {
    private final ColOfCards[] mainBoard;
    private final AceStacksOfCards[] aceBoard;
    private final Stack<Card> deck;
    private final Stack<Card> dealtDeck;
    private final Stack<Card> moveStack;

    private final String[][] drawMainBoard;

    public Board(DeckOfCards deck) {
        this.mainBoard = new ColOfCards[7];
        this.aceBoard = new AceStacksOfCards[4];
        this.moveStack = new Stack<>();
        this.deck = new Stack<>();
        this.dealtDeck = new Stack<>();
        this.drawMainBoard = new String[21][7];


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

    public boolean deal() {
        if (deck.empty()) {
            if (dealtDeck.empty()) {
                return false;
            }
            int dealtDeckSize = dealtDeck.size();
            for (int i = 0; i < dealtDeckSize; i++) {
                Card cardToPutBack = dealtDeck.pop();
                cardToPutBack.setFaceDown();
                deck.push(cardToPutBack);
            }
        } else {
            dealtDeck.push(deck.pop());
            dealtDeck.peek().setFaceUp();
        }
        return true;
    }

    public boolean moveFromDeal(int to) {
        Card cardToMove = dealtDeck.peek();
        Card movedCard;
        if (to < 7) {
            movedCard = mainBoard[to].pushValidate(cardToMove);
        } else {
            movedCard = aceBoard[cardToMove.getCardSuit()-1].pushValidate(cardToMove);
        }
        if (movedCard != null) {
            dealtDeck.pop();
            if (!(dealtDeck.size() <= 0)) {
                dealtDeck.peek().setFaceUp();
            }
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
        if (!(mainBoard[from].size() <= 0)) {
            mainBoard[from].peek().setFaceUp();
        }
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
            return false;
        } else {
            while (!moveStack.empty()) {
                toCol.push(moveStack.pop());
            }
            if (!fromCol.empty())
                fromCol.peek().setFaceUp();
            return true;
        }
    }

    public void printBoard() {
        String dealtCard;
        String unDealtCard;
        String spades;
        String clubs;
        String heats;
        String diamonds;
        if (!dealtDeck.empty())
            if (dealtDeck.peek().toString() == null)
                dealtCard = "-*-";
            else
                dealtCard = make3Length(dealtDeck.peek().toString());
        else
            dealtCard = "   ";
        if (!deck.empty())
            if (deck.peek().toString() == null)
                unDealtCard = "-*-";
            else
                unDealtCard = make3Length(deck.peek().toString());
        else
            unDealtCard = "   ";
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
                           "|"+unDealtCard+"| |"+dealtCard+"|       |"+spades+"| |"+clubs+"| |"+heats+"| |"+diamonds+"|\n" +
                           "└---┘ └---┘       └---┘ └---┘ └---┘ └---┘\n");

        for (String[] strings : drawMainBoard) {
            Arrays.fill(strings, null);
        }

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
        for (String[] subDrawMainBoard : drawMainBoard) {
            for (String toDraw : subDrawMainBoard) {
                if (toDraw != null) {
                    returnLine = true;
                    System.out.print(toDraw + " ");
                } else {
                    System.out.print("      ");
                }
            }
            if (returnLine)
                System.out.println();
            returnLine = false;
        }
        System.out.println();
    }

    private String make3Length(String string) {
        if (string.length() == 2) {
            string = string + " ";
        }
        return string;
    }
}
