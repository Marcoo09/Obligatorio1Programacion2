package domains;

import java.util.ArrayList;
/**
 * @author Felipe Najson and Marco Fiorito
 */
public class GameBoard{

    private Token[][] tokenMatrix;
    private ArrayList<Player> listOfPlayers;
    private int tokenPositionX;
    private int tokenPositionY;
    private String mode = "VERN";

    public static String[] posibleDirectionsMovements = {"I", "A", "D"};

    public GameBoard(ArrayList<Player> listOfPlayers) {
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

    public Player getPlayerRed() {
        return this.listOfPlayers.get(1);
    }

    public Player getPlayerBlue() {
        return this.listOfPlayers.get(0);
    }

    public ArrayList<Integer> getPossibleMovements(int parmNumber, int positionOfTokenX, int positionOfTokenY) {

        return this.sumOfDiagonalsAndEdges(parmNumber, positionOfTokenX, positionOfTokenY);
    }

    //Intial matrix of the game
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
                
                //if is the first the row, draw the tokens of blue player
                if (i == 0) {
                    currentToken.setPlayer(currentPlayer);
                    currentToken.setTokenNumber(tokenNumbers[j]);
                    currentToken.setColor("\033[34m");
                    this.tokenMatrix[i][j] = currentToken;
                }
                //if is the the row is the last, draw the tokens of red player
                else {
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
                    if (currentToken.getTokenNumber() == tokenNumber && currentToken.getPlayer().equals(aPlayer)) {
                        positionX = j;
                        positionY = i;
                        founded = true;
                    }
                }
            }
        }
        this.setTokenPositionX(positionX);
        this.setTokenPositionY(positionY);

    }

    public ArrayList<Integer> sumOfDiagonalsAndEdges(int initialValue, int positionOfTokenX, int positionOfTokenY) {
        ArrayList<Integer> sum = new ArrayList<>();
        //Initial point of diagonal
        int positionX = positionOfTokenX;
        int positionY = positionOfTokenY;

        int firstDiagonal = initialValue;
        int secondDiagonal = initialValue;
        int horizontalSum = initialValue;
        int verticalSum = initialValue;

        //First Diagonal
        while (positionY != 0 && positionX != 0) {
            positionY--;
            positionX--;
            if (tokenMatrix[positionY][positionX] != null) {
                firstDiagonal += tokenMatrix[positionY][positionX].getTokenNumber();
            }
        }

        positionX = positionOfTokenX;
        positionY = positionOfTokenY;

        while (positionY != tokenMatrix.length - 1 && positionX != tokenMatrix[0].length - 1) {
            positionY++;
            positionX++;
            if (tokenMatrix[positionY][positionX] != null) {
                firstDiagonal += tokenMatrix[positionY][positionX].getTokenNumber();
            }
        }

        if (firstDiagonal != initialValue && firstDiagonal < 9 && !(sum.contains(firstDiagonal))) {
            sum.add(firstDiagonal);
        }

        positionX = positionOfTokenX;
        positionY = positionOfTokenY;

        //Second Diagonala
        while (positionY != 0 && positionX != tokenMatrix[0].length - 1) {
            positionY--;
            positionX++;
            if (tokenMatrix[positionY][positionX] != null) {
                secondDiagonal += tokenMatrix[positionY][positionX].getTokenNumber();
            }
        }
        positionX = positionOfTokenX;
        positionY = positionOfTokenY;

        while (positionY != tokenMatrix.length - 1 && positionX != 0) {
            positionY++;
            positionX--;
            if (tokenMatrix[positionY][positionX] != null) {
                secondDiagonal += tokenMatrix[positionY][positionX].getTokenNumber();
            }
        }

        if (secondDiagonal != initialValue && secondDiagonal < 9 && !(sum.contains(secondDiagonal))) {
            sum.add(secondDiagonal);
        }

        positionX = positionOfTokenX;
        positionY = positionOfTokenY;

        //Horizontal Sum
        for (int i = 0; i < tokenMatrix.length; i++) {
            if (tokenMatrix[i][positionX] != null && i != positionY) {
                horizontalSum += tokenMatrix[i][positionX].getTokenNumber();
            }
        }

        if (horizontalSum != initialValue && horizontalSum < 9 && !(sum.contains(horizontalSum))) {
            sum.add(horizontalSum);
        }

        //Vertical Sum
        for (int j = 0; j < tokenMatrix[0].length; j++) {
            if (tokenMatrix[positionY][j] != null && j != positionX) {
                verticalSum += tokenMatrix[positionY][j].getTokenNumber();
            }
        }

        if (verticalSum != initialValue && verticalSum < 9 && !(sum.contains(verticalSum))) {
            sum.add(verticalSum);
        }

        
        return sum;
    }

}
