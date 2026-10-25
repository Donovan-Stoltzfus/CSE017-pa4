import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.ListIterator;
/**
    Generic class to impelment an array based list
 */
public class ArrayList<E> implements List<E>, Cloneable{
    // data member: array for the list elements
    private E[] elements;
    // data member: size of the list
    private int size;
    /**
    * Default constructor creates the array with a default length of 10 and sets size to 0
    */
    public ArrayList() {
        elements = (E[]) new Object[10];
        size = 0;
    }
    /**
    * Constructor with one parameter creates the array with length equal to capacity and sets size to 0
    * @param capacity initial capacity of the array elements
    */
    public ArrayList(int capacity) {
        elements = (E[]) new Object[capacity];
        size = 0;
    }
    /**
     * Method to add an new element to this list
     * @param item the value to add
     * @return true if this list changed as a result of the call
     */
    public boolean add(E item) {
        add(size, item);
        return true;
    }
    /**
     * Method to add an element at a given index
     * @param index the position at which element will be inserted
     * @param item the value of the element to be added
     * @throws IndexOutOfBoundsException if index is negative or greater than this list's size
     */
    public void add(int index, E item){
        if(index > size || index < 0)
            throw new ArrayIndexOutOfBoundsException();
        ensureCapacity();
        for(int i=size-1; i>=index; i--){
            elements[i+1] = elements[i];
        }
        elements[index] = item;
        size++;
    }
    /**
     * Method to add a collection to this list
     * @param c the collection to be added
     * @return true if this list changed as a result of the call
     */
    public boolean addAll(Collection<? extends E> c){
        Iterator iter = c.iterator();
        boolean isChanged = false;
        while (iter.hasNext()) {
            E nextElement = (E) iter.next();
            add(nextElement);
            isChanged = true;
        }
        return isChanged;
    }

    /**
     * Method to add an element as the first element of this list
     * @param e element to be added
     */
    public void addFirst(E e){
        add(0, e);
    }
    
    /**
     * Method to add an element as the last element of this list
     * @param e to be added
     */
    public void addLast(E e){
        add(size, e);
    }

    /**
     * Check if the index is valid
     * @param index to be checked
     * @throws ArrayIndexOutOFBoundsException is index is out of bounds
     */
    private void checkIndex(int index){
        if(index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException(
            "Index out of bounds. Must be between 0 and "+(size-1));
    }

    /**
     * Method to clear this list
     */
    public void clear() {
        size = 0; 
    }

    /**
     * clone this list
     * @return a deep copy of this list
     */
    public Object clone(){
        ArrayList<E> newArrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            newArrayList.add(get(i));
        }
        return newArrayList;
    }

    /**
     * Method to search for an object in this list
     * @param o the object being searched in this list
     * @return true if this list contains the given object
     */
    public boolean contains(Object o){
        Iterator<E> iter = iterator();
        while(iter.hasNext()){
            E element = iter.next();
            if(element.equals(o)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to search for a given collection in this list
     * @param c collection whose presence in this list is to be tested
     * @return true if this list contains all of the elements in the given collection
     */
    public boolean containsAll(Collection<?> c){
        Iterator iter = c.iterator();
        while (iter.hasNext()) {
            E element = (E) iter.next();
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Grow the length of the array 'elements' by 1.5 if it is full
     */
    private void ensureCapacity() {
	    if(size >= elements.length) {
            int newCap = (int) (elements.length * 1.5);
            E[] newElements = (E[]) new Object[newCap];
            for(int i=0; i<size; i++){
                newElements[i] = elements[i];
            }
            elements = newElements;
	    }
    }

    /**
     * Method to compare two lists
     * @param o the object to be compared to this list
     * @return true if this list is equal to the given object, false otherwise
     */
    public boolean equals(Object o){
        if (o instanceof List) {
            for (int i = 0; i < size; i++) {
                List<E> other = (List<E>) o;
                if (other.size() != size)
                    return false;
                if (!((List) o).get(i).equals(get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * return the element at the specified position
     * @param index the position of the element to get
     * @return the value of the element at the given position
     * @throws IndexOutOfBoundsException if index is out of bounds (index < 0 || index >= size())
     */
    public E get(int index) {
        checkIndex(index);
        return elements[index];
    }

    /**
     * Method to get the first element of this list
     * @return the value of the first element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getFirst() {
		if (size == 0)
            throw new NoSuchElementException();
        return get(0);
    }

    /**
     * Method to get the last element of this list
     * @return the value of the last element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getLast() {
        if (size == 0)
            throw new NoSuchElementException();
        return get(size-1);
    }

    /**
     * Method to get the index of the first occurrence of the given object
     * @param o element to search for
     * @return the index of the first occurrence of o, -1 if o was not found
     * @throws ClassCastException if the type of the given object is not compatible with this list's type
     * @throws NullPointerException if the given object is null
     */
    public int indexOf(Object o){
        for (int i = 0; i < size; i++) {
            if (get(i).equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method to check if this list is empty
     * @return true if this list is empty
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Method to get an iterator for this list
     * @return an iterator over the elements starting at the beginning of this list 
     */
    public Iterator<E> iterator(){
		return new ArrayIterator();
    }

    /**
     * Method to get the index of the last occurrence of the given object
     * @param o element to search for
     * @return the index of the last occurrence of o, -1 if o was not found
     * @throws ClassCastException if the type of the given object is not compatible with this list's type
     * @throws NullPointerException if the given object is null
     */
    public int lastIndexOf(Object o){
        for (int i = size-1; i >= 0; i--) {
            if (get(i).equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method to get a list iterator over the elements of this list
     * @return a ListIterator object starting at the first element of this list
     */
    public ListIterator<E> listIterator(){
        return new ArrayListIterator();
    }
    /**
     * Method to get a list iterator over the elements of this list
     * @param index the position at which the list iterator should start
     * @return a ListIterator object starting at the element at the given index
     *         the given index indicates the first element that would be returned by an initial call to next()
     *         an initial call to previous() would return the element at the given index minus one
     * @throws IndexOutOfBoundsException if the index is out of bounds (index < 0 || index > size())
     */
    public ListIterator<E> listIterator(int index){
        return new ArrayListIterator(index);
    }

    /**
     * Method to remove an element at a given index
     * @param index of the element to be removed
     * @return the value of the element that was removed
     * @throws IndexOutOfBoundsException is the index is out of bounds (index < 0 || index >= size)
     */
    public E remove(int index) {
        checkIndex(index);
        E value = elements[index];
        for(int i=index; i<size-1; i++){
            elements[i] = elements[i+1];
        }
        size--;
        return value;
    }

    /**
     * Method to remove a given object from this list
     * @param o the object to find and remove
     * @return true if o was found and removed
     */
    public boolean remove(Object o){
        for(int i=0; i<size; i++){
            if(elements[i].equals(o)){
                remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to remove all this list's elements that are also contained in the given collection
     * @param c collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     */
    public boolean removeAll(Collection<?> c){
        boolean listChanged = false;
        for (int i = size-1; i >= 0; i--) {
            if (c.contains(elements[i])) {
                remove(elements[i]);
                listChanged = true;
            }
        }
        return listChanged;
    }

    /**
     * Method to remove the first element of this list
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     */
    public E removeFirst(){
        if (size == 0)
            throw new NoSuchElementException();
        return remove(0);
    }
    /**
     * Method to remove the last element of this list
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     */
    public E removeLast(){
        if (size == 0)
            throw new NoSuchElementException();
        return remove(size-1);
    }

    /**
     * Method to retain only the elements in this list that are contained in the given collection
     * @param c collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     */
    public boolean retainAll(Collection<?> c){
        boolean isChanged = false;
        for (int i = size-1; i >= 0; i--) {
            Iterator iter = c.iterator();
            boolean shouldRemove = true;
            while (iter.hasNext()) {
                if (iter.next() == get(i)) {
                    shouldRemove = false;
                    break;
                }
            }
            if (shouldRemove) {
                remove(i);
                isChanged = true;
            }
        }
        return isChanged;
    }

    /**
     * Method to return a reversed copy of this list
     * @return a reversed-ordered copy of this list
     */
    public List<E> reversed(){
        ArrayList<E> newList = new ArrayList(size);
        for (int i = size-1; i >= 0; i--) {
            newList.add(get(i));
        }
        return newList;
    }

    /**
     * Method to update the value of the element at a given index
     * @param index the position of the element to be modified
     * @param element the new value of the element to be modified
     * @return the old value of the modified element
     * @throws IndexOutOfBoundsException is the index is out of bounds (index < 0 || index >= size) 
     * @throws NullPointerException if the given element is null
     */
    public E set(int index, E element) {
        checkIndex(index);
        E oldValue = elements[index];
        elements[index] = element;
        return oldValue;
    }

    /**
     * Method to get the size of the list
     * @return number of elements in this list
     */
    public int size() {
        return size; 
    }
    
    /**
     * Method to sort this list
     * @param c the comparator used to sort the list
     *          if c is null, the method uses the natural ordering to sort the list
     */
    public void sort(Comparator<? super E> c){
        for (int i = 1; i < size; i++) {
            E currVal = elements[i];
            int prevIndex = i-1;
            int comparisonVal = -1;
            while (prevIndex >= 0 && comparisonVal < 0) {
                E prevVal = elements[prevIndex];
                if (c == null) 
                    comparisonVal = ((Comparable) currVal).compareTo(prevVal);
                else 
                    comparisonVal = c.compare(currVal, prevVal);
                if (comparisonVal < 0) {
                    prevIndex--;
                }
            }
            remove(i);
            add(prevIndex + 1, currVal);
        }
    }

    /**
     * Method to extract a sublist from this list
     * @param from the index at which the sublist should start (inclusive)
     * @param to the index at which the sublit should stop (exclusive)
     * @return a copy of the specified range within this list
     *         an empty list if from equals to
     * @throws IndexOutOfBoundsException if the indices from and to are illegal
     *         (from < 0 || to > size || from > to)
     */
    public List<E> subList(int from, int to){
        if (from < 0 || to > size || from > to)
            throw new ArrayIndexOutOfBoundsException("Index out of bounds in subList");
        ArrayList<E> newList = new ArrayList<>(to - from);
        for (int i = from; i < to; i++) {
            newList.add(get(i));
        }
        return newList;
    }

    /**
     * Method to get the elements of this list in an array of objects
     * @return an array of type Object that contains the elements from this list
     */
    public Object[] toArray(){
        Object[] newArray = new Object[size];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        return newArray;
    }

    /**
     * toString method
     * @return a formatted string containing all the elements of the list
     */
    public String toString() {
        if(size == 0)
            return "[]";
        String output = "[";
        for(int i=0; i<size-1; i++)
            output += elements[i] + " ";
        output += elements[size-1] + "]";
        return output;
    }

    /**
     * Method to resize the list capacity the actual size of the list
     */
    public void trimToSize() {
        if (size != elements.length) {
            E[] newElements = (E[]) new Object[size];
            for(int i=0; i<size; i++)
                newElements[i] = elements[i];
            elements = newElements;
        }
    }
    

    /*******************************************************
     * Inner class to implement the interface Iterator<E>  *
     *******************************************************/
    private class ArrayIterator implements Iterator<E>{
        // data member current: the index of the element at which the iterator is pointing
        private int current = 0;
        /**
        @return true if current did not reach the end of the list, false otherwise
        */
	    public boolean hasNext() {
            return current < size; 
        }
        /**
            @return the value of the current element and moves the index current to the next element
            @throws NoSuchElementException if current is out of bounds
        */
	    public E next() {
            if(current < 0 || current >= size)
                throw new NoSuchElementException("No more elements");
            return elements[current++]; 
        }
    } 
    
    /*******************************************************************************
     * Inner class to implement the interface ListIterator for the class ArrayList *
     *******************************************************************************/
    private class ArrayListIterator implements ListIterator<E>{
        
        private int current;
        /**
         * Default constructor
         */
        public ArrayListIterator(){
            current = 0;
        }
        /**
         * Constructor to initialize current to index
         * @param index the position where the iterator starts
         */
        public ArrayListIterator(int index){
            if (index < 0) 
                throw new ArrayIndexOutOfBoundsException("Must insert valid index");
            current = index;
        }
        /**
         * checks if the iterator has a next element in the list
         * @return true if the end of the list is not reached
         */
        public boolean hasNext(){
            return current < size;
        }
        /**
         * get the next element
         * @return the value of the next element pointed to by the iterator
         */
        public E next(){
            if (current >= size) {
                throw new ArrayIndexOutOfBoundsException("No more elements");
            }
            return elements[current++];
        }
        /**
         * checks if the iterator has a previous element in the list
         * @return true if the beginning of the list is not reached
         */
        public boolean hasPrevious(){
            int lastIndex = current - 1;
            return lastIndex >= 0;
        }
        /**
         * get the previous element
         * @return the value of the element element pointed to by the iterator
         */
        public E previous(){
            int lastIndex = current - 1;
            if (lastIndex >= 0) {
                current--;
                return get(lastIndex);
            }
            throw new ArrayIndexOutOfBoundsException("No previous element");
        }
        public void add(E e){
            throw new UnsupportedOperationException();
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
        public int nextIndex(){
            throw new UnsupportedOperationException();
        }
        public int previousIndex(){
            throw new UnsupportedOperationException();
        }
        public void set(E e){
            throw new UnsupportedOperationException();
        }
    }
}