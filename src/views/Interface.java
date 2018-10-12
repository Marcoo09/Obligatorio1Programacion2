package views;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import domains.Game;
import domains.GameBoard;
import domains.Match;
import domains.Player;
import domains.Token;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Marco Fiorito and Felipe Najson
 */
public class Interface {

    public Interface() {
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

                    Interface.drawDefaultGameBoard(gameboard, match);

                    Interface.turnByTurn(game, match, gameboard);

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
            System.out.print("\n" + (i + 1) + ": " + game.getListOfPlayers().get(i));
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

        boolean isFinished = false;

        while (!isFinished) {

            System.out.println("Turno del jugador Rojo\n\n");

            Interface.turn(game, match, gameboard, "red");
            
            isFinished = match.isFinished();
            
            if(!isFinished){
                System.out.println("Turno del jugador Azul\n\n");
                Interface.turn(game, match, gameboard,"blue");                
            }

            isFinished = match.isFinished();
        }

    }

    public static void turn(Game game, Match match, GameBoard gameboard,String playerColor) {
        Scanner input = new Scanner(System.in);

        GameBoard actualGameBoard = gameboard;
        Player player = null;
                
        if(playerColor.equals("red")){
            player = actualGameBoard.getPlayerRed();
        }else if(playerColor.equals("blue")){
            player = actualGameBoard.getPlayerBlue();
        }

        boolean isTurnRed = true;
        boolean validMovement = false;
        boolean validPositionMovement;
        boolean playAtLeastOneTime;
        boolean validResponse = false;

        ArrayList<Integer> posibleTokenMovements = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

        String allDataAboutMovement;
        String movementDirection;
        String mode;
        String continueTurn;

        int tokenToMove = 0;
        int positionOfTokenX;
        int positionOfTokenY;

        while (isTurnRed) {
            playAtLeastOneTime = false;

            System.out.println("Posibles movimientos: ");
            Interface.showPosibleDirectionsMovements(posibleTokenMovements, tokenToMove);
            System.out.println("\n(D)DERECHA (I)IZQUIERA (A)ADELANTE \n");

            System.out.println("Posibles jugadas: \n                  MOVIMIENTO\n                  CAMBIO DE TABLERO\n                  X para retirarse");
            
            blockWhile:
            while (!validMovement) {
                System.out.println("");
                allDataAboutMovement = Interface.askForString("una jugada");
                allDataAboutMovement = allDataAboutMovement.trim().toLowerCase();

                if (allDataAboutMovement.equals("x")) {
                    isTurnRed = false;
                    match.setWinner(player);
                    break blockWhile;
                }else if(allDataAboutMovement.equals("vern") || allDataAboutMovement.equals("verr")){                
                    gameboard.setMode(allDataAboutMovement);
                    Interface.drawCurrentGameBoard(match, gameboard);
                }else{
                    
                    if (allDataAboutMovement.length() == 2) {
                        try {
                            tokenToMove = Integer.parseInt("" + allDataAboutMovement.charAt(0));
                            movementDirection = Character.toString(allDataAboutMovement.charAt(1));
                            
                            //Validate if the movement is posible and the direction is correct
                            if (posibleTokenMovements.contains(tokenToMove) && Interface.validMovementDirectionInput(movementDirection)) {

                                actualGameBoard.searchPositionOfToken(tokenToMove, player);
                                positionOfTokenX = actualGameBoard.getTokenPositionX();
                                positionOfTokenY = actualGameBoard.getTokenPositionY();

                                validPositionMovement = Interface.validatePositionMovement(player, actualGameBoard, positionOfTokenX, positionOfTokenY, movementDirection);
                                if (validPositionMovement) {
                                    validMovement = true;

                                    actualGameBoard = Interface.movePiece(player, actualGameBoard, positionOfTokenX, positionOfTokenY, movementDirection);
                                    Interface.drawCurrentGameBoard(match, actualGameBoard);

                                    actualGameBoard.searchPositionOfToken(tokenToMove, player);

                                    positionOfTokenX = actualGameBoard.getTokenPositionX();
                                    positionOfTokenY = actualGameBoard.getTokenPositionY();

                                    posibleTokenMovements = actualGameBoard.getPossibleMovements(tokenToMove, positionOfTokenX, positionOfTokenY);
                                    
                                    playAtLeastOneTime = true;
                                } else {
                                    System.out.println("El movimiento no es válido porque hay otra ficha o porque te sales del tablero \n");
                                }

                            }else{
                                System.out.println("\nLa ficha o la dirección no son válidas");
                            }
                        } catch (InputMismatchException e) {
                            if (e.toString().equals("java.util.InputMismatchException")) {
                                System.out.println("Debes ingresar un número");
                            } else {
                                System.out.println("Debes ingresar un número más corto");
                            }
                            validMovement = false;
                            input.next();
                        }
                    } else {
                        System.out.println("Ingrese un valor correcto");
                    }
                }

            }

            //Verify if the player have the possibility to continue or if don't want to continue
            if (playAtLeastOneTime) {
                if (posibleTokenMovements.isEmpty()) {
                    System.out.println("No tienes más movimientos posibles \n");
                    isTurnRed = false;
                } else {
                    continueTurn = Interface.askForString("si quieres seguir jugando (S) o (N)");
                    continueTurn.trim();

                    while (!validResponse) {
                        if (!(continueTurn.equalsIgnoreCase("S") || continueTurn.equalsIgnoreCase("N"))) {
                            System.out.println("\nDebes ingresar (S) o (N)\n");
                            continueTurn = Interface.askForString("si quieres seguir jugando (S) o (N)");
                            continueTurn.trim();
                        } else {
                            validResponse = true;
                        }
                    }

                    if (continueTurn.equalsIgnoreCase("N")) {
                        isTurnRed = false;
                    } else {
                        validMovement = false;
                    }

                }
            }

        }
    }

    public static void drawDefaultGameBoard(GameBoard gameboard, Match match) {
        Token tokenMatrix[][] = gameboard.getTokenMatrix();
        int row = tokenMatrix.length;
        int col = tokenMatrix[0].length;
        String mode = gameboard.getMode();
        int[] tokens = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        gameboard.fillInitialMatrix(tokens);
        match.setGameBoard(gameboard);

        if (mode.equalsIgnoreCase("verr")) {
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
        } else if (mode.equalsIgnoreCase("vern")) {
            for (int i = 0; i < row; i++) {
                System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                for (int j = 0; j < col; j++) {
                    if (tokenMatrix[i][j] != null) {
                        System.out.print("| " + tokenMatrix[i][j].getColor() + tokenMatrix[i][j].getTokenNumber() + "\033[30m ");
                    } else {
                        System.out.print("\033[30m" + "|   ");
                    }
                }
                System.out.print("|");

                System.out.println("");
            }
            System.out.print("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n\n");

        }

    }

    public static void drawCurrentGameBoard(Match match, GameBoard gameboard) {
        Scanner input = new Scanner(System.in);

        Token tokenMatrix[][] = gameboard.getTokenMatrix();
        int row = tokenMatrix.length;
        int col = tokenMatrix[0].length;
        String mode = gameboard.getMode();

        match.setGameBoard(gameboard);

        if (mode.equalsIgnoreCase("verr")) {
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
        } else if (mode.equalsIgnoreCase("vern")) {
            for (int i = 0; i < row; i++) {
                System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                for (int j = 0; j < col; j++) {
                    if (tokenMatrix[i][j] != null) {
                        System.out.print("| " + tokenMatrix[i][j].getColor() + tokenMatrix[i][j].getTokenNumber() + "\033[30m ");
                    } else {
                        System.out.print("\033[30m" + "|   ");
                    }
                }
                System.out.print("|");

                System.out.println("");
            }
            System.out.print("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n\n");

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

        Match currentMatch = null;

        System.out.println("Elige una de las siguientes partidas para repetir:\n");
        for (int i = 0; i < listOfMatches.size(); i++) {
            currentMatch = listOfMatches.get(i);
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
            Interface.drawCurrentGameBoard(currentMatch, listOfGameBoards.get(i));
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

    public static GameBoard movePiece(Player auxPlayer, GameBoard actualGameBoard, int positionOfTokenX, int positionOfTokenY, String movementDirection) {
        Token[][] matrix = actualGameBoard.getTokenMatrix();

        //Player Red
        if (auxPlayer.equals(actualGameBoard.getPlayerRed())) {
            if (movementDirection.equalsIgnoreCase("D")) {
                matrix[positionOfTokenY - 1][positionOfTokenX + 1] = matrix[positionOfTokenY][positionOfTokenX];
                matrix[positionOfTokenY][positionOfTokenX] = null;
            }
            if (movementDirection.equalsIgnoreCase("I")) {
                matrix[positionOfTokenY - 1][positionOfTokenX - 1] = matrix[positionOfTokenY][positionOfTokenX];
                matrix[positionOfTokenY][positionOfTokenX] = null;

            }
            if (movementDirection.equalsIgnoreCase("A")) {
                matrix[positionOfTokenY - 1][positionOfTokenX] = matrix[positionOfTokenY][positionOfTokenX];
                matrix[positionOfTokenY][positionOfTokenX] = null;

            }
        } //Player Blue
        else if (auxPlayer.equals(actualGameBoard.getPlayerBlue())) {
            if (movementDirection.equalsIgnoreCase("D")) {
                matrix[positionOfTokenY + 1][positionOfTokenX - 1] = matrix[positionOfTokenY][positionOfTokenX];
                matrix[positionOfTokenY][positionOfTokenX] = null;
            }
            if (movementDirection.equalsIgnoreCase("I")) {
                matrix[positionOfTokenY + 1][positionOfTokenX + 1] = matrix[positionOfTokenY][positionOfTokenX];
                matrix[positionOfTokenY][positionOfTokenX] = null;

            }
            if (movementDirection.equalsIgnoreCase("A")) {
                matrix[positionOfTokenY + 1][positionOfTokenX] = matrix[positionOfTokenY][positionOfTokenX];
                matrix[positionOfTokenY][positionOfTokenX] = null;

            }
        }

        actualGameBoard.setTokenMatrix(matrix);
        return actualGameBoard;
    }

    //Range Validator in attribute
    public static boolean validateAttribute(int numberToValidate, int intialRange, int finalRange) {
        //Check if the first parameter is between the range
        boolean returnValue = (numberToValidate >= intialRange && numberToValidate <= finalRange);
        if (!returnValue) {
            System.out.println("Ingrese un valor entre " + intialRange + " - " + finalRange);
        }
        return returnValue;
    }
    
    //Range Validator in values only (without system out)
    public static boolean validateRange(int numberToValidate, int intialRange, int finalRange) {
        //Check if the first parameter is between the range        
        return (numberToValidate >= intialRange && numberToValidate <= finalRange);
    }

    public static boolean validMovementDirectionInput(String inputMovement) {
        boolean isValidMovement = false;
        for (int i = 0; i < GameBoard.posibleDirectionsMovements.length; i++) {
            String currentValue = GameBoard.posibleDirectionsMovements[i];
            if (inputMovement.equalsIgnoreCase(currentValue)) {
                isValidMovement = true;
            }
        }
        return isValidMovement;
    }

    public static boolean validatePositionMovement(Player auxPlayer, GameBoard actualGameBoard, int positionOfTokenX, int positionOfTokenY, String movementDirection) {
        Token[][] matrix = actualGameBoard.getTokenMatrix();
        Token auxTokenToCompare = null;

        boolean isValidPositionMovement = false;
        boolean isOutOfRangeY;
        boolean isOutOfRangeX;

        //Player Red
        if (auxPlayer.equals(actualGameBoard.getPlayerRed())) {
            isOutOfRangeY = Interface.validateRange(positionOfTokenY - 1, 0, 7);

            if (movementDirection.equalsIgnoreCase("D")) {
                isOutOfRangeX = Interface.validateRange(positionOfTokenX + 1, 0, 8);

                if (isOutOfRangeX && isOutOfRangeY) {
                    auxTokenToCompare = matrix[positionOfTokenY - 1][positionOfTokenX + 1];

                    if (auxTokenToCompare == null) {
                        isValidPositionMovement = true;
                    }
                }

            } else if (movementDirection.equalsIgnoreCase("I")) {
                isOutOfRangeX = Interface.validateRange(positionOfTokenX - 1, 0, 8);

                if (isOutOfRangeX && isOutOfRangeY) {
                    auxTokenToCompare = matrix[positionOfTokenY - 1][positionOfTokenX - 1];

                    if (auxTokenToCompare == null) {
                        isValidPositionMovement = true;
                    }
                }

            } else if (movementDirection.equalsIgnoreCase("A")) {
                isOutOfRangeX = Interface.validateRange(positionOfTokenX, 0, 8);

                if (isOutOfRangeX && isOutOfRangeY) {
                    auxTokenToCompare = matrix[positionOfTokenY - 1][positionOfTokenX];

                    if (auxTokenToCompare == null) {
                        isValidPositionMovement = true;
                    }

                }

            }
        } //Player blue
        else if (auxPlayer.equals(actualGameBoard.getPlayerBlue())) {

            isOutOfRangeY = Interface.validateRange(positionOfTokenY + 1, 0, 7);

            if (movementDirection.equalsIgnoreCase("D")) {
                isOutOfRangeX = Interface.validateRange(positionOfTokenX - 1, 0, 8);

                if (isOutOfRangeX && isOutOfRangeY) {
                    auxTokenToCompare = matrix[positionOfTokenY + 1][positionOfTokenX - 1];

                    if (auxTokenToCompare == null) {
                        isValidPositionMovement = true;
                    }

                }

            } else if (movementDirection.equalsIgnoreCase("I")) {
                isOutOfRangeX = Interface.validateRange(positionOfTokenX + 1, 0, 8);

                if (isOutOfRangeX && isOutOfRangeY) {
                    auxTokenToCompare = matrix[positionOfTokenY + 1][positionOfTokenX + 1];

                    if (auxTokenToCompare == null) {
                        isValidPositionMovement = true;
                    }

                }

            } else if (movementDirection.equalsIgnoreCase("A")) {
                isOutOfRangeX = Interface.validateRange(positionOfTokenX, 0, 8);

                if (isOutOfRangeX && isOutOfRangeY) {
                    auxTokenToCompare = matrix[positionOfTokenY + 1][positionOfTokenX];

                    if (auxTokenToCompare == null) {
                        isValidPositionMovement = true;
                    }

                }

            }
        }

        return isValidPositionMovement;
    }

    public static void showPosibleDirectionsMovements(ArrayList<Integer> posibleTokenMovents, int tokenMoved) {
        int currentValue;
        ArrayList<Integer> auxArraylist = new ArrayList<>();
        Iterator<Integer> it = posibleTokenMovents.iterator();

        while (it.hasNext()) {
            currentValue = it.next();
            System.out.print(currentValue + " ");
        }

    }
}
