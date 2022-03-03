package chess;

import javax.swing.*;
import java.util.ArrayList;

/*****************************************************************
 A simple chess game model and a simple GUI program that allows two
 humans to play the chess game or to play against AI.
 This class is responsible for storing the chessboard and
 implementing the game logic.

 @author Isabella Snyder(Computer Science), Chad VanderWall(Computer Science),
 Seth Konynenbelt(Computer Engineering)
 @version Winter 2022
 *****************************************************************/

public class ChessModel implements IChessModel {

    /**
     * Defines a double array of Chess Pieces to create the board
     */
    private IChessPiece[][] board;

    /**
     * Defines a player
     */
    private Player player;

    /**
     * A temp variable to save the piece in the spot you move to
     */
    public IChessPiece saveToPiece;

    /**
     * A temp variable to save the piece in the spot you move from
     */
    public IChessPiece saveFromPiece;

    /**
     * An array list of moves to save the history of moves made
     */
    public ArrayList<Move> moves = new ArrayList<Move>();

    /**
     * An array list of moves to save the pieces in the spots you move to
     */
    public ArrayList<IChessPiece> savePieceTo = new ArrayList<IChessPiece>();

    /**
     * An array list of moves to save the pieces in the spots you move to
     */
    public ArrayList<IChessPiece> savePieceFrom = new ArrayList<IChessPiece>();

    /**
     * A flag to determine if they are playing against the AI
     */
    public boolean aiFlag;


    /*****************************************************************
     Default constructor that creates a new board and puts all of the
     pieces on the board.
     *****************************************************************/
    public ChessModel() {

        // Creates new board of IChessPiece objects
        board = new IChessPiece[8][8];

        // Loops through board to create Pawns in proper places
        int i = 0;
        while (i < board.length) {

            // Creates new Pawns for both black and white player
            board[1][i] = new Pawn(Player.BLACK);
            board[6][i] = new Pawn(Player.WHITE);
            i++;
        }

        // Creates new Rooks for both black and white player
        board[0][0] = new Rook(Player.BLACK);
        board[0][7] = new Rook(Player.BLACK);
        board[7][0] = new Rook(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);

        // Creates new Knights for both black and white player
        board[0][1] = new Knight(Player.BLACK);
        board[0][6] = new Knight(Player.BLACK);
        board[7][1] = new Knight(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);

        // Creates new Bishops for both black and white player
        board[0][2] = new Bishop(Player.BLACK);
        board[0][5] = new Bishop(Player.BLACK);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);

        // Creates new Kings for both black and white player
        board[0][4] = new King(Player.BLACK);
        board[7][4] = new King(Player.WHITE);

        // Creates new Queens for both black and white player
        board[0][3] = new Queen(Player.BLACK);
        board[7][3] = new Queen(Player.WHITE);

        // Sets current player instance variable to white
        // Chess game rules dictate that white always goes first
        this.player = Player.WHITE;

        // Creates an option to play against AI or another player
        Object[] selectionValues = {"AI", "Another player"};
        String initialSelection = "Another player";
        Object selection = JOptionPane.showInputDialog(null,
                "Who would you like to play against?",
                "Player selection", JOptionPane.QUESTION_MESSAGE,
                null, selectionValues, initialSelection);

        //Sets the AI flag depending on selection
        if (selection.toString().equals("AI"))
            aiFlag = true;
        else
            aiFlag = false;
    }

    /*****************************************************************
     Checks to see if the game is complete by checking if the king is
     in check, then if there is any possible moves to get out of check.
     If not, the game is complete

     @return true if there is a checkmate, false if there is
     not a checkmate
     *****************************************************************/
    public boolean isComplete() {
        boolean valid = false;

        //find a players piece
        for (int row = 0; row < numRows(); row++) {
            for (int col = 0; col < numColumns(); col++) {

                if (pieceAt(row, col) != null)
                    if (pieceAt(row, col).player().equals(this.player)) {
                        IChessPiece tempPiece = board[row][col];

                        //checking all of their moves
                        for (int tempRow = 0; tempRow < numRows(); tempRow++) {
                            for (int tempCol = 0; tempCol < numColumns(); tempCol++) {
                                Move tempMove = new Move(row, col, tempRow, tempCol);

                                // create a temporary chess board and fill with pieces from
                                // actual board of game
                                IChessPiece[][] tempBoard = new IChessPiece[numRows()][numColumns()];
                                for (int r = 0; r < numRows(); r++) {
                                    for (int c = 0; c < numColumns(); c++) {
                                        tempBoard[r][c] = pieceAt(r, c);
                                    }
                                }

                                // checking if the tempPiece is a valid move
                                if (tempPiece.isValidMove(tempMove, tempBoard)) {
                                    if (inCheck(this.currentPlayer()) == true) {
                                        tempBoard[tempMove.toRow][tempMove.toColumn] =
                                                tempBoard[tempMove.fromRow][tempMove.fromColumn];

                                        // remove original piece moving
                                        tempBoard[tempMove.fromRow][tempMove.fromColumn] = null;

                                        // checking if you are still in check after the move
                                        if (inCheckTemp(this.currentPlayer(), tempBoard) == false)
                                            return false;
                                        else
                                            valid = true;
                                    }
                                }
                            }
                        }
                    }
            }
        }
        return valid;
    }

    /*****************************************************************
     Checks to see if the selected move is a valid move for all general
     pieces

     @param move, the move the piece is trying to accomplish
     @throws IndexOutOfBoundsException when the move is out of bounds
     @return true if the move is a valid move, false if the move
     is not a valid move
     *****************************************************************/
    public boolean isValidMove(Move move) {

        //Checks to see if it is a valid place on the board.
        if ((move.fromRow > numRows() || move.fromRow < 0) || (move.fromColumn > numColumns()
                || move.fromColumn < 0) || (move.toRow > numRows() || move.toRow < 0)
                || (move.toColumn > numColumns() || move.toColumn < 0)) {

            // throw new IndexOutOfBoundsException
            throw new IndexOutOfBoundsException();
        }

        //Checks to see if it is a valid place on the board.
        if ((move.fromRow > numRows() || move.fromRow < 0) || (move.fromColumn > numColumns()
                || move.fromColumn < 0) || (move.toRow > numRows() || move.toRow < 0)
                || (move.toColumn > numColumns() || move.toColumn < 0)) {

            return false;
        }

        // only create temporary piece at location moving from if
        // a piece exists at that location
        if (board[move.fromRow][move.fromColumn] != null) {
            IChessPiece piece =
                    board[move.fromRow][move.fromColumn];

            // if temporary piece makes valid move, return true
            if (piece.isValidMove(move, board))
                return true;
        }
        return false;
    }

    /*****************************************************************
     Moves the piece to the selected location after checking that the
     validity of the move is true

     @param move, the move the piece is trying to accomplish
     @throws IndexOutOfBoundsException when move is out of bounds
     *****************************************************************/
    public void move(Move move) {
        // if either [move.fromRow][move.fromColumn] or
        // [move.toRow][move.toColumn] don't represent valid
        // locations on board, throw exception
        if ((move.fromRow > numRows() || move.fromRow < 0)
                || (move.fromColumn > numColumns()
                || move.fromColumn < 0)
                || (move.toRow > numRows() || move.toRow < 0)
                || (move.toColumn > numColumns()
                || move.toColumn < 0)) {

            // throw new IndexOutOfBoundsException
            throw new IndexOutOfBoundsException();
        }

        // create temporary piece from location moving from
        IChessPiece piece = board[move.fromRow][move.fromColumn];

        // if the Move object is valid and the player who owns the piece
        // is the current player, then move the piece
        if (isValidMove(move) && piece.player() == player) {

            // if the king has castled
            if (board[move.fromRow][move.fromColumn] == board[7][4] || board[move.fromRow][move.fromColumn] == board[0][4]) {
                if (((King) board[move.fromRow][move.fromColumn]).castled && ((King) board[move.fromRow][move.fromColumn]).hasMoved == false) {
                    // move the knight
                    Move move1 = new Move(move.fromRow, move.fromColumn + 3, move.toRow, move.fromColumn + 1);
                    saveMoveModel(move1);

                    board[move.toRow][move.fromColumn + 1] =
                            board[move.fromRow][move.fromColumn + 3];
                    // remove original piece moving
                    board[move.fromRow][move.fromColumn + 3] = null;

                    Move move2 = new Move(move.fromRow, move.fromColumn, move.toRow, move.fromColumn + 2);
                    saveMoveModel(move2);
                    //move king
                    board[move.fromRow][move.fromColumn + 2] =
                            board[move.fromRow][move.fromColumn];
                    // remove original piece moving
                    board[move.fromRow][move.fromColumn] = null;
                    return;
                }
            }

            saveMoveModel(move);

            board[move.toRow][move.toColumn] =
                    board[move.fromRow][move.fromColumn];

            // remove original piece moving
            board[move.fromRow][move.fromColumn] = null;

            // create a new window showing the player moves into check
            if (movesIntoCheck() == true) {
                undo();
                JOptionPane.showMessageDialog(null,
                        "Cannot Move into Check", "Check",
                        JOptionPane.INFORMATION_MESSAGE, null);
            }

            // now switch player's turn
            this.player = player.next();
        }
    }

    /*****************************************************************
     Checks to see if the player is currently in check.

     @return true if the player is in check and false if
     the player is not in check
     *****************************************************************/
    public boolean movesIntoCheck() {
        if (inCheck(player)) {
            return true;
        } else
            return false;
    }

    /*****************************************************************
     Checks to see if the players king is in check by searching for
     their king and then checking to see if it is a valid move for
     any opposite player pieces to take the king.

     @param p, the current players turn
     @return true if the player is in check, false if the player is
     not in check.
     *****************************************************************/
    public boolean inCheck(Player p) {
        boolean valid = false;
        int row = -1;
        int colum = -1;

        //iterate through the whole board to find the kings current row and column
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numColumns(); j++) {
                if (board[i][j] != null) {
                    if (board[i][j].player().equals(p) && board[i][j].
                            type().equals("King")) {
                        row = i;
                        colum = j;
                        break;
                    }
                }
            }
        }

        // check all the opposite players to see if they can kill the king
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numColumns(); j++) {
                if (board[i][j] != null && !board[i][j].player().equals(p)) {
                    Move s = new Move(i, j, row, colum);
                    if (board[i][j].isValidMove(s, board)) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    /*****************************************************************
     Checks to see if the temporary copy of the board's player is in check
     by searching for their king and then checking to see if it is a
     valid move for any opposite player pieces to take the king

     @param p, the player whose turn it is
     @param tempBoard, the temporary chess board
     @return true if the temporary board is in check and false if the
     temporary board is not in check
     *****************************************************************/
    public boolean inCheckTemp(Player p, IChessPiece[][] tempBoard) {
        boolean valid = false;

        //iterate through the whole board to find the kings current row and column
        int row = -1;
        int colum = -1;

        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numColumns(); j++) {
                if (tempBoard[i][j] != null) {
                    if (tempBoard[i][j].player().equals(p) && tempBoard[i][j].type().equals("King")) {
                        row = i;
                        colum = j;
                        break;
                    }
                }
            }
        }

        // check all the opposite players to see if they can kill the king
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numColumns(); j++) {
                if (tempBoard[i][j] != null && !tempBoard[i][j].player().equals(p)) {
                    Move s = new Move(i, j, row, colum);
                    if (tempBoard[i][j].isValidMove(s, tempBoard)) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    /*****************************************************************
     Returns the current player

     @return the current player
     *****************************************************************/
    public Player currentPlayer() {
        return player;
    }

    /*****************************************************************
     Returns the number of rows

     @return the number of rows, which is 8
     *****************************************************************/
    public int numRows() {
        return 8;
    }

    /*****************************************************************
     Returns the number of columns

     @return the number of columns, which is 8
     *****************************************************************/
    public int numColumns() {
        return 8;
    }

    /*****************************************************************
     Returns what piece is at a specific row and column

     @param  row, the row of the chess board
     @param  column, the row of the column
     @return board, the chess board full of pieces
     *****************************************************************/
    public IChessPiece pieceAt(int row, int column) {
        return board[row][column];
    }

    /*****************************************************************
     Shifts to the next players turn
     *****************************************************************/
    public void setNextPlayer() {
        player = player.next();
    }

    /*****************************************************************
     Sets the given piece at the row and column given

     @param row, the row the piece is set in
     @param column, the column the piece is set in
     @param piece, the piece to be set
     *****************************************************************/
    public void setPiece(int row, int column, IChessPiece piece) {
        board[row][column] = piece;
    }

    /*****************************************************************
     Creates an AI for the player to play against
     *****************************************************************/
    public void AI() {

        // 1. Get out of check 2. Attack and put into check
        // 3. Get out of danger 4. Attack 5. Move
        if (getOutOfCheck() == false)
            if (canPutIntoCheck() == false)
                if (canTakePiece() == false)
                    if (getOutOfDanger() == false)
                        if (moveUp() == false)
                            moveUp();
    }

    /*****************************************************************
     Checks to see if the AI is in danger of a piece getting taken
     and moving that piece

     @return true if the AI piece moved out of danger and false
     if the AI piece couldn't move from danger
     *****************************************************************/
    public boolean getOutOfDanger() {
        boolean flag = false;

        // find a black player
        for (int toRow = 0; toRow < numRows(); toRow++) {
            for (int toCol = 0; toCol < numColumns(); toCol++) {
                if (pieceAt(toRow, toCol) != null &&
                        pieceAt(toRow, toCol).player().equals(Player.BLACK)) {

                    // try to move that black player
                    for (int fromRow = toRow; fromRow < numRows(); fromRow++) {
                        for (int fromCol = toRow; fromCol < numColumns(); fromCol++) {

                            Move move = new Move(fromRow, fromCol, toRow, toCol);
                            IChessPiece piece = pieceAt(fromRow, fromCol);

                            // if it can take a white player, do it!
                            if (pieceAt(fromRow, fromCol) != null)
                                if (pieceAt(fromRow, fromCol).player().equals(Player.WHITE)
                                        && piece.isValidMove(move, board)) {
                                    Move move1 = new Move(fromRow, fromCol, toRow, toCol);

                                    // if it is a valid move do it
                                    if (piece.isValidMove(move1, board)) {

                                        //find a way for black to move out
                                        for (int toRowB = 0; toRowB < numRows(); toRowB++) {
                                            for (int toColB = 0; toColB < numColumns(); toColB++) {
                                                Move moveA = new Move(toRow, toCol, toRowB, toColB);
                                                IChessPiece piece1 = pieceAt(toRow, toCol);

                                                // if black has a valid move, move out of danger
                                                if (piece1.isValidMove(moveA, board)) {
                                                    saveMoveModel(moveA);
                                                    setPiece(toRowB, toColB, piece1);
                                                    board[toRow][toCol] = null;
                                                    setNextPlayer();
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
        return flag;
    }

    /*****************************************************************
     Checks to see if the AI is in check and attempts to move out
     of check

     @return true if the AI piece moved out of check and false
     if the AI was not in check or couldn't move
     *****************************************************************/
    public boolean getOutOfCheck() {
        if (inCheck(Player.BLACK) == true) {
            // find a black piece
            for (int fromRow = 0; fromRow < numRows(); fromRow++) {
                for (int fromCol = 0; fromCol < numColumns(); fromCol++) {
                    if (pieceAt(fromRow, fromCol) != null &&
                            pieceAt(fromRow, fromCol).player().equals(Player.BLACK)) {

                        // try all of AI pieces moves
                        for (int toRow = 0; toRow < numRows(); toRow++) {
                            for (int toCol = 0; toCol < numColumns(); toCol++) {

                                Move move = new Move(fromRow, fromCol, toRow, toCol);
                                IChessPiece piece = pieceAt(fromRow, fromCol);

                                // if AI has a valid move then move it
                                if (piece.isValidMove(move, board)) {
                                    saveMoveModel(move);
                                    setPiece(toRow, toCol, piece);
                                    board[fromRow][fromCol] = null;
                                    setNextPlayer();

                                    // if it is still in check, undo it
                                    if (inCheck(Player.BLACK) == true)
                                        undo();
                                    else
                                        return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /*****************************************************************
     Tries to find a piece for the AI to attack and attacks
     the player if there is a piece found

     @return true if the AI piece attacked a white player and false
     if the AI could not find an attack
     *****************************************************************/
    public boolean canTakePiece() {
        boolean flag = false;

        // find a black player
        for (int fromRow = 0; fromRow < numRows(); fromRow++) {
            for (int fromCol = 0; fromCol < numColumns(); fromCol++) {
                if (pieceAt(fromRow, fromCol) != null &&
                        pieceAt(fromRow, fromCol).player().equals(Player.BLACK)) {

                    // try to move that black player
                    for (int toRow = fromRow; toRow < numRows(); toRow++) {
                        for (int toCol = fromCol; toCol < numColumns(); toCol++) {

                            Move move = new Move(fromRow, fromCol, toRow, toCol);
                            IChessPiece piece = pieceAt(fromRow, fromCol);

                            // if it can take a white player, do it!
                            if (pieceAt(toRow, toCol) != null)
                                if (pieceAt(toRow, toCol).player().equals(Player.WHITE)
                                        && piece.isValidMove(move, board)) {
                                    Move move1 = new Move(fromRow, fromCol, toRow, toCol);

                                    // if it is a valid move destroy the white piece
                                    if (piece.isValidMove(move1, board)) {
                                        saveMoveModel(move1);
                                        setPiece(toRow, toCol, piece);
                                        board[fromRow][fromCol] = null;
                                        setNextPlayer();
                                        flag = true;
                                        return flag;
                                    }
                                }
                        }
                    }
                }
            }
        }
        return flag;
    }

    /*****************************************************************
     Checks to see if the AI can put the White player into check

     @return true if the AI piece moved to put white into check
     if the AI piece couldn't put the white player into check
     *****************************************************************/
    public boolean canPutIntoCheck() {
        boolean flag = false;

        // find a black player
        for (int fromRow = 0; fromRow < numRows(); fromRow++) {
            for (int fromCol = 0; fromCol < numColumns(); fromCol++) {
                if (pieceAt(fromRow, fromCol) != null &&
                        pieceAt(fromRow, fromCol).player().equals(Player.BLACK)) {

                    // try to move that black player
                    for (int toRow = fromRow; toRow < numRows(); toRow++) {
                        for (int toCol = fromCol; toCol < numColumns(); toCol++) {

                            Move move = new Move(fromRow, fromCol, toRow, toCol);
                            IChessPiece piece = pieceAt(fromRow, fromCol);

                            // if it can take a white player, do it!
                            if (pieceAt(toRow, toCol) != null)
                                if (pieceAt(toRow, toCol).player().equals(Player.WHITE)
                                        && piece.isValidMove(move, board)) {
                                    Move move1 = new Move(fromRow, fromCol, toRow, toCol);

                                    // if it is a valid move destroy the white piece
                                    if (piece.isValidMove(move1, board)) {
                                        saveMoveModel(move1);
                                        setPiece(toRow, toCol, piece);
                                        board[fromRow][fromCol] = null;
                                        setNextPlayer();

                                        // If it put the white player into check do it
                                        if (inCheck(Player.WHITE) == true) {
                                            flag = true;
                                            return flag;
                                        }
                                        // See if there is another move to attack and put into check
                                        else
                                            undo();
                                    }
                                }
                        }
                    }
                }
            }
        }
        return flag;
    }

    /*****************************************************************
     Attempts to move any of the pieces up

     @return true if the AI piece was moved up and false
     if the AI piece was not moved up
     *****************************************************************/
    public boolean moveUp() {

        //makes a random move in the forward motion
        for (int fromRow = 0; fromRow < numRows(); fromRow++) {
            for (int fromCol = 0; fromCol < numColumns(); fromCol++) {
                if (pieceAt(fromRow, fromCol) != null &&
                        pieceAt(fromRow, fromCol).player().equals(Player.BLACK)) {
                    for (int toRow = fromRow; toRow < numRows(); toRow++) {
                        for (int toCol = fromCol; toCol < numColumns(); toCol++) {

                            Move move = new Move(fromRow, fromCol, toRow, toCol);
                            IChessPiece piece = pieceAt(fromRow, fromCol);

                            // tries to move the pawn up
                            if (pieceAt(fromRow, fromCol).type().equals("Pawn") && piece.isValidMove(move, board)) {
                                Move movePawn = new Move(fromRow, fromCol, toRow + 1, toCol);
                                if (piece.isValidMove(movePawn, board)) {
                                    saveMoveModel(movePawn);
                                    setPiece(toRow + 1, toCol, piece);
                                    board[fromRow][fromCol] = null;
                                    setNextPlayer();
                                    return true;
                                } else {
                                    saveMoveModel(move);
                                    setPiece(toRow, toCol, piece);
                                    board[fromRow][fromCol] = null;
                                    setNextPlayer();
                                    return true;
                                }

                                // tries to move the knight up
                            } else if (pieceAt(fromRow, fromCol).type().equals("Knight") && piece.isValidMove(move, board)) {
                                saveMoveModel(move);
                                setPiece(toRow, toCol, piece);
                                board[fromRow][fromCol] = null;
                                setNextPlayer();
                                return true;

                                // tries to move the bishop up
                            } else if (pieceAt(fromRow, fromCol).type().equals("Bishop") && piece.isValidMove(move, board)) {
                                saveMoveModel(move);
                                setPiece(toRow, toCol, piece);
                                board[fromRow][fromCol] = null;
                                setNextPlayer();
                                return true;

                                // tries to move the rook up
                            } else if (pieceAt(fromRow, fromCol).type().equals("Rook") && piece.isValidMove(move, board)) {
                                saveMoveModel(move);
                                setPiece(toRow, toCol, piece);
                                board[fromRow][fromCol] = null;
                                setNextPlayer();
                                return true;

                                // tries to move the queen up
                            } else if (pieceAt(fromRow, fromCol).type().equals("Queen") && piece.isValidMove(move, board)) {
                                saveMoveModel(move);
                                setPiece(toRow, toCol, piece);
                                board[fromRow][fromCol] = null;
                                setNextPlayer();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /*****************************************************************
     * Main program to test the piece movement
     *****************************************************************/
    public static void main(String[] args) {
        ChessModel cb = new ChessModel();
        Move m = new Move(7, 1, 5, 2);
        cb.move(m);

        if (cb.isValidMove(m) == true)
            cb.move(m);

        cb.display();
    }

    /*****************************************************************
     Gets the current board
     @return the current board
     *****************************************************************/
    public IChessPiece[][] getBoard() {
        return board;
    }

    /*****************************************************************
     Displays the current board
     *****************************************************************/
    public void display() {
        IChessPiece[][] board = getBoard();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c] != null)
                    System.out.print(board[r][c].type() + "\t");
                else
                    System.out.print("null\t");
            }
            System.out.println();
        }
    }

    /*****************************************************************
     Saves the move made after checking validity is true and adds
     it to multiple array lists

     @param move, the move to be saved
     *****************************************************************/
    public void saveMoveModel(Move move) {
        moves.add(move);
        saveFromPiece = board[move.fromRow][move.fromColumn];
        savePieceFrom.add(saveFromPiece);
        saveToPiece = board[move.toRow][move.toColumn];
        savePieceTo.add(saveToPiece);
    }

    /*****************************************************************
     Undoes the last move made and switches the player back, allows
     multiple undos by storing the moves in array lists
     *****************************************************************/
    public void undo() {

        // remove the moves from the arrayList
        if (moves.size() > 0) {
            board[moves.get(moves.size() - 1).toRow][moves.get(moves.size() - 1).toColumn]
                    = savePieceTo.get(savePieceTo.size() - 1);
            board[moves.get(moves.size() - 1).fromRow][moves.get(moves.size() - 1).fromColumn]
                    = savePieceFrom.get(savePieceFrom.size() - 1);
            savePieceTo.remove(savePieceTo.size() - 1);
            savePieceFrom.remove(savePieceFrom.size() - 1);
            moves.remove(moves.size() - 1);

            setNextPlayer();
        }

        // display a window if there is no more undo's
        else {
            JOptionPane.showMessageDialog(null,
                    "No Moves to Undo!");
        }
    }

    /*****************************************************************
     Method to check for promotion of Pawn to a Queen
     *****************************************************************/
    public void promotion() {

        //Loop the ends of the board to look for pawns on opposite side
        for (int col = 0; col < numColumns(); col++) {

            //Checks to see if space is null
            if (board[0][col] != null) {

                //Check if a white pawn is on black end
                if(pieceAt(7,col) != null)
                if (pieceAt(0,col).player().equals(Player.WHITE)
                        && pieceAt(0,col).type().equals("Pawn")) {
                    IChessPiece piece = new Queen(Player.WHITE);
                    setPiece(0,col,piece);
                }

                //Check if a black pawn is on white end
                if(pieceAt(7,col) != null)
                if (pieceAt(7,col).player().equals(Player.BLACK)
                        && pieceAt(7,col).type().equals("Pawn")) {
                    IChessPiece pieceB = new Queen(Player.BLACK);
                    setPiece(7,col,pieceB);
                }
            }
        }

        //Loop the ends of the board to look for pawns on opposite side
        for (int col = 0; col < numColumns(); col++) {

            //Checks to see if space is null
            if (board[0][col] != null && board[7][0] != null) {

                //Check if a white pawn is on black end
                if (pieceAt(0,col).player().equals(Player.WHITE)
                        && pieceAt(0,col).type().equals("Pawn")
                        && pieceAt(0,col) != null) {
                    IChessPiece piece = new Queen(Player.WHITE);
                    setPiece(0,col,piece);
                }

                //Check if a black pawn is on white end
                if(pieceAt(7,col) != null)
                if (pieceAt(7,col).player().equals(Player.BLACK)
                        && pieceAt(7,col).type().equals("Pawn")) {
                    IChessPiece pieceB = new Queen(Player.BLACK);
                    setPiece(7,col,pieceB);
                }
            }
        }
    }
}







