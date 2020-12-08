import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameBoard extends JPanel {
    private mineField mf;
    private JLabel status;
    private final int width;
    private final int height;
    private static BufferedImage temp;
    
    public GameBoard(int size) {
        this.mf = new mineField(size);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        setFocusable(true);
        
        mf = new mineField(size);
        
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                
                if (mf.getMode() == 0) {
                    mf.unveilCell(p.x/100,  p.y/100);
                }
                else if (mf.getMode() == 1) {
                    mf.flagCell(p.x/100, p.y/100);
                }
                
                updateStatus();
                repaint();
            }
        });
        width = mf.getSize() * 100;
        height = mf.getSize() * 100;
    }
    
    public void updateStatus() {
        if (mf.checkGame() == 0) {
            status.setText("Game in progress");
        }
        else if (mf.checkGame() == 1) {
            status.setText("You win!");
        }
        else if (mf.checkGame() == 2) {
            status.setText("You lose!");
        }
        else {
            throw new IllegalArgumentException("checkGame invalid");
        }
    }
    
    public void save() {
        
    }
    
    public void toFlagMode() {
        mf.flagMode();
    }
    
    public void flag(int r, int c) {
        mf.flagCell(r, c);
    }
    
    public void peek() {
        
    }
    
    public void reset() {
        // TODO Auto-generated method stub
    }
    
    public JLabel getStatus() {
        return status;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Draw board grid
        for (int i = 0; i <= mf.getSize(); i++) {
            g.drawLine(i * 100, 0, i * 100, width);
            g.drawLine(0, i * 100, height, i * 100);
        }
        
        //Unveil appropriate cell
        for (int i = 0; i < mf.getSize(); i++) {
            for (int j = 0; j <mf.getSize(); j++) {
                String state = mf.getObj(i, j);
                if (state.equals("F") || state.equals("F*")) {
                    try {
                        if (temp == null) {
                            temp = ImageIO.read(new File("game_img/red_flag.jpg"));
                        }
                    } catch (IOException e) {
                        System.out.println("Img error" + e.getMessage());
                    }
                    g.drawImage(temp, i*100, j*100, (i+1)*100, (j+1)*100, null);
                }
                else if (state.equals("0") || state.equals("1") || state.equals("2") || state.equals("3") || 
                        state.equals("4") || state.equals("5") || state.equals("6") ||
                        state.equals("7") || state.equals("8")) {
                    g.drawString(state, (i+1)*100, j*100);
                }
                else if (state.equals("X")) {
                    try {
                        if (temp == null) {
                            temp = ImageIO.read(new File("game_img/bomb.jpg"));
                        }
                    } catch (IOException e) {
                        System.out.println("Img error" + e.getMessage());
                    }
                    g.drawImage(temp, i*100, j*100, (i+1)*100, (j+1)*100, null);
                }
            }
        }
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
