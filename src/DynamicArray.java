import java.util.Arrays;

public class DynamicArray {
    private int[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public long elementAccesses = 0;

    public DynamicArray() {
        this.data = new int[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    public void add(int x) {
        ensureCapacity();
        data[size++] = x;
        elementAccesses++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity();
        int moved = size - index;
        if (moved > 0) {
            System.arraycopy(data, index, data, index + 1, moved);
            elementAccesses += moved; // считаем перемещения элементов
        }
        data[index] = x;
        size++;
        elementAccesses++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int removedValue = data[index];
        elementAccesses++;
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
            elementAccesses += numMoved;
        }
        size--;
        return removedValue;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elementAccesses++;
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            elementAccesses++;
            if (data[i] == x) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void resetMetrics() {
        this.elementAccesses = 0;
    }
}
