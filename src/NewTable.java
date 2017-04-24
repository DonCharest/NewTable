import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Created by williamkohler on 4/23/17.
 */
public class NewTable<K, E> {

    // Each object of this tiny public class holds the key and the data of one
    // entry in the hash table.
    class HashPair<K, E>{
        K key;
        E element;
    }

    boolean debug = false;
    int initialCapacity;
    int size;


    private ArrayList<LinkedList<HashPair<K, E>>> table;

    public NewTable(int initialCapacity){
        this.initialCapacity = initialCapacity;
        table = new ArrayList<>(initialCapacity);
        size = 0;

        LinkedList<HashPair<K, E>> linkedList = new LinkedList<>();

        // Creates an empty LinkedList @ each node of the ArrayList
        for (int index = 0; index < initialCapacity; index++) {
            table.add(linkedList);
        }
    }

    /**
     * Add a new element to this table using the specified key
     * If there is not already an element with the specified key, then this table's size must be less than
     * it's capacity
     * (i.e. size( ) < capacity ( ))
     * Also, neither key nor element may be null.
     * @param key
     *  The non-null key to use for the new element
     * @param element
     *  The new element that's being added to this table
     * @throws NullPointerException
     *  Indicates that key or element is null
     */
    public E put(K key, E element){

        if (key == null || element == null){
            throw new NullPointerException("key or element is null");
        }

        int ii = hash(key);

        if (debug) {
            System.out.println("Hash: " + ii);
        }

        LinkedList<HashPair<K, E>> linkedList = table.get(ii);
        // Two other variables for the new HashPair (if needed) and the return value:
        HashPair<K, E> pair;
        E answer;


        ListIterator<HashPair<K, E>> cursor = linkedList.listIterator(0);
        // Step through the one linked list using the iterator
        while (cursor.hasNext()){
            pair = cursor.next();
            if (pair.key.equals(key)){
                // We found the given key already on the list.
                answer = pair.element;
                pair.element = element;
                return answer;
            }
        }

        // The specified key was not on linkedList, so create a new node for the new entry
        pair = new HashPair<K, E>();
        pair.key = key;
        pair.element = element;
        // Add to linkedList & up size;
        linkedList.add(pair);
        size++;
        // If specified key is not on linkedList, return null
        return null;

    }

    /**
     * Determine whether a specified key is in the table
     * @param key
     *  The non-null key to look for
     * @return
     *  True - If table contains an object with the specified key.
     *  False - if the table does not contain an object with the specified key.
     * Note: Key.equals() is used to compare the key to the keys that are in the table
     * @throws NullPointerException
     * indicates that the key is null
     */
    public boolean containsKey(K key) throws NullPointerException{

        if (key==null){
            throw new NullPointerException("key is null");
        }

        for (int index=0; index < initialCapacity; index++){
            LinkedList linkedList = table.get(index);

            for (int index2 = 0; index2 <linkedList.size(); index2++) {
                HashPair hashPair = (HashPair) linkedList.get(index2);
                if (hashPair.key.equals(key)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrive an object for a specified key
     * @param key
     *  The non-null key to look for
     * @return
     * @throws NullPointerException
     *  Indicates that the key is null
     */
    public E get(K key) throws NullPointerException {

        if (key==null){
            throw new NullPointerException("Key is null");
        }

        // find the hash value for the key.
        int hashValue = hash(key);
        // Go to the index of the hash value to loop through the linkedList
        LinkedList linkedList = table.get(hashValue);
        // If the keys match, return the element.
        for (int index = 0; index < linkedList.size(); index++) {
            HashPair hashPair = (HashPair) linkedList.get(index);
            if (hashPair.key.equals(key)){
                return (E)hashPair.element;
            }
        }

        return null;
    }


    /**
     * Creates a hash value for a key
     * @param key
     *  The value to be given a hash value
     * @return
     *  Hash value for the key
     */
    public int hash(K key){
        return Math.abs(key.hashCode()) % initialCapacity;
    }

    public static void main(String[] args) {

        NewTable<String, String> newTable = new NewTable<>(5);

        System.out.println("PUT TEST");
        System.out.println("----------");
        System.out.println(newTable.put("Kohler", "Remy"));
        System.out.println(newTable.put("Kohler", "Bill"));
        System.out.println(newTable.put("Kohler", "Remy"));
        System.out.println(newTable.put("Cat", "Remy"));
        System.out.println(newTable.put("Light", "Remy"));
        System.out.println(newTable.put("Computer", "Remy"));
        System.out.println("\n");

        System.out.println("CONTAINS KEY TEST");
        System.out.println("----------");
        System.out.print("Contains Kohler: ");
        System.out.println(newTable.containsKey("Kohler"));
        System.out.print("Contains Peggy: ");
        System.out.println(newTable.containsKey("Peggy"));
        System.out.println("\n");

        System.out.println("GET TEST");
        System.out.println("----------");
        System.out.print("Get Key 'Kohler': ");
        System.out.println(newTable.get("Kohler"));
        System.out.print("get Key 'Beer': ");
        System.out.println(newTable.get("Beer"));






    }

}
