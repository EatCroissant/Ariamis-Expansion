package ariamis.help;

import net.minecraft.item.ItemStack;

import java.util.*;

public class ComparableMap<T, V> {
    ArrayList<T> keys;
    ArrayList<V> values;

    public ComparableMap() {
        keys = new ArrayList<T>();
        values = new ArrayList<V>();
    }

    public int size() {
        return Math.min(keys.size(), values.size());
    }

    public boolean isEmpty() {
        return keys.isEmpty() && values.isEmpty();
    }

    public boolean containsKey(T key_) {
        for (T key : keys)
            if (0 == ((Comparable<T>) key_).compareTo(key))
                return true;
        return false;
    }


    public boolean containsValue(Object value) {
        for (V key : values)
            if (value.equals(key))
                return true;
        return false;
    }

    private V val(int index) {
        return values.get(index);
    }

    private T key(int index) {
        return keys.get(index);
    }

    public V get(T key_) {
        if (key_ == null) return null;
        for (int i = 0; i < keys.size(); i++)
            if (0 == ((Comparable) key_).compareTo(keys.get(i)))
                return values.get(i);
        return null;
    }

    public V put(T key_, V value) {
        if (key_ == null) return null;
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) == null) {
                for (int j = 0; j < keys.size(); j++)
                    System.out.println("Array keys: " + j + " i " + i + " " + keys.get(i));
            }
            if (0 == ((Comparable) key_).compareTo(keys.get(i))) {
                values.set(i, value);
                return values.get(i);
            }
        }
        values.add(value);
        keys.add(key_);
        return value;
    }

    public V remove(T key_) {
        if (key_ == null) return null;
        for (int i = 0; i < keys.size(); i++)
            if (((Comparable) key_).compareTo(keys.get(i)) == 0) {
                keys.remove(i);
                return values.remove(i);
            }
        return null;
    }

    public Object[] keyArray() {
        return keys.toArray();
    }

    public <T> T[] keyArray(T[] a) {
        return keys.toArray(a);
    }

    public Object[] entryArray() {
        return values.toArray();
    }

    public <V> V[] entryArray(V[] a) {
        return values.toArray(a);
    }

    public ComparableMap splice(int from, int num) {
        ComparableMap<T, V> val = new ComparableMap<T, V>();
        for (int i = 0; i < num; i++)
            val.put(keys.get(i + from), values.get(i + from));
        return val;
    }

    public static ComparableMap normalisation(ComparableMap a) {
        ComparableMap<Object, Float> t2 = new ComparableMap<Object, Float>();
        float probs = 0;
        for (int i = 0; i < a.values.size(); i++) {
            probs += (Float) a.values.get(i);
        }
        for (int i = 0; i < a.values.size(); i++) {
            t2.values.add(((Float) (a.values.get(i)) / probs));
            t2.keys.add(a.keys.get(i));
        }
        return t2;
    }

    public ComparableMap revSortByValues() {
        if (!(values.get(0) instanceof Comparable)) throw new IllegalArgumentException("values must be a Comparable");
        T[] keys = (T[]) this.keys.toArray();
        V[] values = (V[]) this.values.toArray();
        for (int i = 0; i < keys.length - 1; i++) {
            while (((Comparable) keys[i]).compareTo(keys[i + 1]) < 0) {
                T t1 = keys[i];
                keys[i] = keys[i + 1];
                keys[i + 1] = t1;
                V t2 = values[i];
                values[i] = values[i + 1];
                values[i + 1] = t2;
                i = Math.max(0, i - 1);
            }
        }
        this.keys.clear();
        this.values.clear();
        this.keys.addAll(Arrays.asList(keys));
        this.values.addAll(Arrays.asList(values));
        return this;
    }


}
