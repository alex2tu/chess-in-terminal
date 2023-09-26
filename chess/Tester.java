package chess;

import java.util.ArrayList;

import board.Board;
import board.Board.Bishop;
import board.Board.King;
import board.Board.Knight;
import board.Board.Pawn;
import board.Board.Piece;
import board.Board.Queen;
import board.Board.Rook;

public class Tester {

    private static ArrayList <Piece> pieces = new ArrayList<Piece>();

    public static void main(String[] args) {
        // TODO Auto-generated method stub

//		ArrayList<Move> pseudoLegalMoves = new ArrayList<Move>();
//
//
//		Move move = new Move(4, 4);
//		System.out.println(move);

//		slidingAttack(1, 0, pieces, 4, 4, true, pseudoLegalMoves);
//		slidingAttack(0, 1, pieces, 4, 4, true, pseudoLegalMoves);
//		slidingAttack(-1, 0, pieces, 4, 4, true, pseudoLegalMoves);
//		slidingAttack(0, -1, pieces, 4, 4, true, pseudoLegalMoves);
//
//		slidingAttack(1, 1, pieces, 4, 4, true, pseudoLegalMoves);
//		slidingAttack(-1, 1, pieces, 4, 4, true, pseudoLegalMoves);
//		slidingAttack(-1, -1, pieces, 4, 4, true, pseudoLegalMoves);
//		slidingAttack(1, -1, pieces, 4, 4, true, pseudoLegalMoves);

//		int[][] kingmoves = {{1, 1} , {1, 0} , {1, -1} , {0, -1} , {-1, -1} , {-1, 0} , {-1, 1} , {0, 1}};
//		int[][] knightmoves = {{2, 1} , {2, -1} , {1, -2} , {-1, -2} , {-2, -1} , {-2, 1} , {-1, 2} , {1, 2}};
//
//		for(int i = 0; i < knightmoves.length; i++) {
//			kingKnightAttack(kingmoves[i][0], kingmoves[i][1], 1, 0, true, pseudoLegalMoves);
//		}
//
//		for(int i = 0; i < pseudoLegalMoves.size(); i++) {
//			System.out.print(pseudoLegalMoves.get(i) + " ");
//		}
    }

//	public static void slidingAttack(int row, int column, ArrayList <Piece> c, int currentRow, int currentColumn, boolean color, ArrayList<Move> d) {
//		while(true) {
//			currentRow += row;
//			currentColumn += column;
//			if(outOfBoundsCheck(currentRow, currentColumn)) {
//				Piece temp = getPieceAtLocation(currentRow, currentColumn);
//				if(temp != null) {
//					if(temp.getColor() != color) {
//						d.add(new Move(currentRow, currentColumn));
//					}
//					break;
//				}
//				d.add(new Move(currentRow, currentColumn));
//			}
//			else {
//				break;
//			}
//		}
//	}
//
//	public static void kingKnightAttack(int row, int column, int currentRow, int currentColumn, boolean color, ArrayList<Move> d) {
//		currentRow += row;
//		currentColumn += column;
//		if(outOfBoundsCheck(currentRow, currentColumn)) {
//			Piece temp = getPieceAtLocation(currentRow, currentColumn);
//			if(temp != null) {
//				if(temp.getColor() != color) {
//					d.add(new Move(currentRow, currentColumn));
//				}
//			}
//			else {
//				d.add(new Move(currentRow, currentColumn));
//			}
//
//		}
//	}

    public static Piece getPieceAtLocation(int row1, int col1) {
        for(int i = 0; i < pieces.size(); i++) {
            if(pieces.get(i).getRow() == row1 && pieces.get(i).getColumn() == col1) {
                return pieces.get(i);
            }
        }
        return null;
    }

//	public static boolean outOfBoundsCheck(int row, int column) {
//		if((row > 7 || row < 0) || (column > 7 || column < 0)) {
//			return false;
//			//out of bounds
//		}
//		return true;
//		//in bounds
//	}

    public static boolean outOfBoundsCheck(int row, int column) {
        if((0 <= row && row <= 7) && (0 <= column && column <= 7)) {
            return true;
            //out of bounds
        }
        return false;
        //in bounds
    }



}
