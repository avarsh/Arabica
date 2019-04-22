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
            uncle = (RedBlackNode<T>)grandparent.getLeft();
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
    LinkedBSTNode<T> parent = null;
    LinkedBSTNode<T> current = root;

    // The removed node is always a leaf or has one child
    LinkedBSTNode<T> removed = null;
    LinkedBSTNode<T> replacement = null;

    while (current != null) {
      int comparison = current.getData().compareTo(data);
      if (comparison == 0) {
        if (current.getLeft() == null && current.getRight() == null) {
          if (parent == null) {
            removed = root;
            root = null;
          } else {
            removed = current;
            if (current.isLeftChild()) {
              parent.setLeft(null);
            } else {
              parent.setRight(null);
            }
          }
        } else if (current.getLeft() == null) {
          replacement = current.getRight();
          if (parent == null) {
            removed = root;
            root = current.getRight();
          } else {
            removed = current;
            if (current.isLeftChild()) {
              parent.setLeft(replacement);
            } else {
              parent.setRight(replacement);
            }
          }
        } else if (current.getRight() == null) {
          replacement = current.getLeft();
          if (parent == null) {
            removed = root;
            root = current.getLeft();
          } else {
            removed = current;
            if (current.isLeftChild()) {
              parent.setLeft(replacement);
            } else {
              parent.setRight(replacement);
            }
          }
        } else {
          LinkedBSTNode<T> minimumParent = current;
          LinkedBSTNode<T> minimum = current.getRight();
          while (minimum.getLeft() != null) {
            minimumParent = minimum;
            minimum = minimum.getLeft();
          }

          removed = minimum;
          replacement = minimum.getRight();
          if (minimum == current.getRight()) {
            current.setRight(replacement);
          } else {
            minimumParent.setLeft(replacement);
          }

          current.setData(minimum.getData());
        }
      } else if (comparison > 0) {
        parent = current;
        current = current.getLeft();
      } else {
        parent = current;
        current = current.getRight();
      }
    }

    if (removed == null) return false;

    RedBlackNode<T> removedColoured = (RedBlackNode<T>)removed;
    RedBlackNode<T> replacementColoured = (RedBlackNode<T>)replacement;

    Colour removedCol = removedColoured.getColour();
    // Null nodes are treated as black
    Colour replacedCol = replacement == null ?
        Colour.BLACK : replacementColoured.getColour();

    if (removedCol == Colour.RED || replacedCol == Colour.RED) {
      if (replacementColoured != null) {
        replacementColoured.setColour(Colour.BLACK);
      }
    } else if (removedCol == Colour.BLACK  && replacedCol == Colour.BLACK) {
      Colour currentCol = Colour.DOUBLEBLACK;

      RedBlackNode<T> currentRB = replacementColoured;

      while (currentCol == Colour.DOUBLEBLACK && currentRB != root) {
        // Parent shouldn't be null
        RedBlackNode<T> sibling;
        if (parent.getLeft() == currentRB) {
          sibling = (RedBlackNode<T>)parent.getRight();
        } else {
          sibling = (RedBlackNode<T>)parent.getLeft();
        }

        if (getColour(sibling) == Colour.BLACK) {
          if (sibling != null) {
            if (getColour(sibling.getLeft()) == Colour.RED) {
              if (sibling.isLeftChild()) {

              }
            } else if (getColour(sibling.getRight()) == Colour.RED) {

            }
          } else {

          }
        }
      }

    }

    return true;
  }

  private Colour getColour(LinkedBSTNode<T> node) {
    if (node == null) return Colour.BLACK;

    return ((RedBlackNode<T>)node).getColour();
  }
}
