package chess;

import javax.swing.*;

/**********************************************************************
 * This program is a chess piece called King that extends the
 * chess piece class. This class contains all the code to
 * ensure the piece "King" behaves correctly
 *
 @author Seth Konynenbelt, Chad VanderWall, Isabella Snyder
 @version Winter 2022
 **********************************************************************/

public class King extends ChessPiece {

	/** to see if the king has castled*/
	public boolean castled;

	/** check if King has moved*/
	public boolean hasMoved = false;

	/*****************************************************************
	 Constructor that calls a super() method and assigns a player
	 *****************************************************************/
	public King(Player player) {
		super(player);
	}

	/*****************************************************************
	 Method that returns a string to represent a King

	 @return the string "King"
	 *****************************************************************/
	public String type() {
		return "King";
	}

	/*****************************************************************
	 the isValidMove() function determines if the move is valid
	 for a pawn piece

	 @return true, if the move is valid
	 false, if the move is not valid
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid = true;

		//Checks to see if it is a valid move in ChessPiece class
		if (super.isValidMove(move, board)) {

			//Do castling logic here. Code done with help of sagnew/Chess GitHub
			if(move.toColumn - move.fromColumn == 2 && move.fromRow == move.toRow) {
				//Castle kingside
				if (hasMoved == false) {
					if (board[move.toRow][move.fromColumn + 1] == null && board[move.toRow][move.fromColumn + 2] == null) {
						castled = true;
						return true;
					}
					// castle not king side
				} else if (move.fromColumn - move.toColumn == 3 && move.fromRow == move.toRow) {
					if (board[move.toRow][move.fromColumn - 1] == null && board[move.toRow][move.fromColumn - 2] == null && board[move.toRow][move.fromColumn - 3] == null) {
						castled = true;
						return true;
					}
				} else {
					castled = false;
				}
			}


			//Move to space is one UP on the board
			if ((move.toRow == move.fromRow - 1) && (move.fromColumn == move.toColumn)) {
				hasMoved = true;
				return valid;
			}

			//Move to space is one DOWN on the board
			if ((move.toRow == move.fromRow + 1) && (move.fromColumn == move.toColumn)) {
				hasMoved = true;
				return valid;
			}

			//Move to space is one to the RIGHT
			if ((move.toColumn == move.fromColumn + 1) && (move.fromRow == move.toRow)) {
				hasMoved = true;
				return valid;
			}

			//Move to space is one to the LEFT
			if ((move.toColumn == move.fromColumn - 1) && (move.fromRow == move.toRow)) {
				hasMoved = true;
				return valid;
			}

			//Move to space is one UP and one RIGHT
			if ((move.toColumn == move.fromColumn + 1) && (move.toRow == move.fromRow - 1)) {
				hasMoved = true;
				return valid;
			}

			//Move to space is one UP and one LEFT
			if ((move.toColumn == move.fromColumn - 1) && (move.toRow == move.fromRow - 1)) {
				hasMoved = true;
				return valid;
			}

			//Move to space is one DOWN and one RIGHT
			if ((move.toColumn == move.fromColumn + 1) && (move.toRow == move.fromRow + 1)) {
				hasMoved = true;
				return valid;
			}

			//Move to space is one DOWN and one LEFT
			if ((move.toColumn == move.fromColumn - 1) && (move.toRow == move.fromRow + 1)) {
				hasMoved = true;
				return valid;
			}
		}

		return false;
	}

	/*****************************************************************
	 A method that creates a new white image icon for the piece

	 @return a white image icon
	 *****************************************************************/
	@Override
	public ImageIcon whiteIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource("/resources/icons/wKing.png"));
		return Icon;
	}

	/*****************************************************************
	 A method that creates a new black image icon for the piece

	 @return a black image icon
	 *****************************************************************/
	@Override
	public ImageIcon blackIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource("/resources/icons/bKing.png"));
		return Icon;
	}
}
