public class Tests {
    public static void main(String[] args) {
        System.out.println("Запуск юнит-тестов...");
        testDynamicArray();
        testLinkedList();
        testMinHeap();
        System.out.println("✅ Все юнит-тесты успешно пройдены!");
    }

    private static void testDynamicArray() {
        DynamicArray da = new DynamicArray();
        assert da.size() == 0;

        da.add(10);
        da.add(0, 5);
        assert da.get(0) == 5;
        assert da.get(1) == 10;
        assert da.contains(10);

        assert da.remove(0) == 5;
        assert da.size() == 1;

        try {
            da.get(100);
            assert false : "Должно быть вызвано исключение IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // Успех
        }
    }

    private static void testLinkedList() {
        LinkedList ll = new LinkedList();
        assert ll.size() == 0;

        ll.add(10);
        ll.add(0, 5);
        assert ll.get(0) == 5;
        assert ll.get(1) == 10;
        assert ll.contains(10);

        assert ll.remove(0) == 5;
        assert ll.size() == 1;

        try {
            ll.get(100);
            assert false;
        } catch (IndexOutOfBoundsException e) {
        }
    }

    private static void testMinHeap() {
        MinHeap heap = new MinHeap();
        assert heap.size() == 0;

        int[] vals = {5, 3, 8, 1, 3, 2};
        for (int v : vals) heap.insert(v);

        int prev = Integer.MIN_VALUE;
        while (heap.size() > 0) {
            int curr = heap.extractMin();
            assert curr >= prev : "Свойство Min-Heap нарушено!";
            prev = curr;
        }
    }
}