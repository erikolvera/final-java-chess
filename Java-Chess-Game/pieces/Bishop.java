package pieces;
import board.Board;
import java.util.ArrayList;
import java.util.List;
import position.Position;

// Bishop class
public class Bishop extends Piece{ 
    
    // constructor
    public Bishop(String color, Position position) {
        super(color, position);
    }

    // getter and setter
    @Override
    public String getColor() {
        return color;
    }
    
    @Override
    public Position getPosition() {
        return position;
    }
    // set position 
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
    // possible moves
    @Override
    public List<Position> possibleMoves(Board board) {
        // Implement logic for bishop's possible moves
        List<Position> moves = new ArrayList<>();

        // Checks all 4 diagonal directions
        int[] rowDirs = {1,1,-1,-1}; 
        int[] colDirs = {1,-1,1,-1}; 


        // current position 
        int row = position.getRow();
        int col = position.getCol(); 
        
        for (int i = 0; i < 4; i++){
            int newRow = row + rowDirs[i];
            int newCol = col + colDirs[i];

            // while loop to check all possible moves in the direction
            while (board.isValidPosition(newRow, newCol)) {
                Position newPos = new Position(newRow, newCol);
                Piece pieceAtPosition = board.getPiece(newPos);

                if (pieceAtPosition == null) {
                    moves.add(newPos);
                } else {
                    if (!pieceAtPosition.getColor().equals(this.color)) {
                        moves.add(newPos);
                    }
                    break;

                }
                // move in the direction 
                newRow += rowDirs[i];
                newCol += colDirs[i];
            }

        }
        // return all possible moves
        return moves;
    }
    // toString method
    @Override
    public String toString() {
        return color.equals("white") ?"♗" : "♝";
    }

    
}