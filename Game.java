import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.*;

public class Game implements Runnable {
    public void run() {
        final JFrame frameIntro = new JFrame ("IntroScreen");
//        frameIntro.setSize(1000, 1200);
        frameIntro.setLocation(300, 300);
//        frameIntro.setResizable(false);
        
        //Game title
        final JPanel gameName = new JPanel();
//        frameIntro.add(gameName, BorderLayout.NORTH);
        final JLabel name = new JLabel("MINESWEEPER");
        name.setFont(new Font("Serif", Font.PLAIN, 40));
        gameName.add(name);
        
        //Begin option
        final JButton Continue = new JButton("Continue");
        Continue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Reader freader;
                BufferedReader file;
                JFrame gameScreen = null;
                try {
                    freader = new FileReader("files/saved");
                    file = new BufferedReader(freader);
//                    try {
//                        gameScreen = PlayerScreen.getFrame(Integer.parseInt(file.readLine()), true);
//                    } catch (NullPointerException e1){
//                        JOptionPane.showMessageDialog(frameIntro, "No saved game found");
//                        System.out.println("No saved game");
//                    }
                    gameScreen = PlayerScreen.getFrame(Integer.parseInt(file.readLine()), true);
                } catch (FileNotFoundException e1) {
                } catch (NumberFormatException e1) {
                    System.out.println("Number format");
                } catch (IOException e1) {
                }
                try {
                    gameScreen.pack();
                    gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gameScreen.setVisible(true);
                    frameIntro.setVisible(false);
                } catch (NullPointerException e1){
                    JOptionPane.showMessageDialog(frameIntro, "No saved game found");
                    System.out.println("No saved game");
                }
//                frameIntro.setVisible(false);
//                gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                gameScreen.setVisible(true);
//                frameIntro.setVisible(false);
                
                
            }
        });
        
        final String[] levels = {"Easy", "Medium", "Hard"};
        final JComboBox<String> level = new JComboBox<String>(levels);
        
        final JButton start = new JButton("Start new game");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                frameIntro.dispose();
                JFrame gameScreen = null;
                switch((String) level.getSelectedItem()) {
                case "Easy": gameScreen = PlayerScreen.getFrame(5, false); break;
                case "Medium": gameScreen = PlayerScreen.getFrame(7, false); break;
                case "Hard": gameScreen = PlayerScreen.getFrame(9, false); break;
                }
                gameScreen.pack();
                gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameScreen.setVisible(true);
                frameIntro.setVisible(false);
            }
        });
        final JPanel optionButtons = new JPanel();
        optionButtons.add(Continue, BorderLayout.NORTH);
        optionButtons.add(level, BorderLayout.CENTER);
        optionButtons.add(start, BorderLayout.SOUTH);
        
        final JLabel instructions = new JLabel("whatever instructions... blah blah blah");
        
        
        frameIntro.add(gameName, BorderLayout.NORTH);
        frameIntro.add(optionButtons, BorderLayout.CENTER);
        frameIntro.add(instructions, BorderLayout.SOUTH);

        
        frameIntro.pack();
        frameIntro.setSize(800, 600);
        frameIntro.setResizable(false);
        frameIntro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameIntro.setVisible(true);
        
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
