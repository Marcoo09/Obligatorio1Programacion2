package views;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import domains.*;
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
                Player newPlayer = Interface.addPlayer(game);
                game.addPlayer(newPlayer);
                System.out.println("<----JUGADOR REGISTRADO---->");
                break;
            case "b":
                if (game.getListOfPlayers().size() > 1) {
                    Match match = Interface.beginMatch(game);
                    game.addMatch(match);
                    System.out.println("<----EMPIEZA EL JUEGO---->\n");

                    gameboard = new GameBoard(match.getListOfPlayers());
                    //Draw the first the gameboard of the match
                    Interface.drawDefaultGameBoard(gameboard, match);
                    //All the logic of plays
                    Interface.turnByTurn(game, match, gameboard);
                    //When the match is finished show who is the winner
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
                if (game.getListOfPlayers().isEmpty() || game.getListOfMatches().isEmpty()) {
                    System.out.println("<----DEBES JUGAR UNA PARTIDA PREVIAMENTE---->");
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

    public static Player addPlayer(Game game) {
        ArrayList<Player> listOfPlayers = game.getListOfPlayers();
        //Variables of Player
        String name;
        String nickName = "";
        int age = 0;

        Player auxPlayer = new Player("", "", 0);

        //Variables used in validators
        boolean ageValidator = false;
        boolean nickNameValidator = false;
        //Scanner used in error handling
        Scanner input = new Scanner(System.in);

        System.out.println("\n<--------Ingrese los datos del jugador-------->\n ");

        name = Interface.askForString("nombre");
        //Validation of nickname
        while (!nickNameValidator) {
            nickName = Interface.askForString("alias");
            auxPlayer.setNickName(nickName);
            nickNameValidator = !(listOfPlayers.contains(auxPlayer));

            if (!nickNameValidator) {
                System.out.println("ALIAS REPETIDO");
            }

        }
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
        String[] wayToFinishOptions = {"Terminar por cierta cantidad de movimientos", "Terminar por haber llevado al lado opuesto una pieza", "Terminar por haber llevado al lado opuesto todas las piezas"};
        /*Variables of the match*/
        Player player1;
        Player player2;
        String wayToFinish;
        int qtyOfMovements = 0;
        ArrayList<Player> possiblePlayers = game.getListOfPlayers();

        int sizeOfPlayerList = possiblePlayers.size();

        System.out.println("\nElige los jugadores");
        //Show the list of players
        for (int i = 0; i < sizeOfPlayerList; i++) {
            System.out.print("\n" + (i + 1) + ": " + game.getListOfPlayers().get(i));
        }
        //Choose blue player
        while (!firstPlayerIsCorrect) {
            System.out.println("");
            p1 = Interface.askForNumeric("el jugador que tendrá el color azul");
            firstPlayerIsCorrect = Interface.validateAttribute(p1, 1, sizeOfPlayerList);
        }
        player1 = possiblePlayers.get(p1 - 1);
        //Choose red player
        while (!SecondPlayerIsCorrect) {
            System.out.println("");
            p2 = Interface.askForNumeric("el jugador que tendra el color rojo");
            SecondPlayerIsCorrect = Interface.validateAttribute(p2, 1, sizeOfPlayerList);
            if (p1 == p2) {
                SecondPlayerIsCorrect = false;
                System.out.println("\nElige un jugador distinto al 1");
            }
        }
        player2 = possiblePlayers.get(p2 - 1);

        //Show and Choose the way of finalize the game
        System.out.println("\nElige una de las siguientes formas de terminar el juego: ");
        System.out.println("");
        for (int i = 0; i < wayToFinishOptions.length; i++) {
            System.out.println((i + 1) + " ) " + wayToFinishOptions[i] + "\n");
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

            System.out.println("\n\033[31mTURNO DEL JUGADOR ROJO \033[30m\n\n");

            Interface.turn(game, match, gameboard, "red");

            isFinished = match.getFinished();

            if (!isFinished) {
                System.out.println("\n\033[34mTURNO DEL JUGADOR AZUL \033[30m\n\n");
                Interface.turn(game, match, gameboard, "blue");
            }

            isFinished = match.getFinished();
        }

    }

    public static void turn(Game game, Match match, GameBoard actualGameBoard, String playerColor) {
        Player player = null;
        //It's used  if one player leaves the match to assign this as winner
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

        int qtyOfMovements = 0;

        ArrayList<Integer> posibleTokenMovements = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

        String allDataAboutMovement;
        String movementDirection;
        String continueTurn;

        int tokenToMove = 0;
        int positionOfTokenX;
        int positionOfTokenY;

        blockWhile:
        while (isTurn) {
            playAtLeastOneTime = false;
            
            posibleTokenMovements =  Interface.removeTokensWithAnyPosibleMovement(actualGameBoard, posibleTokenMovements, playerColor, player);
            
            if (qtyOfMovements == 0) {
                System.out.println("Posibles movimientos: ");
                Interface.showPosibleDirectionsMovements(posibleTokenMovements, tokenToMove);
                System.out.println("\n(D)DERECHA (I)IZQUIERA (A)ADELANTE \n");
            }

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
                            //Difference between direction and the token to move.
                            tokenToMove = Integer.parseInt("" + allDataAboutMovement.charAt(0));
                            movementDirection = Character.toString(allDataAboutMovement.charAt(1));

                            //Validate if the token selected is posible and the direction is correct
                            if (posibleTokenMovements.contains(tokenToMove) && Interface.validMovementDirectionInput(movementDirection)) {

                                actualGameBoard.searchPositionOfToken(tokenToMove, player);

                                positionOfTokenX = actualGameBoard.getTokenPositionX();
                                positionOfTokenY = actualGameBoard.getTokenPositionY();
                                //Validate if the new positions is in the matrix or if exist another token in this position
                                validPositionMovement = Interface.validatePositionMovement(player, actualGameBoard, positionOfTokenX, positionOfTokenY, movementDirection);
                                if (validPositionMovement) {
                                    validMovement = true;
                                    //Move the piece and draw
                                    actualGameBoard = Interface.movePiece(player, actualGameBoard, positionOfTokenX, positionOfTokenY, movementDirection);
                                    Interface.drawCurrentGameBoard(match, actualGameBoard, false);
                                    //Update the positions
                                    actualGameBoard.searchPositionOfToken(tokenToMove, player);

                                    positionOfTokenX = actualGameBoard.getTokenPositionX();
                                    positionOfTokenY = actualGameBoard.getTokenPositionY();
                                    //Update the arraylist which is showed to the user.
                                    posibleTokenMovements = actualGameBoard.getPossibleMovements(tokenToMove, positionOfTokenX, positionOfTokenY);
                                    
                                     //Remove tokens which hasn't any posible movement
                                    posibleTokenMovements =  Interface.removeTokensWithAnyPosibleMovement(actualGameBoard, posibleTokenMovements, playerColor, player);;

                                    playAtLeastOneTime = true;
                                } else {
                                    System.out.println("\nEL MOVIMIENTO NO ES VÁLIDO PORQUE HAY OTRA FICHA O PORQUE TE SALES DEL TABLERO\n");
                                }
                            } else {
                                System.out.println("\nLA FICHA O LA DIRECCIÓN NO SON VÁLIDAS");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nEL PRIMER DÍGITO DEBE SER UN NÚMERO");
                            validMovement = false;
                        }
                    } else {
                        System.out.println("\nINGRESE UN VALOR CORRECTO");
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
                        validResponse = false;
                        qtyOfMovements++;
                        System.out.println("Posibles movimientos: ");
                        Interface.showPosibleDirectionsMovements(posibleTokenMovements, tokenToMove);
                        System.out.println("\n(D)DERECHA (I)IZQUIERA (A)ADELANTE \n");

                        continueTurn = Interface.askForString("si quieres seguir jugando (S) o (N)");
                        continueTurn.trim();

                        while (!validResponse) {

                            if (continueTurn.equalsIgnoreCase("s") || continueTurn.equalsIgnoreCase("n")) {
                                validResponse = true;
                            } else {
                                System.out.println("\nDEBES INGRESAR (S) o (N)\n");
                                continueTurn = Interface.askForString("si quieres seguir jugando (S) o (N)");
                                continueTurn.trim();
                            }

                        }
                        //if don't want to continue, leave the main while or init a new turn of the current player
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
        //Clone each token to save another gameboard and not modify all the gameboards
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

    public static void drawCurrentGameBoard(Match match, GameBoard gameboard, boolean replayMatch) {
        Token tokenMatrix[][] = gameboard.getTokenMatrix();
        int row = tokenMatrix.length;
        int col = tokenMatrix[0].length;
        String mode = gameboard.getMode();

        GameBoard auxGameboard = new GameBoard(match.getListOfPlayers());
        Token[][] auxMatrix = auxGameboard.getTokenMatrix();
        //Clone each token to save another gameboard and not modify all the gameboards
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
        //Show the matteches and select one
        System.out.println("Elige una de las siguientes partidas para repetir (número):\n");
        for (int i = 0; i < listOfMatches.size(); i++) {
            currentMatch = listOfMatches.get(i);
            System.out.println("Partida (" + (i + 1) + ") : " + currentMatch);
        }

        while (!indexOfMatchValidator) {
            chosenOption = Interface.askForNumeric("partida");
            indexOfMatchValidator = Interface.validateAttribute(chosenOption, 1, listOfMatches.size());
        }

        Match selectedMatch = listOfMatches.get(chosenOption - 1);
        ArrayList<GameBoard> listOfGameBoards = selectedMatch.getListOfGameBoard();
        //Show the gameboard one by one when the user select ENTER
        for (int i = 0; i < listOfGameBoards.size() && !exitValidator; i++) {
            entry = " ";
            Interface.drawCurrentGameBoard(currentMatch, listOfGameBoards.get(i), true);
            //if the user write s, leave the show of match turns, else advance
            while (!entry.equalsIgnoreCase("") && !exitValidator) {
                entry = input.nextLine();
               entry =  entry.trim();
                exitValidator = entry.equalsIgnoreCase("s");
                if (!entry.equalsIgnoreCase("") && !exitValidator) {
                    System.out.println("ESCRIBE UN VALOR VÁLIDO (Presione ENTER para avanzar o S para salir)");
                }

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
    
    public static ArrayList<Integer> removeTokensWithAnyPosibleMovement(GameBoard gameboard, ArrayList<Integer> posibleTokenMovements, String playerColor, Player player) {
        int size = posibleTokenMovements.size();
        int currentTokenValue;
        ArrayList<Integer> returnedArrayList = new ArrayList<>();
        
        boolean hasMovementsToA = true;
        boolean hasMovementsToD = true;
        boolean hasMovementsToI = true;
        
        int positionOfTokenX;
        int positionOfTokenY;
        
        if (playerColor.equalsIgnoreCase("red")) {
            for (int i = 0; i < size; i++) {
                currentTokenValue = posibleTokenMovements.get(i);
                gameboard.searchPositionOfToken(currentTokenValue, player);
                
                positionOfTokenX = gameboard.getTokenPositionX();
                positionOfTokenY = gameboard.getTokenPositionY();
                
                hasMovementsToA = Interface.validatePositionMovement(player,gameboard,positionOfTokenX,positionOfTokenY,"A");
                hasMovementsToD = Interface.validatePositionMovement(player,gameboard,positionOfTokenX,positionOfTokenY ,"D");
                hasMovementsToI = Interface.validatePositionMovement(player,gameboard,positionOfTokenX,positionOfTokenY,"I");
                
                if(hasMovementsToA || hasMovementsToD || hasMovementsToI){
                    returnedArrayList.add(currentTokenValue);
                }
            }
        } else if (playerColor.equalsIgnoreCase("blue")) {
            for (int i = 0; i < size; i++) {
                currentTokenValue = posibleTokenMovements.get(i);
                gameboard.searchPositionOfToken(currentTokenValue, player);
                
                positionOfTokenX = gameboard.getTokenPositionX();
                positionOfTokenY = gameboard.getTokenPositionY();
                
                hasMovementsToA = Interface.validatePositionMovement(player,gameboard,positionOfTokenX,positionOfTokenY ,"A");
                hasMovementsToD = Interface.validatePositionMovement(player,gameboard,positionOfTokenX ,positionOfTokenY,"D");
                hasMovementsToI = Interface.validatePositionMovement(player,gameboard,positionOfTokenX,positionOfTokenY,"I");
                
                if(hasMovementsToA || hasMovementsToD || hasMovementsToI){
                    returnedArrayList.add(currentTokenValue);
                }
            }
        }
        
        return returnedArrayList;
    }
        
    public static void showRanking(Game game) {
        game.sortPlayersByWonGames();
        ArrayList<Player> listOfPlayers = game.getListOfPlayers();
        System.out.println("\n      :: RANKING ::     \n");
        for (int i = 0; i < listOfPlayers.size(); i++) {
            System.out.println("..: " + (i + 1) + " :.." + listOfPlayers.get(i));
        }
    }

}
