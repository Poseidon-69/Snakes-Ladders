import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SnakesAndLaddersGame extends JFrame {
  
  private JPanel gameBoard;
  private HashMap<Integer, Integer> snakeMap;
  private HashMap<Integer, Integer> ladderMap;
  private int currentPlayerPosition;
  
  public SnakesAndLaddersGame() {
    setTitle("Snakes and Ladders Game");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    snakeMap = new HashMap<>();
    ladderMap = new HashMap<>();
    currentPlayerPosition = 1;
    
    createSnakeMap();
    createLadderMap();
    
    gameBoard = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        
        int x = 0;
        int y = height - 50;
        int squareSize = width / 10;
        int count = 100;
        for (int i = 0; i < 10; i++) {
          for (int j = 0; j < 10; j++) {
            if (count % 2 == 0) {
              g.setColor(Color.WHITE);
            } else {
              g.setColor(Color.GRAY);
            }
            g.fillRect(x, y, squareSize, squareSize);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, squareSize, squareSize);
            count--;
            x += squareSize;
          }
          y -= squareSize;
          x = 0;
        }
        
        for (Integer start : snakeMap.keySet()) {
          int end = snakeMap.get(start);
          drawSnake(g, start, end, squareSize);
        }
        
        for (Integer start : ladderMap.keySet()) {
          int end = ladderMap.get(start);
          drawLadder(g, start, end, squareSize);
        }
      }
    };
    
    add(gameBoard);
    
    JButton rollButton = new JButton("Roll Dice");
    rollButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int diceValue = rollDice();
        movePlayer(diceValue);
      }
    });
    add(rollButton, "South");
    
    setVisible(true);
  }
  
  private void createSnakeMap() {
    snakeMap.put(17, 7);
    snakeMap.put(54, 34);
    snakeMap.put(62, 19);
    snakeMap.put(64, 60);
    snakeMap.put(87, 24);
    snakeMap.put(93, 73);
    snakeMap.put(95, 75);
    snakeMap.put(99, 78);
  }
  
  private void createLadderMap() {
    ladderMap.put(4, 14);
    ladderMap.put(9, 31);
    ladderMap.put(20, 38);
    ladderMap.put(28, 84);
    ladderMap.put(40, 59);
    ladderMap.put(63, 81);
    ladderMap.put(71, 91);
    ladderMap.put(17, 7);
  }
  
  private void drawSnake(Graphics g, int start, int end, int squareSize) {
    int startX = (start % 10) * squareSize;
    int startY = (10 - (start / 10)) * squareSize;
    int endX = (end % 10) * squareSize;
    int endY = (10 - (end / 10)) * squareSize;
    g.setColor(Color.RED);
    g.drawLine(startX + (squareSize / 2), startY + (squareSize / 2), endX + (squareSize / 2), endY + (squareSize / 2));
    g.setColor(Color.BLACK);
  }
  private void drawLadder(Graphics g, int start, int end, int squareSize) {
    int startX = (start % 10) * squareSize;
    int startY = (10 - (start / 10)) * squareSize;
    int endX = (end % 10) * squareSize;
    int endY = (10 - (end / 10)) * squareSize;
    g.setColor(Color.GREEN);
    g.drawLine(startX + (squareSize / 2), startY + (squareSize / 2), endX + (squareSize / 2), endY + (squareSize / 2));
    g.setColor(Color.BLACK);
  }
  
  private void drawPlayer(Graphics g, int position, int squareSize) {
    int x = ((position - 1) % 10) * squareSize;
    int y = (10 - ((position - 1) / 10)) * squareSize;
    g.setColor(Color.BLUE);
    g.fillOval(x + 5, y + 5, squareSize - 10, squareSize - 10);
    g.setColor(Color.BLACK);
  }
  
  private int rollDice() {
    Random rand = new Random();
    int diceValue = rand.nextInt(6) + 1;
    JOptionPane.showMessageDialog(this, "You rolled a " + diceValue + "!");
    return diceValue;
  }
  
  private void movePlayer(int diceValue) {
    int newPosition = currentPlayerPosition + diceValue;
    if (newPosition > 100) {
      JOptionPane.showMessageDialog(this, "You cannot move that far!");
      return;
    }
    int oldPosition = currentPlayerPosition;
    currentPlayerPosition = newPosition;
    Integer snakeEnd = snakeMap.get(currentPlayerPosition);
    if (snakeEnd != null) {
      currentPlayerPosition = snakeEnd;
      JOptionPane.showMessageDialog(this, "Oh no! You landed on a snake! You slide down to square " + currentPlayerPosition + ".");
    }
    Integer ladderEnd = ladderMap.get(currentPlayerPosition);
    if (ladderEnd != null) {
      currentPlayerPosition = ladderEnd;
      JOptionPane.showMessageDialog(this, "Congratulations! You landed on a ladder! You climb up to square " + currentPlayerPosition + ".");
    }
    if (currentPlayerPosition == 100) {
      JOptionPane.showMessageDialog(this, "Congratulations! You have won the game!");
    }
    gameBoard.repaint();
  }
  
  public static void main(String[] args) {
    new SnakesAndLaddersGame();
  }
}