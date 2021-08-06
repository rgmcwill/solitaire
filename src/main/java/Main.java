
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


        /*
        Commands
        If 2 numbers given, separated by a space then it will try to move from that column to another.
        If 3 numbers given, separated by a spaces then it will try to move from that column to another and move that many cards down
        If A and then a number, separated by a space then it will try to move the card from the given column to the ace stacks
        If B and then a number, separated by a space then it will try to move the card form the draw stack to the given column,
            if the number is bigger then or equal to 7 it will try to move it to the ace stack
         */
        while (true) {
            Scanner input = new Scanner(System.in);
            String line = input.nextLine();
            String[] splitLine = line.split(" ");
            System.out.println(splitLine[0]);
            boolean isGood = false;
            if (splitLine[0].equals("A")) {
                isGood = board.moveToAceStacks(Integer.valueOf(splitLine[1]));
            } else if (splitLine[0].equals("D")) {
                board.deal();
            } else if (splitLine[0].equals("B")) {
                isGood = board.moveFromDeal(Integer.valueOf(splitLine[1]));
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
