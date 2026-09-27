import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap {
    private int[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    // Метрика: количество сравнений элементов
    public long comparisons = 0;

    public MinHeap() {
        heap = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    public void insert(int x) {
        ensureCapacity();
        heap[size] = x;
        siftUp(size);
        size++;
    }

    public int peekMin() {
        if (size == 0) throw new NoSuchElementException("Heap is empty");
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) throw new NoSuchElementException("Heap is empty");
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return min;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            comparisons++;
            if (heap[i] < heap[parent]) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {
        while (2 * i + 1 < size) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = left;

            if (right < size) {
                comparisons++;
                if (heap[right] < heap[left]) {
                    smallest = right;
                }
            }

            comparisons++;
            if (heap[i] > heap[smallest]) {
                swap(i, smallest);
                i = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public int size() {
        return size;
    }

    public void resetMetrics() {
        this.comparisons = 0;
    }
}