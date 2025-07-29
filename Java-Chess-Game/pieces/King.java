package pieces;
import board.Board;
import java.util.ArrayList;
import java.util.List;
import position.Position;

// King class
public class King extends Piece {


    // constructor
    public King(String color, Position position) {
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
    
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }




   

    // possible moves
    @Override
    public List<Position> possibleMoves(Board board) {
        List <Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

        // checks all 8 directions
        int[] rowDirs = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colDirs = {-1, 0, 1, -1, 1, -1, 0, 1};

        // while loop to check all possible moves in the direction
        for (int i = 0; i < 8; i++) {
            int newRow = row + rowDirs[i];
            int newCol = col + colDirs[i];

            // checks if the new position is valid
            if (board.isValidPosition(newRow, newCol)) {
                Position newPos = new Position(newRow, newCol);
                Piece pieceAtPosition = board.getPiece(newPos);

                // checks if there is a piece at the new position
                if (pieceAtPosition == null || !pieceAtPosition.getColor().equals(getColor())) {
                    moves.add(newPos);
                }
            }
        }
        return moves;

    }

    // toString/print
    @Override
    public String toString() {
        return getColor().equals("white") ? "♔" : "♚";
    }
}