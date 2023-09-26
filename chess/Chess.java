package chess;

import java.util.ArrayList;
import java.util.Scanner;

import board.Board;
import board.Board.Piece;
import board.Board.Move;
import board.Board.Pawn;

public class Chess {

    public static Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    //Standard FEN: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    //Stalemate FEN: "8/8/8/8/3q4/4k3/8/4K3 b - - 0 1"
    //Castling Test FEN: "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1"
    //Castling Test Check FEN: "rnbqkbn1/pppppppp/5r2/8/8/8/PPPPP2P/RNBQK2R w KQq - 0 1"
    //Castling King King Test FEN: "rnbq1bnr/pppppppp/8/8/8/8/PPPPP1kP/RNBQK2R w KQ - 0 1"
    //enPassant Test FEN: "rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d 0 1"
    //indexing Test FEN: "r1bqkbnr/p1pp1ppp/2n5/1p2p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 0 4"
    //Pawn Promotion Test FEN: "rnbqk3/pppp2P1/8/8/8/8/PPPPPP1P/RNBQKBNR w KQq - 0 1"
    //Pawn Promotion Test 2 FEN: "rnbqk3/pppp4/6P1/8/8/8/PPPPPP1P/RNBQKBNR w KQq - 0 1"
    //Pawn Promotion Checkmate Test FEN: "rnbqk3/ppppp1P1/8/8/8/8/PPPPPP1P/RNBQKBNR w KQq - 0 1"
    public static Scanner scanner = new Scanner (System.in);

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        boolean winCheck = false;
        while(!winCheck) {
            board.boardPrinter();
            if (board.returnTurn()) {
                System.out.print("Player 1, enter the starting square. ");
            }
            else {
                System.out.print("Player 2, enter the starting square. ");
            }
            String str = scanner.nextLine().trim();
            Piece piece;
            ArrayList<Move> PLM;
            while(true) {
                while(true) {
                    if((str.length() == 2) && Character.isLetter(str.charAt(0)) && Character.isDigit(str.charAt(1))) {
                        break;
                    }
                    str = userReInput();
                }
                piece = board.getUserPiece(str);
                while(piece == null) {
                    str = userReInput();
                    if((str.length() == 2) && Character.isLetter(str.charAt(0)) && Character.isDigit(str.charAt(1))) {
                        piece = board.getUserPiece(str);
                    }
                    //reenter a string
                }
                PLM = piece.getPseudoLegalMove();
                board.filterMoves(PLM);
                if(!(PLM.size() == 0)) {
                    break;
                }
                str = userReInput();
            }
            board.printPseudoLegalMove(PLM);
            System.out.println();
            System.out.print("Choose a move! ");
            String str2 = scanner.nextLine().trim(); //asks user what move out of the list to make
            boolean pieceCheck = false;
            while(true) {
                for(Move m: PLM) { //loops through list and matches
                    if(!(str2.length() == 2) || !Character.isLetter(str2.charAt(0)) || !Character.isDigit(str2.charAt(1))) {
                        break;
                    }
                    if(m.toString().equals(str2)) {
                        board.makeMove(m);
                        pieceCheck = true;
                        break;
                    }
                }
                if(pieceCheck) {
                    break;
                }
                str2 = userReInput();
            }
            if(board.getPawnPromote() != null) {
                Piece paw = board.getPawnPromote();
                board.removeAdd(paw, board.pawnPromotionPiece(paw.getRow(), paw.getColumn()));
            }
            board.turnUpdater();
            if(board.mateCheck()) {
                board.boardPrinter();
                break;
            }
        }
    }

    public static String userReInput() {
        System.out.println("Invalid square, please try again: "); //asks user for reinput
        String input = scanner.nextLine().trim();
        return input;
    }

}
