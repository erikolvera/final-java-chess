package board;
import pieces.*;
import position.Position;

public class Board {
    private Piece[][] board;
    private String currentTurn = "white";
    private boolean gameOver = false;
    public void setCurrentTurn(String color) {
    this.currentTurn = color;
}
    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }
    /*
     * this class represents a chess board
     * it initializes the board with pieces in their starting positions  
     * board is an 8x8 grid of Piece objects                             
    */
    public void setPiece(Piece piece) {
    Position pos = piece.getPosition();
    board[pos.getRow()][pos.getCol()] = piece;
        }

    public void initializeBoard() { // create initial board setup
        board[0][0] = new Rook("black", new Position(0,0)); // top left position
        board[0][1] = new Knight("black", new Position(0,1));
        board[0][2] = new Bishop("black", new Position(0,2));
        board[0][3] = new Queen("black", new Position(0,3));
        board[0][4] = new King("black", new Position(0,4));
        board[0][5] = new Bishop("black", new Position(0,5));
        board[0][6] = new Knight("black", new Position(0,6));
        board[0][7] = new Rook("black", new Position(0,7)); // top right position

        for(int i=0; i<8; i++){
            board[1][i] = new Pawn("black", new Position(1,i));
        }

        board[7][0] = new Rook("white", new Position(7,0)); // bottom left position
        board[7][1] = new Knight("white", new Position(7,1));
        board[7][2] = new Bishop("white", new Position(7,2));
        board[7][3] = new Queen("white", new Position(7,3));  
        board[7][4] = new King("white", new Position(7,4)); 
        board[7][5] = new Bishop("white", new Position(7,5));
        board[7][6] = new Knight("white", new Position(7,6));
        board[7][7] = new Rook("white", new Position(7,7)); // bottom right position

        for(int i=0; i<8; i++){
            board[6][i] = new Pawn("white", new Position(6,i));
        }
    }

    public Piece getPiece(Position pos){
        int row = pos.getRow(); //Gets the row number from the position object
        int col = pos.getCol(); //Gets column number from the position object

        //Verifies it's within the 8x8 board
        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            return board[row][col];
        }
        return null;
    }

    public boolean movePiece(Position from, Position to) {
        Piece movingPiece = getPiece(from);

        //Runs if player chooses a spot where there isn't a piece
        if (movingPiece == null) {
            System.out.println("No piece in that position.");
            return false;
        }
        if (!movingPiece.getColor().equals(currentTurn)) {
            System.out.println("It is " + movingPiece.getColor() + "'s turn.");
            return false;
        }
        if (!movingPiece.possibleMoves(this).contains(to)) {
            System.out.println("That is not a valid move.");
            return false;
        }
        
        Piece capturedPiece = getPiece(to);
        board[from.getRow()][from.getCol()] = null;
        board[to.getRow()][to.getCol()] = movingPiece;
        movingPiece.setPosition(to);

        if(isCheck(currentTurn)){ //Checks if the move puts the player in check
            board[from.getRow()][from.getCol()] = movingPiece;
            board[to.getRow()][to.getCol()] = capturedPiece;
            movingPiece.setPosition(from);
            System.out.println("This move puts you in check.");
        
            return false;
        }
          currentTurn = currentTurn.equals("white") ? "black" : "white";

    if (Checkmate(currentTurn)) {
        System.out.println("Checkmate! " + (currentTurn.equals("white") ? "Black" : "White") + " wins.");
        gameOver = true;
    } else if (isCheck(currentTurn)) {
        System.out.println(currentTurn + " is in check.");
    }

    return true;
    
    }

    public void display(){  // display the board in console
        System.out.println("  a b c d e f g h"); // displays the letters of the board

        for(int row=0; row<8; row++) { // displays the numbers of the board
            System.out.print((8-row)+ " ");
            for(int col=0; col<8; col++) {
                Piece p = board[row][col];
                if(p != null){
                    System.out.print(p + " ");
                }else{
                    if((row+col)%2==0){ // basically if 0 then black square, else white square, even/odd grid
                        System.out.print("⬛");  // black square
                    } else {
                        System.out.print("⬜");  // white square
                    }
                }
            }
            System.out.println(" " + (8-row));
        }

        System.out.println("  a b c d e f g h"); // displays the letters of the board
    }  
    
    private Position findKingPosition(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece instanceof King && piece.getColor().equals(color)) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    public boolean isCheck(String color) { // checks if the player is in check
        Position kingPosition = findKingPosition(color);
        if (kingPosition == null) {
            return false;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.possibleMoves(this).contains(kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean Checkmate(String color) {
        if (!isCheck(color)){
            return false;
        }
        for (int row = 0; row < 8; row++) { // Loop through rows
            for (int col = 0; col < 8; col++) { // Loop through columns
                Piece piece = board[row][col]; // Get the piece at the current position
                if (piece != null && piece.getColor().equals(color)) { // Check if the piece is not null and belongs to the current player
                    for (Position move : piece.possibleMoves(this)) { // Loop through possible moves for the piece

                        Position from = piece.getPosition(); // Get the current position of the piece
                        Position to = move; // Get the piece that will be captured by the move
                        Piece capturedPiece = getPiece(to); // Get the piece that will be captured by the move
                        
                        board[from.getRow()][from.getCol()] = null; // Remove the piece from its current position
                        board[to.getRow()][to.getCol()] = piece; // Move the piece to the new position
                        piece.setPosition(to); // Update the piece's position

                        boolean check = isCheck(color); // Check if the player is still in check

                        // Undo the move and check if the player is still in check
                        board[from.getRow()][from.getCol()] = piece;
                        board[to.getRow()][to.getCol()] = capturedPiece;
                        piece.setPosition(from);

                        if (!check) {// if the player is not in check after the move, it is not checkmate
                            return false;
                        }
                    }
                }
            }
        }
        return true; // if the player is in check and there are no valid moves, it is checkmate
    }



    
     public String getCurrentTurn() {
        return currentTurn;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public Boolean isValidPosition(int row, int col) { // checks if the position is within the 8x8 board
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

}