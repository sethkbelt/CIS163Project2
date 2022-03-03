package chess;

import org.junit.Test;

import static org.junit.Assert.*;

public class chessJunits {

    private Object model;
    ChessModel cb = new ChessModel();

    @Test
    public void testPawnBlack() {
        Move m = new Move(6, 0, 5, 0);
        Move m1 = new Move(1, 1, 2, 1);

        if (cb.isValidMove(m) == true)
            cb.move(m);
        if (cb.isValidMove(m1) == true)
            cb.move(m1);

        assertEquals("Pawn", cb.getBoard()[2][1].type());

    }

    @Test
    public void testPawnWhiteTwo() {
        // moves pawn ahead two
        Move m = new Move(6, 1, 4, 1);

        if (cb.isValidMove(m) == true)
            cb.move(m);

        assertEquals("Pawn", cb.getBoard()[4][1].type());

    }

    @Test
    public void testRook() {
        //white pawn, black pawn, white rook
        Move m = new Move(6, 0, 5, 0);
        Move m1 = new Move(1, 1, 2, 1);
        Move m2 = new Move(7, 0, 6, 0);

        cb.move(m);
        cb.move(m1);
        cb.move(m2);

        assertEquals("Rook", cb.getBoard()[6][0].type());
    }

    @Test
    public void testBishop() {
        //white pawn, black pawn, white bishop
        Move m = new Move(6, 1, 5, 1);
        Move m1 = new Move(1, 1, 2, 1);
        Move m2 = new Move(7, 2, 5, 0);

        cb.move(m);
        cb.move(m1);
        cb.move(m2);

        assertEquals("Bishop", cb.getBoard()[5][0].type());
    }

    @Test
    public void testKnight() {
        //white knight
        Move m = new Move(7, 1, 5, 2);
        cb.move(m);
        assertEquals("Knight", cb.getBoard()[5][2].type());
    }

    @Test
    public void testQueen() {
        //white pawn, black pawn, white bishop
        Move m = new Move(6, 3, 5, 3);
        Move m1 = new Move(1, 1, 2, 1);
        Move m2 = new Move(7, 3, 6, 3);

        cb.move(m);
        cb.move(m1);
        cb.move(m2);

        assertEquals("Queen", cb.getBoard()[6][3].type());
    }

    @Test
    public void testKing() {
        //white pawn, black pawn, white bishop
        Move m = new Move(6, 4, 5, 4);
        Move m1 = new Move(1, 1, 2, 1);
        Move m2 = new Move(7, 4, 6, 4);

        cb.move(m);
        cb.move(m1);
        cb.move(m2);

        assertEquals("King", cb.getBoard()[6][4].type());
    }
}




