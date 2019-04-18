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

        // Case 2: The parent node is not red
        if (path.get(path.size() - 1).getColour() == Colour.BLACK) {
          return true;
        } else {

        }
      }
    }
  }

  @Override
  public boolean remove(T data) {
    return false;
  }
}
