package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import board.Board.Pawn;
import board.Board.Piece;

import java.lang.Math;

public class Board {

    private ArrayList <Piece> pieces = new ArrayList<Piece>();

    private int turnNumber = 0;

    private int enPassant = -1;

    private int enPassantStore = -1;

    private boolean K = false;

    private boolean Q = false;

    private boolean k = false;

    private boolean q = false;

    private boolean KStore;

    private boolean QStore;

    private boolean kstore;

    private boolean qstore;

    public Board(String FEN) {
        int rowr = 0;
        int columnc = 0;
        String[] FENarray = FEN.split(" ");
        String[] FENboard = FENarray[0].split("/");
        for(String s: FENboard) {
            columnc = 0;
            for(int i = 0; i < s.length(); i++) {
                char temp = s.charAt(i);
                if(temp == 'r') {
                    pieces.add(new Rook(false, rowr, columnc));
                }
                else if(temp == 'n') {
                    pieces.add(new Knight(false, rowr, columnc));
                }
                else if(temp == 'b') {
                    pieces.add(new Bishop(false, rowr, columnc));
                }
                else if(temp == 'q') {
                    pieces.add(new Queen(false, rowr, columnc));
                }
                else if(temp == 'k') {
                    pieces.add(new King(false, rowr, columnc));
                }
                else if(temp == 'p') {
                    pieces.add(new Pawn(false, rowr, columnc));
                }
                else if(temp == 'P') {
                    pieces.add(new Pawn(true, rowr, columnc));
                }
                else if(temp == 'R') {
                    pieces.add(new Rook(true, rowr, columnc));
                }
                else if(temp == 'N') {
                    pieces.add(new Knight(true, rowr, columnc));
                }
                else if(temp == 'B') {
                    pieces.add(new Bishop(true, rowr, columnc));
                }
                else if(temp == 'Q') {
                    pieces.add(new Queen(true, rowr, columnc));
                }
                else if(temp == 'K') {
                    pieces.add(new King(true, rowr, columnc));
                }
                else if(Character.isDigit(temp)) {
                    temp = (char) (temp - 48);
                    columnc += temp - 1;
                }
                columnc++;
            }

            rowr++;
        }

        char turn = FENarray[1].charAt(0);
        if(turn == 'w') {
            turnNumber = 0;
        }
        else {
            turnNumber = 1;
        }

        for(int i = 0; i < FENarray[2].length(); i++) {
            char castling = FENarray[2].charAt(i);
            if(castling == 'K') {
                K = true;
            }
            else if(castling == 'Q') {
                Q = true;
            }
            else if(castling == 'k') {
                k = true;
            }
            else if(castling == 'q') {
                q = true;
            }
        }

        char passant = FENarray[3].charAt(0);
        if(passant == '-') {
            enPassant = -1;
        }
        else {
            int passantC = (int) (passant - 97);
            enPassant = passantC;
        }

        //rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    }

    public class Move {

        private int startingRow;
        private int startingColumn;
        private int endRow;
        private int endColumn;

        public Move(int startR, int startC, int r, int c) {
            startingRow = startR;
            startingColumn = startC;
            endRow = r;
            endColumn = c;
        }

        public int getStartRow() {
            return startingRow;
        }

        public int getStartCol() {
            return startingColumn;
        }

        public int getRow() {
            return endRow;
        }
        public int getColumn() {
            return endColumn;
        }

        public String toString() {
            char letter = (char) endColumn;
            letter = (char) (letter + 97);
            return letter + "" + (8 - endRow);
        }

        public String startSquare() {
            char letter = (char) startingColumn;
            letter = (char) (letter + 97);
            return letter + "" + (8 - startingRow);
        }

    }


    public abstract class Piece {

        //white: color == true, black: color == false

        protected boolean color;
        protected int row;
        protected int column;

        public Piece(boolean c, int x, int y) {

        }
        public abstract ArrayList<Move> getPseudoLegalMove();

        public abstract char getPiece();

        public abstract int getRow();

        public abstract int getColumn();

        public void move(int endR, int endC) {
            row = endR;
            column = endC;
        }

        public abstract boolean getColor();
    }

    public void slidingAttack(int rowChange, int columnChange, int currentRow, int currentColumn, boolean color, ArrayList<Move> d) {
        int startRow = currentRow;
        int startColumn = currentColumn;
        while(true) {
            currentRow += rowChange;
            currentColumn += columnChange;
            if(outOfBoundsCheck(currentRow, currentColumn)) {
                Piece temp = getPieceAtLocation(currentRow, currentColumn);
                if(temp != null) {
                    if(temp.getColor() != color) {
                        d.add(new Move(startRow, startColumn, currentRow, currentColumn));
                    }
                    break;
                }
                d.add(new Move(startRow, startColumn, currentRow, currentColumn));
            }
            else {
                break;
            }
        }
    }

    public void diagonalAttack(ArrayList<Move> d, int row, int column, boolean color) {
        slidingAttack(1, 1, row, column, color, d);
        slidingAttack(-1, 1, row, column, color, d);
        slidingAttack(-1, -1, row, column, color, d);
        slidingAttack(1, -1, row, column, color, d);
    }
    public void rookAttack(ArrayList<Move> d, int row, int column, boolean color) {
        slidingAttack(1, 0, row, column, color, d);
        slidingAttack(0, 1, row, column, color, d);
        slidingAttack(-1, 0, row, column, color, d);
        slidingAttack(0, -1, row, column, color, d);
    }

    public void kingKnightAttack(int rowChange, int columnChange, int currentRow, int currentColumn, boolean color, ArrayList<Move> d) {
        int startRow = currentRow;
        int startColumn = currentColumn;
        currentRow += rowChange;
        currentColumn += columnChange;
        if(outOfBoundsCheck(currentRow, currentColumn)) {
            Piece temp = getPieceAtLocation(currentRow, currentColumn);
            if(temp != null) {
                if(temp.getColor() != color) {
                    d.add(new Move(startRow, startColumn, currentRow, currentColumn));
                }
            }
            else {
                d.add(new Move(startRow, startColumn, currentRow, currentColumn));
            }

        }
    }

    public abstract class castlingPiece extends Piece {

        public castlingPiece(boolean c, int x, int y) {
            super(c, x, y);
        }
    }

    public class Rook extends castlingPiece {

        public Rook(boolean c, int x, int y) {
            super(c, x, y);
            color = c;
            row = x;
            column = y;
        }

        public ArrayList<Move> getPseudoLegalMove() {
            ArrayList<Move> pseudoLegalMoves = new ArrayList<Move>();
            pseudoLegalMoves.clear();
            rookAttack(pseudoLegalMoves, row, column, color);

//			filterMoves(pseudoLegalMoves);

            return pseudoLegalMoves;
        }

        public char getPiece() {
            if(color) {
                return 'R';
            }
            return 'r';
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public boolean getColor() {
            return color;
        }

    }

    public class Queen extends Piece {

        public Queen(boolean c, int x, int y) {
            super(c, x, y);
            color = c;
            row = x;
            column = y;
        }

        public ArrayList<Move> getPseudoLegalMove() {
            ArrayList<Move> pseudoLegalMoves = new ArrayList<Move>();
            rookAttack(pseudoLegalMoves, row, column, color);
            diagonalAttack(pseudoLegalMoves, row, column, color);

//			filterMoves(pseudoLegalMoves);

            return pseudoLegalMoves;
        }

        public char getPiece() {
            if(color) {
                return 'Q';
            }
            return 'q';
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public boolean getColor() {
            return color;
        }
    }

    public class Pawn extends Piece {
        public Pawn(boolean c, int x, int y) {
            super(c, x, y);
            color = c;
            row = x;
            column = y;
        }

        public ArrayList<Move> getPseudoLegalMove() {
            ArrayList<Move> pseudoLegalMoves = new ArrayList<Move>();
            pawnMoves(pseudoLegalMoves);

//			filterMoves(pseudoLegalMoves);

            return pseudoLegalMoves;
        }

        public void pawnMoves(ArrayList<Move> d) {
            int currentRow = row;
            int currentColumn = column;
            currentRow += turnNumberConversion();
            //pawn push code
            Piece push = getPieceAtLocation(currentRow, currentColumn);
            if(push == null) {
                d.add(new Move(row, column, currentRow, currentColumn));
            }
            for(int i = column - 1; i <= column + 1; i+=2) {
                //pawn capture code
                if(i != -1 && i != 8) {
                    Piece capture = getPieceAtLocation(currentRow, i);
                    if(capture != null) {
                        if(capture.getColor() != color) {
                            d.add(new Move(row, column, currentRow, i));
                        }
                    }
                }
            }
            if((color && currentRow == 5 && push == null) || (!color && currentRow == 2 && push == null)) {
                Piece doublePush = getPieceAtLocation(currentRow + turnNumberConversion(), currentColumn);
                if(doublePush == null) {
                    d.add(new Move(row, column, currentRow + turnNumberConversion(), currentColumn));

                }
            }
            if(enPassant != -1 && row == (turnNumber % 2) + 3) {
                //white(even) returns 3, black(odd) returns 4
                for(int i = column - 1; i <= column + 1; i+=2) {
                    //diagonals
                    if(i == enPassant) {
                        d.add(new Move(row, column, currentRow, i));
                    }
                }
            }

        }

        public char getPiece() {
            if(color) {
                return 'P';
            }
            return 'p';
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public boolean getColor() {
            return color;
        }

        public String toString() {
            return "Pawn";
        }
    }

    public class Knight extends Piece {

        public Knight(boolean c, int x, int y) {
            super(c, x, y);
            color = c;
            row = x;
            column = y;
        }

        public ArrayList<Move> getPseudoLegalMove() {
            ArrayList<Move> pseudoLegalMoves = new ArrayList<Move>();
            int[][] knightmoves = {{2, 1} , {2, -1} , {1, -2} , {-1, -2} , {-2, -1} , {-2, 1} , {-1, 2} , {1, 2}};

            for(int i = 0; i < knightmoves.length; i++) {
                kingKnightAttack(knightmoves[i][0], knightmoves[i][1], row, column, color, pseudoLegalMoves);
            }

//			filterMoves(pseudoLegalMoves);

            return pseudoLegalMoves;
        }

        public char getPiece() {
            if(color) {
                return 'N';
            }
            return 'n';
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public boolean getColor() {
            return color;
        }
    }

    public class King extends castlingPiece {

        public King(boolean c, int x, int y) {
            super(c, x, y);
            color = c;
            row = x;
            column = y;
        }

        public ArrayList<Move> getPseudoLegalMove() {
            ArrayList<Move> pseudoLegalMoves = new ArrayList<Move>();
            int[][] kingmoves = {{1, 1} , {1, 0} , {1, -1} , {0, -1} , {-1, -1} , {-1, 0} , {-1, 1} , {0, 1}};

            for(int i = 0; i < kingmoves.length; i++) {
                kingKnightAttack(kingmoves[i][0], kingmoves[i][1], row, column, color, pseudoLegalMoves);
            }

            if(returnColor() ? Q : q) {
                if(getPieceAtLocation(getBackRank(),1) == null && getPieceAtLocation(getBackRank(),2) == null && getPieceAtLocation(getBackRank(),3) == null) {
                    if(!checkCheck(getBackRank(), 4, color) && !checkCheck(getBackRank(), 2, color) && !checkCheck(getBackRank(), 3, color)) {
                        pseudoLegalMoves.add(new Move(row, column, getBackRank(), 2));
                    }
                }
            }

            if(returnColor() ? K : k) {
                if(getPieceAtLocation(getBackRank(),5) == null && getPieceAtLocation(getBackRank(),6) == null) {
                    if(!checkCheck(getBackRank(), 4, color) && !checkCheck(getBackRank(), 5, color) && !checkCheck(getBackRank(), 6, color)) {
                        pseudoLegalMoves.add(new Move(row, column, getBackRank(), 6));
                    }

                }
            }

//			filterMoves(pseudoLegalMoves);

            return pseudoLegalMoves;

        }

        public char getPiece() {
            if(color) {
                return 'K';
            }
            return 'k';
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public boolean getColor() {
            return color;
        }
    }

    public class Bishop extends Piece {

        public Bishop(boolean c, int x, int y) {
            super(c, x, y);
            color = c;
            row = x;
            column = y;
        }

        public ArrayList<Move> getPseudoLegalMove() {
            ArrayList<Move> pseudoLegalMoves = new ArrayList<Move>();
            pseudoLegalMoves.clear();
            diagonalAttack(pseudoLegalMoves, row, column, color);

//			filterMoves(pseudoLegalMoves);

            return pseudoLegalMoves;
        }

        public char getPiece() {
            if(color) {
                return 'B';
            }
            return 'b';
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public boolean getColor() {
            return color;
        }
    }

    public void boardPrinter() {
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
                if(getPieceAtLocation(i, j) != null) {
                    System.out.print(getPieceAtLocation(i, j).getPiece());
                }
                else {
                    System.out.print(" ");
                }
                System.out.print("  |  ");
            }
            System.out.println();
            System.out.println(lines4);
        }
    }

    public void turnUpdater() {
        //call in chess to update turnNumber
        turnNumber++;
    }

    public int turnNumberConversion() {
        //pawn pushes
        //white (even) returns -1, black (odd) returns 1
        return 2 * (turnNumber % 2) - 1;
    }

    public int getBackRank() {
        //castling
        //white(even) returns 7, black(odd) returns 0
        return -7 * ((turnNumber % 2) - 1);
    }

    public boolean outOfBoundsCheck(int row, int column) {
        if((0 <= row && row <= 7) && (0 <= column && column <= 7)) {
            return true;
            //in bounds
        }
        return false;
        //out of bounds
    }

    public boolean checkCheck(int row, int column, boolean color) { //input color of king
//		ArrayList<Move> tempAL = new ArrayList<Move>();
//
//		rookAttack(tempAL, row, column, color);
//		for(Move i: tempAL) {
//			Piece tempP = getPieceAtLocation(i.getRow(), i.getColumn());
//			if((tempP instanceof Rook || tempP instanceof Queen) && tempP.getColor() != color) {
//				return true;
//			}
//		}
//		diagonalAttack(tempAL, row, column, color);
//		return false;

//		System.out.println("checkCheck started"); //testing
        for(Piece i: pieces) {
            if(i.getColor() != color && (!(i instanceof King))) {
//				System.out.println(i.getPiece() + " " + i.getRow() + " " + i.getColumn()); //testing
                for(Move j: i.getPseudoLegalMove()) {
                    if(j.getRow() == row && j.getColumn() == column) {
                        return true; //king is in check
                    }
                }
            }
            else if(i.getColor() != color && i instanceof King) {
//				System.out.println("Is king"); //testing
                ArrayList<Move> LegalKingMoves = new ArrayList<Move>();
                int[][] kingmoves = {{1, 1} , {1, 0} , {1, -1} , {0, -1} , {-1, -1} , {-1, 0} , {-1, 1} , {0, 1}};

                Piece king = findKing(!color);

                for(int j = 0; j < kingmoves.length; j++) {
                    kingKnightAttack(kingmoves[j][0], kingmoves[j][1], king.getRow(), king.getColumn(), king.getColor(), LegalKingMoves);
                }
//				printPseudoLegalMove(LegalKingMoves); //testing
                for(Move k: LegalKingMoves) {
                    if(k.getRow() == row && k.getColumn() == column) {
                        return true; //king is in check
                    }
                }
            }
        }
//		System.out.println("Not in check "); //testing
        return false;
    }

    public boolean mateCheck() { //stalemate and checkmate
//		System.out.println("mateCheck Started!"); //testing
        for(int i = 0; i < pieces.size(); i++) {
            if(pieces.get(i).getColor() == returnColor()) {
                System.out.println(pieces.get(i).getPiece()); //testing
                ArrayList<Move> mateChecker = pieces.get(i).getPseudoLegalMove();
//				System.out.println("Filter Moves About to Start! "); //testing
                filterMoves(mateChecker);
//				System.out.println("Filter Moves Ended! "); //testing
                if(mateChecker.size() != 0) {
//					System.out.println("Not mate!"); //testing
                    return false;
                }
            }
        }

//		for(Piece i: pieces) {
//			if(i.getColor() == returnColor()) {
//				System.out.println(i.getPiece()); //testing
//				ArrayList<Move> mateChecker = i.getPseudoLegalMove();
////				System.out.println("Filter Moves About to Start! "); //testing
//				filterMoves(mateChecker);
////				System.out.println("Filter Moves Ended! "); //testing
//				if(mateChecker.size() != 0) {
////					System.out.println("Not mate!"); //testing
//					return false;
//				}
//			}
//		}
        Piece king = findKing(returnColor());
        if(checkCheck(king.getRow(), king.getColumn(), king.getColor())) {
            if(!returnTurn()) {
                System.out.println("Player 1 Wins, Game Over.");
            }
            else {
                System.out.println("Player 2 Wins, Game Over.");
            }
            return true;
        }
        System.out.println("It's a Draw, Game Over.");
        return true;
    }

    public void filterMoves(ArrayList<Move> x) {
        //makeMove and use checkCheck
        System.out.println("filterMoves started"); //testing
        for(int i = 0; i < x.size(); i++) {
            Move m = x.get(i);
//			System.out.println("ArrayList Size: " + x.size()); //testing
            System.out.println(m); //testing
            Piece tem = getPieceAtLocation(m.getRow(), m.getColumn());
            int index = pieces.indexOf(tem);
            makeMove(m);
            Piece king = findKing(returnColor());
//			System.out.println("king found"); //testing
            if(checkCheck(king.getRow(), king.getColumn(), king.getColor())) {
                System.out.println("test 1"); //testing
                unMake(m, tem, index);
                x.remove(i);
                i -= 1;
//				System.out.println("AL Size: " + x.size()); //testing
            }
            else {
                System.out.println("test 2"); //testing
                unMake(m, tem, index);
            }
//			System.out.println("test 3"); //testing
        }
//		for(Move i: x) {
//			System.out.println("ArrayList Size: " + x.size()); //testing
//			System.out.println(i); //testing
//			makeMove(i);
//			Piece king = findKing(returnColor());
//			System.out.println("king found"); //testing
//			if(checkCheck(king.getRow(), king.getColumn(), king.getColor())) {
//				System.out.println("test 1"); //testing
//				getPieceAtLocation(i.getRow(), i.getColumn()).move(i.getStartRow(), i.getStartCol());
//				x.remove(i);
//				System.out.println("AL Size: " + x.size()); //testing
//			}
//			else {
//				System.out.println("test 2"); //testing
//				getPieceAtLocation(i.getRow(), i.getColumn()).move(i.getStartRow(), i.getStartCol());
//			}
//			System.out.println("test 3"); //testing
//		}

        //after, move to original position with startingRow and startingColumn
    }

    public Piece findKing(boolean color) { //input color king you want to find
        for(Piece p: pieces) {
            if(p instanceof King && p.getColor() == color) {
                return p;
            }
        }
        return null;
    }

    public Piece findQueen(boolean color) { //testing
        for(Piece p: pieces) {
            if(p instanceof Queen && p.getColor() == color) {
                return p;
            }
        }
        return null;
    }

    public boolean returnColor() {
        if(turnNumber % 2 == 0) {
            return true; //white
        }
        return false; //black
    }

    public void makeMove(Move x) {
        System.out.println("makeMove started"); //testing
        Piece movingPiece = getPieceAtLocation(x.getStartRow(), x.getStartCol());
        int row = x.getRow();
        int column = x.getColumn();
//		System.out.println(movingPiece.getPiece() + " " + x.getStartRow() + " " + x.getStartCol()); //testing

        if(movingPiece instanceof Pawn && Math.abs((x.getStartCol()-column)) == 1 && getPieceAtLocation(row, column) == null) {
            pieces.remove(getPieceAtLocation(row + (-2 * (turnNumber % 2) + 1), column));
            //-2 * (turnNumber % 2) + 1
            //white(even) returns 1, black (odd) returns -1
        }

//		for(Piece weo: pieces) { //testing
//			System.out.print(weo.getPiece() + " ");
//		}

//		System.out.println(); //testing

        if(getPieceAtLocation(row, column) != null) {
            pieces.remove(getPieceAtLocation(row, column));
//			for(Piece weo: pieces) { //testing
//				System.out.print(weo.getPiece() + " ");
//			}
        }

        movingPiece.move(row, column);

//		boardPrinter(); //testing

        if(movingPiece instanceof Pawn && Math.abs((x.getStartRow()-row)) == 2) {
            //en passant
            //white(even) returns 2, black(odd) returns 5
            // 3 * (turnNumber % 2) + 2
            enPassantStore = enPassant;
            enPassant = column;
        }
        else {
            enPassantStore = enPassant;
            enPassant = -1;
        }
        System.out.println("eP = " + enPassant); //testing

        //special cases
//		if(movingPiece instanceof Pawn && (row == 7 || row == 0)) {
//			pieces.remove(movingPiece);
//			pieces.add(pawnPromotionPiece(row, column));
//			//pawn promotions
//		}
        if(movingPiece instanceof castlingPiece) {
            if(movingPiece instanceof King) {
                KStore = K;
                QStore = Q;
                kstore = k;
                qstore = q;
                if(returnColor()) { //white king moves
                    K = false;
                    Q = false;
                }
                else { // black king moves
                    k = false;
                    q = false;
                }
                if(Math.abs((x.getStartCol()-column)) == 2) {
//					System.out.println(getBackRank()); //testing
//					System.out.println(7 * ((x.getStartCol()-(column*2))/-8)); //testing
//					System.out.println(getPieceAtLocation(7, 7)); //testing
//					System.out.println(getPieceAtLocation(getBackRank(),  7 * ((x.getStartCol()-(column*2))/-8))); //testing
                    getPieceAtLocation(getBackRank(), 7 * ((x.getStartCol()-(column*2))/-8)).move(getBackRank(), ((column-4)/2) + 4);
                    //getPieceAtLocation(getBackRank(), 7 * ((x.getStartCol()-(column*2))/-8))
                    //4, 6 -> 7; 4, 2 -> 0
                    //find king, find rook on the side person wants to castle
                    //((4-column)/2) + 4
                    //move rook over the king to finish the castling sequence
                }
            }
            if(movingPiece instanceof Rook) {
                if(returnColor()) { //white
                    if(x.getStartCol() == 0) { //queen's side
                        Q = false;
                    }
                    else { //king's side
                        K = false;
                    }
                }
                else { //black
                    if(x.getStartCol() == 0) {
                        q = false;
                    }
                    else {
                        k = false;
                    }
                }
            }
        }
        //change piece ArrayList
        boardPrinter(); //testing
        System.out.println("eP = " + enPassant); //testing
    }

    public void unMake(Move x, Piece p, int indx) {
        System.out.println("unmake move started"); //testing
        Piece movingPiece = getPieceAtLocation(x.getRow(), x.getColumn());
        int row = x.getStartRow();
        int column = x.getStartCol();

        movingPiece.move(row, column);


        if(p != null) {
            pieces.add(indx, p);
//			for(Piece weo: pieces) { //testing
//				System.out.print(weo.getPiece() + " ");
//			}
        }

        if(movingPiece instanceof castlingPiece) {
            if(movingPiece instanceof King) {
                if(returnColor()) { //white king unmoves
                    K = KStore;
                    Q = QStore;
                }
                else { // black king unmoves
                    k = kstore;
                    q = qstore;
                }
                if(Math.abs((x.getColumn()-column)) == 2) {
                    System.out.println("rook castle unmake move started"); //testing
                    getPieceAtLocation(getBackRank(), ((x.getColumn()-4)/2) + 4).move(getBackRank(), 7 * ((column-(x.getColumn()*2))/-8));
                    //Rook moves back to original position in unmake move
                }
            }
            if(movingPiece instanceof Rook) {
                if(returnColor()) { //white
                    if(x.getStartCol() == 0) { //queen's side
                        Q = true;
                    }
                    else { //king's side
                        K = true;
                    }
                }
                else { //black
                    if(x.getStartCol() == 0) {
                        q = true;
                    }
                    else {
                        k = true;
                    }
                }
            }
        }

        enPassant = enPassantStore;

        if(movingPiece instanceof Pawn && Math.abs((column-x.getColumn())) == 1 && p == null) {
            pieces.add(new Pawn(!returnColor(), x.getRow() + (-2 * (turnNumber % 2) + 1), x.getColumn()));
            //-2 * (turnNumber % 2) + 1
            //white(even) returns 1, black (odd) returns -1
        }

        boardPrinter(); //testing
        System.out.println("eP = " + enPassant); //testing
    }


    public Piece pawnPromotionPiece(int row, int column) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the desired promoted piece (r, n, b, q): ");
        boolean validInput = false;
        while(!validInput) {
            String str = scanner.nextLine().trim().toLowerCase();
            if(str.equals("r") || str.equals("rook")) {
                if(row == 0) {
                    return new Rook(true, 0, column);
                    //color check
                }
                else {
                    return new Rook(false, 7, column);
                }
            }
            else if(str.equals("n") || str.equals("knight")) {
                if(row == 0) {
                    return new Knight(true, 0, column);
                    //color check
                }
                else {
                    return new Knight(false, 7, column);
                }
            }
            else if(str.equals("b") || str.equals("bishop")) {
                if(row == 0) {
                    return new Bishop(true, 0, column);
                    //color check
                }
                else {
                    return new Bishop(false, 7, column);
                }
            }
            else if(str.equals("q") || str.equals("queen")) {
                if(row == 0) {
                    return new Queen(true, 0, column);
                    //color check
                }
                else {
                    return new Queen(false, 7, column);
                }
            }
            else {
                System.out.println("Invalid input. Please try again: ");
            }
        }
        return null;
    }

    public Piece getPieceAtLocation(int row1, int col1) {
        for(int i = 0; i < pieces.size(); i++) {
            if(pieces.get(i).getRow() == row1 && pieces.get(i).getColumn() == col1) {
                return pieces.get(i);
            }
        }
        return null;
    }

    public Move getterMove() {
        Move move = new Move(7,7,7,7);
        return move;
    }

    public Piece getUserPiece(String str) {
        int column = (int) str.charAt(0);
        column = column - 97;
        int row = Integer.parseInt(str.substring(1));
        row = 8 - row;
        for(Piece p: pieces) {
            if(p.getRow() == row && p.getColumn() == column && p.getColor() == returnColor()) {
                return p;
            }
        }
        return null;
    }

    public boolean returnTurn() {
        System.out.println("tN = " + turnNumber); //testing
        System.out.println("eP = " + enPassant); //testing
        if(turnNumber % 2 == 0) {
            return true; //white turn
        }
        else {
            return false; //black turn
        }
    }

    public void printPseudoLegalMove(ArrayList<Move> d) {
        for(int i = 0; i < d.size(); i++) {
            System.out.print(d.get(i) + " ");
        }
    }

    public Piece getPawnPromote() {
        for(Piece p: pieces) {
            if(p instanceof Pawn) {
                if(p.getRow() == 7 || p.getRow() == 0) {
                    return p;
                }
            }
        }
        return null;
    }

    public void removeAdd(Piece remove, Piece add) {
        pieces.remove(remove);
        pieces.add(add);
    }

}
