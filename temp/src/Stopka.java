import java.util.ArrayList;

public class Stopka {
    private ArrayList<Karta> lst;
    public Stopka() {
        lst = new ArrayList<Karta>();
    }
    public Karta get(int nan) {
        return lst.get(nan);
    }
    public void add(Karta elem) {
        lst.add(elem);
    }
    public void remove(int nan) {
        lst.remove(nan);
    }
    public int size() {
        return lst.size();
    }
    public void clear() {
        lst.clear();
    }
}
