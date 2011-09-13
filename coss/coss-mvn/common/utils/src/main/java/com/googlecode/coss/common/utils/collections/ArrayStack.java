/**
 * 
 */
package com.googlecode.coss.common.utils.collections;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * An implementation of the {@link java.util.Stack} API that is based on an
 * <code>ArrayList</code> instead of a <code>Vector</code>, so it is not
 * synchronized to protect against multi-threaded access. The implementation is
 * therefore operates faster in environments where you do not need to worry
 * about multiple thread contention.
 * <p>
 * The removal order of an <code>ArrayStack</code> is based on insertion order:
 * The most recently added element is removed first. The iteration order is
 * <i>not</i> the same as the removal order. The iterator returns elements from
 * the bottom up, whereas the {@link #remove()} method removes them from the top
 * down.
 * <p>
 * Unlike <code>Stack</code>, <code>ArrayStack</code> accepts null entries.
 * 
 * 
 * @see java.util.Stack
 */
public class ArrayStack<E> extends ArrayList<E> {

	final private static long serialVersionUID = 2130079159931574598L;

	/**
	 * Constructs a new empty <code>ArrayStack</code>. The initial size is
	 * controlled by <code>ArrayList</code> and is currently 10.
	 */
	public ArrayStack() {
		super();
	}

	/**
	 * Constructs a new empty <code>ArrayStack</code> with an initial size.
	 * 
	 * @param initialSize
	 *            the initial size to use
	 * @throws IllegalArgumentException
	 *             if the specified initial size is negative
	 */
	public ArrayStack(int initialSize) {
		super(initialSize);
	}

	/**
	 * Return <code>true</code> if this stack is currently empty.
	 * <p>
	 * This method exists for compatability with <code>java.util.Stack</code>.
	 * New users of this class should use <code>isEmpty</code> instead.
	 * 
	 * @return true if the stack is currently empty
	 */
	public boolean empty() {
		return isEmpty();
	}

	/**
	 * Returns the top item off of this stack without removing it.
	 * 
	 * @return the top item on the stack
	 */
	public E peek() {
		int n = size();
		if (n <= 0) {
			return null;
		} else {
			return get(n - 1);
		}
	}

	/**
	 * Returns the n'th item down (zero-relative) from the top of this stack
	 * without removing it.
	 * 
	 * @param n
	 *            the number of items down to go
	 * @return the n'th item on the stack, zero relative
	 */
	public E peek(int n) throws EmptyStackException {
		int m = (size() - n) - 1;
		if (m < 0) {
			return null;
		} else {
			return get(m);
		}
	}

	/**
	 * Pops the top item off of this stack and return it.
	 * 
	 * @return the top item on the stack
	 * @throws EmptyStackException
	 *             if the stack is empty
	 */
	public E pop() {
		int n = size();
		if (n <= 0) {
			return null;
		} else {
			return remove(n - 1);
		}
	}

	/**
	 * Pushes a new item onto the top of this stack. The pushed item is also
	 * returned. This is equivalent to calling <code>add</code>.
	 * 
	 * @param item
	 *            the item to be added
	 * @return the item just pushed
	 */
	public E push(E item) {
		add(item);
		return item;
	}

	/**
	 * Returns the one-based position of the distance from the top that the
	 * specified object exists on this stack, where the top-most element is
	 * considered to be at distance <code>1</code>. If the object is not present
	 * on the stack, return <code>-1</code> instead. The <code>equals()</code>
	 * method is used to compare to the items in this stack.
	 * 
	 * @param object
	 *            the object to be searched for
	 * @return the 1-based depth into the stack of the object, or -1 if not
	 *         found
	 */
	public int search(E object) {
		int i = size() - 1; // Current index
		int n = 1; // Current distance
		while (i >= 0) {
			Object current = get(i);
			if ((object == null && current == null) || (object != null && object.equals(current))) {
				return n;
			}
			i--;
			n++;
		}
		return -1;
	}
}
