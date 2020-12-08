import java.awt.*;
import java.awt.event.*;
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
                frameIntro.dispose();
                //Add function to retrieve saved file
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
                case "Easy": gameScreen = PlayerScreen.getFrame(5); break;
                case "Medium": gameScreen = PlayerScreen.getFrame(7); break;
                case "Hard": gameScreen = PlayerScreen.getFrame(9); break;
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
