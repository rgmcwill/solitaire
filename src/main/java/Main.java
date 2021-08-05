
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        DeckOfCards deck = new DeckOfCards();

//        deck.shuffle(520);

//        deck.flip();

        Board board = new Board(deck);
        board.printBoard();

//        for (int i = 0; i < 2000; i++) {
//            System.out.println(board.deal());
//        }

        while (true) {
            Scanner input = new Scanner(System.in);
            String line = input.nextLine();
            String[] splitLine = line.split(" ");
            System.out.println(splitLine[0]);
            boolean isGood = false;
            if (splitLine[0].equals("A")) {
                isGood = board.moveToAceStacks(Integer.valueOf(splitLine[1]));
            } else {
                if (splitLine.length == 2) {
                    isGood = board.moveToColAtDepth(Integer.valueOf(splitLine[0]), Integer.valueOf(splitLine[1]), 1);
                } else if (splitLine.length == 3) {
                    isGood = board.moveToColAtDepth(Integer.valueOf(splitLine[0]), Integer.valueOf(splitLine[1]), Integer.valueOf(splitLine[2]));
                }
            }


            if (isGood) {
                System.out.println("!!");
            } else {
                System.out.println("You are Bad");
            }

            board.printBoard();
        }
    }
}
