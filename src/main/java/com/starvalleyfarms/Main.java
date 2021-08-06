package com.starvalleyfarms;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();

        deck.shuffle(520);

        Board board = new Board(deck);
        board.printBoard();


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
            switch (splitLine[0]) {
                case "A":
                    if (splitLine.length == 2) {
                        isGood = board.moveToAceStacks(Integer.parseInt(splitLine[1]));
                    }
                    break;
                case "D":
                    isGood = board.deal();
                    break;
                case "B":
                    if (splitLine.length == 2) {
                        isGood = board.moveFromDeal(Integer.parseInt(splitLine[1]));
                    }
                    break;
                default:
                    if (splitLine.length == 2) {
                        isGood = board.moveToColAtDepth(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), 1);
                    } else if (splitLine.length == 3) {
                        isGood = board.moveToColAtDepth(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]));
                    }
                    break;
            }


            if (isGood) {
                board.printBoard();
            } else {
                System.out.println("Bad Move");
            }
        }
    }
}
