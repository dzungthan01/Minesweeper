public class GameObj {
    
    private int px;
    private int py;
    
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