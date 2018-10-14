    package domains;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Felipe Najson and Marco Fiorito
 */

public class Game {
    private ArrayList<Match> listOfMatches;
    private ArrayList<Player> listOfPlayers;
    
    public Game(){
        listOfMatches = new ArrayList<>();      
        listOfPlayers = new ArrayList<>();
    }
    
    public ArrayList<Match> getListOfMatches() {
        return listOfMatches;
    }

    public void addMatch(Match match){
        this.getListOfMatches().add(match);
    }
    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }
    
    public void addPlayer(Player player){
        this.getListOfPlayers().add(player);
    }
   
    public ArrayList<GameBoard> replayMatch(Match match){
        return new ArrayList();
    }
    
    public void sortMatchesByDateTime(){
        Collections.sort(this.getListOfMatches());
    }
    public void sortPlayersByWonGames(){
        Collections.sort(listOfPlayers);
    }
    
}
