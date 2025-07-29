package gui;

import board.Board;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import pieces.Piece;
import position.Position;

public class boardGUI extends JFrame {
    private Board gameBoard;
    private JPanel[][] squares;
    private Position selectedPosition;

    public boardGUI(Board board) {
        this.gameBoard = board;
        this.squares = new JPanel[8][8];
        this.selectedPosition = null;

        setTitle("Java Chess");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        initializeBoardGUI();
        updateBoardGUI();
        setVisible(true);
    }

    private void initializeBoardGUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel square = new JPanel(new BorderLayout());
                square.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);

                final int r = row;
                final int c = col;

                square.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(r, c);
                    }
                });

                squares[row][col] = square;
                add(square);
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        if (gameBoard.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game is over.");
            return;
        }

        Position clicked = new Position(row, col);
        Piece clickedPiece = gameBoard.getPiece(clicked);

        if (selectedPosition == null) {
            // First click: select your own piece
            if (clickedPiece != null && clickedPiece.getColor().equals(gameBoard.getCurrentTurn())) {
                selectedPosition = clicked;
                highlightSquare(row, col, true);
            }
        } else {
            // Second click: try to move
            boolean moved = gameBoard.movePiece(selectedPosition, clicked);
            clearHighlights();
            selectedPosition = null;
            updateBoardGUI();

            if (moved && gameBoard.isGameOver()) {
                String winner = gameBoard.getCurrentTurn().equals("white") ? "Black" : "White";
                JOptionPane.showMessageDialog(this, "Checkmate! " + winner + " wins.");
            } else if (moved && gameBoard.isCheck(gameBoard.getCurrentTurn())) {
                JOptionPane.showMessageDialog(this, gameBoard.getCurrentTurn() + " is in check!");
            }
        }
    }

    private void highlightSquare(int row, int col, boolean highlight) {
        squares[row][col].setBorder(highlight ? BorderFactory.createLineBorder(Color.RED, 3) : null);
    }

    private void clearHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                highlightSquare(row, col, false);
            }
        }
    }

    private void updateBoardGUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].removeAll();
                Piece piece = gameBoard.getPiece(new Position(row, col));
                if (piece != null) {
                    JLabel label = new JLabel(getUnicodeSymbol(piece), SwingConstants.CENTER);
                    label.setFont(new Font("Arial", Font.PLAIN, 36));
                    squares[row][col].add(label);
                }
                squares[row][col].revalidate();
                squares[row][col].repaint();
            }
        }
    }

    private String getUnicodeSymbol(Piece piece) {
        String color = piece.getColor();
        String name = piece.getClass().getSimpleName();

        return switch (name) {
            case "King" -> color.equals("white") ? "♔" : "♚";
            case "Queen" -> color.equals("white") ? "♕" : "♛";
            case "Rook" -> color.equals("white") ? "♖" : "♜";
            case "Bishop" -> color.equals("white") ? "♗" : "♝";
            case "Knight" -> color.equals("white") ? "♘" : "♞";
            case "Pawn" -> color.equals("white") ? "♙" : "♟";
            default -> "?";
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board();
            new boardGUI(board);
        });
    }
}
