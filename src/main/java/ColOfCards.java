import java.util.Stack;

public class ColOfCards extends Stack<Card> {
    @Override
    public Card pop() {
        return super.pop();
    }

    @Override
    public Card push(Card card) {
        return super.push(card);
    }

    @Override
    public Card peek() {
        Card card = super.peek();
        card.setFaceUp();
        return card;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
