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
    public int size = 0;
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
        container.add(value); // add to end
        int here = container.size()-1; // put marker on the new value
        T tempHereVal = container.get(here); // get the value of the last element
        T tempParent = container.get(getParentIndex(here)); // get the value of the parent
        while(tempParent!=null&&tempHereVal.compareTo(tempParent)<0) {
            // swap parent with child while the child is smaller than the parent
            swap(here, getParentIndex(here));
            here = getParentIndex(here); // move up
            tempHereVal = container.get(here); // move up markers
            tempParent = container.get(getParentIndex(here));
        }
        size++;
    }

    public void bubbleDown(int index){
        // recursive procedure to "bubble down" the value of interest
        // recursive helper function
        int l = getLeftChildIndex(index); // check out children's indexes
        int r = getRightChildIndex(index);
        int smallest = index;

        T tempIndex = container.get(index);
        // find smallest value, between root and children
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
        // swap root with parent, if it's not the smallest and bubble down
        if (smallest != index){
                swap(index, smallest);
                bubbleDown(smallest);
        }

    }

    public T peek() {
        // peek element in index 1
        if (container.size()<2){
            throw new NoSuchElementException();
        }
        return container.get(1);
    }

    public T remove() {
        // remove element at index one
        if (container.size()<2){
            throw new NoSuchElementException();
        }
        T smallest = container.get(1);
        // swap with the last element
        swap(1, container.size()-1);
        // remove this last element before checking values
        container.remove(container.size()-1);

        // use bubble down recursive function to "bubble down" the largest value
        if (container.size()>2) {
            bubbleDown(1);
        }

        size--;
        return smallest;
    }

    public int size() {
        return this.size;

    }
}
