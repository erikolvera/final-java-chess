package player;
import board.Board;
import java.util.ArrayList;
import pieces.Piece;
import position.Position;

public class Player {
    private String color; // color of the player, e.g., "white" or "black"
    private ArrayList<Piece> pieces; // pieces owned by the player
    private Board board;

    public Player(String color, Board board) {
        this.color = color; // sets the player's color
        this.board = board; // link to the shared game board
        this.pieces = new ArrayList<>(); // initialize the pieces owned by the player
    }

    public boolean makeMove(Position from, Position to) {
        Piece movingPiece = board.getPiece(from);


        //checking if the player's move is valid
        if (movingPiece == null || !movingPiece.getColor().equals(this.color)) {
            System.out.println("Invalid move");
            return false; // Exit early if mode was invalid
        }

        boolean success = board.movePiece(from, to); //Board performs the move

        if (success) {
            return true; //Move succeeds
        }
        return false;
    }
}