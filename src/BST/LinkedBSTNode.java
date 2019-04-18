package BST;

import java.util.Deque;
import java.util.LinkedList;

public class LinkedBSTNode<T extends Comparable<T>> extends BSTNode<T> {
  private LinkedBSTNode<T> left;
  private LinkedBSTNode<T> right;
  private boolean isLeft = false;
  private boolean isRight = false;

  public LinkedBSTNode(T data) {
    this.data = data;
  }

  public LinkedBSTNode<T> getLeft() {
    return left;
  }

  public LinkedBSTNode<T> getRight() {
    return right;
  }

  public void setLeft(LinkedBSTNode<T> left) {
    this.left = left;
    if (this.left != null) {
      this.left.isLeft = true;
      this.left.isRight = false;
    }
  }

  public void setRight(LinkedBSTNode<T> right) {
    this.right = right;
    if (this.right != null) {
      this.right.isLeft = false;
      this.right.isRight = true;
    }
  }

  public boolean isLeftChild() {
    return isLeft;
  }

  public boolean isRightChild() {
    return isRight;
  }

  public int getHeight() {
    Deque<LinkedBSTNode<T>> toVisit = new LinkedList<LinkedBSTNode<T>>();
    Deque<Integer> depth = new LinkedList<>();
    toVisit.add(this);
    depth.add(0);
    int maxDepth = Integer.MIN_VALUE;
    while (!toVisit.isEmpty()) {
      LinkedBSTNode<T> current = toVisit.poll();
      int level = depth.poll();

      if (current.getLeft() == null && current.getRight() == null) {
        // Leaf node
        if (level > maxDepth) {
          maxDepth = level;
        }
      } else {
        if (current.getRight() != null) {
          toVisit.addFirst(current.getRight());
          depth.addFirst(level + 1);
        }

        if (current.getLeft() != null) {
          toVisit.addFirst(current.getLeft());
          depth.addFirst(level + 1);
        }
      }
    }

    return maxDepth;
  }
}
