package obligatorio1;

import java.util.ArrayList;

/*
 * @author Felipe Najson and Marco Fiorito
 */
public class Movement {
    private int tokenPositionX;
    private int tokenPositionY;
    private GameBoard currentGameBoard;
    private ArrayList<Integer> possibleMovements;

    public Movement(int tokenPositionX, int tokenPositionY, GameBoard currentGameBoard, ArrayList<Integer> possibleMovements) {
        this.tokenPositionX = tokenPositionX;
        this.tokenPositionY = tokenPositionY;
        this.currentGameBoard = currentGameBoard;
        this.possibleMovements = possibleMovements;
    }

    public int getTokenPositionX() {
        return tokenPositionX;
    }

    public void setTokenPositionX(int tokenPositionX) {
        this.tokenPositionX = tokenPositionX;
    }

    public int getTokenPositionY() {
        return tokenPositionY;
    }

    public void setTokenPositionY(int tokenPositionY) {
        this.tokenPositionY = tokenPositionY;
    }

    public GameBoard getCurrentGameBoard() {
        return currentGameBoard;
    }

    public void setCurrentGameBoard(GameBoard currentGameBoard) {
        this.currentGameBoard = currentGameBoard;
    }

    public ArrayList<Integer> getPossibleMovements() {
        return possibleMovements;
    }

    public void setPossibleMovements(ArrayList<Integer> possibleMovements) {
        this.possibleMovements = possibleMovements;
    }

    /**/
    public void sumOfDiagonalsAndEdges(){
        
    }
    
    public Boolean validMovement(){
        return true;
    }
    @Override
    public String toString() {
        return "Movement{" + "tokenPositionX=" + tokenPositionX + ", tokenPositionY=" + tokenPositionY + ", currentGameBoard=" + currentGameBoard + ", possibleMovements=" + possibleMovements + '}';
    }
    
}
