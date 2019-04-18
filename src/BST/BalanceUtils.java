package BST;

public class BalanceUtils {

  public enum Scheme {PERFECT, AVL, REDBLACK};

  public static <T extends Comparable<T>> LinkedBSTNode<T> leftRotation(LinkedBSTNode<T> node) {
    LinkedBSTNode<T> right = node.getRight();
    node.setRight(right.getLeft());
    right.setLeft(node);
    return right;
  }

  public static <T extends Comparable<T>> LinkedBSTNode<T> rightRotation(LinkedBSTNode<T> node) {
    LinkedBSTNode<T> left = node.getLeft();
    node.setLeft(left.getRight());
    left.setRight(node);
    return left;
  }

  public static <T extends Comparable<T>> LinkedBSTNode<T> leftRightRotation(LinkedBSTNode<T> node) {
    node.setLeft(leftRotation(node.getLeft()));
    return rightRotation(node);
  }

  public static <T extends Comparable<T>> LinkedBSTNode<T> rightLeftRotation(LinkedBSTNode<T> node) {
    node.setRight(rightRotation(node.getRight()));
    return leftRotation(node);
  }
}
