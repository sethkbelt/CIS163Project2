package chess;

import javax.swing.*;

/**********************************************************************
 * This program is a chess piece called bishop that extends the
 * chess piece class. This class contains all the code to
 * ensure the piece "Bishop" behaves correctly
 *
 @author Seth Konynenbelt, Chad VanderWall, Isabella Snyder
 @version Winter 2022
 **********************************************************************/

public class Bishop extends ChessPiece {

	/** Top to Bottom in a positive direction(TBPos) */
	private int pieceCountTBPos;

	/** Top to Bottom in a negative direction(TBNeg) */
	private int pieceCountTBNeg;

	/** Bottom to Top in a positive direction(BTPos) */
	private int pieceCountBTPos;

	/** Bottom to Top in a negative direction(BTNeg) */
	private int pieceCountBTNeg;

	/*****************************************************************
	 Constructor that calls a super() method and assigns a player
	 *****************************************************************/
	public Bishop(Player player) {
		super(player);
	}

	/*****************************************************************
	 Method that returns a string to represent a bishop

	 @return the string "Bishop"
	 *****************************************************************/
	public String type() {
		return "Bishop";
	}

	/*****************************************************************
	 the isValidMove() function determines if the move is valid
	 for a Bishop piece

	 @return true, if the move is valid
	 false, if the move is not valid
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		checkForClearPath(move, board);

		for (int i = 0; i < 8; i++) {

			// Check for valid move
			if ((move.fromColumn + i == move.toColumn)
					&& (move.fromRow + i == move.toRow)
					&& pieceCountTBPos == 0
					&& super.isValidMove(move, board)) {
				return true;
			}

			else if ((move.fromColumn - i == move.toColumn)
					&& (move.fromRow + i == move.toRow)
					&& pieceCountTBNeg == 0
					&& super.isValidMove(move, board)) {
				return true;
			}

			else if ((move.fromColumn + i == move.toColumn)
					&& (move.fromRow - i == move.toRow)
					&& pieceCountBTPos == 0
					&& super.isValidMove(move, board)) {
				return true;
			}

			else if ((move.fromColumn - i == move.toColumn)
					&& (move.fromRow - i == move.toRow)
					&& pieceCountBTNeg == 0
					&& super.isValidMove(move, board)) {
				return true;
			}

			// Check Capture Top to Bottom Left to Right
			if ((move.fromColumn + i == move.toColumn)
					&& (move.fromRow + i == move.toRow)
					&& pieceCountTBPos == 1
					&& super.isValidMove(move, board)) {

				if (board[move.toRow][move.toColumn] != null
						&& board[move.toRow][move.toColumn].player()
						.equals(player().next())) {
					return true;
				}
			}

			// Check Capture Top to Bottom Right to Left
			if (((move.fromColumn - i == move.toColumn)
					&& (move.fromRow + i == move.toRow)
					&& pieceCountTBNeg == 1 && super.isValidMove(move,
					board))) {

				if (board[move.toRow][move.toColumn] != null
						&& board[move.toRow][move.toColumn].player()
						.equals(player().next())) {
					return true;
				}
			}

			// Check Capture Bottom to Top Left to Right
			if ((move.fromColumn + i == move.toColumn)
					&& (move.fromRow - i == move.toRow)
					&& pieceCountBTPos == 1
					&& super.isValidMove(move, board)) {

				if (board[move.toRow][move.toColumn] != null
						&& board[move.toRow][move.toColumn].player()
						.equals(player().next())) {
					return true;
				}
			}

			// Check Capture Bottom to Top Right to Left
			if ((move.fromColumn - i == move.toColumn)
					&& (move.fromRow - i == move.toRow)
					&& pieceCountBTNeg == 1
					&& super.isValidMove(move, board)) {

				if (board[move.toRow][move.toColumn] != null
						&& board[move.toRow][move.toColumn].player()
						.equals(player().next())) {
					return true;
				}
			}
		}
		return false;
	}

	public void checkForClearPath(Move move, IChessPiece[][] board) {

		// Top to Bottom in a positive direction(TBPos)
		pieceCountTBPos = 0;
		pieceCountTBNeg = 0;
		pieceCountBTPos = 0;
		pieceCountBTNeg = 0;


		// Top to Bottom Left to Right
		if (super.isValidMove(move, board)) {

			int tempRow = move.fromRow;
			int tempCol = move.fromColumn;

			for (int i = move.fromRow; i < move.toRow; i++) {
				tempRow += 1;
				tempCol += 1;
				if ((tempRow < 8 && tempCol < 8)
						&& (tempRow >= 0 && tempCol >= 0)) {

					IChessPiece x = board[tempRow][tempCol];

					if (x != null)
						pieceCountTBPos++;
				}
			}
		}

		// Top to Bottom Right to Left
		if (super.isValidMove(move, board)) {
			int tempRow = move.fromRow;
			int tempCol = move.fromColumn;

			for (int i = move.fromRow; i < move.toRow; i++) {
				tempRow += 1;
				tempCol -= 1;
				if ((tempRow < 8 && tempCol < 8)
						&& (tempRow >= 0 && tempCol >= 0)) {

					IChessPiece x = board[tempRow][tempCol];

					if (x != null)
						pieceCountTBNeg++;
				}
			}
		}

		// Bottom to Top Left to Right
		if (super.isValidMove(move, board)) {
			int tempRow = move.fromRow;
			int tempCol = move.fromColumn;

			for (int i = move.fromRow; i > move.toRow; i--) {
				tempRow -= 1;
				tempCol += 1;

				if ((tempRow < 8 && tempCol < 8)
						&& (tempRow >= 0 && tempCol >= 0)) {

					IChessPiece x = board[tempRow][tempCol];

					if (x != null)
						pieceCountBTPos++;
				}
			}
		}

		// Bottom to Top Right to Left
		if (super.isValidMove(move, board)) {
			int tempRow = move.fromRow;
			int tempCol = move.fromColumn;

			for (int i = move.fromRow; i > move.toRow; i--) {
				tempRow -= 1;
				tempCol -= 1;

				if ((tempRow < 8 && tempCol < 8)
						&& (tempRow >= 0 && tempCol >= 0)) {

					IChessPiece x = board[tempRow][tempCol];

					if (x != null)
						pieceCountBTNeg++;
				}
			}
		}
	}

	/*****************************************************************
	 A method that creates a new white image icon for the piece

	 @return a white image icon
	 *****************************************************************/
	@Override
	public ImageIcon whiteIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource("/" +
				"resources/icons/wBishop.png"));
		return Icon;
	}

	/*****************************************************************
	 A method that creates a new black image icon for the piece

	 @return a black image icon
	 *****************************************************************/
	@Override
	public ImageIcon blackIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource("/" +
				"resources/icons/bBishop.png"));
		return Icon;
	}

}
