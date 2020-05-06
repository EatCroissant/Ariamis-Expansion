package ariamis.help;

public class Pair<T,V> {
    T x;
    V y;
    public Pair(T a, V b){
        x=a;
        y=b;
    }

    public T getX() {
        return x;
    }

    public V getY() {
        return y;
    }
}
