// Noah Zhao
// 4/14/24
// CSE 123
// C1: Abstract Strategy Games
// TA: Daniel Welicki

import java.util.*;

// this class represents a game of Connect Four that extends the
// AbstractStrategyGames
public class ConnectFour extends AbstractStrategyGame {
    private char[][] board;
    private boolean isXTurn;
    private int xCount;
    private int oCount;

    //Constructor method for ConnectFour, creates empty game board
    public ConnectFour() {
        board = new char[][] {
                { '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-' },
        };
        isXTurn = true;
        xCount = 0;
        oCount = 0;
    }

    //This method displays instructions for the game
    //Returns:
    // - String: instructions for the Connect Four game
    public String instructions() {
        return "Connect Four is a turn-based strategy game where the goal is to\n" +
                "connect four tokens in a row, either horizontally or vertically.\n" +
                "Players are able to drop tokens into the grid, reaching as far " +
                "bottom as possible.\n"
                + "Players are also given the choice to remove a token of theirs from" +
                " the bottom of a column,\ninstead of dropping a token.";
    }

    //this method displays the current state of the board
    //Returns:
    // - String: visualized display of the board
    public String toString() {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                result += board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    //this method prompts the start of each move, with a boolean alternating turns between the
    //two players
    //Parameters:
    // - input: scanner that asks for user input to make decisions in the game
    //Exceptions:
    // - IllegalArgumentException: thrown if column chosen is outside range or user
    // attempts to remove a token on round 1
    public void makeMove(Scanner input) {
        if (isXTurn) {

            //message on first round
            boolean containsToken = false;
            System.out.println("Player 1: Would you like to remove a token (1) " + 
            "or place a token (2)?");

            int choice = input.nextInt();

            //if choice is not in the range, or token attempted to be removed from empty board
            while (choice < 1 || choice > 2 || (choice == 1 && xCount == 0)) {
                throw new IllegalArgumentException();
            }

            int lineOfFirstX = 0;

            //removing a token 
            if (choice == 1 && xCount > 0) {

                System.out.println("Player 1: What column would you like to remove a token from?");
                int column = input.nextInt() - 1;

                while (!containsToken) {
                    for (int i = 5; i >= 0; i--) {
                        if (board[i][column] == 'X' && containsToken == false) {
                            lineOfFirstX = i;
                            containsToken = true;
                        } else if (containsToken == false) {
                            System.out.println("You have no tokens in this column." +
                             "Please pick again.");
                            column = input.nextInt();
                        }
                    }
                }

                //shifts all values downwards
                if (containsToken) {
                    for (int i = lineOfFirstX; i > 0; i--) {
                        board[i][column] = board[i - 1][column];
                    }
                    board[0][column] = '-';
                }
                xCount--;
                isXTurn = false;
            } else {
                boolean tokenPlaced = false;

                // placing a token
                System.out.println("Player 1: What column would you like to place your token in?");
                int column = input.nextInt() - 1;

                // checks if column is full alread, asks for reinput until open column found
                while (board[0][column] != '-') {
                    System.out.println("Column is full, please pick another one");
                    column = input.nextInt() - 1;
                }

                // starting from bottom of board, if the value closest to bottom is empty,
                // insert value
                while (!tokenPlaced) {
                    for (int i = 5; i >= 0; i--) {
                        if (board[i][column] == '-' && tokenPlaced == false) {
                            board[i][column] = 'X';
                            tokenPlaced = true;
                            xCount++;
                        }
                    }
                    isXTurn = false;
                }
            }
        } else {

            //message on first round
            boolean containsToken = false;

            //prompt message
            System.out.println("Player 2: Would you like to remove a token (1) " +
             "or place a token (2)?");
            int choice = input.nextInt();

            //if choice falls outside range or token attempted to be removed from empty board
            while (choice < 1 || choice > 2 || (choice == 1 && oCount == 0)) {
                throw new IllegalArgumentException();
            }

            int lineOfFirstO = 0;
            
            //removing token
            if (choice == 1 && oCount > 0) {
                System.out.println("Player 2: What column would you like to remove a token from?");
                int column = input.nextInt() - 1;

                //finding the lowest value of O
                while (!containsToken) {
                    for (int i = 5; i >= 0; i--) {
                        if (board[i][column] == 'O' && containsToken == false) {
                            lineOfFirstO = i;
                            containsToken = true;
                        } else if (containsToken == false) {
                            System.out.println("You have no tokens in this column." + 
                            "Please pick again.");
                            column = input.nextInt();
                        }
                    }
                }
                
                //shifting all values downwards
                if (containsToken) {
                    for (int i = lineOfFirstO; i > 0; i--) {
                        board[i][column] = board[i - 1][column];
                    }
                    board[0][column] = '-';
                    oCount--;
                    isXTurn = true;
                }
               
            } else {
                boolean tokenPlaced = false;

                System.out.println("Player 2: What column would you like to place your token in?");
                int column = input.nextInt() - 1;

                // checks if column is full alread, asks for reinput until open column found
                while (board[0][column] != '-') {
                    System.out.println("Column is full, please pick another one");
                    column = input.nextInt() - 1;
                }

                // starting from bottom of board, if the value closest to bottom is empty,
                // insert value
                while (!tokenPlaced) {
                    for (int i = 5; i >= 0; i--) {
                        if (board[i][column] == '-' && tokenPlaced == false) {
                            board[i][column] = 'O';
                            tokenPlaced = true;
                            oCount++;
                        }
                    }
                    isXTurn = true;
                }
            }
        }

    }

    //this method returns an int representing who the next player is
    //Returns:
    // int: value of the player whos turn is next OR -1 if the game is over
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        return isXTurn ? 1 : 2;
    }

    //this method checks whether or not the board is full or not, resulting in a tie
    //Returns:
    // - boolean: true if board is full, false if board is not full
    private boolean checkFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    //this method checks who the winner of the game is, if there is one
    //Parameters:
    // - char x: symbol used by Player 1 or Player 2 that is used as their token on the board
    //Returns:
    // - boolean: true if the character used by Player 1 or 2 results in a pattern that wins them
    // the game, false otherwise.
    public boolean checkWinner(char x) {
        int count = 0;
        // checking rows
        for (int i = 0; i < board.length; i++) {
            count = 0;
            for (int j = 0; j < board[0].length; j++) {

                if (board[i][j] == x) {
                    count++;
                } else {
                    count = 0;
                }

                if (count == 4) {
                    return true;
                }
            }
        }

        // checking columns
        for (int i = 0; i < board[0].length; i++) {
            count = 0;
            for (int j = 0; j < board.length; j++) {

                if (board[j][i] == x) {
                    count++;
                } else {
                    count = 0;
                }

                if (count == 4) {
                    return true;
                }
            }
        }

        return false;
    }

    // checks what outcome has resulted from the most recent turn, whether or not
    // someone won, the game is tied, or the game is ongoing.
    // Returns:
    // - int: number between -1 and 1, with each number representing a different outcome
    // (1 = Player 1 Wins, 2 = Player 2 wins, 0 = tie, -1 = ongoing game)
    public int getWinner() {
        if (checkWinner('X')) {
            return 1;
        } else if (checkWinner('O')) {
            return 2;
        } else if (checkFull()) {
            return 0;
        } else {
            return -1;
        }
    }

}
