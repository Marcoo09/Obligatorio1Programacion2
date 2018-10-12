package domains;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Felipe Najson and Marco Fiorito
 */
public class GameBoard {

    private Token[][] tokenMatrix;
    private ArrayList<Player> listOfPlayers;
    private int tokenPositionX;
    private int tokenPositionY;
    private String mode = "VERN";
    
    public static String[] posibleDirectionsMovements = {"I","A","D"}; 
    
    public GameBoard( ArrayList<Player> listOfPlayers) {
        this.tokenMatrix = new Token[8][9];
        this.listOfPlayers = listOfPlayers;
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

    public Token[][] getTokenMatrix() {
        return tokenMatrix;
    }

    public void setTokenMatrix(Token[][] tokenMatrix) {
        this.tokenMatrix = tokenMatrix;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    
    public Player getPlayerRed(){
        return this.listOfPlayers.get(1);
    }  
    
    public Player getPlayerBlue(){
        return this.listOfPlayers.get(0);
    }  
    
    public ArrayList<Integer> getPossibleMovements(int parmNumber, int positionOfTokenX, int positionOfTokenY) {
        
        return this.sumOfDiagonalsAndEdges(parmNumber, positionOfTokenX, positionOfTokenY);
    }

    public void fillInitialMatrix(int[] tokenNumbers) {
        int row = this.tokenMatrix.length;
        int col = this.tokenMatrix[0].length;
        int counter = 0;
        Player currentPlayer;

        Token currentToken = new Token();

        for (int i = 0; i < row; i += row - 1) {
            currentPlayer = listOfPlayers.get(counter);
            for (int j = 1; j < col; j++) {
                currentToken = new Token();
                if (i == 0) {
                    currentToken.setPlayer(currentPlayer);
                    currentToken.setTokenNumber(tokenNumbers[j]);
                    currentToken.setColor("\033[34m");
                    this.tokenMatrix[i][j] = currentToken;
                } else {
                    currentToken.setPlayer(currentPlayer);
                    currentToken.setTokenNumber(tokenNumbers[col - j]);
                    currentToken.setColor("\033[31m");
                    this.tokenMatrix[i][j - 1] = currentToken;
                }
            }
            counter++;
        }
    }
    
    public void searchPositionOfToken(int tokenNumber, Player aPlayer) {
        Token[][] tokenMatrix = this.getTokenMatrix();
        int positionX = 0;
        int positionY = 0;
        boolean founded = false;
        
        for (int i = 0; i < tokenMatrix.length && !founded; i++) {
            for (int j = 0; j < tokenMatrix[0].length && !founded; j++) {
                Token currentToken = tokenMatrix[i][j];
                if (currentToken != null) {
                    if (currentToken.getTokenNumber() == tokenNumber && currentToken.getPlayer().equals(aPlayer) ) {
                        positionX = j;
                        positionY = i;
                        founded = true;
                    }
                }
            }
        }
        this.setTokenPositionX(positionX);
        this.setTokenPositionY(positionY);
        
        //return new int[]{positionX, positionY};
    }
    
    public ArrayList<Integer> sumOfDiagonalsAndEdges(int initialValue, int positionOfTokenX, int positionOfTokenY) {
        ArrayList<Integer> sum = new ArrayList<>(Arrays.asList(initialValue, initialValue, initialValue, initialValue));
        //Initial point of diagonal
        int positionX = positionOfTokenX;
        int positionY = positionOfTokenY;
        int tokenNumber= 0;
        
        //Primera diagonal
        while (positionY != 0 && positionX != 0) {
            positionY--;
            positionX--;
            if (tokenMatrix[positionY][positionX] != null) {
                tokenNumber = tokenMatrix[positionY][positionX].getTokenNumber();
                sum.set(0, sum.get(0)+tokenNumber);
            }
        }
        tokenNumber= 0;

        positionX = positionOfTokenX;
        positionY = positionOfTokenY;

        while (positionY != tokenMatrix.length - 1 && positionX != tokenMatrix[0].length - 1) {
            positionY++;
            positionX++;
            if (tokenMatrix[positionY][positionX] != null) {
                tokenNumber = tokenMatrix[positionY][positionX].getTokenNumber();
                sum.set(0, sum.get(0)+tokenNumber);
            }
        }

        positionX = positionOfTokenX;
        positionY = positionOfTokenY;
        tokenNumber= 0;
        //2da
        while (positionY != 0 && positionX != tokenMatrix[0].length - 1) {
            positionY--;
            positionX++;
            if (tokenMatrix[positionY][positionX] != null) {
                tokenNumber = tokenMatrix[positionY][positionX].getTokenNumber();
                sum.set(1, sum.get(1)+tokenNumber);   
            }
        }
        positionX = positionOfTokenX;
        positionY = positionOfTokenY;
        tokenNumber= 0;
        while (positionY != tokenMatrix.length - 1 && positionX != 0) {
            positionY++;
            positionX--;
            if (tokenMatrix[positionY][positionX] != null) {
                tokenNumber = tokenMatrix[positionY][positionX].getTokenNumber();
                sum.set(1, sum.get(1)+tokenNumber);
            }
        }

        positionX = positionOfTokenX;
        positionY = positionOfTokenY;
        tokenNumber= 0;                
        for (int i = 0; i < tokenMatrix.length; i++) {
            if (tokenMatrix[i][positionX] != null && i != positionY) {
                tokenNumber = tokenMatrix[i][positionX].getTokenNumber();
                sum.set(2, sum.get(2)+tokenNumber);
            }
        }
        tokenNumber= 0;
        for (int j = 0; j < tokenMatrix[0].length; j++) {
            if (tokenMatrix[positionY][j] != null && j != positionX) {
                 tokenNumber = tokenMatrix[positionY][j].getTokenNumber();
                sum.set(3, sum.get(3)+tokenNumber); 
            }
        }
                
        for (int i = 0; i < sum.size(); i++) {
            if (sum.get(i)==initialValue|| sum.get(i)>8) {
                sum.remove(i);
                i--;
            }
        }
         return sum;
    }
}
