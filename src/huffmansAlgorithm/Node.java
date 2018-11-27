package huffmansAlgorithm;

public class Node {
    private int value;
    private int weight;
    private Node left;
    private Node right;

    public Node(int value, int weight, Node left, Node right) {
        this.value = value;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", weight=" + weight +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
