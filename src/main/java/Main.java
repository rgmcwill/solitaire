import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        DeckOfCards deck = new DeckOfCards();

        deck.shuffle(520);

        Board board = new Board(deck);
        board.printBoard();

        while (true) {
            Scanner input = new Scanner(System.in);
            String line = input.nextLine();
            String[] splitLine = line.split(" ");
            board.moveToCol(Integer.valueOf(splitLine[1]),Integer.valueOf(splitLine[0]));
        }
    }
}
