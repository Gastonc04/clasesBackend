import java.util.Arrays;

public class ListaBack implements Lista {
    private int[] v;
    private int size;

    public ListaBack() {
        v = new int[10];
        size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void add(int x) {
        v[size] = x;
        size++;
    }

    @Override
    public String toString() {
        // Para evitar consumir memoria al hacer los append (creo)
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < size; i++) {

        }
        return "[]";
    }
}
