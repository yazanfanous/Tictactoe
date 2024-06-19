//Yazan Fanous
//June 10th 2024
//Tic-Tac-Toe

//Create a game of tic-tac-toe where the user is able to play against antoher user as well as against an AI.


//Import libraries

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Creating main class
public class Tictactoe {
    private JFrame frame;
    private JPanel introPanel;
    private JPanel gamePanel;
    private JPanel exitPanel;
    private JButton[] buttons = new JButton[9];
    private boolean xTurn = true;
    private boolean isPlayerVsAI = false; // Flag to track game mode

    public Tictactoe() {
        // Initialize frame
        frame = new JFrame("Game");

        // Initialize panels
        introPanel = new JPanel();
        gamePanel = createGamePanel();
        exitPanel = createExitPanel();

        // Intro Screen
        introPanel.setLayout(new GridLayout(3, 1, 0, 10));
        introPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Label with welcome statement
        JLabel introLabel = new JLabel("Welcome to Tic-Tac-Toe!", SwingConstants.CENTER);
        introLabel.setFont(new Font("Arial", Font.BOLD, 24));
        introPanel.add(introLabel);

        // Button for player vs player
        JButton playerVsPlayerButton = new JButton("2 Players");
        playerVsPlayerButton.setFont(new Font("Arial", Font.PLAIN, 18));
        playerVsPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isPlayerVsAI = false;
                switchToGamePanel();
            }
        });
        introPanel.add(playerVsPlayerButton);

        // Button for player vs AI
        JButton playerVsAIButton = new JButton("1 Player (vs AI)");
        playerVsAIButton.setFont(new Font("Arial", Font.PLAIN, 18));
        playerVsAIButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isPlayerVsAI = true;
                switchToGamePanel();
            }
        });
        introPanel.add(playerVsAIButton);

        // Add the intro panel to the frame
        frame.add(introPanel);

        // Set Frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close when the frame is closed
        frame.setSize(400, 400); // Set the size of the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible
    }

    // Method to switch to the game panel
    private void switchToGamePanel() {
        frame.remove(introPanel); // Remove intro panel from the frame
        frame.add(gamePanel); // Add the game panel
        frame.revalidate(); // Refresh the frame to reflect changes
        frame.repaint(); // Repaint the frame
    }

    // Method to create the main game panel
    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add buttons to the game panel
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    if (button.getText().isEmpty()) { // Check if the button is empty
                        if (xTurn) {
                            button.setText("X"); // Places X if it's their turn
                        } else {
                            button.setText("O"); // Places O if it's their turn
                        }
                        button.setEnabled(false);
                        xTurn = !xTurn; // Toggle turn

                        checkForWinner(); // Check for winner after each move

                        if (isPlayerVsAI && !xTurn) {
                            makeAIMove(); // Make AI move if it's their turn
                            checkForWinner(); // Check for winner after AI move
                        }
                    }
                }
            });
            gamePanel.add(buttons[i]);
        }

        return gamePanel;
    }

    // Method to make a move for AI
    private void makeAIMove() {
        // AI move logic
        int index;
        do {
            index = (int) (Math.random() * 9);
        } while (!buttons[index].isEnabled());

        buttons[index].setText("O");
        buttons[index].setEnabled(false);
        xTurn = true;
    }

    // Method to check for a winner or tie
    public void checkForWinner() {
        // Check rows
        for (int i = 0; i < 9; i += 3) {
            if (buttons[i].getText().equals(buttons[i + 1].getText()) &&
                    buttons[i].getText().equals(buttons[i + 2].getText()) &&
                    !buttons[i].isEnabled()) {
                JOptionPane.showMessageDialog(frame, buttons[i].getText() + " wins!");
                gameOver();
                return;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i].getText().equals(buttons[i + 3].getText()) &&
                    buttons[i].getText().equals(buttons[i + 6].getText()) &&
                    !buttons[i].isEnabled()) {
                JOptionPane.showMessageDialog(frame, buttons[i].getText() + " wins!");
                gameOver();
                return;
            }
        }

        // Check diagonals
        if (buttons[0].getText().equals(buttons[4].getText()) &&
                buttons[0].getText().equals(buttons[8].getText()) &&
                !buttons[0].isEnabled()) {
            JOptionPane.showMessageDialog(frame, buttons[0].getText() + " wins!");
            gameOver();
            return;
        }
        if (buttons[2].getText().equals(buttons[4].getText()) &&
                buttons[2].getText().equals(buttons[6].getText()) &&
                !buttons[2].isEnabled()) {
            JOptionPane.showMessageDialog(frame, buttons[2].getText() + " wins!");
            gameOver();
            return;
        }

        // Check for tie
        boolean tie = true;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].isEnabled()) {
                tie = false;
                break;
            }
        }
        if (tie) {
            JOptionPane.showMessageDialog(frame, "Tie game!");
            gameOver();
        }
    }

    // Method to end the game
    public void gameOver() {
        // Game over logic
        frame.remove(gamePanel);
        frame.add(exitPanel);
        frame.revalidate();
        frame.repaint();
        // Reset game state
        resetGame();
    }

    // Method to reset game
    public void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        xTurn = true;
        if (!isPlayerVsAI) {
            isPlayerVsAI = false;
        }
    }

    // Method to create the exit panel
    private JPanel createExitPanel() {
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new GridLayout(3, 1, 0, 10));
        exitPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Game over message
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        exitPanel.add(gameOverLabel);

        // Button to return to main menu
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.PLAIN, 18));
        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(exitPanel);
                frame.add(introPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        exitPanel.add(mainMenuButton);

        // Button to exit the game
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        exitPanel.add(exitButton);

        return exitPanel;
    }

    public static void main(String[] args) {
        new Tictactoe();
    }
}
