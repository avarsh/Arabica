package BST;

public class BSTNode<T extends Comparable<T>> {
  protected T data;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
