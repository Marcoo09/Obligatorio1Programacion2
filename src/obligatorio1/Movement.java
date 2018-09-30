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
    public void sumOfDiagonalsAndEdges(){/*
        Scanner input = new Scanner(System.in);
        int fil = input.nextInt();
        int col = input.nextInt();
        int cantF = input.nextInt();
        int cantCol = input.nextInt();
        int [][] arr = new int [cantF][cantCol];
        
        for(int i = 0; i< cantF; i++){
            for(int j = 0; j< cantCol; j++){
                arr[i][j] = input.nextInt();
            }
        }
        boolean repetido = false;
        int iToCompare = arr[fil][col];
        int auxValue = 1, derAr, derAb,izAr,izAb,restaFil,sumaCol,restaCol,sumaFil;
        while(!repetido && (auxValue < Math.max(cantCol, cantF))){
            restaFil = fil - auxValue;
            sumaCol = col + auxValue; 
            sumaFil = fil + auxValue; 
            restaCol = col - auxValue;
            derAr = restaFil >= 0 && sumaCol < cantCol?arr[restaFil][sumaCol]:0;
            derAb = sumaFil < cantF && sumaCol < cantCol?arr[sumaFil][sumaCol]:0;
            izAr = restaFil >= 0 && restaCol >= 0?arr[restaFil][restaCol]:0;
            izAb = sumaFil < cantF && restaCol >= 0 ?arr[sumaFil][restaCol]:0;
            repetido = iToCompare == derAr || iToCompare == derAb || iToCompare == izAb || iToCompare == izAr?true:false;
            auxValue ++;
        }
        String output = repetido?"REPETIDO":"UNICO";
        System.out.println(output);
    */
    }
    
    public Boolean validMovement(){
        return true;
    }
    @Override
    public String toString() {
        return "Movement{" + "tokenPositionX=" + tokenPositionX + ", tokenPositionY=" + tokenPositionY + ", currentGameBoard=" + currentGameBoard + ", possibleMovements=" + possibleMovements + '}';
    }
    
}
