package chess;

import javax.swing.*;

/**********************************************************************
 * This program is a chess piece called pawn that extends the
 * chess piece class. This class contains all the code to
 * ensure the piece "Pawn" behaves correctly
 *
 @author Seth Konynenbelt, Chad VanderWall, Isabella Snyder
 @version Winter 2022
 **********************************************************************/

public class Pawn extends ChessPiece {


	/*****************************************************************
	 Constructor that calls a super() method and assigns a player
	 *****************************************************************/
	public Pawn(Player player) {
		super(player);
	}

	/*****************************************************************
	 Method that returns a string to represent a pawn

	 @return the string "Pawn"
	 *****************************************************************/
	public String type() {
		return "Pawn";
	}

	/*****************************************************************
	 the isValidMove() function determines if the move is valid
	 for a pawn piece

	 @return true, if the move is valid for a pawn
	 		 false, if the move is not valid for a pawn
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		// check WHITE pieces
		if ((super.player() == Player.WHITE)) {
			int i = 0;

			// loops through the board(white pawn pieces)
			while (i < 8) {
				IChessPiece temp = board[5][i];

				// checks to make sure there is not a piece in front of a pawn
				if (super.isValidMove(move, board)
						&& ((move.fromRow == 6) && (move.fromColumn == i))
						&& (((move.toRow == 4) && (move.toColumn == i))
						|| ((move.toRow == 5) && (move.toColumn == i)))
						&& temp != null)

					return false;

				// make sure there is a black piece in a diagonal spot to take
				if (super.isValidMove(move, board)
						&& ((move.fromRow <= 6)
						&& (move.fromColumn == i))
						&& (move.toRow == move.fromRow - 1)
						&& ((move.toColumn == move.fromColumn + 1)
						|| (move.toColumn == move.fromColumn - 1))
						&& board[move.toRow][move.toColumn] != null
						&& (board[move.toRow][move.toColumn].player()
						.equals(Player.BLACK))) {

					return true;
				}

				// make sure first move is either two or one pieces
				else if (super.isValidMove(move, board)
						&& ((move.fromRow == 6) && (move.fromColumn == i))
						&& (((move.toRow == 4) && (move.toColumn == i))
						|| ((move.toRow == 5) && (move.toColumn == i)))
						&& board[move.toRow][move.toColumn] == null)

					return true;

					// move one space with no capture and not first move
				else if (super.isValidMove(move, board)
						&& ((move.fromRow <= 5) && (move.fromColumn == i))
						&& (move.toRow == move.fromRow - 1)
						&& (move.fromColumn == move.toColumn)
						&& board[move.toRow][move.toColumn] == null)

					return true;

				i++;
			}

		}

		// check BLACK pieces
		else if ((super.player() == Player.BLACK)) {

			int i = 0;

			// loops through the board
			while (i < 8) {

				IChessPiece temp = board[2][i];

				// checks to make sure there is not a piece in front of a pawn
				if (super.isValidMove(move, board)
						&& ((move.fromRow == 1) && (move.fromColumn == i))
						&& (((move.toRow == 3) && (move.toColumn == i))
						|| ((move.toRow == 2) && (move.toColumn == i)))
						&& temp != null)

					return false;

				// make sure there is a black piece in a diagonal spot to take
				if (super.isValidMove(move, board)
						&& ((move.fromRow >= 1) && (move.fromColumn == i))
						&& (move.toRow == move.fromRow + 1)
						&& ((move.toColumn == move.fromColumn + 1)
						|| (move.toColumn == move.fromColumn - 1))
						&& board[move.toRow][move.toColumn] != null
						&& (board[move.toRow][move.toColumn].player()
						.equals(Player.WHITE))) {

					return true;
				}

				// make sure first move is either two or one pieces
				else if (super.isValidMove(move, board)
						&& ((move.fromRow == 1) && (move.fromColumn == i))
						&& (((move.toRow == 3) && (move.toColumn == i))
						|| ((move.toRow == 2) && (move.toColumn == i)))
						&& board[move.toRow][move.toColumn] == null)

					return true;

					// move one space with no capture and not first move
				else if (super.isValidMove(move, board)
						&& ((move.fromRow >= 2) && (move.fromColumn == i))
						&& (move.toRow == move.fromRow + 1)
						&& (move.fromColumn == move.toColumn)
						&& board[move.toRow][move.toColumn] == null)

					return true;

				i++;

			}

		}

		// returns false if nothing is true
		return false;
	}

	/*****************************************************************
	 A method that creates a new white image icon for the pawn

	 @return a white image icon
	 *****************************************************************/
	@Override
	public ImageIcon whiteIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource(
				"/resources/icons/wPawn.png"));
		return Icon;
	}

	/*****************************************************************
	 A method that creates a new black image icon for the pawn

	 @return a black image icon
	 *****************************************************************/
	@Override
	public ImageIcon blackIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource(
				"/resources/icons/bPawn.png"));
		return Icon;
	}
	}
