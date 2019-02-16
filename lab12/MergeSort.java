import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
    makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> res = new Queue<Queue<Item>>();
        for (Item item : items) {
            Queue<Item> queue = new Queue<>();
            queue.enqueue(item);
            res.enqueue(queue);
        }
        return res;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> res = new Queue<>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (q1.peek().compareTo(q2.peek()) < 0) {
                res.enqueue(q1.peek());
                q1.dequeue();
            } else {
                res.enqueue(q2.peek());
                q2.dequeue();
            }
        }
        Iterator<Item> iterator = q1.isEmpty() ? q2.iterator() : q1.iterator();
        while (iterator.hasNext()) {
            res.enqueue(iterator.next());
        }
        return res;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> walk = makeSingleItemQueues(items);
        Iterator<Queue<Item>> curr = walk.iterator();
        Iterator<Queue<Item>> next = walk.iterator();
        next.next();
        Queue<Queue<Item>> res = new Queue<Queue<Item>>();
        while (true) {
            Queue<Item> merged = mergeSortedQueues(curr.next(), next.next());
            walk.dequeue();
            walk.dequeue();
            res.enqueue(merged);
//            System.out.println(merged);
            if (walk.size() == 1 || walk.size() == 0) {
                if (walk.size() == 1) {
                    res.enqueue(walk.peek());
                }
                if (res.size() == 1) {
                    return res.peek();
                }
                walk = res;
                res = new Queue<Queue<Item>>();
                curr = walk.iterator();
                next = walk.iterator();
                next.next();
            } else {
                curr.next();
                next.next();
            }
        }
    }

    public static void main(String args[]) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        students.enqueue("Tom");
        students.enqueue("Jack");
        students.enqueue("Dick");
        for (String stu : students) {
            System.out.print(stu + " ");
        }
        System.out.println("");
        Queue<String> sortedStudents = mergeSort(students);
        for (String stu : sortedStudents) {
            System.out.print(stu + " ");
        }
        System.out.println("");
    }
}
