package chess;

import javax.swing.*;

/**********************************************************************
 * This program is a chess piece called knight that extends the
 * chess piece class. This class contains all the code to
 * ensure the piece "Knight" behaves correctly
 *
 @author Seth Konynenbelt, Chad VanderWall, Isabella Snyder
 @version Winter 2022
 **********************************************************************/

public class Knight extends ChessPiece {

	/*****************************************************************
	 Constructor that calls a super() method and assigns a player
	 *****************************************************************/
	public Knight(Player player) {
		super(player);
	}

	/*****************************************************************
	 Method that returns a string to represent a king

	 @return the string "King"
	 *****************************************************************/
	public String type() {
		return "Knight";
	}

	/*****************************************************************
	 the isValidMove() function determines if the move is valid
	 for a King piece

	 @return true, if the move is valid
	 false, if the move is not valid
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board){


		boolean valid = true;

		//Checks to see if it is a valid move in ChessPiece class
		if (super.isValidMove(move, board)) {

			//Move to space is TWO DOWN and ONE LEFT
			if ((move.fromColumn + 2 == move.toColumn) && (move.fromRow - 1 == move.toRow)) {
				return valid;
			}

			//Move to space is TWO DOWN and ONE RIGHT
			if ((move.fromColumn + 2 == move.toColumn) && (move.fromRow + 1 == move.toRow)) {
				return valid;
			}

			//Move to space is ONE DOWN and TWO LEFT
			if ((move.fromColumn + 1 == move.toColumn) && (move.fromRow - 2 == move.toRow)) {
				return valid;
			}

			//Move to space is ONE DOWN and TWO RIGHT
			if ((move.fromColumn + 1 == move.toColumn) && (move.fromRow + 2 == move.toRow)) {
				return valid;
			}

			//Move to space is TWO UP and ONE LEFT
			if ((move.fromColumn - 2 == move.toColumn) && (move.fromRow - 1 == move.toRow)) {
				return valid;
			}

			//Move to space is TWO UP and ONE RIGHT
			if ((move.fromColumn - 2 == move.toColumn) && (move.fromRow + 1 == move.toRow)) {
				return valid;
			}

			//Move to space is ONE UP and TWO LEFT
			if ((move.fromColumn - 1 == move.toColumn) && (move.fromRow - 2 == move.toRow)) {
				return valid;
			}

			//Move to space is ONE UP and TWO RIGHT
			if ((move.fromColumn - 1 == move.toColumn) && (move.fromRow + 2 == move.toRow)) {
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
		ImageIcon Icon = new ImageIcon(getClass().getResource("/resources/icons/wKnight.png"));
		return Icon;
	}

	/*****************************************************************
	 A method that creates a new black image icon for the piece

	 @return a black image icon
	 *****************************************************************/
	@Override
	public ImageIcon blackIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource("/resources/icons/bKnight.png"));
		return Icon;
	}
}

