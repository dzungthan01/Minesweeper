import java.util.ArrayList;
import java.util.Collections;

public class mineField {

    private GameObj[][] board;
    private int size;
    private int numBomb;
    private int mode; //0: uncover; 1: flag; 2: peek
    private int gameOver; // 0: game in progress, 1: win, 2: lose
    
    public mineField(int size) {
        this.board = new GameObj[size][size];
        this.gameOver = 0;
        this.numBomb = size*size/2 - size*size/10;
        this.size = size;
        this.mode = 0; 
    }
    
//    public boolean okayMove(int r, int c) {
//        if ()
//    }
    
    public int getSize() {
        return size;
    }
    
    public int checkGame() {
        return gameOver;
    }
    
    public String getObj(int r, int c) {
        return board[r][c].getName();
    }
    
    public void setBomb() {
        ArrayList<String> shuffle = new ArrayList<String>();
        String temp = null;
        for (int i = 0; i< size; i++) {
            for (int j = 0; j<size; j++) {
                temp = String.valueOf(i) + String.valueOf(j);
                shuffle.add(temp);
            }
        }
        Collections.shuffle(shuffle);
        for (int i = 0; i<numBomb; i++) {
            String index = shuffle.get(i);
            String first = index.substring(0, 1);
            String sec = index.substring(1);
            int f = Integer.parseInt(first);
            int s = Integer.parseInt(sec);
            board[f][s]= new GameObj(f, s, "C*");
        }
    }
    
    public void setCell(int r, int c, String name) {
        board[r][c] = new GameObj(r, c, name);
    }
    
    public void uncoverCell(int r, int c) {
        if (getObj(r, c).equals("C*")) {
            setCell(r, c, "X");
        }
        else if (getObj(r, c).equals("C") && numOfAdjacentMines(c, r) != 0) {
            setCell(r, c, Integer.toString(numOfAdjacentMines(c, r)));
        }
        else if (getObj(r, c).equals("C") && numOfAdjacentMines(c, r) == 0) {
            setCell(r, c, "0");
            for (int d1 = -1; d1 <= 1; d1 ++) {
                for (int d2 = -1; d2 <= 1; d2++) {
                    uncoverCell(r + d1, c + d2);
                }
            }
        }
    }
    
    public void unveilCell(int r, int c) {
        if (getObj(r, c).equals("C")) {
            uncoverCell(r, c);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (!board[r][c].getName().equals("C") || !board[r][c].getName().equals("C*")) {
                        gameOver = 1;
                    }
                }
            }
        }
        else if (getObj(r, c).equals("C*")) {
            setCell(r, c, "X");
            gameOver = 2;
        }
    }
    
    public void flagCell(int r, int c) {
        if (getObj(r, c).equals("C")) {
            setCell(r, c, "F");
        }
        else if (getObj(r, c).equals("C*")) {
            setCell(r, c, "F*");
        }
    }
    
    public void coverCell(int r, int c) {
        //if number then recover it
        //if "X" then recover it
    }
    
    public int getMode() {
        return mode;
    }
    
    public void flagMode() {
        mode = 1;
    }
    
    public void peekMode() {
        mode = 2;
    }
    
    public int numOfAdjacentMines(int x, int y) {
        int num = 0;
        for (int d1 = -1; d1 <= 1; d1 ++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                if ((d1 != 0 || d2 != 0) && getObj(x + d1, y + d2).equals("C*") || 
                        getObj(x + d1, y + d2).equals("F*") || getObj(x + d1, y + d2).equals("X")) {
                    num++;
                }
            }
        }
        return num;
    }
    
}


