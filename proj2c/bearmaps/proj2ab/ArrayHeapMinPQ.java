package bearmaps.proj2ab;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<ArrayHeapNode> arrayHeap;
    private HashMap<T, Integer> heapMap;
    private Object noSuchElementException;


    private class ArrayHeapNode {
        private T item;
        private double priority;

        ArrayHeapNode(T item, double priority) {
            this.item = item;
            this.priority = priority;

        }
    }

    public ArrayHeapMinPQ() {
        arrayHeap = new ArrayList<>();
        heapMap = new HashMap<>();
    }

    private void swimUp(int x) {
        while (x > 0) {
            int r = root(x);
            if (priorityComparator(r, x)) {
                swap(r, x);
                x = r;
            } else {
                break;
            }
        }
    }

    private void sinkDown(int x) {
        while (left(x) < size()) {
            int child = left(x);
            if ((right(x) < size()) && priorityComparator(child, right(x))) {
                child = right(x);
            }
            if (priorityComparator(x, child))  {
                swap(x, child);
                x = child;
            } else {
                break;
            }
        }
    }

    private boolean priorityComparator(int i, int j) {
        return arrayHeap.get(i).priority > arrayHeap.get(j).priority;
    }

    private int right(int x) {
        return 2 * x + 2;
    }

    private int left(int x) {
        return 2 * x + 1;
    }

    private int root(int x) {
        return (x - 1) / 2;
    }

    private void swap(int i, int j) {
        ArrayHeapNode tempNode = arrayHeap.get(i);
        arrayHeap.set(i, arrayHeap.get(j));
        arrayHeap.set(j, tempNode);
        T ithIndex = arrayHeap.get(j).item;
        T jthIndex = arrayHeap.get(i).item;
        heapMap.put(jthIndex, i);
        heapMap.put(ithIndex, j);

    }

    @Override
    public boolean contains(T item) {
        return heapMap.containsKey(item);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        } else {
            ArrayHeapNode newNode = new ArrayHeapNode(item, priority);
            arrayHeap.add(newNode);
            heapMap.put(item, size() - 1);
            swimUp(size() - 1);
        }
    }

    public T getSmallest() {
        if (heapMap.isEmpty()) {
            return (T) new NoSuchElementException();
        } else {
            return arrayHeap.get(0).item;
        }
    }

    public T removeSmallest() {
        if (arrayHeap.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            T root = getSmallest();
            swap(0, size() - 1);
            arrayHeap.remove(size() - 1);
            sinkDown(0);
            heapMap.remove(root);
            return root;
        }
    }

    public int size() {
        return arrayHeap.size();
    }

    public void changePriority(T item, double priority) {
        if (contains(item)) {
            int index = heapMap.get(item);
            double originalPriority = arrayHeap.get(index).priority;
            arrayHeap.get(index).priority = priority;
            if (originalPriority > priority) {
                swimUp(index);
            } else {
                sinkDown(index);
            }
        } else {
            throw new NoSuchElementException();
        }
    }
}

