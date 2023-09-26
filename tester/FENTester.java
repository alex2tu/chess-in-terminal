package tester;

import board.Board.Bishop;
import board.Board.King;
import board.Board.Knight;
import board.Board.Pawn;
import board.Board.Queen;
import board.Board.Rook;

public class FENTester {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        int rowr = 0;
        int columnc = 0;
        String[] FENarray = FEN.split(" ");
        String[] FENboard = FENarray[0].split("/");
        for(String s: FENboard) {
            columnc = 0;
            for(int i = 0; i < s.length(); i++) {
                char temp = s.charAt(i);
                if(temp == 'r') {
                    //pieces.add(new Rook(false, rowr, columnc));
                }
                else if(temp == 'n') {
                    //pieces.add(new Rook(false, rowr, columnc));
                }
                else if(temp == 'b') {
                    //pieces.add(new Bishop(false, rowr, columnc));
                }
                else if(temp == 'q') {
                    //pieces.add(new Queen(false, rowr, columnc));
                }
                else if(temp == 'k') {
                    //pieces.add(new King(false, rowr, columnc));
                }
                else if(temp == 'p') {
                    //pieces.add(new Pawn(false, rowr, columnc));
                }
                else if(temp == 'P') {
                    //pieces.add(new Pawn(true, rowr, columnc));
                }
                else if(temp == 'R') {
                    //pieces.add(new Rook(true, rowr, columnc));
                }
                else if(temp == 'N') {
                    //pieces.add(new Knight(true, rowr, columnc));
                }
                else if(temp == 'B') {
                    //pieces.add(new Bishop(true, rowr, columnc));
                }
                else if(temp == 'Q') {
                    //pieces.add(new Queen(true, rowr, columnc));
                }
                else if(temp == 'K') {
                    //pieces.add(new King(true, rowr, columnc));
                }
                else if(Character.isDigit(temp)) {
                    temp = (char) (temp - 48);
                    columnc += temp;
                }
                columnc++;
            }

            rowr++;
        }

        char turn = FENarray[1].charAt(0);
        if(turn == 'w') {
//			turnNumber == 0;
        }
        else {
//			turnNumber == 1;
        }

        for(int i = 0; i < FENarray[2].length(); i++) {
            char castling = FENarray[2].charAt(i);
            if(castling == 'K') {
                //K = true;
            }
            else if(castling == 'Q') {
                //Q = true;
            }
            else if(castling == 'k') {
                //k = true;
            }
            else if(castling == 'q') {
                //q = true;
            }
        }

        char passant = FENarray[3].charAt(0);
        if(passant == '-') {
            //enPassant = -1;
        }
        else {
            int passantC = (int) (passant - 97);
//			enPassant = passantC;
        }


        //  rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1



    }

    //if(temp == 'w') { turnNumber == 0;
    //else { turnNumber == 1;

}
