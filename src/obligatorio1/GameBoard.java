package obligatorio1;

import java.util.ArrayList;

/**
 *
 * @author Felipe Najson and Marco Fiorito
 */
public class GameBoard {

    private Token[][] tokenState;
    private ArrayList<Player> listOfPlayers;

    public GameBoard( ArrayList<Player> listOfPlayers) {
        this.tokenState = new Token[8][9];
        this.listOfPlayers = listOfPlayers;
    }

    public void drawDefaultGameBoard(String mode) {
        int row = this.tokenState.length;
        int col = this.tokenState[0].length;
        int[] tokens = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        this.fillMatrix(tokens);

        if (mode.equalsIgnoreCase("verr")) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (this.tokenState[i][j] != null) {
                        System.out.print(this.tokenState[i][j].getColor() + this.tokenState[i][j].getTokenNumber() + " ");
                    } else {
                        System.out.print("\033[30m" + "- ");
                    }
                }
                System.out.println("");
            }
        } else if (mode.equalsIgnoreCase("vern")) {
            for (int i = 0; i < row; i++) {
                System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                for (int j = 0; j < col; j++) {
                    if (this.tokenState[i][j] != null) {
                        System.out.print("| " + this.tokenState[i][j].getColor() + this.tokenState[i][j].getTokenNumber() +"\033[30m ");
                    } else {
                        System.out.print("\033[30m" + "|   ");
                    }
                }
                System.out.print("|");

                System.out.println("");
            }
            System.out.print("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");

        }        

    }

    public void drawCurrentGameBoard() {

    }

    public void fillMatrix(int[] tokenNumbers) {
        int row = this.tokenState.length;
        int col = this.tokenState[0].length;
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
                    this.tokenState[i][j] = currentToken;
                } else {
                    currentToken.setPlayer(currentPlayer);
                    currentToken.setTokenNumber(tokenNumbers[col - j]);
                    currentToken.setColor("\033[31m");
                    this.tokenState[i][j - 1] = currentToken;
                }
            }
            counter++;
        }
    }
}
