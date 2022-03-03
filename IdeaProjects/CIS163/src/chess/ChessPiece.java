package chess;

import javax.swing.*;


public abstract class ChessPiece implements IChessPiece {

	public abstract ImageIcon whiteIcon();

	public abstract ImageIcon blackIcon();

	protected final Player owner;



	protected ChessPiece(Player player) {
		this.owner = player;
	}

	public abstract String type();

	public Player player() {
		return owner;
	}
	//

	/**
	 * Returns whether the piece at location {@code [move.fromRow, move.fromColumn]} is allowed to move to location
	 * {@code [move.fromRow, move.fromColumn]}.
	 * <p>
	 * Note:  Pieces don't store their own location (because doing so would be redundant).  Therefore,
	 * the {@code [move.fromRow, move.fromColumn]} component of {@code move} is necessary.
	 * {@code this} object must be the piece at location {@code [move.fromRow, move.fromColumn]}.
	 * (This method makes no sense otherwise.)
	 *
	 * @param move  a  object describing the move to be made.
	 * @param board the in which this piece resides.
	 * @return {@code true} if the proposed move is valid, {@code false} otherwise.
	 * @throws IndexOutOfBoundsException if either {@code [move.fromRow, move.fromColumn]} or {@code [move.toRow,
	 *                                   move.toColumn]} don't represent valid locations on the board.
	 * @throws IllegalArgumentException  if {@code this} object isn't the piece at location {@code [move.fromRow, move.fromColumn]}.
	 */

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		IChessPiece x = board[move.fromRow][move.fromColumn];
		IChessPiece y = board[move.toRow][move.toColumn];

		//Checks to see if from and to are same location
		if (((move.fromRow == move.toRow)
				&& (move.fromColumn == move.toColumn)))
			return false;

		//Checks to see if desired piece is at the place in question
		if (!(this.equals(x)) && !x.type().equals("Queen")) {
			return false;
		}

		// Checks to see if the players are different
		if (y != null && this.owner.equals(y.player()))
			return false;
		else
			return true;
	}
}
