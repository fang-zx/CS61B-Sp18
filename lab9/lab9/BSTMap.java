package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) return null;
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
        } else {
            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                p.left = putHelper(key, value, p.left);
            } else if (cmp > 0) {
                p.right = putHelper(key, value, p.right);
            } else {
                p.value = value;
            }

        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) return;
        Node p = putHelper(key, value, root);
        size++;
        if (root == null) {
            root = p;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    void keySetHelper(Set<K> keys, Node p) {
        if (p == null) return;
        keys.add(p.key);
        keySetHelper(keys, p.left);
        keySetHelper(keys, p.right);
    }


    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        keySetHelper(keys, root);
        return keys;
    }

    private Node min(Node p) {
        if (p.left == null) return p;
        else return min(p.left);
    }

    private Node removeMin(Node p) {
        if (p.left == null) return p.right;
        p.left = removeMin(p.left);
        return p;
    }

    private Node removeHelper(K key, V value, Node p, boolean valueCheck) {
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeHelper(key, value, p.left, valueCheck);
        } else if (cmp > 0) {
            p.right = removeHelper(key, value, p.right, valueCheck);
        } else {
            if (valueCheck && !p.value.equals(value)) {
                return null;
            }
            if (p.left == null)  return p.right;
            if (p.right == null) return p.left;
            Node t = p;
            Node minNode = min(p.right);
            p.key = minNode.key;
            p.value = minNode.value;
        }
        return p;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) return null;
        size--;
        Node removed =  removeHelper(key, null, root, false);
        return removed != null ? removed.value : null;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        Node removed = removeHelper(key, value, root, true);
        return removed != null ? removed.value : null;
    }


    private class BSTMapIterator<K> implements Iterator<K> {
        Deque<Node> nodes;

        public BSTMapIterator() {
            nodes = new LinkedList<>();
            init(root);
        }

        private void init(Node p) {
            if (p != null) {
                init(p.left);
                nodes.add(p);
                init(p.right);
            }
        }

        @Override
        public boolean hasNext() {
            return !nodes.isEmpty();
        }

        @Override
        public K next() {
            return (K) nodes.remove().key;
        }
    }
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator<>();
    }
}
