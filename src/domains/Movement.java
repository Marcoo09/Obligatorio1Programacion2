package domains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
 * @author Felipe Najson and Marco Fiorito
 */
public class Movement {

    private int tokenPositionX;
    private int tokenPositionY;
    private GameBoard currentGameBoard;
    private ArrayList<Integer> possibleMovements;

    public Movement(GameBoard currentGameBoard) {
        this.currentGameBoard = currentGameBoard;
        this.possibleMovements = new  ArrayList<>();
    }

    /*Getter and Setter of position*/
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

    /*More Getter and Setter*/
    public GameBoard getCurrentGameBoard() {
        return currentGameBoard;
    }

    public void setCurrentGameBoard(GameBoard currentGameBoard) {
        this.currentGameBoard = currentGameBoard;
    }

    public ArrayList<Integer> getPossibleMovements(int parmNumber, int positionOfTokenX, int positionOfTokenY) {
        Token token = new Token();
        token.setTokenNumber(parmNumber);
        
        return this.sumOfDiagonalsAndEdges(token, positionOfTokenX, positionOfTokenY);
    }

    public void setPossibleMovements(ArrayList<Integer> possibleMovements) {
        this.possibleMovements = possibleMovements;
    }

    public ArrayList<Integer> sumOfDiagonalsAndEdges(Token parmToken, int positionOfTokenX, int positionOfTokenY) {
        Token[][] tokenMatrix = this.getCurrentGameBoard().getTokenMatrix();
        int initialValue = parmToken.getTokenNumber();
        ArrayList<Integer> sum = new ArrayList<>(Arrays.asList(initialValue, initialValue, initialValue, initialValue));

        //Initial point of diagonal
        int positionX = positionOfTokenX;
        int positionY = positionOfTokenY;
        
        /*
        System.out.println("x " + positionX + " Y " + positionY);
        int firstDiagonal = 0;
        int secondDiagonal = 0;

        int verticalEdge = 0;
        int horizontalEdge = 0;
        
        int cantF = tokenMatrix.length - 1;
        int cantCol = tokenMatrix[0].length - 1;
        
        //Sum of diagonals
        
        int auxValue = 1, derAr, derAb,izAr,izAb,restaFil,sumaCol,restaCol,sumaFil;
        while((auxValue < Math.max(cantCol, cantF))){           
            restaFil = positionY - auxValue;
            sumaCol = positionX + auxValue; 
            sumaFil = positionY + auxValue; 
            restaCol = positionX - auxValue;
            System.out.println("RF " + restaFil + " SC " +sumaCol + " SF " + sumaFil + " RC " + restaCol );
            derAr = (restaFil >= 0 && sumaCol < cantCol)?tokenMatrix[restaFil][sumaCol].getTokenNumber():0;
            
            derAb = (sumaFil < cantF && sumaCol < cantCol)?tokenMatrix[sumaFil][sumaCol].getTokenNumber():0;
            
            izAr = (restaFil >= 0 && restaCol >= 0)?tokenMatrix[restaFil][restaCol].getTokenNumber():0;
            izAb = (sumaFil < cantF && restaCol >= 0 )?tokenMatrix[sumaFil][restaCol].getTokenNumber():0;

                        
            firstDiagonal += derAr + izAb;
            secondDiagonal += derAb + izAr;
            
            auxValue ++;
        }
        
        /*
        int auxValue = 1, rigthTop, rightBottom,leftTop,leftBottom,decrementRow,sumColumn,decrementColumn,sumRow;
        while((auxValue < Math.max(cantCol, cantF))){
            decrementRow = positionY - auxValue;
            sumColumn = positionX + auxValue; 
            sumRow = positionY + auxValue; 
            decrementColumn = positionX - auxValue;
            
            rigthTop = decrementRow >= 0 && sumColumn < cantCol?tokenMatrix[decrementRow][sumColumn].getTokenNumber():0;
            
            rightBottom = sumRow < cantF && sumColumn < cantCol?tokenMatrix[sumRow][sumColumn].getTokenNumber():0;
            
            leftTop = decrementRow >= 0 && decrementColumn >= 0?tokenMatrix[decrementRow][decrementColumn].getTokenNumber():0;
            
            leftBottom = sumRow < cantF && decrementColumn >= 0 ?tokenMatrix[sumRow][decrementColumn].getTokenNumber():0;
            
            firstDiagonal += rigthTop + leftBottom;
            secondDiagonal += rightBottom + leftTop;
            auxValue ++;
        }
        
        if(firstDiagonal < 9 && firstDiagonal != initialValue){
            sum.add(firstDiagonal);
        }
        
        if(secondDiagonal < 9 && secondDiagonal != initialValue){
            sum.add(secondDiagonal);
        }
        
        // Sum of edges
        
        for(int i = 0; i < cantF; i++){
            verticalEdge += tokenMatrix[i][positionX].getTokenNumber();
        }
        
        for(int i = 0;i < cantCol; i++){
            horizontalEdge += tokenMatrix[positionY][i].getTokenNumber();
        }
        
       if(verticalEdge < 9 && verticalEdge != initialValue){
            sum.add(verticalEdge);
        }
        
        if(horizontalEdge < 9 && horizontalEdge != initialValue){
            sum.add(horizontalEdge);
        }
        */
        //Sum of edges
        
        
        //Primera diagonal
        while (positionY != 0 && positionX != 0) {
            positionY--;
            positionX--;
            if (tokenMatrix[positionX][positionY] != null) {
                int tokenNumber = tokenMatrix[positionX][positionY].getTokenNumber();
                sum.add(0, sum.get(0) + tokenNumber);
            }

        }

        positionX = this.getTokenPositionX();
        positionY = this.getTokenPositionY();

        while (positionY != tokenMatrix.length - 1 && positionX != 0) {
            positionY++;
            positionX--;
            if (tokenMatrix[positionX][positionY] != null) {
                int tokenNumber = tokenMatrix[positionX][positionY].getTokenNumber();
                sum.add(0, sum.get(0) + tokenNumber);
            }
        }

        positionX = this.getTokenPositionX();
        positionY = this.getTokenPositionY();

        //2da
        while (positionY != 0 && positionX != tokenMatrix[0].length - 1) {
            positionY--;
            positionX++;
            if (tokenMatrix[positionX][positionY] != null) {
                int tokenNumber = tokenMatrix[positionX][positionY].getTokenNumber();
                sum.add(1, sum.get(1) + tokenNumber);                
            }
        }
        positionX = this.getTokenPositionX();
        positionY = this.getTokenPositionY();
        while (positionY != tokenMatrix.length - 1 && positionX != tokenMatrix[0].length - 1) {
            positionY++;
            positionX++;
            if (tokenMatrix[positionX][positionY] != null) {
                int tokenNumber = tokenMatrix[positionX][positionY].getTokenNumber();
                sum.add(1, sum.get(1) + tokenNumber);    
            }
        }

        positionX = this.getTokenPositionX();
        positionY = this.getTokenPositionY();
                        
        for (int i = 0; i < tokenMatrix.length; i++) {
            if (tokenMatrix[i][positionX] != null && i != positionY) {
                int tokenNumber = tokenMatrix[i][positionX].getTokenNumber();
                sum.add(2, sum.get(2) + tokenNumber);    
            }
        }
        for (int j = 0; j < tokenMatrix[0].length; j++) {
            if (tokenMatrix[positionY][j] != null && j != positionX) {
                int tokenNumber = tokenMatrix[positionY][j].getTokenNumber();
                sum.add(3, sum.get(3) + tokenNumber);  
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Movement{" + "tokenPositionX=" + tokenPositionX + ", tokenPositionY=" + tokenPositionY + ", currentGameBoard=" + currentGameBoard + ", possibleMovements=" + possibleMovements + '}';
    }

}
