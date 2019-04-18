package examples;

import BST.LinkedBST;

public class LinkedExample {

  private static LinkedBST<Integer> buildTree(int[] elements) {
    LinkedBST<Integer> tree = new LinkedBST<Integer>();
    for (int i = 0; i < elements.length; i++) {
      tree.add(elements[i]);
    }

    return tree;
  }

  public static void main(String[] args) {
    int[] elements = {6, 5, 10, 4, 9, 11};
    //int[] elements = {4, 5, 6, 9, 10, 11};
    LinkedBST<Integer> tree = buildTree(elements);

    System.out.println(tree);

    System.out.println("4 is in the tree: " + tree.contains(4));
    System.out.println("8 is in the tree: " + tree.contains(8));

    tree.add(8);

    System.out.println(tree);
    System.out.println("8 is in the tree: " + tree.contains(8));

    tree.remove(8);
    System.out.println(tree);
    tree.remove(10);
    System.out.println(tree);
    tree.remove(6);
    System.out.println(tree);
  }
}
