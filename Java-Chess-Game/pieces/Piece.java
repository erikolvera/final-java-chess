package pieces;
import board.Board;
import java.util.List;
import position.Position;
public abstract class Piece {
    protected String color;
    protected Position position;
    
    public Piece(String color, Position position) { // constructor 
        this.color = color;
        this.position = position;
    }

    //return piece color
    public String getColor() {
        return this.color;
    }

    //return piece position
    public Position getPosition() {
        return this.position;
    }

    //set piece position
    public void setPosition(Position position) {
        this.position = position;
    }
    
    
    public abstract List<Position> possibleMoves(Board board);
    
    public abstract String toString();
}