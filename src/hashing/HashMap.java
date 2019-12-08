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
// check equality of the keys and then use .set()

        //Pair<K, V> old = new Pair<K,V>();
        if(!i.hasNext()){
            table[slot].add(putter);
            size++;
            return null;
        }

        int index = -1;
        while(i.hasNext()){
            Pair<K, V> here = i.next();
            index++;
            if (key==null&&here.left==null){
                table[slot].set(index, putter);
                return here.right;
            }
            if(here.left.equals(key)){
              table[slot].set(index, putter);
                return here.right;
            }
        }
        table[slot].add(putter);
        size++;

        /* YOUR CODE HERE */
        return null;
    }

    public V get(K key) {
        int slot = getSlot(key);
        if (table[slot] == null) {
            return null;
        }
        ListIterator<Pair<K, V>> i = table[slot].listIterator();
        if(!i.hasNext()){
            return null;
        }

        while(i.hasNext()){
            Pair<K, V> here = i.next();
            if (key==null&&here.left==null){
                return here.right;
            }
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
        if(!i.hasNext()){
            return null;
        }

        while(i.hasNext()){
            Pair<K, V> here = i.next();
            if (key==null&&here.left==null){
                i.remove();
                size--;
                return here.right;
            }
            if(here!=null&&here.left.equals(key)){
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
        private boolean nextKeeper;
        int progress;
        //private int here;


        /* YOUR CODE HERE */


        HashMapIterator(HashMap<K, V> hashMap) {
            this.hashMap = hashMap;
            binKeeper=0;
            listKeeper=0;
            nextKeeper=false;
            progress=0;

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

            while (binKeeper<table.length&&(table[binKeeper]==null || listKeeper == table[binKeeper].size())) {
                        binKeeper++;
                        listKeeper = 0;
            }

            if(binKeeper<table.length&&table[binKeeper]!=null) {
                ListIterator<Pair<K, V>> i = table[binKeeper].listIterator();
                for (int index = 0; index < listKeeper; index++) {
                    i.next();
                    //progress++;
                }
            /*
             if (listKeeper == table[binKeeper].size()-1) {
                binKeeper++;
                listKeeper = 0;
            } else {
            listKeeper++;
            } */
                if (i.hasNext()) {
                    return true;
                }


                while (binKeeper < table.length && (table[binKeeper] == null || listKeeper == table[binKeeper].size())) {
                    binKeeper++;
                    listKeeper = 0;
                }
            }

            if(binKeeper < table.length&&table[binKeeper]!=null) {
                return true;
            }
            //here++;
            return false;
            //return (here+1<=size);
        }

        @Override
        public Pair<K, V> next() {
            /* YOUR CODE HERE */
            //int tableSize = table.length;
            //Pair<K, V> result;
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            while (binKeeper<table.length&&(table[binKeeper]==null || listKeeper == table[binKeeper].size())){
                binKeeper++;
                listKeeper=0;
            }

            if (binKeeper<table.length&&table[binKeeper] != null) {
                ListIterator<Pair<K, V>> i = table[binKeeper].listIterator();
                for (int index = 0; index < listKeeper; index++) {
                    i.next();
                }
                if (i.hasNext()) {
                    nextKeeper = true;
                    listKeeper++;
                    progress++;
                    return i.next();
                }
                while (binKeeper<table.length&&(table[binKeeper]==null || listKeeper == table[binKeeper].size())) {
                    binKeeper++;
                    listKeeper = 0;
                }
                if (binKeeper<table.length&&table[binKeeper]!=null) {
                    ListIterator<Pair<K, V>> j = table[binKeeper].listIterator();
                    listKeeper++;
                    nextKeeper=true;
                    return j.next();
                }
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            /* YOUR CODE HERE */
            if (!nextKeeper||size==0) {
                throw new IllegalStateException();
            }
            ListIterator<Pair<K, V>> i = table[binKeeper].listIterator();
            for (int index = 0; index < listKeeper; index++) {
                i.next();// needs to call next at least once!
            }
            i.remove();
            size--;
        }
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        /* DO NOT MODIFY */
        LinkedList<Pair<K, V>>[] newTable = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, table.length * 2);

        /* YOUR CODE HERE */

        this.table = newTable;
    }

}
