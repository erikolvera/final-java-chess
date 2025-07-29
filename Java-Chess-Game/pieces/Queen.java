package pieces;
import board.Board;
import java.util.ArrayList;
import java.util.List;
import position.Position;

// Queen class
public class Queen extends Piece {

    public Queen(String color, Position position) {
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
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

    // checks all 8 directions
        int[] rowDirs = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colDirs = {-1, 0, 1, -1, 1, -1, 0, 1};

        // while loop to check all possible moves in the direction
        for (int i = 0; i < 8; i++) {
            int newRow = row + rowDirs[i];
            int newCol = col + colDirs[i];

            // check if the new position is valid
            while (board.isValidPosition(newRow, newCol)) {
                Position newPos = new Position(newRow, newCol);
                Piece pieceAtPosition = board.getPiece(newPos);

                // if the new position is empty, add it to the list
                if (pieceAtPosition == null ) {
                    moves.add(newPos);
                } else{ // if the new position is occupied by a piece of the opposite color, add it to the list
                    if(!pieceAtPosition.getColor().equals(this.color)){
                        moves.add(newPos);
                    }
                    break;
                }
                // update new row and col
                newRow += rowDirs[i];
                newCol += colDirs[i];
            }
        }
        
        // return possible moves
        return moves;
    }
    // toString/print
    @Override
    public String toString() {
        return getColor().equals("white") ? "♕" : "♛";
    }
}