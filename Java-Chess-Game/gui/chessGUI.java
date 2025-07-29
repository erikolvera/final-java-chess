package gui;
import java.awt.*;
import javax.swing.*;

public class chessGUI extends JFrame {
    //Sets colors for the board
    private final Color whiteColor = new Color(255,255,230); // cream color
    private final Color blackColor = new Color(0,0,0); // black color
    private JPanel boardPanel;

    public chessGUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centers the window on your screen
        setSize(new Dimension(400, 400)); // window size
        
        boardPanel = new JPanel(new GridLayout(8,8)); // initializes 8x8 grid layout
        drawBoard();

        add(boardPanel);
        setVisible(true);
    }

    private void drawBoard() {
        for (int row = 0; row < 8; row++) { // Loop through rows
            for (int col =0; col < 8; col++) { // // Loop through columns
                JPanel square = new JPanel();
                Color color = (row + col) % 2 == 0 ? whiteColor : blackColor;
                square.setBackground(color);
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // adds black border around square edges
                boardPanel.add(square);
            }
        }
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new chessGUI());
    }
}
