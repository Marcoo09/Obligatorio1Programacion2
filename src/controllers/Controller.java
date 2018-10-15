package controllers;

import domains.*;
import views.Interface;

/*
 * @author Marco Fiorito and Felipe Najson
 */
public class Controller {

    public static void main(String[] args){
        
        /*Instance of view*/
        Interface view = new Interface();
                
        /*General Variables*/
        Game game = new Game();
        GameBoard gameboard = null;
        
        /*Ways to finalize*/
        String[] menuOptions = {"Registrar jugador", "Jugar Partida", "Replicar Partida", "Ranking", "Salir"};
        
        /*Validator of Program*/
        boolean executeProgram = true;
                
        while (executeProgram) {
            executeProgram = Interface.executeMenu(game, gameboard, menuOptions);
        }

    }

}
