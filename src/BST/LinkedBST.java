package BST;

import java.util.*;

/*
 * A linked-list interpretation of a binary search tree. Aims to be
 * non-recursive and thread-safe.
 */
public class LinkedBST<T extends Comparable<T>> implements BST<T> {

  protected LinkedBSTNode<T> root;
  protected LinkedBSTNode<T> INVALID = new LinkedBSTNode<T>(null);

  public boolean isBalanced() {
    int height = getHeight();

    Deque<LinkedBSTNode<T>> toVisit = new LinkedList<>();
    Deque<Integer> depths = new LinkedList<>();

    toVisit.addFirst(root);
    depths.addFirst(0);
    while(!toVisit.isEmpty()) {
      LinkedBSTNode<T> node = toVisit.poll();
      int level = depths.poll();

      if (node != null) {
        // All nodes at depth = height - 2 or less have 2 children
        // Nodes at depth = height - 1 can have any number of children (up to 2)
        // Nodes at depth = height must be leaves
        if (level <= height - 2) {
          if (node.getRight() == null || node.getLeft() == null) {
            return false;
          }
        } else if (level == height) {
          if (node.getLeft() != null || node.getRight() != null) {
            return false;
          }
        }

        toVisit.add(node.getLeft());
        toVisit.add(node.getRight());
        depths.add(level + 1);
        depths.add(level + 1);
      }
    }

    return true;
  }

  protected LinkedBSTNode<T> insertNode(LinkedBSTNode<T> node) {
    if (root == null) {
      root = node;
      return root;
    } else {
      LinkedBSTNode<T> current = root;

      while (true) {
        int comparison = current.getData().compareTo(node.getData());
        if (comparison == 0) {
          return INVALID;
        } else if (comparison > 0) {
          if (current.getLeft() == null) {
            current.setLeft(node);
            return current.getLeft();
          } else {
            current = current.getLeft();
          }
        } else {
          if (current.getRight() == null) {
            current.setRight(node);
            return current.getRight();
          } else {
            current = current.getRight();
          }
        }
      }
    }
  }

  /**
   * Inserts a node containing data into the tree, preserving
   * the set and binary tree invariants.
   * @param data The data to be inserted.
   * @return A boolean representing whether the insertion was successful.
   */
  @Override
  public boolean add(T data) {
    return insertNode(new LinkedBSTNode<>(data)) == INVALID;
  }

  /**
   * Calculates a unique path from the root to a node containing
   * specific data.
   * @param data The node with the data to get a path for.
   * @return A list of nodes representing a path from the root, or null if
   * the data is not in the tree.
   */
  public List<LinkedBSTNode<T>> getPathToNode(T data) {
    LinkedBSTNode<T> current = root;
    List<LinkedBSTNode<T>> path = new LinkedList<LinkedBSTNode<T>>();
    while (current != null) {
      path.add(current);
      int comparison = current.getData().compareTo(data);
      if (comparison == 0) {
        return path;
      } else if (comparison > 0) {
        current = current.getLeft();
      } else {
        current = current.getRight();
      }
    }

    return null;
  }

  protected LinkedBSTNode<T> removeNode(T data) {
    LinkedBSTNode<T> parent = null;
    LinkedBSTNode<T> current = root;

    while (current != null) {
      int comparison = current.getData().compareTo(data);
      if (comparison == 0) {
        if (current.getLeft() == null && current.getRight() == null) {
          if (parent == null) {
            root = null;
            return root;
          } else {
            if (current.isLeftChild()) {
              parent.setLeft(null);
            } else if (current.isRightChild()) {
              parent.setRight(null);
            }
          }


          return parent;
        } else if (current.getLeft() == null) {
          if (parent == null) {
            root = current.getRight();
            return root;
          } else {
            if (current.isLeftChild()) {
              parent.setLeft(current.getRight());
            } else if (current.isRightChild()) {
              parent.setRight(current.getRight());
            }
          }

          return parent;
        } else if (current.getRight() == null) {
          if (parent == null) {
            root = current.getLeft();
            return root;
          } else {
            if (current.isLeftChild()) {
              parent.setLeft(current.getLeft());
            } else if (current.isRightChild()) {
              parent.setRight(current.getLeft());
            }
          }

          return parent;
        } else {
          LinkedBSTNode<T> minimumParent = current;
          LinkedBSTNode<T> minimum = current.getRight();
          while (minimum.getLeft() != null) {
            minimumParent = minimum;
            minimum = minimum.getLeft();
          }

          if (minimum == current.getRight()) {
            current.setRight(minimum.getRight());
          } else {
            minimumParent.setLeft(minimum.getRight());
          }

          current.setData(minimum.getData());
          return minimumParent;
        }
      } else if (comparison > 0) {
        parent = current;
        current = current.getLeft();
      } else {
        parent = current;
        current = current.getRight();
      }
    }

    return INVALID;
  }

  @Override
  public boolean remove(T data) {
    return removeNode(data) == INVALID;
  }


  /**
   * Checks whether data is in the tree.
   * @param data The data to search for.
   * @return Whether the data is contained in the tree.
   */
  @Override
  public boolean contains(T data) {
    LinkedBSTNode<T> current = root;

    while (current != null) {
      int comparison = current.getData().compareTo(data);
      if (comparison == 0) {
        return true;
      } else if (comparison > 0) {
        current = current.getLeft();
      } else {
        current = current.getRight();
      }
    }

    return false;
  }

  /**
   * Calculates the total height of the tree.
   * @return The height of the tree.
   */
  public int getHeight() {
    return root == null ? -1 : root.getHeight();
  }

  /**
   * Calculates the height of any subtree.
   * @param node The node representing a subtree.
   * @return
   */
  public static int getHeight(LinkedBSTNode<?> node) {
    return node == null ? -1 : node.getHeight();
  }

  /*
   * Builds a (not yet parenthesised)
   * representation of a binary search tree
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    Deque<LinkedBSTNode<T>> stack = new LinkedList<>();
    Deque<Integer> depth = new LinkedList<>();
    stack.addFirst(root);
    depth.addFirst(0);
    int prevLevel = 0;
    while(!stack.isEmpty()) {
      LinkedBSTNode<T> current = stack.poll();
      int level = depth.poll();

      if (level != prevLevel) {
      //  builder.append("(");
      }

      Integer next = depth.peek();

      if (current != null) {
        if (current.getRight() == null && current.getLeft() == null) {
          //builder.append("(");
          builder.append(current.getData());
          //builder.append(")");
        } else {
          builder.append(current.getData());
          stack.addFirst(current.getRight());
          depth.addFirst(level + 1);
          stack.addFirst(current.getLeft());
          depth.addFirst(level + 1);
        }

        builder.append(" ");
      } else {
        builder.append("() ");
      }

      if (next == null || next < level) {
        for (int i = 0; i < level - 1; i++) {
          //builder.append(")");
        }
      }

      prevLevel = level;
    }

    return builder.toString();
  }
}
