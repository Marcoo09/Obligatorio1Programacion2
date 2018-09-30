/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

/**
 *
 * @author Marco Fiorito and Felipe Najson
 */

public class Token {
    private String color;
    private int tokenNumber;
    private Player player;

    public Token(String color, int tokenNumber, Player player) {
        this.color = color;
        this.tokenNumber = tokenNumber;
        this.player = player;
    }
    
    public Token(){
        this.color = "\033[30m" ;
        this.tokenNumber = 0;
        this.player = null;
    }
 
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
        
}
