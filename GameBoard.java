import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GameBoard extends JPanel {
    private mineField mf;
    private int size;
    private JLabel status;
    private final int width;
    private final int height;
//    private static BufferedImage temp;
    
    public GameBoard(int size, boolean saved) {
        this.mf = new mineField(size);
        this.size = size;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        setFocusable(true);
        
        mf = new mineField(size);
        if (!saved) {
            mf.setBomb();
        }
        else {
            mf.loadBomb();
            FileReader freader;
            BufferedReader file;
            try {
                freader = new FileReader("files/saved");
                file = new BufferedReader(freader);
                file.readLine();
                mf.setFlag(Integer.parseInt(file.readLine()));
                mf.setPeek(Integer.parseInt(file.readLine()));
            } catch (FileNotFoundException e1) {
            } catch (IOException e1) {
                
            } 
        }
       

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                
                if (mf.getMode() == 0) {
                    mf.uncoverCell(p.x/100,  p.y/100);
                    repaint();
                }
                else if (mf.getMode() == 1) {
                    mf.flagCell(p.x/100, p.y/100);
                    repaint();
                }
                else if (mf.getMode() == 2) {
                    mf.peekCell(p.x/100, p.y/100);
                    repaint();
                    Timer timer = new Timer(1500, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            long currentTime = System.currentTimeMillis();
                            if (System.currentTimeMillis() - currentTime < 1500) {
                                mf.recoverCell(p.x/100, p.y/100);
                                repaint();
                            }
                        }
                    });
                    timer.start();
                }
                
                updateStatus();
            }
        });
        width = mf.getSize() * 100;
        height = mf.getSize() * 100;
        status = new JLabel("Game in progress");
    }
    
    public void updateStatus() {
        if (mf.checkGame() == 0) {
            status.setText("Game in progress -- " + "Flag Remaining: " + mf.getFlagLeft() + 
                    " -- Peek Remaining: " + mf.getPeekLeft());
        }
        else if (mf.checkGame() == 1) {
            status.setText("You win!");
        }
        else if (mf.checkGame() == 2) {
            status.setText("You lose!");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    mf.uncoverCell(i, j);
                }
            }
        }
        else {
            throw new IllegalArgumentException("checkGame invalid");
        }
    }
    
    public void save() {
        mf.saveGame(mf.convertMFtoListString());
    }
    
    public void toFlagMode() {
        mf.flagMode();
    }
    
    public void toPeekMode() {
        mf.peekMode();
    }
    
    public void flag(int r, int c) {
        mf.flagCell(r, c);
    }
    
    public void resetMode() {
        mf.resetMode();
    }
    
    public void reset() {
        this.mf = new mineField(size);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        setFocusable(true);
        
        mf = new mineField(size);
        mf.setBomb();
        status.setText("Game in progress");
        mf.resetSavedFile();
    }
    
    public JLabel getStatus() {
        updateStatus();
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
                        BufferedImage temp = ImageIO.read(new File("game_img/flag.jpg"));
                        g.drawImage(temp, i*100, j*100, 100, 100, null);
                    
                    } catch (IOException e) {
                        System.out.println("Img error" + e.getMessage());
                    }
//                    g.drawImage(temp, i*100, j*100, 100, 100, null);
                }
                else if (state.equals("0") || state.equals("1") || state.equals("2") || state.equals("3") || 
                        state.equals("4") || state.equals("5") || state.equals("6") ||
                        state.equals("7") || state.equals("8")) {

                    g.drawString(state, (2*i + 1)*50, (2*j+1)*50);
                }
                else if (state.equals("X")) {
                    try {
                        BufferedImage temp = ImageIO.read(new File("game_img/bomb.jpg"));
                        g.drawImage(temp, i*100, j*100, 100, 100, null);
                    } catch (IOException e) {
                        System.out.println("Img error" + e.getMessage());
                    }
                    
                }
            }
        }
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
