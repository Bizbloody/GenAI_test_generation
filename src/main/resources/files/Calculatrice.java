package files;

public class Calculatrice {
    private int x;
    private int y;
    public Calculatrice(int a, int b) {
        x = a;
        y = b;
    }
    public int ajouter() {
        x -= y;
        return x;
    }
    @Override
    public String toString() {
        return "x = " + x + "; y = " + y;
    }
}