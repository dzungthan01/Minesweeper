import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class mineField {

    private GameObj[][] board;
    private BufferedWriter bw;
    private int size;
    private int numBomb;
    private int flag;
    private int peek;
    private int mode; //0: uncover; 1: flag; 2: peek
    private int gameOver; // 0: game in progress, 1: win, 2: lose
    
    public mineField(int size) {
        this.board = new GameObj[size][size];
        this.gameOver = 0;
        this.numBomb = size*size/2 - 3*size*size/10;
        this.size = size;
        this.mode = 0; 
        this.flag = size*size/2 - 3*size*size/10;
        this.peek = 3;
    }
    
    
    public int getSize() {
        return size;
    }
    
    public void setPeek(int a) {
        peek = a;
    }
    
    public int getPeekLeft() {
        return peek;
    }
    
    public void setFlag(int a) {
        flag = a;
    }
    
    public int getFlagLeft() {
        return flag;
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
        for (int i = numBomb; i < size*size; i++) {
            String index = shuffle.get(i);
            String first = index.substring(0, 1);
            String sec = index.substring(1);
            int f = Integer.parseInt(first);
            int s = Integer.parseInt(sec);
            board[f][s]= new GameObj(f, s, "C");
        }
    }
    
    public void setCell(int r, int c, String name) {
        board[r][c] = new GameObj(r, c, name);
    }
    
    public void peekCell(int r, int c) {
        if (peek > 0) {
            if (getObj(r, c).equals("C*")) {
                setCell(r, c, "X");
            }
            else if (getObj(r, c).equals("C")) {
                setCell(r, c, Integer.toString(numOfAdjacentMines(r, c)));
            }
            peek--;
        }
    }
    
    public void uncoverCell(int r, int c) {
        if (getObj(r, c).equals("C*")) {
            setCell(r, c, "X");
            gameOver = 2;
        }
        else if (getObj(r, c).equals("C") && numOfAdjacentMines(r, c) != 0) {
            setCell(r, c, Integer.toString(numOfAdjacentMines(r, c)));
            if(checkIfDone()) {
                gameOver = 1;
            }
            else {
                gameOver = 0;
            }
            
        }
        else if (getObj(r, c).equals("C") && numOfAdjacentMines(r, c) == 0) {
            setCell(r, c, "0");
            for (int d1 = -1; d1 <= 1; d1 ++) {
                for (int d2 = -1; d2 <= 1; d2++) {
                    if ((d1 != 0 || d2 != 0) && (r + d1) >= 0 && (r + d1) < size && (c + d2) >= 0 
                            && (c + d2) < size) {
                        uncoverCell(r + d1, c + d2);
                       }
                }
            }
            if (checkIfDone()) {
                gameOver = 1;
            }
            else {
                gameOver = 0;
            }
        }
    }
    
    public boolean checkIfDone() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getName().equals("C") || board[i][j].getName().equals("C*")) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    public void flagCell(int r, int c) {
        if (flag > 0) {
            if (mode == 1) {
                if (getObj(r, c).equals("C")) {
                    setCell(r, c, "F");
                    if (checkIfDone()) {
                        gameOver = 1;
                    }
                    else {
                        gameOver = 0;
                    }
                }
                else if (getObj(r, c).equals("C*")) {
                    setCell(r, c, "F*");
                    if (checkIfDone()) {
                        gameOver = 1;
                    }
                    else {
                        gameOver = 0;
                    }
                }
                flag--;
            }
        } 
    }
    
    public void recoverCell(int r, int c) {
        if (getObj(r, c).equals("0") || getObj(r, c).equals("1") || getObj(r, c).equals("2") || 
                getObj(r, c).equals("3") || getObj(r, c).equals("4") || getObj(r, c).equals("5") || 
                getObj(r, c).equals("6") || getObj(r, c).equals("7") || getObj(r, c).equals("8")) {
            setCell(r, c, "C");
        }
        else if (getObj(r, c).equals("X")) {
            setCell(r, c, "C*");
        }
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
    
    public void resetMode() {
        mode = 0;
    }
    
    public int numOfAdjacentMines(int r, int c) {
        int num = 0;
        for (int d1 = -1; d1 <= 1; d1 ++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                if ((d1 != 0 || d2 != 0) && (r + d1) >= 0 && (r + d1) < size && (c + d2) >= 0 
                     && (c + d2) < size && (getObj(r + d1, c + d2).equals("C*") || //trim this
                     getObj(r + d1, c + d2).equals("F*") || getObj(r + d1, c + d2).equals("X"))) {
                    num++;
                }
            }
        }
        return num;
    }
    
    public List<String> convertMFtoListString() {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(Integer.toString(size));
        temp.add(Integer.toString(flag));
        temp.add(Integer.toString(peek));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp.add(getObj(i, j));
            }
        }
        return temp;
    }
    
    public void saveGame(List<String> mf) {
        File file = Paths.get("files/saved").toFile();
        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (String s : mf) {
                try {
                    bw.write(s + "\n"); 
                    bw.flush();
                }  catch (IOException e) {
                    
                }
            }

        } catch (IOException e) {
            System.out.println("Invalid file path");
        }
    }
    
    public void resetSavedFile() {
        File file = Paths.get("files/saved").toFile();
        try {
            bw = new BufferedWriter(new FileWriter(file));

        } catch (IOException e) {
            System.out.println("Invalid file path");
        }
    }
    
    public void loadBomb() {
        File file = Paths.get("files/saved").toFile();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            br.readLine();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    try {
                        setCell(i, j, br.readLine());
                    } catch (IOException e) {
                        System.out.println("IOException in loadBomb");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Invalid file path");
        }
    }
    
}


