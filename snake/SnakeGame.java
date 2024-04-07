// public class SnakeGame {
    
// }
import javax.swing.*;
import javax.swing.Timer; // Explicitly import javax.swing.Timer
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {

    private static final int BOX_SIZE = 20;
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static final int GAME_SPEED = 150;

    private ArrayList<Point> snake;
    private Point food;
    private String direction;
    private boolean running;
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(BOARD_WIDTH * BOX_SIZE, BOARD_HEIGHT * BOX_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        direction = "RIGHT";
        running = true;
        generateFood();
        timer = new Timer(GAME_SPEED, this); // Use javax.swing.Timer
        timer.start();
    }

    private void generateFood() {
        int x = (int) (Math.random() * BOARD_WIDTH);
        int y = (int) (Math.random() * BOARD_HEIGHT);
        food = new Point(x, y);
    }

    private void move() {
        Point head = snake.get(0);
        Point newHead = (Point) head.clone();

        switch (direction) {
            case "UP":
                newHead.translate(0, -1);
                break;
            case "DOWN":
                newHead.translate(0, 1);
                break;
            case "LEFT":
                newHead.translate(-1, 0);
                break;
            case "RIGHT":
                newHead.translate(1, 0);
                break;
        }

        if (newHead.equals(food)) {
            snake.add(0, newHead);
            generateFood();
        } else {
            snake.remove(snake.size() - 1);
            if (snake.contains(newHead) || newHead.x < 0 || newHead.x >= BOARD_WIDTH || newHead.y < 0 || newHead.y >= BOARD_HEIGHT) {
                running = false;
                timer.stop();
            } else {
                snake.add(0, newHead);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillRect(food.x * BOX_SIZE, food.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);

        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * BOX_SIZE, point.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
        }

        if (!running) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over!", BOARD_WIDTH * BOX_SIZE / 2 - 70, BOARD_HEIGHT * BOX_SIZE / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (!direction.equals("DOWN")) {
                    direction = "UP";
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!direction.equals("UP")) {
                    direction = "DOWN";
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!direction.equals("RIGHT")) {
                    direction = "LEFT";
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!direction.equals("LEFT")) {
                    direction = "RIGHT";
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new SnakeGame(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
