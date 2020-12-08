import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class PlayerScreen extends JFrame {
    private int size;
    public PlayerScreen(int size) {
        this.size = size;
    }
    // static function that return frame 
    public static JFrame getFrame(int size) {
        final JFrame frame = new JFrame ("MSweeper");
        frame.setLocation(300, 300);
        
        //Board set up
        final GameBoard board = new GameBoard(size);
        frame.add(board, BorderLayout.CENTER);
        
        
        //Panel
        final JPanel option_panel = new JPanel();
        frame.add(option_panel, BorderLayout.NORTH);
        
        //Buttons within panel
        final JButton save = new JButton("Save game");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.save();  
            }
        });
        
        
        final JButton flag = new JButton("Flag"); //Include number of flag
        
        flag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.toFlagMode();
            }
        });
        
        final JButton peek = new JButton("Peek"); //Include number of peek
        peek.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.peek();  
            }
        });
        
       final JButton newGame = new JButton("New game");
       newGame.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               board.reset();  
           }
       });
       option_panel.add(newGame);
       option_panel.add(save);
       option_panel.add(flag);
       option_panel.add(peek);
       
       //Status panel
       final JPanel status_bar = new JPanel();
       status_bar.add(board.getStatus());
       frame.add(status_bar, BorderLayout.SOUTH);
       
//       frame.pack();
//       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//       frame.setVisible(true);
       
       return frame;
       
    }
}
