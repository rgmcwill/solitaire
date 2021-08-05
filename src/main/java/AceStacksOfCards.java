import java.util.Stack;

public class AceStacksOfCards extends Stack<Card> implements StackOfCards {
    public Card pushValidate(Card card) {
        if (super.size() != 0) {
            Card baseCard = super.peek();
            if (baseCard.getCardRank() + 1 == card.getCardRank() && baseCard.getCardSuit() == card.getCardSuit())
                return super.push(card);
        } else if (card.getCardRank() == 1) {
            return super.push(card);
        }
        return null;
    }
}
