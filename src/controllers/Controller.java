package controllers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import domains.Game;
import domains.GameBoard;
import domains.Match;
import domains.Player;
import domains.Token;
import views.Interface;

/*
 * @author Marco Fiorito and Felipe Najson
 */
public class Controller {

    public static void main(String[] args) {
        
        /*Instance of view*/
        Interface view = new Interface();
                
        /*General Variables*/
        Game game = new Game();
        GameBoard gameboard = null;
        
        /*Ways to finalize*/
        String[] menuOptions = {"Registrar jugador", "Jugar Partida", "Replicar Partida", "Ranking", "Salir"};
        
        /*Validator of Program*/
        boolean executeProgram = Interface.executeMenu(game, gameboard, menuOptions);
                
        while (executeProgram) {
            Interface.executeMenu(game, gameboard, menuOptions);
        }

    }

}
