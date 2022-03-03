package chess;


import java.util.ArrayList;

/**********************************************************************
 * This program saves the current chess board into an array List
 *
 @author Seth Konynenbelt, Chad VanderWall, Isabella Snyder
 @version Winter 2022
 **********************************************************************/

public class saveState {

    public int saveFromColumn;
    public int saveFromRow;
    public IChessPiece saveFromPiece;
    public int saveToRow;
    public int saveToColumn;
    public IChessPiece saveToPiece;

    private ArrayList<saveState> list = new ArrayList<saveState>();

    /**********************************************************************
     * saves a move into an Arraylist
     * @param save, the move to be saved
     **********************************************************************/
    public void saveMove(saveState save) {
        list.add(save);
        System.out.println(list);
    }

    /**********************************************************************
     * removes a move from the arrayList
     **********************************************************************/
    public void removeFromList(){
        list.remove(list.size()-1);
    }
}
