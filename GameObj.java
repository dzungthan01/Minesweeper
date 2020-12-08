public class GameObj {
    
    private int px;
    private int py;
    
    private int width;
    private int height;
    
    private int maxX;
    private int maxY;
    
    private String name;
    
    public GameObj(int r, int c, String name) {
        this.name = name;
        this.px = c;
        this.py = r;
    }
    
    public String getName() {
        return name;
    }
}