package BST;

public class RedBlackNode<T extends Comparable<T>> extends LinkedBSTNode<T> {
  private Colour colour;

  public RedBlackNode(T data, Colour colour) {
    super(data);
    this.colour = colour;
  }

  public Colour getColour() {
    return colour;
  }

  public void setColour(Colour colour) {
    this.colour = colour;
  }
}
