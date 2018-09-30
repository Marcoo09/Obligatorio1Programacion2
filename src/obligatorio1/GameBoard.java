
package obligatorio1;

import java.util.ArrayList;

/**
 *
 * @author Felipe Najson and Marco Fiorito
 */
public class GameBoard {
    private int turn;
    private Token[][] tokenState;
    private ArrayList<Player> listOfPlayers;
    
    public GameBoard(int turn, ArrayList<Player> listOfPlayers) {
        this.turn = turn;
        this.tokenState = new Token[8][9];
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    
    public void drawDefaultGameBoard(String mode){
        int row = this.tokenState.length;
        int col = this.tokenState[0].length;        
        int[] tokens = {0,1,2,3,4,5,6,7,8,9};
        this.fillMatrix(tokens);
        
        if(mode.equalsIgnoreCase("verr")){
            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    if(this.tokenState[i][j] != null){
                         System.out.print(this.tokenState[i][j].getColor() + this.tokenState[i][j].getTokenNumber() + " ");                        
                    }else{
                        System.out.print("\033[30m" + "- ");
                    }
                }
                System.out.println("");
            }            
        }else if(mode.equalsIgnoreCase("vern")){
            
            
        }
    /*
        int[][] initGameBoard = {{0,1,2,3,4,5,6,7,8},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{1,2,3,4,5,6,7,8,0}};
        int[][] player = {{0,1,1,1,1,1,1,1,1},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{2,2,2,2,2,2,2,2,0}};
        initGameBoard("verr",initGameBoard,player);
    }
    
    public static void drawGameBoard(String mode){
        if(mode.equalsIgnoreCase("verr")){
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 9; j++){
                    System.out.print("- ");
                }
                System.out.println("");
            }
        }else if(mode.equalsIgnoreCase("vern")){
             for(int i = 0; i < 8; i++){
                 System.out.println("+-+-+-+-+-+-+-+-+-+");
                for(int j = 0; j < 10; j++){
                    System.out.print("| ");
                }
                System.out.println("");
            }
            System.out.println("+-+-+-+-+-+-+-+-+-+");
        }
    }
    
    public static void initGameBoard(String mode, int[][] estado,int[][] player){
        String black = "\033[30m"; 
        String red = "\033[31m"; 
        String green = "\033[32m"; 
        String yellow = "\033[33m"; 
        String blue = "\033[34m"; 
        String purple = "\033[35m"; 
        String cyan = "\033[36m"; 
        String white = "\033[37m";
        String reset = "\u001B[0m";
        
        if(mode.equalsIgnoreCase("verr")){
            for(int i = 0; i < estado.length; i++){
                for(int j = 0; j < estado[0].length; j++){
                    if(estado[i][j] != 0){
                        if(player[i][j] == 1){
                            System.out.print(blue + estado[i][j] + " ");                        
                        }else{
                            System.out.print(red + estado[i][j] + " ");
                        }
                    }else{
                        System.out.print(black + "- ");
                    }
                }
                System.out.println("");
            }
        }else if(mode.equalsIgnoreCase("vern")){
             for(int i = 0; i < estado.length; i++){
                 System.out.println("+-+-+-+-+-+-+-+-+-++-+-+-+-+-+-+-+-+-");
                for(int j = 0; j < estado[0].length; j++){
                    if(estado[i][j] != 0){
                        if(player[i][j] == 1){
                            System.out.print("| " + blue + estado[i][j] + " ");
                        }else{
                            System.out.print("| " + red + estado[i][j] + " ");
                        }
                    }else{
                        System.out.print( black + "|   ");
                    }
                }
                System.out.print("|");

                System.out.println("");
            }
            System.out.print("+-+-+-+-+-+-+-+-+-++-+-+-+-+-+-+-+-+-");
        }
    }
        */
        
    }
    
    public void drawCurrentGameBoard(){
        
    }
    
    public void fillMatrix(int[] tokenNumbers){
        int row = this.tokenState.length;
        int col = this.tokenState[0].length;
        int counter = 0;
        Player currentPlayer;
        
        Token currentToken = new Token();
        
        for(int i = 0; i < col; i = col - 1){
            currentPlayer = listOfPlayers.get(counter);
            
            for (int j = 1; j < col; j++) {
                if(i==0){
                    currentToken.setPlayer(currentPlayer);
                    currentToken.setTokenNumber(tokenNumbers[j]);
                    currentToken.setColor("\\033[34m");
                }else{
                    currentToken.setPlayer(currentPlayer);
                    currentToken.setTokenNumber(tokenNumbers[col - j]);
                    currentToken.setColor("\033[31m");
                }
                this.tokenState[i][j] = currentToken;
            }
            counter++;
        }
    }
}

