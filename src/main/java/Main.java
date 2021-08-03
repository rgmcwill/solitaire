public class Main {
    public static void main(String args[]) {
        DeckOfCards deck = new DeckOfCards();

        deck.shuffle(520);

        deck.flip();

        Board board = new Board(deck);

//        for (int i = 0; i < 52; i++) {
//
//            System.out.println("Dealing Card: " + deck.deal());
//            System.out.println("Number or Cards left in deck: " + deck.getNcards());
//        }
    }
}
