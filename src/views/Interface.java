package views;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import domains.Game;
import domains.GameBoard;
import domains.Match;
import domains.Movement;
import domains.Player;
import domains.Token;
import java.util.Arrays;

/**
 * @author Marco Fiorito and Felipe Najson
 */
public class Interface {

    public Interface(){
        System.out.println("..:: Bienvenido al juego Sumas ::..");
    }
    
    public static boolean executeMenu(Game game, GameBoard gameboard, String[] menuOptions) {
        
        /*Validator of Program*/
        boolean executeProgram = true;
        
        System.out.println("\n<--------INGRESE UNA DE LAS SIGUIENTES OPCIONES DEL MENÚ(NÚMERO)-------->\n");
        
        for (int i = 0; i < menuOptions.length; i++) {
            int value = i + 97;
            System.out.println((char) value + " - " + menuOptions[i]);
        }

        String entry = Interface.askForString("opción").toLowerCase();
        switch (entry) {
            case "a":
                Player newPlayer = Interface.addPlayer();
                game.addPlayer(newPlayer);
                System.out.println("JUGADOR REGISTRADO");
                break;
            case "b":
                if (game.getListOfPlayers().size() > 1) {
                    Match match = Interface.beginMatch(game);
                    game.addMatch(match);
                    System.out.println("EMPIEZA EL JUEGO");

                    gameboard = new GameBoard(match.getListOfPlayers());
                    
                    String mode = Interface.askForMode();
                    
                    Interface.drawDefaultGameBoard(gameboard, mode, match);
                    
                    Interface.turnByTurn(game,match,gameboard);
                    
                    Interface.anounceWinner(match);
                } else {
                    System.out.println("Debes registrar por lo menos dos jugadores");
                }
                break;
            case "c":
                if (game.getListOfMatches().isEmpty()) {
                    System.out.println("Debes jugar antes alguna partida para poder replicar una");
                } else {
                    Interface.replayMatch(game);
                }
                break;
            case "d":
                if (game.getListOfPlayers().isEmpty()) {
                    System.out.println("Debes registrar algún jugador antes.");
                } else {
                    System.out.println("Ranking");
                    //Controller.getRanking();
                }
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

        return executeProgram;
    }
    
    public static Player addPlayer() {
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
        return new Player(name, nickName, age);

    }
    
    public static void anounceWinner(Match match) {
        Player playerWinner = match.getWinner();
        playerWinner.setWonGames(playerWinner.getWonGames() + 1);
        
        System.out.println("El jugador que ha ganado es: \n");
        System.out.println(playerWinner);
    }


    public static Match beginMatch(Game game) {
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
        for (int i = 0; i < sizeOfPlayerList; i++) {
            System.out.print((i + 1) + ": " + game.getListOfPlayers().get(i));
        }
        while (!firstPlayerIsCorrect) {
            System.out.println("");
            p1 = Interface.askForNumeric("el jugador que tendrá el color azul");
            firstPlayerIsCorrect = Interface.validateAttribute(p1, 1, sizeOfPlayerList);
        }
        player1 = possiblePlayers.get(p1 - 1);

        while (!SecondPlayerIsCorrect) {
            System.out.println("");
            p2 = Interface.askForNumeric("el jugador que tendra el color rojo");
            SecondPlayerIsCorrect = Interface.validateAttribute(p2, 1, sizeOfPlayerList);
            if (p1 == p2) {
                SecondPlayerIsCorrect = false;
                System.out.println("Elige un jugador distinto al 1");
            }
        }
        player2 = possiblePlayers.get(p2 - 1);

        System.out.println("Elige una de las siguientes formas de terminar el juego: ");
        System.out.println("");
        for (int i = 0; i < wayToFinishOptions.length; i++) {
            System.out.println((i + 1) + " " + wayToFinishOptions[i]);
        }
        while (!wayToFinishValidator) {
            chosenOption = Interface.askForNumeric("opción");
            System.out.println("");
            wayToFinishValidator = validateAttribute(chosenOption, 1, 3);
        }
        if (chosenOption == 1) {
            while (!qtyOfMovementsValidator) {
                qtyOfMovements = Interface.askForNumeric("cantidad movimientos que desea");
                System.out.println("");
                qtyOfMovementsValidator = Interface.validateAttribute(qtyOfMovements, 0, Integer.MAX_VALUE);
            }
        }

        return new Match(player1, player2, Match.ways[chosenOption - 1], qtyOfMovements);
    }

    
    public static void turnByTurn(Game game, Match match, GameBoard gameboard) {
        Movement movement = new Movement(gameboard);
        
        boolean isFinished = false;
        
        while(!isFinished){
            
            Interface.turnRed(game, match, gameboard);
            
            Interface.turnBlue(game, match, gameboard);
            
            isFinished = match.isFinished();
        }
        
    }
    
    public static void turnRed(Game game, Match match, GameBoard gameboard) {
        Scanner input = new Scanner(System.in);
       
        boolean isTurnRed = true;
        boolean validInputMovement = false;
        boolean validMovement;
        ArrayList<Integer> posibleTokenMovements = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
        
        String allDataAboutMovement;
        int tokenToMove = 0;
        String movementDirection;
        
        while(isTurnRed){
            System.out.println("Posibles movimientos:");
            Interface.showPosibleDirectionsMovements(posibleTokenMovements);
            System.out.println("\n(D)DERECHA (I)IZQUIERA (A)ADELANTE \n");
            while(!validInputMovement){

                allDataAboutMovement = Interface.askForString("una ficha acompañada del movimiento que deseas");
                allDataAboutMovement.trim();
                if(allDataAboutMovement.length() == 2){
                    try{
                        tokenToMove = allDataAboutMovement.charAt(0);
                        movementDirection = Character.toString(allDataAboutMovement.charAt(1));

                        if(Interface.validateAttribute(tokenToMove, 1, 8) && Interface.validMovementDirectionInput(movementDirection)){
                            //Hay que hacer la valdiación de movimiento
                            Interface.drawCurrentGameBoard(gameboard);
                        }
                    } catch (InputMismatchException e) {
                        if (e.toString().equals("java.util.InputMismatchException")) {
                            System.out.println("Debes ingresar un número");
                        } else {
                            System.out.println("Debes ingresar un número más corto");
                        }
                        validInputMovement = false;
                        input.next();
                    }
                }else{
                    System.out.println("Ingrese un valor correcto");
                }

            }
            
        }  
    }

    public static void turnBlue(Game game, Match match, GameBoard gameboard) {
        Scanner input = new Scanner(System.in);
       
        boolean isTurnBlue = true;
        boolean validInputMovement = false;
        boolean validMovement;
        ArrayList<Integer> posibleTokenMovements = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
        
        String allDataAboutMovement;
        int tokenToMove = 0;
        String movementDirection;
        
        while(isTurnBlue){
            System.out.println("Posibles movimientos:");
            Interface.showPosibleDirectionsMovements(posibleTokenMovements);
            System.out.println("\n(D)DERECHA (I)IZQUIERA (A)ADELANTE \n");
            while(!validInputMovement){

                allDataAboutMovement = Interface.askForString("una ficha acompañada del movimiento que deseas");
                allDataAboutMovement.trim();
                if(allDataAboutMovement.length() == 2){
                    try{
                        tokenToMove = allDataAboutMovement.charAt(0);
                        movementDirection = Character.toString(allDataAboutMovement.charAt(1));

                        if(Interface.validateAttribute(tokenToMove, 1, 8) && Interface.validMovementDirectionInput(movementDirection)){
                            //Hay que hacer la valdiación de movimiento
                            Interface.drawCurrentGameBoard(gameboard);
                        }
                    } catch (InputMismatchException e) {
                        if (e.toString().equals("java.util.InputMismatchException")) {
                            System.out.println("Debes ingresar un número");
                        } else {
                            System.out.println("Debes ingresar un número más corto");
                        }
                        validInputMovement = false;
                        input.next();
                    }
                }

            }
            
        }
    }
    
    public static void drawDefaultGameBoard(GameBoard gameboard, String mode, Match match) {
        Token tokenMatrix[][] = gameboard.getTokenMatrix();
        int row = tokenMatrix.length;
        int col = tokenMatrix[0].length;
        int[] tokens = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        
        gameboard.fillInitialMatrix(tokens);
        match.setGameBoard(gameboard);
        
        if (mode.equals("verr")) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (tokenMatrix[i][j] != null) {
                        System.out.print(tokenMatrix[i][j].getColor() + tokenMatrix[i][j].getTokenNumber() + " ");
                    } else {
                        System.out.print("\033[30m" + "- ");
                    }
                }
                System.out.println("");
            }
        }else if (mode.equals("vern")) {
            for (int i = 0; i < row; i++) {
                System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                for (int j = 0; j < col; j++) {
                    if (tokenMatrix[i][j] != null) {
                        System.out.print("| " + tokenMatrix[i][j].getColor() + tokenMatrix[i][j].getTokenNumber() +"\033[30m ");
                    } else {
                        System.out.print("\033[30m" + "|   ");
                    }
                }
                System.out.print("|");

                System.out.println("");
            }
            System.out.print("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");

        }        

    }
    
    public static void drawCurrentGameBoard(GameBoard gameboard) {
        
        //Hay que rescribirlo
        
        Scanner in = new Scanner(System.in);
        Token[][] gameBoard = gameboard.getTokenMatrix();
        int x = 0;
        int y = 0;

        System.out.println("COMIENZA EL JUGADOR AZUL");
        System.out.println("\nELIGA PRIMERA PIEZA A MOVER");
        int piezaAuxiliar = in.nextInt();
        for (int i = 0; i < gameboard.getTokenMatrix().length; i++) {
            System.out.println("entro");
            for (int j = 0; j < gameboard.getTokenMatrix()[0].length; j++) {
                if (gameBoard[i][j] != null) {
                    if (gameBoard[i][j].getTokenNumber() == piezaAuxiliar && gameBoard[i][j].getPlayer() == gameboard.getPlayerRed()) {
                        x = j;
                        y = i;
                    }
                }

            }
        }
        System.out.println("\n(D)DERECHA (I)IZQUIERA (A)ADELANTE");
        String movimiento = Interface.askForString("Movimiento");

        if ("D".equalsIgnoreCase(movimiento)) {
            System.out.println("D");
            gameBoard[y - 1][x + 1] = gameBoard[y][x];
        }
        if ("I".equals(movimiento)) {
            System.out.println("I");
            gameBoard[y - 1][x - 1] = gameBoard[y][x];
        }
        if ("A".equals(movimiento)) {
            System.out.println("A");
            gameBoard[y - 1][x] = gameBoard[y][x];
        }

        System.out.println("Salio 2");
        for (int i = 0; i < gameboard.getTokenMatrix().length; i++) {
            for (int j = 0; j < gameboard.getTokenMatrix()[0].length; j++) {
                if (gameBoard[i][j] == null) {
                    System.out.print("-");
                } else {
                    System.out.print(gameBoard[i][j].getTokenNumber());
                }
            }
            System.out.println("");
        }

    }    
    
    public static void replayMatch(Game game) {
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
        while (!indexOfMatchValidator) {
            chosenOption = Interface.askForNumeric("partida");
            indexOfMatchValidator = Interface.validateAttribute(chosenOption, 1, listOfMatches.size());
        }
        Match selectedMatch = listOfMatches.get(chosenOption - 1);
        ArrayList<GameBoard> listOfGameBoards = selectedMatch.getListOfGameBoard();

        System.out.println("Presione n para avanzar de jugada o s para salir\n");
        for (int i = 0; i < listOfGameBoards.size() && !exitValidator; i++) {
            entry = "";
            Interface.drawCurrentGameBoard(listOfGameBoards.get(i));
            while (!entry.equals("n") && !exitValidator) {
                entry = input.nextLine();
                exitValidator = entry.equals("s");

            }
        }

    }
 
    /*Utils used above*/
    
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
    
    public static String askForMode() {
        String ret = "";
        boolean isCorrect = false;
        
        while(!isCorrect){
            ret = Interface.askForString("modo a ver el tablero");
            ret.toLowerCase();
            if(ret.equalsIgnoreCase("verr") || ret.equalsIgnoreCase("vern")){
                isCorrect = true;
            }
        }
        
        return ret;
    }
    
   //Range Validator
    public static boolean validateAttribute(int numberToValidate, int intialRange, int finalRange) {
        //Check if the first parameter is between the range
        boolean returnValue = (numberToValidate >= intialRange && numberToValidate <= finalRange);
        if (!returnValue) {
            System.out.println("Ingrese un valor entre " + intialRange + " - " + finalRange);
        }
        return returnValue;
    }
    
    public static boolean validMovementDirectionInput(String inputMovement){
        boolean isValidMovement = false;
        for (int i = 0; i < GameBoard.posibleDirectionsMovements.length; i++) {
            String currentValue = GameBoard.posibleDirectionsMovements[i];
            if(inputMovement.equalsIgnoreCase(currentValue)){
                isValidMovement = true;
            }
        }
        return isValidMovement;
    }
    
    public static void showPosibleDirectionsMovements(ArrayList<Integer> posibleTokenMovents){
        for (int i = 0; i < posibleTokenMovents.size(); i++) {
            System.out.print(posibleTokenMovents.get(i) + " - " );
        }
    }
}
