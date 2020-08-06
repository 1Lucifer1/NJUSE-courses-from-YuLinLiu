public class PolynomialNode {
    private PolynomialNode next;
    private int num;
    private int degree;

    public PolynomialNode(int num, int degree){
        this.num = num;
        this.degree = degree;
        this.next = null;
    }

    public void setDegree(int degree) { this.degree = degree; }
    public int getDegree() { return degree; }

    public void setNum(int num) { this.num = num; }
    public int getNum() { return num; }

    public void setNext(PolynomialNode next) { this.next = next; }
    public PolynomialNode getNext() { return next; }

    public static PolynomialNode add(PolynomialNode a,PolynomialNode b){
        PolynomialNode c =new PolynomialNode(a.num+b.num,a.degree);
        return c;
    }
}
