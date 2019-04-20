package BST;

import java.util.List;
import java.util.stream.Collectors;

public class RedBlack<T extends Comparable<T>> extends LinkedBST<T> {

  @Override
  public boolean add(T data) {
    LinkedBSTNode<T> node = insertNode(new RedBlackNode<T>(data, Colour.RED));

    if (node == INVALID) return false;

    LinkedBSTNode<T> current = node;

    while (true) {

      if (current == root) {
        // Case 1: If the new node is the root, colour it red
        ((RedBlackNode) root).setColour(Colour.BLACK);
        return true;
      } else {

        List<RedBlackNode<T>> path = getPathToNode(current.getData())
            .stream().map(n -> (RedBlackNode<T>) n).collect(Collectors.toList());

        RedBlackNode<T> parent = path.get(path.size() - 2);

        if (parent.getColour() == Colour.BLACK) {
          // Case 2: The parent node is black
          return true;
        } else {
          // Case 3: If there is an uncle node:
          RedBlackNode<T> grandparent = path.get(path.size() - 3);
          RedBlackNode<T> uncle;
          if (parent.isLeftChild()) {
            uncle = (RedBlackNode<T>)grandparent.getRight();
          } else {
            assert(parent.isRightChild()); // Debugging only
            uncle = (RedBlackNode<T>)grandparent.getRight();
          }

          // If uncle node is red, then parent, uncle become black,
          // grandparent is red.
          if (uncle != null && uncle.getColour() == Colour.RED) {
            parent.setColour(Colour.BLACK);
            uncle.setColour(Colour.BLACK);
            grandparent.setColour(Colour.RED);
            current = grandparent;
          } else {
            // Case 4: Zigzag!
            if (parent.isLeftChild() && current.isRightChild()) {
              grandparent.setLeft(BalanceUtils.leftRotation(parent));
              current = grandparent.getLeft().getLeft();
            } else if (parent.isRightChild() && current.isLeftChild()) {
              grandparent.setRight(BalanceUtils.rightRotation(parent));
              current = grandparent.getRight().getRight();
            }

            // Case 5
            // Update the path - we may have rotated
            path = getPathToNode(current.getData())
                .stream().map(n -> (RedBlackNode<T>) n).collect(Collectors.toList());
            parent = path.get(path.size() - 2);
            grandparent = path.get(path.size() - 3);
            RedBlackNode<T> greatgrandparent = path.size() > 3 ?
                path.get(path.size() - 4) : null;
            boolean isLeft = grandparent.isLeftChild();
            parent.setColour(Colour.BLACK);
            grandparent.setColour(Colour.RED);
            if (current.isLeftChild()) {
              RedBlackNode<T> child = (RedBlackNode<T>)BalanceUtils.rightRotation(grandparent);
              if (greatgrandparent == null) {
                root = child;
              } else {
                if (isLeft) {
                  greatgrandparent.setLeft(child);
                } else {
                  greatgrandparent.setRight(child);
                }
              }
            } else {
              assert (current.isRightChild()); // Debugging purposes
              RedBlackNode<T> child = (RedBlackNode<T>)BalanceUtils.leftRotation(grandparent);
              if (greatgrandparent == null) {
                root = child;
              } else {
                if (isLeft) {
                  greatgrandparent.setLeft(child);
                } else {
                  greatgrandparent.setRight(child);
                }
              }
            }

            return true;
          }
        }
      }
    }
  }

  @Override
  public boolean remove(T data) {
    return false;
  }
}
