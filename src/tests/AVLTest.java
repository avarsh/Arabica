package tests;

import BST.AVL;
import org.junit.Test;
import static org.junit.Assert.*;

public class AVLTest {

  @Test
  public void insertion() {
    int[] elements = {1, 2, 3, 4, 5, 6, 7};
    AVL<Integer> tree = new AVL<Integer>();
    for (int i : elements) {
      tree.add(i);
      assertTrue(tree.isBalanced());
    }

    System.out.println(tree);
    int expected = (int)(Math.floor(Math.log(elements.length)/Math.log(2)));
    assertEquals(expected, tree.getHeight());
  }
}
