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
            if (splitLine.length == 2) {
                board.moveToColAtDepth(Integer.valueOf(splitLine[0]), Integer.valueOf(splitLine[1]), 1);
            }
            else if (splitLine.length == 3) {
                board.moveToColAtDepth(Integer.valueOf(splitLine[0]), Integer.valueOf(splitLine[1]), Integer.valueOf(splitLine[2]));
            }
        }
    }
}
