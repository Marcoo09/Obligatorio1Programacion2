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

    public ArrayList<Integer> getPossibleMovements(Token parmToken) {
        return this.sumOfDiagonalsAndEdges(parmToken);
    }

    public void setPossibleMovements(ArrayList<Integer> possibleMovements) {
        this.possibleMovements = possibleMovements;
    }

    public ArrayList<Integer> sumOfDiagonalsAndEdges(Token parmToken) {
        Token[][] tokenMatrix = this.getCurrentGameBoard().getTokenMatrix();
        int initialValue = parmToken.getTokenNumber();
        ArrayList<Integer> sum = new ArrayList<>(Arrays.asList(initialValue, initialValue, initialValue, initialValue));

        //Initial point of diagonal
        int positionX = this.getTokenPositionX();
        int positionY = this.getTokenPositionY();
        
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

    public void searchToken(Token token) {
        Token[][] tokenMatrix = this.getCurrentGameBoard().getTokenMatrix();
        int positionX = 0;
        int positionY = 0;

        for (int i = 0; i < tokenMatrix.length; i++) {
            for (int j = 0; j < tokenMatrix[0].length; j++) {
                if (tokenMatrix[i][j] != null) {
                    if (tokenMatrix[i][j].getTokenNumber() == token.getTokenNumber() && tokenMatrix[i][j].getPlayer() == token.getPlayer()) {
                        positionX = j;
                        positionY = i;
                    }
                }
            }
        }
        this.setTokenPositionX(positionX);
        this.setTokenPositionY(positionY);
    }

    public Boolean validBluePlayerMovement(Token parmToken, int positionX, int positionY) {
        
        //El problema aca es si realmente es necesario realizar este metodo de esta forma
        //Validar un movimiento puede ser simplemente evaluar si no hay una ficha nuestra o del rival adelante
        //además de evaluar si no se sale del tablero
        //
        
        return true;
    }
    
    public Boolean validRedPlayerMovement(Token parmToken, int positionX, int positionY) {
        
        //El problema aca es si realmente es necesario realizar este metodo de esta forma
        //Validar un movimiento puede ser simplemente evaluar si no hay una ficha nuestra o del rival adelante
        //además de evaluar si no se sale del tablero
        //
        
        return true;
    }

    @Override
    public String toString() {
        return "Movement{" + "tokenPositionX=" + tokenPositionX + ", tokenPositionY=" + tokenPositionY + ", currentGameBoard=" + currentGameBoard + ", possibleMovements=" + possibleMovements + '}';
    }

}
