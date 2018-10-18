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
    private String wayToFinish;
    private int qtyOfMovements;
    private boolean finished;

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

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean getFinished() {
        return this.finished;
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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ArrayList<GameBoard> getListOfGameBoard() {
        return listOfGameBoards;
    }

    public void setGameBoard(GameBoard gameboard) {
        this.getListOfGameBoard().add(gameboard);
    }

    public boolean isFinished() {
        String wayToFinalize = this.getWayToFinish();
        boolean returnedValue = false;
        int qtyOfReds = 0;
        int qtyOfBlues = 0;

        GameBoard lastGameBoard = this.getListOfGameBoard().get(this.getListOfGameBoard().size() - 1);
        Token[][] lastMatrix = lastGameBoard.getTokenMatrix();

        //Case 1
        if (wayToFinalize.equals("movimientos")) {
            int qtyOfMovements = this.getQtyOfMovements();
            this.setQtyOfMovements(qtyOfMovements - 1);
            
            if (this.getQtyOfMovements() == 0) {
                returnedValue = true;
                this.setFinished(true);
                //Verify what player have more token in the other side
                for (int i = 0; i < 9; i++) {
                    if (lastMatrix[0][i] != null && lastMatrix[0][i].getPlayer().equals(lastGameBoard.getPlayerRed())) {
                        qtyOfReds++;
                    }
                }
                //Verify if all are blue 
                for (int i = 0; i < 9; i++) {
                    if (lastMatrix[7][i] != null && lastMatrix[7][i].getPlayer().equals(lastGameBoard.getPlayerBlue())) {
                        qtyOfBlues++;
                    }
                }
                //Choose the winner
                if (qtyOfReds > qtyOfBlues) {
                    this.setWinner(lastGameBoard.getPlayerRed());
                } else if (qtyOfReds < qtyOfBlues) {
                    this.setWinner(lastGameBoard.getPlayerBlue());
                } else {
                    this.setWinner(null);
                }
            }
            //Case 2    
        } else if (wayToFinalize.equals("pieza")) {
            for (int i = 0; i < 9 && !returnedValue; i++) {
                if (lastMatrix[0][i] != null && lastMatrix[0][i].getPlayer().equals(lastGameBoard.getPlayerRed())) {
                    this.setFinished(true);
                    returnedValue = true;
                    this.setWinner(lastGameBoard.getPlayerRed());
                }
                if (lastMatrix[7][i] != null && lastMatrix[7][i].getPlayer().equals(lastGameBoard.getPlayerBlue())) {
                    this.setFinished(true);
                    returnedValue = true;
                    this.setWinner(lastGameBoard.getPlayerBlue());
                }
            }
            //Case 3
        } else {
            int qtyOfRedsTokens = 0;
            int qtyOfBluesTokens = 0;
            
            //Verify if all tokens are red
            for (int i = 0; i < 9; i++) {
                if (lastMatrix[0][i] != null && (lastMatrix[0][i].getPlayer().equals(lastGameBoard.getPlayerRed())) ) {
                    qtyOfRedsTokens++;
                }
            }

            if (qtyOfRedsTokens == 8) {
                this.setFinished(true);
                returnedValue = true;
                this.setWinner(lastGameBoard.getPlayerRed());
            }
            
            //Verify if all tokens are red
            for (int i = 0; i < 9; i++) {
                if (lastMatrix[7][i] != null && lastMatrix[7][i].getPlayer().equals(lastGameBoard.getPlayerBlue())) {
                    qtyOfBluesTokens++;
                }
            }
            
            if (qtyOfBluesTokens == 8) {
                this.setFinished(true);
                returnedValue = true;
                this.setWinner(lastGameBoard.getPlayerBlue());
            }
        }

        return returnedValue;
    }

    @Override
    public int compareTo(Object t) {
        Match parm = (Match) t;
        return this.getDateTime().compareTo(parm.getDateTime());
    }

    @Override
    public String toString() {
        LocalDateTime time = this.getDateTime();
        return " Día: " + time.getDayOfMonth() + ". Hora: " + time.getHour() + ":" + time.getMinute() + " - Y la forma de finalización es: "  + this.getWayToFinish();
    }

}
