public class Node {
    private Node right;
    private Node left;
    private char element;

    public Node(char element){
        this.element = element;
    }

    public char getElement() {
        return element;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setElement(char element) {
        this.element = element;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
