package heaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Min-heap implementation where the smallest element is the root of the heap.
 * The heap should be stored in an ArrayList<T> container, with a null element
 * at index 0, and the root element starting at index 1. Use the indexing
 * formulas discussed in class to retrieve and assign child nodes.
 *
 * @param <T> The generic type stored in the heap. Must be a Comparable type.
 *           For more information on the Comparable interface, please read:
 *           https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Comparable.html
 */
public class MinHeap<T extends Comparable<T>> {
    public ArrayList<T> container = new ArrayList<>();
    /* YOUR CODE HERE */

    public MinHeap() {
        container.add(0, null);
    }

    public MinHeap(Collection<T> collection) {
        container.add(0, null);
    }

    private int getParentIndex(int childIndex) {
        return childIndex / 2;
    }

    private int getLeftChildIndex(int parentIndex) {
        return parentIndex * 2;
    }

    private int getRightChildIndex(int parentIndex) {
        return getLeftChildIndex(parentIndex) + 1;
    }

    private void swap(int left, int right) {
        T leftValue = container.get(left);
        container.set(left, container.get(right));
        container.set(right, leftValue);
    }

    /**
     * Inserts a value into the heap, "bubbling up" to the correct position.
     * @param value The value to be inserted.
     */
    public void insert(T value) {
        //
        container.add(value);
        int here = container.size()-1;
        T tempHereVal = container.get(here);
        T tempParent = container.get(getParentIndex(here));
        while(tempParent!=null&&tempHereVal.compareTo(tempParent)<0) {
            swap(here, getParentIndex(here));
            here = getParentIndex(here);
            tempHereVal = container.get(here);
            tempParent = container.get(getParentIndex(here));
        }
    }
/* Not needed
    public void bubbleUp(int index){
        // recursive procedure to "bubble up" the value of interest

        T tempParent = container.get(getParentIndex(index));
        T tempIndex = container.get(index);
        if (tempParent!=null&&index>0&&tempParent.compareTo(tempIndex)>0){
            swap(index, getParentIndex(index));
            bubbleUp(getParentIndex(index));
        }
    }*/

    public void bubbleDown(int index){
        // recursive procedure to "bubble down" the value of interest
        int l = getLeftChildIndex(index); // check out children's indexes
        int r = getRightChildIndex(index);
        int smallest = index;

        T tempIndex = container.get(index);
        if (l<container.size()) {
            T tempL = container.get(l);
            if (tempL.compareTo(tempIndex) < 0) {
                smallest = l;
            }
        }

        if (r<container.size()) {
            T tempR = container.get(r);
            if (tempR.compareTo(container.get(smallest)) < 0) {
                smallest = r;
            }
        }
        if (smallest != index){
                swap(index, smallest);
                bubbleDown(smallest);
        }

    }

    public T peek() {
        //
        if (container.size()<2){
            throw new NoSuchElementException();
        }
        return container.get(1);
    }

    public T remove() {
        //
        if (container.size()<2){
            throw new NoSuchElementException();
        }
        T smallest = container.get(1);
        swap(1, container.size()-1);
        container.remove(container.size()-1);

        if (container.size()>2) {
            bubbleDown(1);
        }
        //container.remove(container.size()-1);
        return smallest;
    }

    public int size() {
        //
        int count =0;
        for (T i : container){
            count++;
        }
        count--;
        return count;
    }
}
