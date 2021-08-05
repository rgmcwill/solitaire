import java.util.Stack;

public class ColOfCards extends Stack<Card> implements StackOfCards {
    public Card pushValidate(Card card) {
        if (super.size() != 0) {
            Card baseCard = super.peek();
            if (baseCard.getCardRank() - 1 == card.getCardRank() && baseCard.isRed() != card.isRed())
                return super.push(card);
        } else if (card.getCardRank() == 13) {
            return super.push(card);
        }
        return null;
    }
}
