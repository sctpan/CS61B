package synthesizer;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer<Integer>(4);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(7);
        arb.enqueue(8);
        Iterator<Integer> iterator = arb.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


    }

    /** Calls tests for ArrayRingBuffer. */
//    public static void main(String[] args) {
//        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
//    }
} 
