package domains;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Felipe Najson and Marco Fiorito
 */
public class Match implements Comparable {

    private ArrayList<Player> listOfPlayers;
    private ArrayList<GameBoard> listOfGameBoards;
    private LocalDateTime date;
    private Player winner;
    private int remainingPlays;
    private String wayToFinish;
    private int qtyOfMovements = 0;

    public static String[] ways = {"movimientos", "pieza", "piezas"};

    public Match(Player player1, Player player2, String wayToFinish, int qtyOfMovements) {
        listOfPlayers = new ArrayList<>();
        this.setPlayer(player1);
        this.setPlayer(player2);

        listOfGameBoards = new ArrayList<>();
        this.setDate(LocalDateTime.now());
        this.setWayToFinish(wayToFinish);
        this.setQtyOfMovements(qtyOfMovements);
    }

    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setPlayer(Player player) {
        this.getListOfPlayers().add(player);
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
        return listOfGameBoards;
    }

    public boolean isFinished() {
        String wayToFinalize = this.getWayToFinish();
        boolean returnedValue = false;
        boolean allReds = false;
        boolean allBlues = false;
        int qtyOfReds = 0;
        int qtyOfBlues = 0;

        GameBoard lastGameBoard = this.getListOfGameBoard().get(this.getListOfGameBoard().size() - 1);
        Token[][] lastMatrix = lastGameBoard.getTokenMatrix();
        
        //Case 1
        if (wayToFinalize.equals("movimientos")) {
            int qtyOfMovements = this.getQtyOfMovements();
            this.setQtyOfMovements(qtyOfMovements - 1);
            if (qtyOfMovements == 0) {
            //Al llegar el contador a cero, me fijo cual tiene mas fichas del otro lado
                for (int i = 0; i < 9; i++) {
                    if (lastMatrix[0][i] != null && lastMatrix[0][i].getPlayer().equals(lastGameBoard.getPlayerRed())) {
                        qtyOfReds++;
                    }
                }
                //Recorro ultima fila, verifico que sean todos azules
                for (int i = 0; i < 9 && allBlues; i++) {
                    if (lastMatrix[7][i] != null && lastMatrix[7][i].getPlayer().equals(lastGameBoard.getPlayerBlue())) {
                        qtyOfBlues++;
                    }
                }
                //Eligo el ganador
                if (qtyOfReds > qtyOfBlues) {
                    this.setWinner(lastGameBoard.getPlayerRed());
                }else if(qtyOfReds < qtyOfBlues){
                    this.setWinner(lastGameBoard.getPlayerBlue());
                }else{
                    this.setWinner(null);
                }
                returnedValue = true;
            }
        //Case 2    
        } else if (wayToFinalize.equals("pieza")) {
            for (int i = 0; i < 9 && !returnedValue; i++) {
                if (lastMatrix[0][i] != null && lastMatrix[0][i].getPlayer().equals(lastGameBoard.getPlayerRed())) {
                    returnedValue = true;
                    this.setWinner(lastGameBoard.getPlayerRed());
                }
                if (lastMatrix[7][i] != null && lastMatrix[7][i].getPlayer().equals(lastGameBoard.getPlayerBlue())) {
                    returnedValue = true;
                    this.setWinner(lastGameBoard.getPlayerBlue());
                }
            }
        //Case 3
        } else {
            
            //Recorro primera fila, verifico que sean todos rojos
            for (int i = 0; i < 9 && allReds; i++) {
                if (lastMatrix[0][i] != null && lastMatrix[0][i].getPlayer().equals(lastGameBoard.getPlayerRed())) {
                    allReds = true;
                } else {
                    allReds = false;
                }
                if (allReds) {
                    returnedValue = true;
                    this.setWinner(lastGameBoard.getPlayerRed());
                }
            }
            //Recorro ultima fila, verifico que sean todos azules
            for (int i = 0; i < 9 && allBlues; i++) {
                if (lastMatrix[7][i] != null && lastMatrix[7][i].getPlayer().equals(lastGameBoard.getPlayerBlue())) {
                    allBlues = true;
                } else {
                    allBlues = false;
                }

                if (allBlues) {
                    returnedValue = true;
                    this.setWinner(lastGameBoard.getPlayerBlue());
                }
            }
            if(!(allBlues || allReds)){
                this.setWinner(null);
            }
        }
        return returnedValue;
    }

    @Override
    public int compareTo(Object t) {
        Match parm = (Match) t;
        return this.getDateTime().compareTo(parm.getDateTime());
    }

}
