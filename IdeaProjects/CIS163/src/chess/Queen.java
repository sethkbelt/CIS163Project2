package chess;

import javax.swing.*;

/**********************************************************************
 * This program is a chess piece called queen that extends the
 * chess piece class. This class contains all the code to
 * ensure the piece "Queen" behaves correctly
 *
 @author Seth Konynenbelt, Chad VanderWall, Isabella Snyder
 @version Winter 2022
 **********************************************************************/

public class Queen extends ChessPiece {

	/*****************************************************************
	 A method that creates a new white image icon for the piece

	 @return a white image icon
	 *****************************************************************/
	@Override
	public ImageIcon whiteIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource(
				"/resources/icons/wQueen.png"));
		return Icon;
	}

	/*****************************************************************
	 A method that creates a new black image icon for the piece

	 @return a black image icon
	 *****************************************************************/
	@Override
	public ImageIcon blackIcon() {
		ImageIcon Icon = new ImageIcon(getClass().getResource(
				"/resources/icons/bQueen.png"));
		return Icon;
	}

	/*****************************************************************
	 Constructor that calls a super() method and assigns a player
	 *****************************************************************/
	public Queen(Player player) {
		super(player);

	}

	/*****************************************************************
	 Method that returns a string to represent a qyeen

	 @return the string "Queen"
	 *****************************************************************/
	public String type() {
		return "Queen";

	}

	/*****************************************************************
	 the isValidMove() function determines if the move is valid
	 for a Queen piece

	 @return true, if the move is valid
	 false, if the move is not valid
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		Bishop move1 = new Bishop(board[move.fromRow][move.fromColumn].player());
		Rook move2 = new Rook(board[move.fromRow][move.fromColumn].player());
		return (move1.isValidMove(move, board) || move2.isValidMove(move, board));
	}
}
