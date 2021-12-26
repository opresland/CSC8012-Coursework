import java.util.*;

/** Purpose of class is to function in the same way as an ArrayList but to keep information in order,
 *
 *    REFERENCES:
 *
 *         Insertion Sort (1/1):
 *         1) Lecture material from CSC8012 module at Newcastle University | Lecture Slides 3
 *         Original Author - Dr Konrad Dabrowski
 *         Modifying Author - Oliver Presland
 *
 */

public class SortedArrayList<E extends Comparable> extends ArrayList<E> {

    //override method; adds items to arrayList in correct index to ensure list is always ordered.
    public boolean add(E e) {
        //Identifies new item as 'newItem'
        E newItem = e;

        //Creating 'j' integer which will identify which index the item should be added to
        //Starts at end of ArrayList
        //Note that adding to index of arrayList shifts all others to right
        //Working backwards through sorted array. 'j' starts at end of ArrayList and goes back through.
        for (int j = super.size(); j >= 0; j--) {

            //If j at beginning of ArrayList no items to compare - add newItem at index 0
            if (j==0){
                super.add(0, newItem);
                break;
                }

            // 'comparisonItem' is next item back from previous
            E comparisonItem = super.get(j-1);

            //If correct index found based on comparison, item will be added
            if (comparisonItem.compareTo(newItem) < 0) {
                super.add(j, newItem);
                break;
            }
        }
        return true;
    }
}