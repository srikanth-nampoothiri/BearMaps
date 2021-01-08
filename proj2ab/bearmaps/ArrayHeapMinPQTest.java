package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import edu.princeton.cs.algs4.Stopwatch;

public class ArrayHeapMinPQTest {

    @Test
    public void testConstructorIntialize() {
        NaiveMinPQ<Integer> naiveHeap = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> heap = new ArrayHeapMinPQ<>();
    }

    @Test
    public void testAddAndRemove() {
        ArrayHeapMinPQ<Integer> heap = new ArrayHeapMinPQ<>();
        heap.add(1,1);
        heap.add(2,2);
        heap.add(3,3);
        heap.add(4,4);
        heap.add(5,6);
        heap.add(6,5);
        heap.add(7,8);
        heap.add(8,7);
        assertEquals(8, heap.size());
        assertEquals(1, (int) heap.removeSmallest());
        assertEquals(2, (int) heap.removeSmallest());
        assertEquals(3, (int) heap.removeSmallest());
        assertEquals(4, (int) heap.removeSmallest());
        assertEquals(6, (int) heap.removeSmallest());
        assertEquals(3, heap.size());
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> intHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveHeap = new NaiveMinPQ<>();
        for (int i = 0; i < 10; i++) {
            intHeap.add(i, i);
            naiveHeap.add(i, i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(i, (int) intHeap.getSmallest());
            intHeap.removeSmallest();
        }

        intHeap.add(1, 1);
        intHeap.add(2, 0);
        intHeap.add(3, 3);
        intHeap.add(4, 6);
        intHeap.add(5, 7);
        intHeap.add(6, 9);
        intHeap.add(7, 4);
        intHeap.add(8, 5);
        intHeap.add(9, 5);
        intHeap.add(10, 1);
        assertEquals(2, (int) intHeap.getSmallest());
    }

    @Test
    public void testContain() {
        ArrayHeapMinPQ<Integer> intHeap = new ArrayHeapMinPQ<>();
        intHeap.add(1, 1);
        intHeap.add(2, 2);
        intHeap.add(3, 3);
        intHeap.add(4, 4);
        intHeap.add(5, 5);
        intHeap.add(6, 6);
        assertTrue(intHeap.contains(5));
        assertFalse(intHeap.contains(7));
        assertTrue(intHeap.contains(1));
        assertTrue(intHeap.contains(2));
        assertFalse(intHeap.contains(0));

    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> intHeap = new ArrayHeapMinPQ<>();
        intHeap.add(1, 1);
        intHeap.add(2, 2);
        intHeap.add(3, 3);
        intHeap.add(4, 4);
        intHeap.add(5, 0);
        intHeap.add(6, 3.7);
        intHeap.changePriority(5, 1.5);
        intHeap.changePriority(6, 0.3);
        assertEquals(6, (int) intHeap.getSmallest());
        assertEquals(6, (int) intHeap.removeSmallest());
        assertEquals(1, (int) intHeap.removeSmallest());
        assertEquals(5, (int) intHeap.getSmallest());
        assertEquals(5, (int) intHeap.removeSmallest());
        assertEquals(2, (int) intHeap.removeSmallest());
        assertEquals(2, intHeap.size());


        ArrayHeapMinPQ<Integer> intHeap2 = new ArrayHeapMinPQ<>();
        for (int i = 10; i > 0; i--) {
            intHeap2.add(i, i);
        }
        intHeap2.changePriority(1, 90);
        assertEquals(2, (int) intHeap2.getSmallest());
        intHeap2.changePriority(1, 1);
        assertEquals(1, (int) intHeap2.getSmallest());
    }

    
    @Test
    public void testManyRandomPoints() {
        ArrayHeapMinPQ<Integer> intHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveHeap = new NaiveMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            int randValues = StdRandom.uniform(1, 1000000);
            if (!intHeap.contains(randValues)) {
                intHeap.add(randValues, randValues);
                naiveHeap.add(randValues, randValues);
            }
        }

        for (int i = 0; i < intHeap.size(); i++) {
            assertEquals(intHeap.removeSmallest(), naiveHeap.removeSmallest());
        }

    }
    
    @Test
    public void testTiming() {
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Integer> intHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveHeap = new NaiveMinPQ<>();

        for (int i = 0; i < 100000; i++) {
            intHeap.add(i, i);
        }

        for (int i = 0; i < 100000; i++) {
            intHeap.removeSmallest();
        }
        System.out.println("Heap Add and Remove Elapsed Time: " + sw.elapsedTime() + " seconds");

        sw = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            naiveHeap.add(i, i);
        }
        for (int i = 0; i < 100000; i++) {
            naiveHeap.removeSmallest();
        }
        System.out.println("Naive Add and Remove Elapsed Time: " + sw.elapsedTime() + " seconds");


        for (int i = 0; i < 100000; i++) {
            intHeap.add(i, i);
            naiveHeap.add(i, i);
        }
        sw = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            intHeap.changePriority(i, 100000 - i);
        }
        System.out.println("Heap changePriority Elapsed Time: " + sw.elapsedTime() + " seconds");

        sw = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            naiveHeap.changePriority(i, 100000 - i);
        }
        System.out.println("Naive changePriority Elapsed Time: " + sw.elapsedTime() + " seconds");
    }
}
