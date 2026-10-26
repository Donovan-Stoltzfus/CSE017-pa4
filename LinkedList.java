import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Comparator;
/**
    LinkedList Generic Class
 */
public class LinkedList<E> implements List<E>, Cloneable{
    // Data members
    private Node head, tail;
    private int size;
    /**
     * Inner class Node
     * ****** Modify this class to hold two references to Node: next and previous
     */
    private class Node {
        E value;
        Node next;
        Node previous;
        Node(E initialValue) {
            value = initialValue;
            next = null;
            previous = null;
        }
    }
    /**
     * Default Constructor
     * creates an empty linkedlist
     */
    public LinkedList() {
        head = tail = null;
        size = 0;
    }

    /**
     * Method to add an new element to this list
     * @param item the value to add
     * @return true if this list changed as a result of the call
     */
    public boolean add(E item) {
        addLast(item);
        return true;
    }

    /**
     * Method to add an element at a given index
     * @param index the position at which element will be inserted
     * @param item the value of the element to be added
     * @throws IndexOutOfBoundsException if index is negative or greater than this list's size
     * ************* Modify this method to work correctly for a doubly-linked list ***************
     */
    public void add(int index, E item){
        if(index < 0 || index > size){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(index == 0){
            addFirst(item);
        }
        else if (index == size){
            addLast(item);
        }
        else{
            Node current = head;
            Node previous = null;
            for(int i=0; i<index; i++){
                previous = current;
                current = current.next;
            }
            Node newNode = new Node(item);
            previous.next = newNode;
            current.previous = newNode;
            newNode.next = current;
            newNode.previous = previous;
            size++;
        }
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
            E element = (E) iter.next();
            addLast(element);
            isChanged = true;
        }
        return isChanged;
    }

    /**
     * Method to add an element as the first element of this list
     * @param e element to be added
     * ************* Modify this method to work correctly for a doubly-linked list ***************
     */
    public void addFirst(E value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = tail = newNode;
        }
        else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * Method to add an element as the last element of this list
     * @param e to be added
     * ************* Modify this method to work correctly for a doubly-linked list ***************
     */
    public void addLast(E item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = tail = newNode;
        }
        else{
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * clears the list
     * resets the size to 0 and head and tail to null
    */
    public void clear() {
        head = tail = null;
        size = 0;
    }    

    /**
     * clone this list
     * @return a deep copy of this list
     */
    public Object clone(){
        Node current = head;
        LinkedList<E> newList = new LinkedList<>();
        while (current != null) {
            E valueCopy = current.value;
            newList.addLast(valueCopy);
            current = current.next;
        }
        return newList;
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
     * Method to compare two lists
     * @param o the object to be compared to this list
     * @return true if this list is equal to the given object, false otherwise
     */
    public boolean equals(Object o){
        if (o instanceof List) {
            List<E> other = (List<E>) o;
            if (other.size() != size)
                return false;
            for (int i = 0; i < size; i++) {
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
    public E get(int index){
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    /**
     * Method to get the first element of this list
     * @return the value of the first element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getFirst() {
        if (head == null)
            throw new NoSuchElementException();
        return head.value;
    }

    /**
     * Method to get the last element of this list
     * @return the value of the last element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getLast() {
        if (head == null)
            throw new NoSuchElementException();
        return tail.value;
    }

    /**
     * Method to get the index of the first occurrence of the given object
     * @param o element to search for
     * @return the index of the first occurrence of o, -1 if o was not found
     * @throws ClassCastException if the type of the given object is not compatible with this list's type
     * @throws NullPointerException if the given object is null
     */
    public int indexOf(Object o) throws ClassCastException{
        if (o == null)
            throw new NullPointerException("Object cannot be null");
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (current.value.equals(o)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    /**
     * check if the list is empty
     * @return true if the list is empty, false otherwise
    */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Method to get an iterator for this list
     * @return an iterator over the elements starting at the beginning of this list 
     */
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Method to get the index of the last occurrence of the given object
     * @param o element to search for
     * @return the index of the last occurrence of o, -1 if o was not found
     * @throws ClassCastException if the type of the given object is not compatible with this list's type
     * @throws NullPointerException if the given object is null
     */
    public int lastIndexOf(Object o) throws NullPointerException{
        if (o == null)
            throw new NullPointerException("Object cannot be null");
        Node current = tail;
        for (int i = size-1; i >= 0; i--) {
            if (current.value.equals(o)) {
                return i;
            }
            current = current.previous;
        }
        return -1;
    }

    /**
     * Method to get a list iterator over the elements of this list
     * @return a ListIterator object starting at the first element of this list
     */
    public ListIterator<E> listIterator() {
        return new LinkedListListIterator();
    }

    /**
     * Method to get a list iterator over the elements of this list
     * @param index the position at which the list iterator should start
     * @return a ListIterator object starting at the element at the given index
     *         the given index indicates the first element that would be returned by an initial call to next()
     *         an initial call to previous() would return the element at the given index minus one
     * @throws IndexOutOfBoundsException if the index is out of bounds (index < 0 || index > size())
     */
    public ListIterator<E> listIterator(int index) {
        return new LinkedListListIterator(index);
    }

    /**
     * Method to remove an element at a given index
     * @param index of the element to be removed
     * @return the value of the element that was removed
     * @throws IndexOutOfBoundsException is the index is out of bounds (index < 0 || index >= size)
     */
    public E remove(int index){
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (index == 0) {
            removeFirst();
        }
        if (index == size-1) {
            removeLast();
        }
        Node removedNode = head;
        for (int i = 0; i < index; i++) {
            removedNode = removedNode.next;
        }
        Node previous = removedNode.previous;
        Node next = removedNode.next;
        previous.next = next;
        next.previous = previous;
        E removedValue = removedNode.value;
        size--;
        return removedValue;
    }

    /**
     * Method to remove a given object from this list
     * @param o the object to find and remove
     * @return true if o was found and removed
     * ************* Modify this method to work correctly for a doubly-linked list ***************
     */
    public boolean remove(Object o){
        Node current = head;
        Node previous = null;
        while(current != null  && !current.value.equals(o)){
            previous = current;
            current = current.next;
        }
        if(current == null){
            return false;
        }
        if(previous == null){
            removeFirst();
            return true;
        }
        if(current == tail){
            removeLast();
            return true;
        }
        Node afterRemoved = current.next;
        previous.next = current.next;
        afterRemoved.previous = current.previous;
        size--;
        return true;
    }

    /**
     * Method to remove all this list's elements that are also contained in the given collection
     * @param c collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     */
    public boolean removeAll(Collection<?> c){
        Node current = head;
        boolean listChanged = false;
        while (current != null) {
            E currVal = current.value;
            if (c.contains(currVal)) {
                remove(currVal);
                listChanged = true;
            }
            current = current.next;
        }
        return listChanged;
    }

    /**
     * Method to remove the first element of this list
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     * ************* Modify this method to work correctly for a doubly-linked list ***************
     */
    public E removeFirst() {
        if (head == null)
            throw new NoSuchElementException();
        if (size == 1) {
            E value = head.value;
            head = tail = null;
            size--;
            return value;
        }
        E val = head.value;
        Node next = head.next;
        next.previous = null;
        head = head.next;
        size--;
        return val;
    }
    /**
     * Method to remove the last element of this list
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     * ************* Modify this method to work correctly for a doubly-linked list ***************
     */
    public E removeLast() {
        if (head == null)
            throw new NoSuchElementException();
        if (size == 1)
            return removeFirst();
        Node newTail = tail.previous;
        newTail.next = null;
        E removedVal = tail.value;
        tail = newTail;
        size--;
        return removedVal;
    }

    /**
     * Method to retain only the elements in this list that are contained in the given collection
     * @param c collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     */
    public boolean retainAll(Collection<?> c){
        Node current = head;
        boolean listChanged = false;
        while (current != null) {
            if (!c.contains(current.value)) {
                remove(current.value);
                listChanged = true;
            }
            current = current.next;
        }
        return listChanged;
    }

    /**
     * Method to return a reversed copy of this list
     * @return a reversed-ordered copy of this list
     */
    public List<E> reversed(){
        LinkedList<E> newList = new LinkedList<>();
        Node current = head;
        while (current != null) {
            newList.addFirst(current.value);
            current = current.next;
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
    public E set(int index, E newVal){
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException("Must be valid index");
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (current.value == null)
            throw new NullPointerException("Element is null");
        E oldVal = current.value;
        current.value = newVal;
        return oldVal;
    }

    /**
     * Get the size of this list
     * @return the number of elements in this list
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
        if (size < 2) {
            return;
        }
        Node current = head.next;
        while (current != null) {
            Node nextNode = current.next;
            Node sortedPrev = current.previous;
            int comparisonVal;
            while (sortedPrev != null) {
                if (c == null)
                    comparisonVal = ((Comparable) current.value).compareTo(sortedPrev.value);
                else
                    comparisonVal = c.compare(current.value, sortedPrev.value);
                if (comparisonVal >= 0) {
                    break;
                }
                sortedPrev = sortedPrev.previous;
            }
            if (sortedPrev == current.previous) {
                current = nextNode;
                continue;
            }
            current.previous.next = current.next;
            if (current.next != null) {
                current.next.previous = current.previous;
            } else {
                tail = current;
            }
            if (sortedPrev == null) {
                current.previous = null;
                current.next = head;
                head.previous = current;
                head = current;
            } else {
                Node sortedAfter = sortedPrev.next;
                sortedAfter.previous = current;
                sortedPrev.next = current;
                current.next = sortedAfter;
                current.previous = sortedPrev;
            }
            current = nextNode;
        }
    }
    
    /**
     * Method to extract a sublist form this list
     * @param from the index at which the sublist should start (inclusive)
     * @param to the index at which the sublit should stop (exclusive)
     * @return a copy of the specified range within this list
     *         an empty list if from is equal to to
     * @throws IndexOutOfBoundsException if the indices from and to are illegal
     *         (from < 0 || to > size || from > to)
     */
    public List<E> subList(int from, int to){
        if (from < 0 || to > size || from > to)
            throw new ArrayIndexOutOfBoundsException("Index out of bounds in subList");
        LinkedList<E> newList = new LinkedList<>();
        Node firstNode = head;
        for (int i = 0; i < from; i++) {
            firstNode = firstNode.next;
        }
        Node current = firstNode;
        for (int i = from; i < to; i++) {
            newList.addLast(current.value);
            current = current.next;
        }
        return newList;
    }
    
    
    /**
     * Method to get the elements of this list in an array of objects
     * @return an array of type Object that contains the elements from this list
     */
    public Object[] toArray(){
        Object[] array = new Object[size];
        Node current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.value;
            current = current.next;
        }
        return array;
    }

    /**
     * toString method
     * @return a formatted string containing all the elements of the list
     */
    public String toString() {
        if(size == 0)
            return "[]";
        String output = "[";
        Node node = head;
        while (node.next != null) {
            output += node.value + " ";
            node = node.next;
        }
        output += node.value + "]";
        return output;
    }
    

    /************************************************************
     * Inner class that implements the interface Iterator       *
     ************************************************************/
    private class LinkedListIterator implements Iterator<E> {
        private Node current = head;
        /**
         * hasNext method
         * @return true if current is not null
         */
        public boolean hasNext() {
            return (current != null);
        }

        /**
         * next method
         * @return the value of the node referenced by current and 
            modifies current to hold the reference of the next node
         * @throws NoSuchElementException if current is null
         */
        public E next() {
            if (current == null)
                throw new NoSuchElementException();
            E value = current.value;
            current = current.next;
            return value;
        }
    }
    
    /*************************************************************
     * Inner class that implements the interface ListIterator    *
     *************************************************************/
    private class LinkedListListIterator implements ListIterator<E> {
        private Node current, previous;
        /**
         * Default constructor
         */
        public LinkedListListIterator(){
            current = head;
            previous = null;
        }
        /**
         * Constructor to initialize current to be the node at position index
         * @param index the position where the iterator starts
         */
        public LinkedListListIterator(int index){
            if (index < 0 || index >= size)
                throw new ArrayIndexOutOfBoundsException("Must insert valid index");
            current = head;
            previous = null;
            for (int i = 0; i < index; i++) {
                previous = current;
                current = current.next;
            }
        }
        /**
         * checks if the iterator has a next element in the list
         * @return true if the end of the list is not reached
         */
        public boolean hasNext() {
            return current != null;
        }
        /**
         * get the next element
         * @return the value of the next element pointed to by the iterator
         */
        public E next() {
            if (current == null) {
                throw new ArrayIndexOutOfBoundsException("No more elements");
            }
            previous = current;
            current = current.next;
            return previous.value;
        }
        /**
         * checks if the iterator has a previous element in the list
         * @return true if the beginning of the list is not reached
         */
        public boolean hasPrevious() {
            return previous != null;
        }
        /**
         * get the previous element
         * @return the value of the element element pointed to by the iterator
         */
        public E previous() {
            if (previous == null) {
                throw new ArrayIndexOutOfBoundsException("No more elements");
            }
            current = previous;
            previous = previous.previous;
            return current.value;
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