package BST;

interface BST<T extends Comparable<T>> {
  boolean add(T element);
  boolean remove(T element);
  boolean contains(T element);
}
