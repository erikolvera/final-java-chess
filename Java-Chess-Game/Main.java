import board.Board;
import java.util.Scanner;
import player.Player;
import position.Position;


public class Main {

    public static boolean isValidPosition(String notation) {  // check if notation is valid and in correct format
        return notation.matches("^[a-h][1-8]$");
    }

    public static Position parsePosition(String notation) {
        int col = notation.charAt(0) - 'a'; // 'a' -> 0, ..., 'h' -> 7 this are the letters of the board they go from a to h
        int row = 8 - Character.getNumericValue(notation.charAt(1)); // '1' -> 7, ..., '8' -> 0 this are the numbers of the board they go from 1 to 8
        return new Position(row, col); // returns the position
    }
    public static void main (String[] args) {
        
        Scanner scanner = new Scanner(System.in); // scanner for user input

        Board board = new Board(); // new board
        board.initializeBoard(); // initialize the board

        // initialize players and start the game
        Player whitePlayer = new Player("white", board);
        Player blackPlayer = new Player("black", board);

        String currentTurn = "white"; // first turn to white
        boolean gameRunning = true; // loop to tell game to keep running

        System.out.println("Welcome to the Chess Game!");
        System.out.println("When entering moves, use format : e2 e4 (move piece from e2 to e4)");

        while(gameRunning){
            board.display(); // display the board
            System.out.println(currentTurn + "'s move: "); // tells user whose turn it is
            String move = scanner.nextLine().trim(); // read user input and trims any whitespace

            if(move.equalsIgnoreCase("quit")) { // quit if user types quit
                break; // exits the loop
            }
            String[] moveParts = move.split(" "); // splits the move into two parts which is source and destination
            if(moveParts.length != 2 || // validate move format and position
             !isValidPosition(moveParts[0]) ||
             !isValidPosition(moveParts[1])) {
                System.out.println("Invalid move format. Use format : e2 e4 (move piece from e2 to e4)"); // bad input
                continue; // restart loop
 
            }

            Position from = parsePosition(moveParts[0]); // convert to initial position/square like e2
            Position to = parsePosition(moveParts[1]); // convert destination square/position to like e4

            boolean moveSuccess; // track if success
            if(currentTurn.equals("white")) { // check whose turn it is
                moveSuccess = whitePlayer.makeMove(from, to); // if it is white, make the move
            } else {
                moveSuccess = blackPlayer.makeMove(from, to); // if it is black, make the move
            }

            if (moveSuccess) { // if move was successful switch turn
                currentTurn = currentTurn.equals("white") ? "black" : "white"; // switch turn
            } else {
                System.out.println("Invalid move. Try again.");

            }
        
        }
        
        scanner.close(); // close scanner input
    }
    }
