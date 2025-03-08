import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class ArrowKeyPuzzle extends JPanel implements KeyListener {
    private static final int SIZE = 5; // Grid size
    private char[][] grid;
    private int playerX, playerY, goalX, goalY;

    public ArrowKeyPuzzle() {
        this.setFocusable(true);
        this.addKeyListener(this);
        initializeGrid();
    }

    private void initializeGrid() {
        grid = new char[SIZE][SIZE];

        // Fill grid with empty spaces
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = ' ';
            }
        }

        // Place player at (0,0)
        playerX = 0;
        playerY = 0;
        grid[playerX][playerY] = 'P';

        // Randomly place goal
        Random rand = new Random();
        do {
            goalX = rand.nextInt(SIZE);
            goalY = rand.nextInt(SIZE);
        } while (goalX == playerX && goalY == playerY); // Ensure it's not on the player
        grid[goalX][goalY] = 'G';

        // Add some walls randomly
        for (int i = 0; i < SIZE; i++) {
            int wallX = rand.nextInt(SIZE);
            int wallY = rand.nextInt(SIZE);
            if (grid[wallX][wallY] == ' ') {
                grid[wallX][wallY] = '#';
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 30));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                g.drawString(String.valueOf(grid[i][j]), j * 40 + 50, i * 40 + 50);
            }
        }
    }

    private void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        // Check boundaries and walls
        if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE && grid[newX][newY] != '#') {
            grid[playerX][playerY] = ' ';
            playerX = newX;
            playerY = newY;
            grid[playerX][playerY] = 'P';

            // Check if player reaches the goal
            if (playerX == goalX && playerY == goalY) {
                JOptionPane.showMessageDialog(this, "You Win!");
                initializeGrid(); // Reset the game
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: movePlayer(-1, 0); break;
            case KeyEvent.VK_DOWN: movePlayer(1, 0); break;
            case KeyEvent.VK_LEFT: movePlayer(0, -1); break;
            case KeyEvent.VK_RIGHT: movePlayer(0, 1); break;
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Arrow Key Puzzle");
        ArrowKeyPuzzle puzzle = new ArrowKeyPuzzle();
        frame.add(puzzle);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
