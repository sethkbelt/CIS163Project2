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
public class Rook extends ChessPiece {

	/*****************************************************************
	 Constructor that calls a super() method and assigns a player
	 *****************************************************************/
	public Rook(Player player) {

		super(player);

	}

	/*****************************************************************
	 Method that returns a string to represent a bishop

	 @return the string "Bishop"
	 *****************************************************************/
	public String type() {
		return "Rook";
	}

	/*****************************************************************
	 the isValidMove() function determines if the move is valid
	 for a Bishop piece

	 @return true, if the move is valid
	 false, if the move is not valid
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		int countPiece = -1;
		// int steps = Math.abs(move.toRow - move.fromRow);

		// up and down
		if (move.fromColumn == move.toColumn
				&& super.isValidMove(move, board)) {

			// south to north movement
			for (int i = move.fromRow; i >= move.toRow; i--) {

				IChessPiece x = board[i][move.toColumn];

				if (x != null) {
					countPiece++;
				}

			}

			// north to south movement
			for (int i = move.fromRow; i <= move.toRow; i++) {
				IChessPiece x = board[i][move.toColumn];

				if (x != null)
					countPiece++;
			}
		}

		// side to side
		if (move.fromRow == move.toRow
				&& super.isValidMove(move, board)) {

			// west to east movement
			for (int i = move.fromColumn; i <= move.toColumn; i++) {

				IChessPiece x = board[move.toRow][i];

				if (x != null)
					countPiece++;
			}

			// east to west movement
			for (int i = move.fromColumn; i >= move.toColumn; i--) {

				IChessPiece x = board[move.toRow][i];

				if (x != null)
					countPiece++;

			}

		}

		if (countPiece == 1)
			// try take piece
			if ((move.fromRow == move.toRow
					|| move.fromColumn == move.toColumn)
					&& board[move.toRow][move.toColumn] != null
					&& board[move.toRow][move.toColumn].player()
					.equals(player().next()))
				return true;

		// if path is clear
		if (countPiece == 0)
			return true;

		else
			return false;
	}

	/*****************************************************************
	 A method that creates a new white image icon for the piece

	 @return a white image icon
	 *****************************************************************/
	@Override
	public ImageIcon whiteIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource("/resources/icons/wRook.png"));
		return Icon;
	}

	/*****************************************************************
	 A method that creates a new black image icon for the piece

	 @return a black image icon
	 *****************************************************************/
	@Override
	public ImageIcon blackIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource("/resources/icons/bRook.png"));
		return Icon;
	}
}
