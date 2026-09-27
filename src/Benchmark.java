import java.util.Random;

public class Benchmark {
    private static final int SEED = 42;
    private static final int REPETITIONS = 5;
    private static final int[] N_SIZES = {100, 1000, 10000, 100000};

    public static void main(String[] args) {
        System.out.println("=== НАЧАЛО ТЕСТИРОВАНИЯ ПРОИЗВОДИТЕЛЬНОСТИ (BENCHMARKS) ===");
        runWorkload1();
        runWorkload2();
        runWorkload3();
        runWorkload4();
        System.out.println("\n=== ТЕСТИРОВАНИЕ ЗАВЕРШЕНО ===");
    }

    private static void runWorkload1() {
        System.out.println("\n--- Workload 1: Random Access (get) ---");
        int m = 10000;
        for (int n : N_SIZES) {
            long totalTimeDA = 0, totalTimeLL = 0;
            long totalAccessesDA = 0, totalAccessesLL = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rand = new Random(SEED + r);
                DynamicArray da = new DynamicArray();
                LinkedList ll = new LinkedList();

                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    da.add(val);
                    ll.add(val);
                }

                int[] indices = new int[m];
                for (int i = 0; i < m; i++) indices[i] = rand.nextInt(n);

                da.resetMetrics();
                ll.resetMetrics();

                long start = System.nanoTime();
                for (int idx : indices) da.get(idx);
                totalTimeDA += (System.nanoTime() - start);
                totalAccessesDA += da.elementAccesses;

                start = System.nanoTime();
                for (int idx : indices) ll.get(idx);
                totalTimeLL += (System.nanoTime() - start);
                totalAccessesLL += ll.elementAccesses;
            }
            System.out.printf("N=%6d | DA Time: %10d ns, Accesses: %8d | LL Time: %10d ns, Accesses: %10d\n",
                    n, totalTimeDA / REPETITIONS, totalAccessesDA / REPETITIONS,
                    totalTimeLL / REPETITIONS, totalAccessesLL / REPETITIONS);
        }
    }

    private static void runWorkload2() {
        System.out.println("\n--- Workload 2: Search (contains) ---");
        int m = 1000;
        for (int n : N_SIZES) {
            long totalTimeDA = 0, totalTimeLL = 0;
            long totalAccessesDA = 0, totalAccessesLL = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rand = new Random(SEED + r);
                DynamicArray da = new DynamicArray();
                LinkedList ll = new LinkedList();

                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    da.add(val);
                    ll.add(val);
                }

                int[] searchVals = new int[m];
                for (int i = 0; i < m; i++) searchVals[i] = rand.nextInt();

                da.resetMetrics();
                ll.resetMetrics();

                long start = System.nanoTime();
                for (int val : searchVals) da.contains(val);
                totalTimeDA += (System.nanoTime() - start);
                totalAccessesDA += da.elementAccesses;

                start = System.nanoTime();
                for (int val : searchVals) ll.contains(val);
                totalTimeLL += (System.nanoTime() - start);
                totalAccessesLL += ll.elementAccesses;
            }
            System.out.printf("N=%6d | DA Search Time: %10d ns, Comp: %10d | LL Search Time: %10d ns, Comp: %10d\n",
                    n, totalTimeDA / REPETITIONS, totalAccessesDA / REPETITIONS,
                    totalTimeLL / REPETITIONS, totalAccessesLL / REPETITIONS);
        }
    }

    private static void runWorkload3() {
        System.out.println("\n--- Workload 3: Insertion and Removal at Index 0 ---");
        for (int n : N_SIZES) {
            long timeDAInsert0 = 0, timeLLInsert0 = 0;
            long timeDARemove0 = 0, timeLLRemove0 = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rand = new Random(SEED + r);
                DynamicArray da = new DynamicArray();
                LinkedList ll = new LinkedList();

                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    da.add(val);
                    ll.add(val);
                }

                long start = System.nanoTime();
                for (int i = 0; i < 1000; i++) da.add(0, 999);
                timeDAInsert0 += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < 1000; i++) ll.add(0, 999);
                timeLLInsert0 += (System.nanoTime() - start);

                // Удаление из начала (1000 раз)
                start = System.nanoTime();
                for (int i = 0; i < 1000; i++) da.remove(0);
                timeDARemove0 += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < 1000; i++) ll.remove(0);
                timeLLRemove0 += (System.nanoTime() - start);
            }
            System.out.printf("N=%6d | DA Insert(0): %10d ns | LL Insert(0): %8d ns | DA Remove(0): %10d ns | LL Remove(0): %8d ns\n",
                    n, timeDAInsert0 / REPETITIONS, timeLLInsert0 / REPETITIONS,
                    timeDARemove0 / REPETITIONS, timeLLRemove0 / REPETITIONS);
        }
    }

    private static void runWorkload4() {
        System.out.println("\n--- Workload 4: Priority Processing (Min-Heap) ---");
        for (int n : N_SIZES) {
            long totalInsertTime = 0, totalExtractTime = 0;
            long totalComparisons = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rand = new Random(SEED + r);
                MinHeap heap = new MinHeap();
                int[] data = new int[n];
                for (int i = 0; i < n; i++) data[i] = rand.nextInt();

                heap.resetMetrics();

                long start = System.nanoTime();
                for (int val : data) heap.insert(val);
                totalInsertTime += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < n; i++) heap.extractMin();
                totalExtractTime += (System.nanoTime() - start);

                totalComparisons += heap.comparisons;
            }
            System.out.printf("N=%6d | Heap Insert Total: %10d ns | Heap Extract Total: %10d ns | Comparisons: %10d\n",
                    n, totalInsertTime / REPETITIONS, totalExtractTime / REPETITIONS,
                    totalComparisons / REPETITIONS);
        }
    }
}