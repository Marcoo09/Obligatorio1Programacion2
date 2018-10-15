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
                System.out.println("<----JUGADOR REGISTRADO---->");
                break;
            case "b":
                if (game.getListOfPlayers().size() > 1) {
                    Match match = Interface.beginMatch(game);
                    game.addMatch(match);
                    System.out.println("<----EMPIEZA EL JUEGO---->\n");

                    gameboard = new GameBoard(match.getListOfPlayers());

                    Interface.drawDefaultGameBoard(gameboard, match);
                  // Interface.harcodeValues(gameboard, match);
                   
                    Interface.turnByTurn(game, match, gameboard);

                    Interface.anounceWinner(match);
                } else {
                    System.out.println("<----DEBES REGISTRAR POR LO MENOS DOS JUGADORES---->");
                }
                break;
            case "c":
                if (game.getListOfMatches().isEmpty()) {
                    System.out.println("<----DEBES JUGAR ANTES ALGUNA PARTIDA PARA PODER REPLICAR UNA---->");
                } else {
                    Interface.replayMatch(game);
                }
                break;
            case "d":
                if (game.getListOfPlayers().isEmpty()||game.getListOfMatches().isEmpty()) {
                    System.out.println("<----DEBES JUGAR UNA PARTIDAD PREVIAMENTE---->");
                } else {
                    Interface.showRanking(game);
                }
                break;
            case "e":
                //Finalize the program
                System.out.println("Salir");
                executeProgram = false;
                System.out.println("<----HA FINALIZADO LA EJECUCIÓN DEL PROGRAMA---->");
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

        if (playerWinner != null) {
            System.out.println("El jugador que ha ganado es: \n");
            playerWinner.setWonGames(playerWinner.getWonGames() + 1);
            System.out.println(playerWinner);
        } else {
            System.out.println("El juego terminó en empate");
        }
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
        String[] wayToFinishOptions = {"Terminar por cierta cantidad de movimientos", "Terminar por haber llevado al lado opuesto un pieza", "Terminar por haber llevado al lado opuesto todas las piezas"};
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
            System.out.println((i + 1) + " " + wayToFinishOptions[i] + "\n");
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

            System.out.println("\033[31mTURNO DEL JUGADOR ROJO \033[30m\n\n");

            Interface.turn(game, match, gameboard, "red");

            isFinished = match.getFinished();

            if (!isFinished) {
                System.out.println("\033[34mTURNO DEL JUGADOR AZUL \033[30m\n\n");
                Interface.turn(game, match, gameboard, "blue");
            }

            isFinished = match.getFinished();
        }

    }

    public static void turn(Game game, Match match, GameBoard actualGameBoard, String playerColor) {
        Player player = null;
        Player notCurrentPlayer = null;

        if (playerColor.equals("red")) {
            player = actualGameBoard.getPlayerRed();
            notCurrentPlayer = actualGameBoard.getPlayerBlue();
        } else if (playerColor.equals("blue")) {
            player = actualGameBoard.getPlayerBlue();
            notCurrentPlayer = actualGameBoard.getPlayerRed();
        }

        boolean isTurn = true;
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
        
        posibleTokenMovements = Interface.removeTokensInTheLastRow(actualGameBoard, posibleTokenMovements, playerColor, player);

                                            
        blockWhile:
        while (isTurn) {
            playAtLeastOneTime = false;

            System.out.println("Posibles movimientos: ");
            Interface.showPosibleDirectionsMovements(posibleTokenMovements, tokenToMove);
            System.out.println("\n(D)DERECHA (I)IZQUIERA (A)ADELANTE \n");

            System.out.println("Posibles jugadas: \n                  MOVIMIENTO\n                  CAMBIO DE TABLERO\n                  X para retirarse");
           
            while (!validMovement) {
                System.out.println("");
                allDataAboutMovement = Interface.askForString("una jugada");
                allDataAboutMovement = allDataAboutMovement.trim().toLowerCase();

                if (allDataAboutMovement.equals("x")) {
                    match.setWinner(notCurrentPlayer);
                    match.setFinished(true);
                    
                    break blockWhile;
                } else if (allDataAboutMovement.equals("vern") || allDataAboutMovement.equals("verr")) {
                    actualGameBoard.setMode(allDataAboutMovement);
                    Interface.drawCurrentGameBoard(match, actualGameBoard, false);
                } else {

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
                                    Interface.drawCurrentGameBoard(match, actualGameBoard, false);

                                    actualGameBoard.searchPositionOfToken(tokenToMove, player);

                                    positionOfTokenX = actualGameBoard.getTokenPositionX();
                                    positionOfTokenY = actualGameBoard.getTokenPositionY();

                                    posibleTokenMovements = actualGameBoard.getPossibleMovements(tokenToMove, positionOfTokenX, positionOfTokenY);
                                    //Remove token which position is the last of the opposite front
                                    posibleTokenMovements = Interface.removeTokensInTheLastRow(actualGameBoard, posibleTokenMovements, playerColor, player);

                                    playAtLeastOneTime = true;
                                } else {
                                    System.out.println("El movimiento no es válido porque hay otra ficha o porque te sales del tablero \n");
                                }

                            } else {
                                System.out.println("\nLa ficha o la dirección no son válidas");
                            }
                        } catch (NumberFormatException e) {
                            //java.lang.NumberFormatException
                            System.out.println("El primer dígito debe ser un número");
                            validMovement = false;
                        }
                    } else {
                        System.out.println("Ingrese un valor correcto");
                    }
                }

            }
            
            if (match.isFinished()) {
                isTurn = false;
            } else {
                //Verify if the player have the possibility to continue or if don't want to continue
                if (playAtLeastOneTime) {
                    if (posibleTokenMovements.isEmpty()) {
                        System.out.println("No tienes más movimientos posibles \n");
                        isTurn = false;
                    } else {
                        continueTurn = Interface.askForString("si quieres seguir jugando (S) o (N)");
                        continueTurn.trim();

                        while (!validResponse) {
                            if (!(continueTurn.equalsIgnoreCase("S") || continueTurn.equalsIgnoreCase("N"))) {
                                System.out.println("\nDebes ingresar (S) o (N)\n");
                                continueTurn = Interface.askForString("si quieres seguir jugando (S) o (N)");
                                continueTurn.trim();
                            } else if(continueTurn.isEmpty()){
                                       validResponse = false; 
                            }else{
                                    validResponse = true;
                            }
                        }

                        if (continueTurn.equalsIgnoreCase("N")) {
                            isTurn = false;
                        } else {
                            validMovement = false;
                        }

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

        GameBoard auxGameboard = new GameBoard(match.getListOfPlayers());
        Token[][] auxMatrix = auxGameboard.getTokenMatrix();

        for (int i = 0; i < tokenMatrix.length; i++) {
            for (int j = 0; j < tokenMatrix[0].length; j++) {
                if (tokenMatrix[i][j] != null) {
                    auxMatrix[i][j] = (Token) tokenMatrix[i][j].clone();
                }
            }
        }

        match.setGameBoard(auxGameboard);

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
    /*Prueba Harcodear*/
     public static void harcodeValues(GameBoard gameboard, Match match) {
        Token tokenMatrix[][] = gameboard.getTokenMatrix();
        int row = tokenMatrix.length;
        int col = tokenMatrix[0].length;
        String mode = gameboard.getMode();
        int[] tokens = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        
        int counter = 0;
        Player currentPlayer;

        Token currentToken = new Token();

        for (int i = 0; i < row; i += row - 1) {
            if(counter == 0){
                currentPlayer = gameboard.getPlayerRed();
            }else{
                currentPlayer = gameboard.getPlayerBlue();
            }
                    
            for (int j = 1; j < col; j++) {
                currentToken = new Token();
                if (i == 0) {
                    currentToken.setPlayer(currentPlayer);
                    currentToken.setTokenNumber(tokens[j]);
                    currentToken.setColor("\033[31m");
                    tokenMatrix[i + 1][j] = currentToken;
                } else {
                    currentToken.setPlayer(currentPlayer);
                    currentToken.setTokenNumber(tokens[col - j]);
                    currentToken.setColor("\033[34m");
                    tokenMatrix[i - 1][j - 1] = currentToken;
                }
            }
            counter++;
        }
        

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
    /*Prueba harcodear*/
    public static void drawCurrentGameBoard(Match match, GameBoard gameboard, boolean replayMatch) {
        Token tokenMatrix[][] = gameboard.getTokenMatrix();
        int row = tokenMatrix.length;
        int col = tokenMatrix[0].length;
        String mode = gameboard.getMode();

        GameBoard auxGameboard = new GameBoard(match.getListOfPlayers());
        Token[][] auxMatrix = auxGameboard.getTokenMatrix();

        for (int i = 0; i < tokenMatrix.length; i++) {
            for (int j = 0; j < tokenMatrix[0].length; j++) {
                if (tokenMatrix[i][j] != null) {
                    auxMatrix[i][j] = (Token) tokenMatrix[i][j].clone();

                }
            }
        }

        if (!replayMatch) {
            match.setGameBoard(auxGameboard);
        }

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

        System.out.println("Size " + listOfGameBoards.size());

        for (int i = 0; i < listOfGameBoards.size() && !exitValidator; i++) {
            entry = "";
            Interface.drawCurrentGameBoard(currentMatch, listOfGameBoards.get(i), true);
            while (!entry.equalsIgnoreCase("n") && !exitValidator) {
                entry = input.nextLine();
                exitValidator = entry.equalsIgnoreCase("s");

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

    public static ArrayList<Integer> removeTokensInTheLastRow(GameBoard gameboard, ArrayList<Integer> posibleTokenMovements, String playerColor, Player player) {
        int size = posibleTokenMovements.size();
        int currentTokenValue;
        ArrayList<Integer> returnedArrayList = new ArrayList<>();
        
        if (playerColor.equalsIgnoreCase("red")) {
            for (int i = 0; i < size; i++) {
                currentTokenValue = posibleTokenMovements.get(i);
                gameboard.searchPositionOfToken(currentTokenValue, player);

                if (!(gameboard.getTokenPositionY() == 0)) {
                    returnedArrayList.add(currentTokenValue);
                }
            }
        } else if (playerColor.equalsIgnoreCase("blue")) {
            for (int i = 0; i < size; i++) {
                currentTokenValue = posibleTokenMovements.get(i);
                gameboard.searchPositionOfToken(currentTokenValue, player);

                if (!(gameboard.getTokenPositionY() == 7)) {
                    returnedArrayList.add(currentTokenValue);
                }
            }
        }

        return returnedArrayList;
    }
    
     public static void showRanking(Game game){
        game.sortPlayersByWonGames();
        ArrayList<Player> listOfPlayers = game.getListOfPlayers();
        System.out.println("\n      :: RANKING ::     \n");
        for (int i = 0; i < listOfPlayers.size(); i++) {
            System.out.println("..: "+(i+1)+ " :.." + listOfPlayers.get(i));
        }
    }

}
