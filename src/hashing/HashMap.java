package hashing;

import lib.Pair;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * HashMap implementation using hashing w/ linked list buckets.
 * Please refer to the official HashMap Java 11 documentation
 * for an explanation on the behavior of each of the methods below.
 *
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashMap.html
 *
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class HashMap<K, V> implements Iterable<Pair<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    // Normally this would be private, but we'll make it public
    // for testing purposes
    public LinkedList<Pair<K, V>>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        /* DO NOT MODIFY */
        table = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, DEFAULT_CAPACITY);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        /* DO NOT MODIFY */
        table = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, initialCapacity);
        size = 0;
    }

    public int getSlot(K key) {
        /* DO NOT MODIFY */
        return key == null ? 0 : (key.hashCode() % table.length);
        /*
            Explanation: null always has a hashCode of zero and will always be in index 0;
                         all non-null keys will have a table entry corresponding to their hashCode
                         that wraps around using the modulo (%) operator to prevent overflow
                         (but not collisions).

                         For example, given a table size of 10,
                         a hashCode of 6 results in a slot of 6, and
                         a hashCode of 16 also results in a slot of 6.
         */
    }

    public V put(K key, V value) {
        int slot = getSlot(key);
        if (table[slot] == null) {
            table[slot] = new LinkedList<>();
        }

        /*
            Use the .set(value) method on the ListIterator to do
            an O(1) replacement of a value.
         */
        ListIterator<Pair<K, V>> i = table[slot].listIterator();
        Pair<K, V> putter = new Pair<>(key, value);
        // if there are no elements already in the slot, add a new pair
        if(!i.hasNext()){
            table[slot].add(putter);
            size++;
            this.expand();
            return null;
        }

        // Otherwise, walk along the list and see if the key already exists there
        int index = -1;
        while(i.hasNext()){
            Pair<K, V> here = i.next();
            index++;
            if (key==null&&here.left==null){
                // If the keys match at null, input the new value
                table[slot].set(index, putter);
                return here.right;
            }
            // Otherwise, if the keys match, reset the value at that key
            if(here.left.equals(key)){
              table[slot].set(index, putter);
                return here.right;
            }
        }
        // Otherwise if it doesn't exist, add it to the end of the list
        // Increase the size, and check if the hashmap needs to expand.
        table[slot].add(putter);
        size++;
        this.expand();
        /* YOUR CODE HERE */
        return null;
    }

    public V get(K key) {
        int slot = getSlot(key);
        if (table[slot] == null) {
            return null;
        }
        ListIterator<Pair<K, V>> i = table[slot].listIterator();
        // if the bin for the hash is empty, return null
        if(!i.hasNext()){
            return null;
        }

        while(i.hasNext()){
            // Otherwise, check through each element to see if the keys match
            Pair<K, V> here = i.next();
            // special case where the keys are both null
            if (key==null&&here.left==null){
                return here.right;
            }
            // normal case where keys match
            if(here!=null&&here.left.equals(key)){
                return here.right;
            }
        }

        /* YOUR CODE HERE */
        return null;
    }

    public V remove(K key) {
        int slot = getSlot(key);
        if (table[slot] == null) {
            return null;
        }

        /*
            Use the remove() method supplied by ListIterator
            to do an O(1) remove on the list bucket.
         */
        ListIterator<Pair<K, V>> i = table[slot].listIterator();
        // if list is empty return null
        if(!i.hasNext()){
            return null;
        }

        while(i.hasNext()){
            Pair<K, V> here = i.next();
            if (key==null&&here.left==null){
                // special null null case
                i.remove();
                size--;
                return here.right;
            }
            if(here!=null&&here.left.equals(key)){
                // regular matching keys case
                // recommended above to use the iterator remove()
                i.remove();
                size--;
                return here.right;
            }
        }

        /* YOUR CODE HERE */
        return null;
    }

    public int size() {
        /* DO NOT MODIFY */
        return size;
    }


    @Override
    public Iterator<Pair<K, V>> iterator() {
        // use iterator on linked list.
        // each bucket wil have it's own iterator.
        // You have to walk through each iterator for each bucket.
        // It requires us to use an iterator for each bucket.
        // next should be constant time.
        /* DO NOT MODIFY */
        return new HashMapIterator(this);
    }

    private class HashMapIterator implements Iterator<Pair<K, V>> {
        HashMap<K, V> hashMap;

        private int binKeeper;
        private int listKeeper;
        private Pair<K, V> nextKeeper;



        /* YOUR CODE HERE */


        HashMapIterator(HashMap<K, V> hashMap) {
            this.hashMap = hashMap;
            binKeeper=0; // keep track of what bin we are in
            listKeeper=0; // keep track of how far along the current linked list
            nextKeeper=null; // keeps track of whether next has been called and what was returned


            /* YOUR CODE HERE */
        }

        @Override
        public boolean hasNext() {
            /*
                hasNext should be worst case O(n), not O(n^2)
                Hint: Use an Iterator to retrieve individual bucket values
                instead of .get(index), which is O(n) on its own
             */

            /* YOUR CODE HERE */
            if(size==0){
                return false;
            }
            // skip empty bins
            while (binKeeper<table.length&&(table[binKeeper]==null || listKeeper == table[binKeeper].size())) {
                        binKeeper++;
                        listKeeper = 0;
            }

            // if the bin is not null and we have not indexed out of the array:
            if(binKeeper<table.length&&table[binKeeper]!=null) {
                ListIterator<Pair<K, V>> i = table[binKeeper].listIterator();
                // throw away the elements until we reach the current place in the list
                // recommended above, instead of calling get(index)
                for (int index = 0; index < listKeeper; index++) {
                   if (i.hasNext()) {
                       i.next();
                   }
                }
                if (i.hasNext()) {
                    return true;
                }

                // check again to skip null bins
                while (binKeeper < table.length && (table[binKeeper] == null || listKeeper == table[binKeeper].size())) {
                    binKeeper++;
                    listKeeper = 0;
                }
            }

            if(binKeeper < table.length&&table[binKeeper]!=null) {
                return true;
            }
            return false;
        }

        @Override
        public Pair<K, V> next() {
            /* YOUR CODE HERE */
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            // skip null bins
            while (binKeeper<table.length&&(table[binKeeper]==null || listKeeper == table[binKeeper].size())){
                binKeeper++;
                listKeeper=0;
            }
            // throw away elements until we reach where the pointer element is
            if (binKeeper<table.length&&table[binKeeper] != null) {
                ListIterator<Pair<K, V>> i = table[binKeeper].listIterator();
                for (int index = 0; index < listKeeper; index++) {
                    if(i.hasNext()) {
                        i.next();
                    }
                }
                // if the linked list hasNext(), easy, we just increate the list keeper and return the values
                // And save the pair as a global variable in case remove() is called
                if (i.hasNext()) {
                    nextKeeper = i.next();
                    listKeeper++;
                    return nextKeeper;
                }
                // otherwise, need to jump down to the occupied bin to see find the next element
                // skip null bins again
                while (binKeeper<table.length&&(table[binKeeper]==null || listKeeper == table[binKeeper].size() || table[binKeeper].size() ==0)) {
                    binKeeper++;
                    listKeeper = 0;
                }
                // once an occupied bin is found, return the first element
                if (binKeeper<table.length&&table[binKeeper]!=null) {
                    ListIterator<Pair<K, V>> j = table[binKeeper].listIterator();
                    listKeeper++;
                    if (j.hasNext()) {
                        nextKeeper = j.next();
                        return nextKeeper;
                    }
                }
            }
            // otherwise, there are no more occupied bins and exception is thrown
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            /* YOUR CODE HERE */
            if (nextKeeper==null||size==0) {
                throw new IllegalStateException();
            }
            // if the iterator goes through all the elements without removing, then the remove function does not remove the last element
            if (binKeeper==table.length&&!hasNext()){
                throw new IllegalStateException();            }
            // using the built in remove() during iteration function
            hashMap.remove(nextKeeper.left);
            // set the pair to null, since we have removed the last element
            nextKeeper=null;
        }
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        /* DO NOT MODIFY */
        LinkedList<Pair<K, V>>[] newTable = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, table.length * 2);

        /* YOUR CODE HERE */
        // I am not sure what the newTable is used for, but I won't modify
        // I chose to create a new hashmap, twice as large, then put all the elements
        HashMap<K,V> newMap = new HashMap(table.length *2);

        if(size/table.length>=LOAD_FACTOR){
            Iterator<Pair<K, V>> i = this.iterator();
            while(i.hasNext()){
                Pair<K, V> newPair = i.next();
                newMap.put(newPair.left, newPair.right);
            }
            // reset the implicit variable values to the new hash table.
            this.table = newMap.table;
            this.size = newMap.size;

        }

    }

}
