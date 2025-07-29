package pieces;
import board.Board;
import java.util.ArrayList;
import java.util.List;
import position.Position;

// Rook class
public class Rook extends Piece {
    public Rook(String color, Position position) {
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

        // Implement logic for rook's possible moves
        List<Position> moves = new ArrayList<>();

        // Checks all 4 cardinal directions
        int row = position.getRow();
        int col = position.getCol();

        int[] rowDirs = {1, -1, 0, 0};
        int[] colDirs = {0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + rowDirs[i];
            int newCol = col + colDirs[i];

            // while loop to check all possible moves in the direction
            while (board.isValidPosition(newRow, newCol)) {
                // checks if the new position is valid
                Position newPos = new Position(newRow, newCol);
                Piece pieceAtPosition = board.getPiece(newPos);

                // if the new position is empty, add it to the list
                if (pieceAtPosition == null) {
                    moves.add(newPos);
                } else { // if the new position is occupied by a piece of the opposite color, add it to the list
                    if(!pieceAtPosition.getColor().equals(getColor())){
                        moves.add(newPos);
                    }
    
                    break;
                }
            newRow += rowDirs[i];
            newCol += colDirs[i];
        }
    }

    return moves;
}
// toString/Print
    @Override
    public String toString() {
        return color.equals("white") ? "♖" : "♜";
    }
}