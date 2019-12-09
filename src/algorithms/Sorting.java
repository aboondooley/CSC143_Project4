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

        if (a.size()==0&&b.size()==0){ // if both are empty, return empty list
            return returnList;
        }
        if (a.size() == 0) { // if one list is empty, return only the other list
            returnList.addAll(b);
            return returnList;
        }
        if (b.size() == 0) {
            returnList.addAll(a);
            return returnList;
        }

        int aI = 0; int bI = 0; // keep track of where each list is at

        while(aI < a.size()&&bI < b.size()){
            if (a.get(aI)==b.get(bI)){
                // if the two elements are equal add them both
                returnList.add(a.get(aI)); aI++;
                returnList.add(b.get(bI)); bI++;
            } else if (a.get(aI)<b.get(bI)){
                // otherwise, add the smallest element
                returnList.add(a.get(aI)); aI++;
            } else {
                returnList.add(b.get(bI)); bI++;
            }
        }

            if (aI<a.size()){
                // if only one list has elements left, add them all because they will be largest
                returnList.addAll(a.subList(aI, a.size()));
            }
            if (bI<b.size()){
                returnList.addAll(b.subList(bI, b.size()));
            }

        return returnList;
    }

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> list) {
        //
         ArrayList<Integer> returnList = new ArrayList<>();

         if (list.size()==0){
             return returnList;
         }
         if (list.size()==1){
             returnList.addAll(list);
             return returnList;
         }

         // cut the list in two by creating two lists
             ArrayList<Integer> first = new ArrayList<>(list.subList(0, list.size()/2));
             ArrayList<Integer> second = new ArrayList<>(list.subList((list.size()/2), list.size()));

             // recurse, until each list has only one element in it
             ArrayList<Integer> firstReturn = mergeSort(first);
             ArrayList<Integer> secondReturn = mergeSort(second);

             // then call merge to zip the two lists together as it recurses back up
             returnList.addAll(merge(firstReturn, secondReturn));


         return returnList;

    }
}
