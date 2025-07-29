package pieces;
import board.Board;
import java.util.ArrayList;
import java.util.List;
import position.Position;

public class Pawn extends Piece {

    public Pawn(String color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int dir = getColor().equals("white") ? -1 : 1;

        int row = getPosition().getRow();
        int col = getPosition().getCol();

        // 1-square forward move
        Position oneForward = new Position(row + dir, col);
        if (board.isValidPosition(oneForward.getRow(), oneForward.getCol()) &&
            board.getPiece(oneForward) == null) {
            moves.add(oneForward);

            // 2-square forward move from starting row
            int startingRow = getColor().equals("white") ? 6 : 1;
            if (row == startingRow) {
                Position twoForward = new Position(row + 2 * dir, col);
                if (board.getPiece(twoForward) == null) {
                    moves.add(twoForward);
                }
            }
        }

        // Capture diagonally
        int[] captureCols = {col - 1, col + 1};
        for (int newCol : captureCols) {
            int newRow = row + dir;
            if (board.isValidPosition(newRow, newCol)) {
                Position capturePos = new Position(newRow, newCol);
                Piece target = board.getPiece(capturePos);
                if (target != null && !target.getColor().equals(getColor())) {
                    moves.add(capturePos);
                }
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return getColor().equals("white") ? "♙" : "♟";
    }
}
