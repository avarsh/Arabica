package tests;

import BST.AVL;
import BST.BalanceUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    int expected = (int)(Math.floor(Math.log(elements.length)/Math.log(2)));
    assertEquals(expected, tree.getHeight());
  }

  @Test
  public void testCase1() {
    int[] elements = {60, 50, 80, 20, 55, 70, 90, 10, 40};
    AVL<Integer> tree = new AVL<Integer>();
    for (int i : elements) {
      tree.add(i);
    }

    assertEquals("60 50 20 10 40 55 80 70 90",
        tree.toString().stripTrailing());

    tree.add(35);

    assertEquals("60 40 20 10 35 50 () 55 80 70 90",
        tree.toString().stripTrailing());
  }

  @Test
  public void removal() {
    Random generator = new Random(982429841); //982429841
    final int SIZE = 100;
    final int MAX = 1000;
    List<Integer> elements = new ArrayList<>(SIZE);
    AVL<Integer> tree = new AVL<Integer>();
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

      assertTrue(tree.isBalanced());
    }
  }
}
