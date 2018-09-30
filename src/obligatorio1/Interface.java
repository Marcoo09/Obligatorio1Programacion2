package obligatorio1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * @author Marco Fiorito and Felipe Najson
 */
public class Interface {

    public static void main(String[] args) {
        Game game = new Game();
        GameBoard gameboard;
        
        String[] menuOptions = {"Registrar jugador", "Jugar Partida", "Replicar Partida","Ranking", "Salir"};
        boolean executeProgram = true;
        
       System.out.println("..:: Bienvenido al juego Sumas ::..");
            
        while(executeProgram){
            System.out.println("\n<--------INGRESE UNA DE LAS SIGUIENTES OPCIONES DEL MENÚ(NÚMERO)-------->\n");
            for (int i = 0; i < menuOptions.length; i++) {
                int value = i + 97;
                System.out.println((char)value + " - " + menuOptions[i]);
            }
            String entry = Interface.askForString("opción").toLowerCase();
            switch (entry) {
                case "a":
                    Player newPlayer = Interface.addPlayer();
                    game.addPlayer(newPlayer);
                    System.out.println("JUGADOR REGISTRADO");
                    break;
                case "b":
                    if(game.getListOfPlayers().size() > 1){
                       Match match = Interface.beginMatch(game);
                       game.addMatch(match);
                       System.out.println("EMPIEZA EL JUEGO");
                       gameboard = new GameBoard(match.getPlayers());
                       gameboard.drawDefaultGameBoard("vern");
                    }else{
                        System.out.println("Se debe registrar por lo menos dos jugadores");
                    }
                    break;
                case "c":
                    System.out.println("Replicar");
                    break;
                case "d":
                    System.out.println("Ranking");
                    break;
                case "e":
                    //Finalize the program
                    System.out.println("Salir");
                    executeProgram = false;
                    System.out.println("Ha finalizado la ejecución del programa");
                    break;
                default:
                    System.out.println("OPCIÓN NO VÁLIDA");
                    break;
            }
        }

    }
    
    public static Player addPlayer(){        
        //Variables of Player
        String name;
        String nickName;
        int age = 0;
        
        //Variables used in validators
        boolean ageValidator = false;
        //Scanner used in error handling
        Scanner input = new Scanner(System.in);

        System.out.println("\n<--------Ingrese los datos del jugador-------->\n ");

        name = Interface.askForString("nombre");
        nickName = Interface.askForString("nickname");
        //Validation of age
        while (!ageValidator) {
            age = Interface.askForNumeric("edad");
            ageValidator = Interface.validateAttribute(age, 0, 120);
        }

        //return the new object Player
        return new Player(name,nickName,age);
    
    }
    
    public static Match beginMatch(Game game){
        //Variable used in validators
        boolean firstPlayerIsCorrect = false;
        boolean SecondPlayerIsCorrect = false; 
        boolean wayToFinishValidator = false;
        boolean qtyOfMovementsValidator = false;
        
        int p1 = 0;
        int p2 = 0;
        int chosenOption = 0;
        String[] wayToFinishOptions = {"Termianar por cierta cantidad de movimientos", "Terminar por haber llevado al lado opuesto un pieza", "Terminar por haber llevado al lado opuesto todas las piezas"};
        /*Variables of the match*/
        Player player1;
        Player player2;
        String wayToFinish;
        int qtyOfMovements = 0;
        ArrayList<Player> possiblePlayers = game.getListOfPlayers();
        
        int sizeOfPlayerList = possiblePlayers.size();
        
        System.out.println("Elige los jugadores");
        for (int i = 0; i < sizeOfPlayerList ; i++) {
            System.out.print((i + 1) + " - " + game.getListOfPlayers().get(i));
        }
        while(!firstPlayerIsCorrect){
            System.out.println("");
            p1 = Interface.askForNumeric("el jugador que tendrá el color azul");
            firstPlayerIsCorrect = Interface.validateAttribute(p1, 1, sizeOfPlayerList);
        }
        player1 = possiblePlayers.get(p1 - 1);
        
        while(!SecondPlayerIsCorrect){
            System.out.println("");
            p2 = Interface.askForNumeric("el jugador que tendra el color rojo");
            SecondPlayerIsCorrect = Interface.validateAttribute(p2, 1, sizeOfPlayerList );
            if(p1 == p2){
                SecondPlayerIsCorrect = false;
                System.out.println("Elige un jugador distinto al 1");
            }
        }
        player2 = possiblePlayers.get(p2 - 1);
        
        System.out.println("Elige una de las siguientes formas de terminar el juego: ");
        for (int i = 0; i < wayToFinishOptions.length; i++) {
            System.out.println((i + 1) + " " + wayToFinishOptions[i]);
        }
        while(!wayToFinishValidator){
            chosenOption = Interface.askForNumeric("opción");
            wayToFinishValidator = validateAttribute(chosenOption, 1, 3);
        }
        if(chosenOption == 1){
            while(!qtyOfMovementsValidator){
                qtyOfMovements = Interface.askForNumeric("cantidad movimientos que desea.");
                qtyOfMovementsValidator = Interface.validateAttribute(qtyOfMovements, 0, Integer.MAX_VALUE);
            }
        }
        
        return new Match(player1,player2, Match.ways[chosenOption - 1],qtyOfMovements);
    }
    
    //Range Validator
    public static boolean validateAttribute(int numberToValidate, int intialRange, int finalRange) {
        //Check if the first parameter is between the range
        boolean returnValue = (numberToValidate >= intialRange && numberToValidate <= finalRange);
        if(!returnValue){
            System.out.println("Ingrese un valor entre " + intialRange + " - " + finalRange);
        }
        return returnValue;
    }
    
    //This method ask for a String and return the value
    public static String askForString(String whatToAsk) {
        Scanner inputString = new Scanner(System.in);
        System.out.print("Ingrese " + whatToAsk + ": ");
        return inputString.nextLine();
    }

    //This method ask for a Number and return the value
    public static int askForNumeric(String whatToAsk) {
        boolean repeat = true;
        Scanner inputNumeric = new Scanner(System.in);
        int ret = 0;

        while (repeat) {
            try {
                System.out.print("Ingrese " + whatToAsk + ": ");
                ret = inputNumeric.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                if (e.toString().equals("java.util.InputMismatchException")) {
                    System.out.println("Debes ingresar un número");
                } else {
                    System.out.println("Debes ingresar un número más corto");
                }
                repeat = true;
                inputNumeric.next();
            }
        }
        return ret;
    }

    public static void replayMatch(Game game){
        boolean indexOfMatchValidator = false;
        boolean exitValidator = false;
        int chosenOption = 0;
        String entry = "";
        
        Scanner input = new Scanner(System.in);
        
        ArrayList<Match> listOfMatches = game.getListOfMatches();
        game.sortMatchesByDateTime();
        
        System.out.println("Elige una de las siguientes partidas para repetir:\n");
        for (int i = 0; i < listOfMatches.size(); i++) {
            Match currentMatch = listOfMatches.get(i);
            System.out.println("Partida " + (i + 1) + ": " + currentMatch.getDateTime());
        }
        while(!indexOfMatchValidator){
            chosenOption = Interface.askForNumeric("partida");
            indexOfMatchValidator = Interface.validateAttribute(chosenOption, 1, listOfMatches.size());
        }
        Match selectedMatch = listOfMatches.get(chosenOption - 1);
        ArrayList<GameBoard> listOfGameBoards = selectedMatch.getListOfGameBoard();
        
        System.out.println("Presione n para avanzar de jugada o s para salir");
        for (int i = 0; i < listOfGameBoards.size() && !exitValidator; i++) {
            entry = "";
            listOfGameBoards.get(i).drawCurrentGameBoard();
            while(!entry.equals("n") && !exitValidator){
                entry = input.nextLine();
                exitValidator = entry.equals("s");

            }
        }
       
    }
}
