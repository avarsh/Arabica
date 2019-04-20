package tests;

import BST.RedBlack;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedBlackTest {

  @Test
  public void insertion() {
    int[] elements = {12, 24, 1, 97, 72, 36, 4, 100,
        18, 5, 8, 73, 84, 108, 96};
    RedBlack<Integer> tree = new RedBlack<Integer>();
    for (int i : elements) {
      tree.add(i);
    }

    assertEquals("12 4 1 5 () 8 72 24 18 36 97 84 73 96 100 () 108",
        tree.toString().stripTrailing());
  }
}
