package game;

import board.Board;
import java.util.Scanner;
import player.Player;

public class Game {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private String currentTurn;

    public Game() {
        board = new Board();
        whitePlayer = new Player("white", board);
        blackPlayer = new Player("black", board);
        currentTurn = "white";
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (!board.isGameOver()) {
            board.display();
            System.out.println(currentTurn + "'s move (format: fromRow fromCol toRow toCol): ");
            int fromRow = scanner.nextInt();
            int fromCol = scanner.nextInt();
            int toRow = scanner.nextInt();
            int toCol = scanner.nextInt();

            boolean moveSuccess;
            if (currentTurn.equals("white")) {
                moveSuccess = whitePlayer.makeMove(
                    new position.Position(fromRow, fromCol),
                    new position.Position(toRow, toCol)
                );
            } else {
                moveSuccess = blackPlayer.makeMove(
                    new position.Position(fromRow, fromCol),
                    new position.Position(toRow, toCol)
                );
            }

            if (moveSuccess) {
                currentTurn = currentTurn.equals("white") ? "black" : "white";
            }
        }
        System.out.println("Game over!");
        scanner.close();
    }
}