package tests;

import BST.LinkedBST;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinkedBSTTest {

  private static LinkedBST<Integer> buildTree(int[] elements) {
    LinkedBST<Integer> tree = new LinkedBST<Integer>();
    for (int i = 0; i < elements.length; i++) {
      tree.add(elements[i]);
    }

    return tree;
  }

  @Test
  public void insertion() {
    int[] elements = {4, 2, 10, 22, -1, 19, 107, 3};
    var tree = buildTree(elements);
    for (int item : elements) {
      assertTrue(tree.contains(item));
    }
  }

  @Test
  public void removal() {
    Random generator = new Random(); //982429841
    final int SIZE = 100;
    final int MAX = 1000;
    List<Integer> elements = new ArrayList<>(SIZE);
    LinkedBST<Integer> tree = new LinkedBST<Integer>();
    for (int i = 0; i < SIZE; i++) {
      int number = generator.nextInt(MAX);

      while (elements.contains(number)) {
        number = generator.nextInt(MAX);
      }

      elements.add(number);
      tree.add(number);
    }

    for (int item : elements) {
      assertTrue(tree.contains(item));
    }

    final int MODIFICATIONS = SIZE - 1;
    for (int i = 0; i < MODIFICATIONS; i++) {
      int index = generator.nextInt(elements.size());
      int data = elements.get(index);
      elements.remove(index);
      tree.remove(data);

      assertFalse(tree.contains(data));

      for (int item : elements) {
        assertTrue(tree.contains(item));
      }
    }
  }

  @Test
  public void heights() {
    int[] e1 = {2, 1, 3};
    assertEquals(1, buildTree(e1).getHeight());

    int[] e2 = {1, 2, 3, 4};
    assertEquals(3, buildTree(e2).getHeight());
  }
}
