package gui;

import board.Board;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import pieces.Piece;
import position.Position;

public class boardGUI implements Serializable {

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private final JPanel[][] gameBoardSquares = new JPanel[ROWS][COLS];
    private JFrame frame;
    private Board board; // Phase 1 Board model

    // Customization settings
    private Color lightSquareColor = Color.WHITE;
    private Color darkSquareColor = Color.GRAY;
    private String pieceStyle = "Classic"; // "Classic" or "Modern"
    private int boardSize = 800; // Default size

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new boardGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(ROWS, COLS));
        frame.setSize(boardSize, boardSize);

        // Initialize model board
        board = new Board();

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game Menu Options");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem settingsItem = new JMenuItem("Settings");

        Font menuFont = new Font("Arial", Font.BOLD, 16);
        gameMenu.setFont(menuFont);
        newGameItem.setFont(menuFont);
        saveGameItem.setFont(menuFont);
        loadGameItem.setFont(menuFont);
        settingsItem.setFont(menuFont);

        gameMenu.add(newGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.add(loadGameItem);
        gameMenu.addSeparator();
        gameMenu.add(settingsItem);
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);

        newGameItem.addActionListener(e -> {
            board = new Board();
            updateBoardFromModel();
        });

        saveGameItem.addActionListener(e -> saveGame());

        loadGameItem.addActionListener(e -> loadGame());

        settingsItem.addActionListener(e -> openSettingsDialog());

        // Initialize board squares
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground((i + j) % 2 == 0 ? lightSquareColor : darkSquareColor);
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setFont(new Font("Serif", Font.PLAIN, 46));
                label.setForeground((i + j) % 2 == 0 ? Color.BLACK : Color.WHITE);
                panel.add(label, BorderLayout.CENTER);
                final int row = i;
                final int col = j;
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleMouseClick(row, col);
                    }
                });
                gameBoardSquares[i][j] = panel;
                frame.add(panel);
            }
        }

        updateBoardFromModel();

        frame.setVisible(true);
    }

    // Track selected square position for moves
    private int selectedRow = -1;
    private int selectedCol = -1;

    private void handleMouseClick(int row, int col) {
        if (board.isGameOver()) {
            JOptionPane.showMessageDialog(frame, "Game over! Start a new game.");
            return;
        }

        Piece clickedPiece = board.getPiece(new Position(row, col));

        if (selectedRow == -1 && clickedPiece != null && clickedPiece.getColor().equals(board.getCurrentTurn())) {
            // Select this piece
            selectedRow = row;
            selectedCol = col;
            highlightSquare(row, col, true);
        } else if (selectedRow != -1) {
            // Attempt move from selected square to clicked square
            Position from = new Position(selectedRow, selectedCol);
            Position to = new Position(row, col);

            boolean moved = board.movePiece(from, to);

            if (moved) {
                updateBoardFromModel();
            } else {
                // Move invalid, keep selected piece until deselected
                JOptionPane.showMessageDialog(frame, "Invalid move.");
            }
            // Remove highlight
            highlightSquare(selectedRow, selectedCol, false);
            selectedRow = -1;
            selectedCol = -1;
        }
    }

    private void highlightSquare(int row, int col, boolean highlight) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) return;
        if (highlight) {
            gameBoardSquares[row][col].setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
        } else {
            gameBoardSquares[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }

    private void updateBoardFromModel() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JLabel label = (JLabel) gameBoardSquares[i][j].getComponent(0);
                Piece piece = board.getPiece(new Position(i, j));
                if (piece != null) {
                    label.setText(getStyledPieceUnicode(piece));
                } else {
                    label.setText("");
                }
                gameBoardSquares[i][j].setBackground((i + j) % 2 == 0 ? lightSquareColor : darkSquareColor);
            }
        }
        frame.setSize(boardSize, boardSize);
    }

    private String getStyledPieceUnicode(Piece piece) {
        // Map piece to Unicode character based on style and color
        String color = piece.getColor();
        String type = piece.getClass().getSimpleName();

        if ("Modern".equals(pieceStyle)) {
            // Modern style uses standard Unicode chess symbols (black/white)
            return switch (type) {
                case "King" -> color.equals("white") ? "\u2654" : "\u265A";
                case "Queen" -> color.equals("white") ? "\u2655" : "\u265B";
                case "Rook" -> color.equals("white") ? "\u2656" : "\u265C";
                case "Bishop" -> color.equals("white") ? "\u2657" : "\u265D";
                case "Knight" -> color.equals("white") ? "\u2658" : "\u265E";
                case "Pawn" -> color.equals("white") ? "\u2659" : "\u265F";
                default -> "";
            };
        } else {
            // Classic style: use bold Unicode pieces or similar
            return switch (type) {
                case "King" -> color.equals("white") ? "♔" : "♚";
                case "Queen" -> color.equals("white") ? "♕" : "♛";
                case "Rook" -> color.equals("white") ? "♖" : "♜";
                case "Bishop" -> color.equals("white") ? "♗" : "♝";
                case "Knight" -> color.equals("white") ? "♘" : "♞";
                case "Pawn" -> color.equals("white") ? "♙" : "♟";
                default -> "";
            };
        }
    }

    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(board);
                JOptionPane.showMessageDialog(frame, "Game saved successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving game: " + ex.getMessage());
            }
        }
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = in.readObject();
                if (obj instanceof Board loadedBoard) {
                    this.board = loadedBoard;
                    updateBoardFromModel();
                    JOptionPane.showMessageDialog(frame, "Game loaded successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid save file.");
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Error loading game: " + ex.getMessage());
            }
        }
    }

    private void openSettingsDialog() {
        JDialog dialog = new JDialog(frame, "Settings", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(400, 300);

        // Light square color
        JLabel lightLabel = new JLabel("Light Square Color:");
        JButton lightButton = new JButton();
        lightButton.setBackground(lightSquareColor);
        lightButton.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(dialog, "Choose Light Square Color", lightSquareColor);
            if (chosen != null) lightButton.setBackground(chosen);
        });

        // Dark square color
        JLabel darkLabel = new JLabel("Dark Square Color:");
        JButton darkButton = new JButton();
        darkButton.setBackground(darkSquareColor);
        darkButton.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(dialog, "Choose Dark Square Color", darkSquareColor);
            if (chosen != null) darkButton.setBackground(chosen);
        });

        // Piece style selection
        JLabel pieceLabel = new JLabel("Piece Style:");
        String[] pieceStyles = {"Classic", "Modern"};
        JComboBox<String> pieceCombo = new JComboBox<>(pieceStyles);
        pieceCombo.setSelectedItem(pieceStyle);

        // Board size selection
        JLabel sizeLabel = new JLabel("Board Size:");
        String[] sizes = {"Small", "Medium", "Large"};
        JComboBox<String> sizeCombo = new JComboBox<>(sizes);
        if (boardSize <= 500) sizeCombo.setSelectedIndex(0);
        else if (boardSize <= 800) sizeCombo.setSelectedIndex(1);
        else sizeCombo.setSelectedIndex(2);

        // OK and Cancel buttons
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");

        okBtn.addActionListener(e -> {
            lightSquareColor = lightButton.getBackground();
            darkSquareColor = darkButton.getBackground();
            pieceStyle = (String) pieceCombo.getSelectedItem();
            String sizeSel = (String) sizeCombo.getSelectedItem();
            boardSize = switch (sizeSel) {
                case "Small" -> 500;
                case "Medium" -> 800;
                case "Large" -> 1000;
                default -> 800;
            };
            updateBoardFromModel();
            dialog.dispose();
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.add(lightLabel);
        dialog.add(lightButton);
        dialog.add(darkLabel);
        dialog.add(darkButton);
        dialog.add(pieceLabel);
        dialog.add(pieceCombo);
        dialog.add(sizeLabel);
        dialog.add(sizeCombo);
        dialog.add(okBtn);
        dialog.add(cancelBtn);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
