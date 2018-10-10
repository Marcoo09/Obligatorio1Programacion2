package domains;

import java.util.ArrayList;

/**
 * @author Felipe Najson and Marco Fiorito
 */
public class GameBoard {

    private Token[][] tokenMatrix;
    private ArrayList<Player> listOfPlayers;
    private int tokenPositionX;
    private int tokenPositionY;
    
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
    public Player getPlayerRed(){
        return this.listOfPlayers.get(1);
    }  
    public Player getPlayerBlue(){
        return this.listOfPlayers.get(1);
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
}
