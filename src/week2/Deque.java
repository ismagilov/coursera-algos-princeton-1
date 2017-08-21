import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Ilya Ismagilov <ilya@singulator.net>
 */
public class Deque<Item> implements Iterable<Item> {
    private int sz;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node it;
        public DequeIterator() {
            it = first;
        }

        @Override
        public boolean hasNext() {
            return it != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Deque is empty");

            Item item = it.item;
            it = it.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // return the number of items on the deque
    public int size() {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (null == item) throw new IllegalArgumentException("null can not be added");

        Node n = new Node();
        n.item = item;

        if (sz == 0) {
            first = n;
            last = n;
        } else {
            n.next = first;
            first.prev = n;

            first = n;
        }

        sz++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (null == item) throw new IllegalArgumentException("null can not be added");

        Node n = new Node();
        n.item = item;

        if (sz == 0) {
            first = n;
            last = n;
        } else {
            last.next = n;
            n.prev = last;

            last = n;
        }

        sz++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Dequeue is empty");

        Item removed = first.item;

        if (sz == 1) {
            first = null;
            last = null;
        } else {
            first.next.prev = null;
            first = first.next;
        }

        sz--;

        return removed;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Dequeue is empty");

        Item removed = last.item;

        if (sz == 1) {
            first = null;
            last = null;
        } else {
            last.prev.next = null;
            last = last.prev;
        }

        sz--;

        return removed;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        // 1
        deque.addFirst(1);
        deque.addLast(2);
        System.out.println(deque.removeFirst());
        deque.addFirst(4);
        deque.addLast(5);
        System.out.println(deque.removeLast());
//
//        deque.addLast(6);
//        for (Integer i : deque)
//            System.out.print(i + " ");
//
//        deque.addLast(7);
//        for (Integer i : deque)
//            System.out.print(i + " ");
//
//        deque.addLast(8);
//        for (Integer i : deque)
//            System.out.print(i + " ");
//
//        System.out.println(deque.removeLast());
//        for (Integer i : deque)
//            System.out.print(i + " ");



//        - failed on operation 7 of 500
//        - student   removeLast() returned 2
//        - reference removeLast() returned 5
//        - sequence of dequeue operations was:
//        deque.addLast(0)
//        deque.addLast(1)
//        deque.addFirst(2)
//        deque.addLast(3)
//        deque.addLast(4)
//        deque.addLast(5)
//        deque.isEmpty()
//        deque.removeLast()      ==> 2

//        - sequence of dequeue operations was:
//        deque.addFirst(0);
//        deque.removeLast();
//        deque.addFirst(2);
    }
}