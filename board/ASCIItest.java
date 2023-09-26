package board;

import java.util.Arrays;

public class ASCIItest {

    public static char[][] Board = new char[8][8];

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        for(int i = 0; i < Board.length; i++) {
            Arrays.fill(Board[i], ' ');
        }
        String lines =  " _____ _____ _____ _____ _____ _____ _____ _____";
        String lines2 = "|     |     |     |     |     |     |     |     |";
        String lines4 = "|_____|_____|_____|_____|_____|_____|_____|_____|";
        for(int i = 0; i < 8; i++) {
            if (i == 0) {
                System.out.println(lines);
            }
            System.out.println(lines2);
            for(int j = 0; j < 8; j++) {
                if (j == 0) {
                    System.out.print("|  ");
                }
                System.out.print(Board[i][j]);
                System.out.print("  |  ");
            }
            System.out.println();
            System.out.println(lines4);
        }
    }

}
