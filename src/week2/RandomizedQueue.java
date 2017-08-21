import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Ilya Ismagilov <ilya@singulator.net>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int last;

    private class QueueIterator implements Iterator<Item> {
        private int it = 0;
        private final Item[] shuffled;

        public QueueIterator() {
            shuffled = Arrays.copyOf(q, last);
            for (int i = 0; i < last; i++) {
                int r = StdRandom.uniform(i, last);
                Item tmp = shuffled[i];
                shuffled[i] = shuffled[r];
                shuffled[r] = tmp;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

        @Override
        public boolean hasNext() {
            return it < shuffled.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Queue is empty");

            return shuffled[it++];
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        last = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return last == 0;
    }

    // return the number of items on the queue
    public int size() {
        return last;
    }

    // add the item
    public void enqueue(Item item) {
        if (null == item) throw new IllegalArgumentException("null can not be added");

        if (last == q.length)
            resize(q.length * 2);

        q[last++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");

        if (last <= q.length / 4)
            resize(q.length / 2);

        int idx = StdRandom.uniform(last);
        Item ret = q[idx];

        q[idx] = q[--last];
        q[last] = null;

        return ret;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");

        int idx = StdRandom.uniform(last);

        return q[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private void resize(int capacity) {
        Item[] resized = Arrays.copyOfRange(q, 0, capacity);

        q = resized;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        q.dequeue();
        q.dequeue();
        q.dequeue();

        for (Integer i : q)
            System.out.println(i);
    }
}
