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

        ArrayList<Integer> shorter;
        ArrayList<Integer> longer;
        if (a.size()==b.size()){
           shorter = a;
           longer = b;
        } else {
            if (a.size()>b.size()){
                shorter = b;
                longer = a;
            } else {
                shorter = a;
                longer = b;
            }
        }

        Integer sCurrent;
        Integer index = 0;
        Integer lCurrent = longer.get(index);

        for (int i = 0; i < shorter.size(); i++){
            sCurrent = shorter.get(i);
            while (lCurrent<=sCurrent){
                returnList.add(lCurrent);
               /* if (lCurrent==sCurrent){
                    returnList.add(sCurrent);
                } */
                index++;
                if (index<longer.size()) {
                    lCurrent = longer.get(index);
                } else{
                    lCurrent=sCurrent+1;
                }
            }
            if(sCurrent<lCurrent){
                returnList.add(sCurrent);
            }
        }
        if (index<longer.size()) {
            returnList.addAll(longer.subList(index, longer.size()));
        }
/*
        if (b.size()==1&&a.size()==1){
            if (a.get(0)<b.get(0)){
                returnList.add(a.get(0)); returnList.add(b.get(0));
            } else {
                returnList.add(b.get(0)); returnList.add(a.get(0));
            }
            return returnList;
        }

        Iterator<Integer> aIterator = a.iterator();
        Iterator<Integer> bIterator = b.iterator();
        int aCurrent = aIterator.next(); // put marker at the front of list a
       // int aIndex = 0; // keep track of index of list a
        int bCurrent = bIterator.next(); // put marker at the front of list b
        //int bIndex = 0; // keep track of of list b

        if (aCurrent < bCurrent) {
            returnList.add(aCurrent);
            if(aIterator.hasNext()){
                aCurrent = aIterator.next();
            } else {
                returnList.add(bCurrent);
            }
        } else if (bCurrent < aCurrent) {
            returnList.add(bCurrent);
            if(bIterator.hasNext()){
                bCurrent = bIterator.next();
            } else {
                returnList.add(aCurrent);
            }
        } else { // values are equal
            returnList.add(aCurrent);
            returnList.add(bCurrent);
            if(aIterator.hasNext()){aCurrent = aIterator.next();}
            if(bIterator.hasNext()){bCurrent = bIterator.next();}
        }
         while (aIterator.hasNext()&&bIterator.hasNext()) {
             // NEED TO ADD A CHECK TO SEE IF INDEX OUT OF BOUNDS HERE
             if (aCurrent < bCurrent) {
                 returnList.add(aCurrent);
                 aCurrent = aIterator.next();
             } else if (bCurrent < aCurrent) {
                 returnList.add(bCurrent);
                 bCurrent = bIterator.next();
             } else { // values are equal
                 returnList.add(aCurrent);
                 returnList.add(bCurrent);
                 aCurrent = aIterator.next();
                 bCurrent = bIterator.next();
                 if(!bIterator.hasNext()&&!aIterator.hasNext()){
                     if (aCurrent < bCurrent) {
                         returnList.add(aCurrent); returnList.add(bCurrent);
                         return returnList;
                     } else {
                         returnList.add(bCurrent); returnList.add(aCurrent);
                         return returnList;
                     }
                 }
             }
         }
         if (aIterator.hasNext()){
             while (aIterator.hasNext()){
                 returnList.add(aIterator.next());
             }
         } else { //
             while (bIterator.hasNext()){
                 returnList.add(bIterator.next());
             }
         } */

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

         if (list.size()>1){
             ArrayList<Integer> first = new ArrayList<>(list.subList(0, list.size()/2));
             ArrayList<Integer> second = new ArrayList<>(list.subList((list.size()/2), list.size()));

             ArrayList<Integer> firstReturn = mergeSort(first);
             ArrayList<Integer> secondReturn = mergeSort(second);

             returnList.addAll(merge(firstReturn, secondReturn));

         }
/*
         if (second.size()==1&&first.size()==1){
             if (second.get(0)<first.get(0)){
                 returnList.add(second.get(0));
                 returnList.add(first.get(0));
             }
         } */

         return returnList;

    }
}
