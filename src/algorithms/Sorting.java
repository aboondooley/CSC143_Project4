package algorithms;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sorting {
    /**
     * Merge two ArrayLists efficiently, in ascending order. Does not modify either argument.
     * @param a The first list.
     * @param b The second list.
     * @return A new sorted ArrayList containing elements from both lists.
     */
    public static ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> returnList = new ArrayList<>();

        if (a.size() == 0) {
            returnList.addAll(b);
            return returnList;
        }
        if (b.size() == 0) {
            returnList.addAll(a);
            return returnList;
        }
        int aCurrent; // put marker at the front of list a
        int aIndex = 0; // keep track of index of list a
        int bCurrent; // put marker at the front of list b
        int bIndex = 0; // keep track of of list b
         while (aIndex<a.size()||bIndex<b.size()) {
             // NEED TO ADD A CHECK TO SEE IF INDEX OUT OF BOUNDS HERE
             aCurrent = a.get(aIndex);
             bCurrent = b.get(bIndex);
             if (aCurrent < bCurrent) {
                 returnList.add(aCurrent);
                 aIndex++;
                // aCurrent = a.get(aIndex);
             } else if (bCurrent < aCurrent) {
                 returnList.add(bCurrent);
                 bIndex++;
                // bCurrent = b.get(bIndex);
             } else { // values are equal
                 returnList.add(aCurrent);
                 returnList.add(bCurrent);
                 aIndex++;
                 bIndex++;
                // aCurrent = a.get(aIndex);
                // bCurrent = b.get(bIndex);
             }
         }

        return returnList;
    }

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> list) {
        //
         ArrayList<Integer> result = new ArrayList<>();
                 return result;

    }
}
