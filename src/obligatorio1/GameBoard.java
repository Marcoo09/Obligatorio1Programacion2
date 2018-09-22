
package obligatorio1;

/**
 *
 * @author Felipe Najson and Marco Fiorito
 */
public class GameBoard {
    private int[][] tokenState;
    private int[][] playerState;
    private int turn;

    public GameBoard(int[][] tokenState, int[][] playerState, int turn) {
        this.tokenState = tokenState;
        this.playerState = playerState;
        this.turn = turn;
    }

    public int[][] getTokenState() {
        return tokenState;
    }

    public void setTokenState(int[][] tokenState) {
        this.tokenState = tokenState;
    }

    public int[][] getPlayerState() {
        return playerState;
    }

    public void setPlayerState(int[][] playerState) {
        this.playerState = playerState;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    
    public void drawDefaultGameBoard(){
        
    }
    
    public void drawCurrentGameBoard(){
        
    }
    
}

