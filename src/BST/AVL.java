package BST;

import java.util.List;

public class AVL<T extends Comparable<T>> extends LinkedBST<T> {

  @Override
  public boolean add(T data) {
    LinkedBSTNode<T> node = insertNode(data);

    if (node == INVALID) return false;

    rebalanceTree(data);

    return true;
  }

  @Override
  public boolean remove(T data) {
    LinkedBSTNode<T> node = removeNode(data);

    if (node == INVALID) return false;

    // Node is only null if we've deleted the tree, in which
    // case it is balanced.
    if (node != null) {
      rebalanceTree(node.getData());
    }

    return true;
  }

  private void rebalanceTree(T data) {
    // Work backwards from the node to find the first unbalanced node
    List<LinkedBSTNode<T>> path = getPathToNode(data);
    for (int i = (path.size() - 1); i >= 0; i--) {
      LinkedBSTNode<T> current = path.get(i);
      LinkedBSTNode<T> parent = i == 0 ? null : path.get(i - 1);
      boolean isLeftChild = current.isLeftChild();
      boolean isRightChild = current.isRightChild();
      LinkedBSTNode<T> modified = rebalance(path.get(i));
      if (modified != current) {
        if (parent == null) {
          root = modified;
        } else {
          // Theoretically if it's not the left child then it will
          // be the right child as it is not the root.
          assert(isLeftChild != isRightChild); // For debugging only.
          if (isLeftChild) {
            parent.setLeft(modified);
          } else {
            parent.setRight(modified);
          }
        }
      }
    }
  }

  private LinkedBSTNode<T> rebalance(LinkedBSTNode<T> node) {
    // Due to the AVL invariant, height difference should be at most 1
    final int deltaHeight = LinkedBST.getHeight(node.getLeft()) -
        LinkedBST.getHeight(node.getRight());

    if (deltaHeight == 2) {
      // Unbalanced to the left, so has a left subtree
      if (getHeight(node.getLeft().getLeft()) >= getHeight(node.getLeft().getRight())) {
        // Addition was in the left subtree of left child, do a right rotation
        return BalanceUtils.rightRotation(node);
      } else {
        return BalanceUtils.leftRightRotation(node);
      }
    } else if (deltaHeight == -2) {
      // Unbalanced to the right so has a right subtree
      if (getHeight(node.getRight().getRight()) >= getHeight(node.getRight().getLeft())) {
        return BalanceUtils.leftRotation(node);
      } else {
        return BalanceUtils.rightLeftRotation(node);
      }
    }

    return node;
  }
}
