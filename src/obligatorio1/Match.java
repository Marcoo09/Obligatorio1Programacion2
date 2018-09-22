
package obligatorio1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Felipe Najson and Marco Fiorito
 */
public class Match implements Comparable{
    private HashMap<Player,Integer> players;
    private ArrayList<GameBoard> listOfGameBoard;
    private LocalDateTime date;
    private Player winner;
    private int remainingPlays;
    private String wayToFinish;
    private int qtyOfMovements = 0;
    //iodsaojdsaiodiosadjoidsajoisdajiodsa
    
    static String[] ways = {"movimientos","pieza","piezas"};
    
    public Match(Player player1, Player player2, String wayToFinish, int qtyOfMovements){
        players = new HashMap<>();
        this.setPlayer(player1, 1);
        this.setPlayer(player2, 2);
        listOfGameBoard = new ArrayList<>();   
        this.setDate(LocalDateTime.now());
        this.setWayToFinish(wayToFinish);
        this.setQtyOfMovements(qtyOfMovements);
    }

    public HashMap<Player, Integer> getPlayers() {
        return players;
    }

    public void setPlayer(Player player ,int value) {
        this.getPlayers().put(player, value);
    }

    public String getWayToFinish() {
        return wayToFinish;
    }

    public void setWayToFinish(String wayToFinish) {
        this.wayToFinish = wayToFinish;
    }

    public int getQtyOfMovements() {
        return qtyOfMovements;
    }

    public void setQtyOfMovements(int qtyOfMovements) {
        this.qtyOfMovements = qtyOfMovements;
    }
    public LocalDateTime getDateTime() {
        return date;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getRemainingPlays() {
        return remainingPlays;
    }

    public void setRemainingPlays(int remainingPlays) {
        this.remainingPlays = remainingPlays;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public ArrayList<GameBoard> getListOfGameBoard() {
        return listOfGameBoard;
    }

    @Override
    public int compareTo(Object t) {
        Match parm = (Match)t;
        return this.getDateTime().compareTo(parm.getDateTime());
    }
    
}
