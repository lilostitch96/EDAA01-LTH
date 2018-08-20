package map;

/**
 * The {@code SimpleHashMap} class represents an open HashMap with single-linked
 * lists containing Entry-couples with a unique Key and a value.
 * <p>
 * Because the key is unique, in the event of and insertion of the same key the
 * new key value will replace the old key value and return the old key value.
 * For example: <blockquote>
 * 
 * <pre>
 * SimpleHashMap.put("One", 1);
 * SimpleHashMap.put("One", 2);
 * </pre>
 * 
 * </blockquote>
 * <p>
 * will result in: <blockquote>
 * 
 * <pre>
 * One = 2
 * </pre>
 * 
 * </blockquote>
 * <p>
 * The type of key and value is to be determined by the user. They are both
 * generic.
 * 
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 * 
 * @author Eneas Hållsten
 * @since the begining of time minus one day
 */
public class SimpleHashMap<K, V> implements Map<K, V> {
	int capacity; // capacity of the array
	int size; // number of used slots in the array
	double loadFactor; // how much of the array is to be occupied
	Entry<K, V>[] table; // the array with entrys

	/**
	 * Constructs an empty hashmap with the default initial capacity (16) and the
	 * default load factor (0.75).
	 */
	public SimpleHashMap() {
		this(16);
	}

	/**
	 * Constructs an empty hashmap with the specified initial capacity and the
	 * default load factor (0.75).
	 */
	public SimpleHashMap(int capacity) {
		this(capacity, 0.75);
	}

	/**
	 * Constructs an empty hashmap whit the specified capacity and the specified
	 * loadFactor.
	 * 
	 * @param capacity
	 * @param loadFactor
	 */
	@SuppressWarnings("unchecked")
	private SimpleHashMap(int capacity, double loadFactor) {
		this.capacity = capacity;
		this.loadFactor = loadFactor;
		size = 0;
		table = (Entry<K, V>[]) new Entry[capacity];

	}

	/**
	 * Builds a String that shows the SimpleHashMap.
	 * 
	 * @return String
	 */
	public String show() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Entry<K, V> e : table) {
			sb.append(Integer.toString(i));
			while (e != null) {
				sb.append("\t");
				sb.append(e.toString());
				e = e.next;
			}
			i++;
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Returns the value of the key key.
	 * 
	 * @param key
	 * @return Value of key
	 */
	@Override
	public V get(Object key) {
		@SuppressWarnings("unchecked")
		K k = (K) key;
		Entry<K, V> e = find(index(k), k);
		if (e != null)
			return e.getValue();
		return null;
	}

	/**
	 * Checks if the SimpleHashMap is empty.
	 * 
	 * @return true if empty
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Inserts the Entry with the key key and value value in the array.
	 * 
	 * @param key
	 * @param value
	 * @return null if no other matching key was found, else the value of the old
	 *         key
	 */
	@Override
	public V put(K key, V value) {
		int i = index(key);
		if (table[i] == null)
			table[i] = new Entry<K, V>(key, value);
		else {
			Entry<K, V> e = find(i, key);
			if (e != null) {
				V oldValue = e.getValue();
				e.setValue(value);
				return oldValue;
			}
			e = table[i];
			while (e.next != null) {
				e = e.next;
			}
			e.next = new Entry<K, V>(key, value);
		}
		size++;
		if (size > capacity * loadFactor) {
			rehash();
		}
		return null;
	}

	/**
	 * If the loadFactor is exceeded, double the size of the SimpleHashMap.
	 */
	@SuppressWarnings("unchecked")
	private void rehash() {
		Entry<K, V>[] oldTable = table;
		table = (Entry<K, V>[]) new Entry[capacity *= 2];
		size = 0;
		for (Entry<K, V> e : oldTable) {
			while (e != null) {
				put(e.getKey(), e.getValue());
				e = e.next;
			}
		}
	}

	/**
	 * Removes the Entry with the key key.
	 * 
	 * @param key
	 * @return the value of the key, null if the array is empty or if the key cannot
	 *         be found.
	 */
	@Override
	public V remove(Object key) {
		@SuppressWarnings("unchecked")
		K k = (K) key;
		int i = index(k);
		if (table[i] == null)
			return null;
		else if (table[i].getKey().equals(k)) {
			Entry<K, V> e = table[i];
			table[i] = e.next;
			size--;
			return e.getValue();
		} else {
			Entry<K, V> previous = table[i];
			Entry<K, V> current = table[i].next;

			while (current != null) {
				if (current.getKey().equals(k)) {
					previous.next = current.next;
					size--;
					return current.getValue();
				}
				previous = current;
				current = current.next;
			}
			return null;
		}
	}

	/**
	 * @return how many slots of the array is in use.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * @param key
	 * @return what index that is to be used for the key key
	 */
	private int index(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	/**
	 * Returns the Entry which has the key key in the list with index index in the
	 * tabell
	 * 
	 * @param index
	 * @param key
	 * @return Entry e
	 */
	private Entry<K, V> find(int index, K key) {
		Entry<K, V> e = table[index];
		while (e != null) {
			if (e.getKey().equals(key))
				break;
			e = e.next;
		}
		return e;
	}

	/**
	 * The {@code Entry<K,V>} class represents the key-value elements. Every
	 * elements has a reference to the next element, initially the next value is set
	 * to null.
	 * 
	 * @param <K>
	 *            the key
	 * @param <V>
	 *            the value
	 *
	 * @author eneashallsten
	 * @since two days ago
	 */
	private static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;

		/**
		 * Construcs an Entry element with the key key and value value.
		 * 
		 * @param key
		 * @param value
		 */
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * @return the key.
		 */
		@Override
		public K getKey() {
			return key;
		}

		/**
		 * @return the value
		 */
		@Override
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value of a key to the new value.
		 * 
		 * @return the old value
		 */
		@Override
		public V setValue(V value) {
			return this.value = value;
		}

		/**
		 * Returns a string with the format: <blockquote>
		 * 
		 * <pre>
		 * {@code key} = {@code value}
		 * </pre>
		 * 
		 * </blockquote>
		 */
		@Override
		public String toString() {
			return key + " = " + value;
		}
	}
}
