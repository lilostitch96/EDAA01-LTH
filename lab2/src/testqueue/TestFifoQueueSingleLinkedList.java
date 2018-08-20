package testqueue;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import queue_singlelinkedlist.FifoQueue;

public class TestFifoQueueSingleLinkedList {
	private FifoQueue<Integer> q1;
	private FifoQueue<Integer> q2;

	@Before
	public void setUp() throws Exception {
		q1 = new FifoQueue<Integer>();
		q2 = new FifoQueue<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		q1 = null;
		q2 = null;
	}

	/**
	 * Test to add two empty queues together.
	 */
	@Test
	public void testAppendEmptyQueue() {

		q1.append(q2);
		assertTrue("Queue not empty when expected", q1.size() == 0);
		assertTrue("Queue not empty when expected", q2.size() == 0);

	}

	/**
	 * Test to add two non-empty queues together.
	 */
	@Test
	public void testAppendNonEmptyQueue() {

		q1.offer(1);
		q1.offer(2);

		q2.offer(3);
		q2.offer(4);
		q1.append(q2);

		assertTrue("Wrong size after append", q1.size() == 4);
		assertTrue("Wrong size after append", q2.size() == 0);
		assertTrue("Wrong element after first poll", q1.poll() == 1);
		assertTrue("Wrong element after second poll", q1.poll() == 2);
		assertTrue("Wrong element after third poll", q1.poll() == 3);
		assertTrue("Wrong element after fourth poll", q1.poll() == 4);

	}

	/**
	 * Test to add empy queue to non-empty queue.
	 */
	@Test
	public void testAppendEmptyQueueToNonEmpty() {
		q2.offer(1);
		q2.offer(2);
		q1.append(q2);
		assertTrue("wrong size after append", q1.size() == 2);
		assertTrue("wrong size after append", q2.size() == 0);
		assertTrue("wrong element after poll", q1.poll() == 1);
		assertTrue("wrong element after poll", q1.poll() == 2);
	}

	/**
	 * Test to add non-empty queue to empty queue.
	 */
	@Test
	public void testAppendNonEmptyQueueToEmpty() {
		q1.offer(1);
		q1.offer(2);
		q1.append(q2);
		assertTrue("wrong size after append", q1.size() == 2);
		assertTrue("wrong size after append", q2.size() == 0);
		assertTrue("wrong element after poll", q1.poll() == 1);
		assertTrue("wrong element after poll", q1.poll() == 2);
	}

	/**
	 * Test to add same queue to itself.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAppendToSelf() {
		q1.offer(1);
		q1.offer(2);
		q1.append(q1);
		assertTrue("wrong size after append", q1.size() == 2);
		assertTrue("wrong element after poll", q1.poll() == 1);
		assertTrue("wrong element after poll", q1.poll() == 2);
	}

	/**
	 * Test to add two empty queues.
	 */
	@Test
	public void testAppendTwoEmptyQueues() {
		q1.append(q2);
		assertTrue("Wrong size after appending", q1.size() == 0);
		assertTrue("Wrong size after appending", q2.size() == 0);
	}
}
