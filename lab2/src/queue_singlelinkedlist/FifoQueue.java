package queue_singlelinkedlist;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**
	 * Inserts the specified element into this queue, if possible post: The
	 * specified element is added to the rear of this queue
	 * 
	 * @param e
	 *            the element to insert
	 * @return true if it was possible to add the element to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> n = new QueueNode<E>(e);
		if (size == 0) {
			n.next = n;
			last = n;
		} else {
			QueueNode<E> pre = last.next;
			last.next = n;
			n.next = pre;
			last = n;
		}
		size++;
		return true;
	}

	/**
	 * Returns the number of elements in this queue
	 * 
	 * @return the number of elements in this queue
	 */
	public int size() {
		return size;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, returning null if
	 * this queue is empty
	 * 
	 * @return the head element of this queue, or null if this queue is empty
	 */
	public E peek() {
		return last != null ? last.next.element : null;
	}

	/**
	 * Retrieves and removes the head of this queue, or null if this queue is empty.
	 * post: the head of the queue is removed if it was not empty
	 * 
	 * @return the head of this queue, or null if the queue is empty
	 */
	public E poll() {
		E e = null;
		if (size == 1) {
			e = last.element;
			last = null;
			size = 0;
		} else if (size > 1) {
			e = last.next.element;
			last.next = last.next.next;
			size--;
		}
		return e;
	}

	/**
	 * Returns an iterator over the elements in this queue
	 * 
	 * @return an iterator over the elements in this queue
	 */
	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<E> {
		private QueueNode<E> pos;

		private QueueIterator() {
			pos = (size > 0) ? last.next : null;
		}

		@Override
		public boolean hasNext() {
			return pos != null;
		}

		@Override
		public E next() {
			if (hasNext()) {
				QueueNode<E> temp = pos;
				pos = (pos != last) ? pos.next : null;
				return temp.element;
			} else {
				throw new NoSuchElementException();
			}
		}

	}

	/**
	 * Appends the specified queue to this queue post: all elements from the
	 * specified queue are appended to this queue. The specified queue (q) is empty
	 * after the call.
	 * 
	 * @param q
	 *            the queue to append
	 * @throws IllegalArgumentException
	 *             if this queue and q are identical
	 */
	public void append(FifoQueue<E> q) throws IllegalArgumentException {
		if (this == q) {
			System.out.println("Same queue");
			throw new IllegalArgumentException();
		} else if(size > 0 && q.size > 0) {
		QueueNode<E> temp = this.last.next;
		this.last.next = q.last.next;
		q.last.next = temp;
		last = q.last;
		
		size += q.size;
		q.size = 0;
		q.last = null;
		} else if (q.size > 0) {
			last = q.last;
			size = q.size;
			
			q.size = 0;
			q.last = null;
		}
	}

	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

	public static void main(String[] args) {
		FifoQueue<String> list = new FifoQueue<String>();
		FifoQueue<String> list2 = new FifoQueue<String>();
		list.offer("1");
		list.offer("2");
		list.offer("3");
		list2.offer("4");
		list2.offer("5");
		list2.offer("6");

		list.append(list2);
		for (String s : list) {
			System.out.println(s);
		}

	}
}
