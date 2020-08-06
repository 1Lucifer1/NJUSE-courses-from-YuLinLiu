public class Polynomial {
    public static void main(String[] args){
        PolynomialLinkedList a= new PolynomialLinkedList(new PolynomialNode(1,1));
        a.add(new PolynomialNode(2,50));
        a.add(new PolynomialNode(2,100));


        PolynomialLinkedList b= new PolynomialLinkedList(new PolynomialNode(2,1));
        b.add(new PolynomialNode(1,49));
        b.add(new PolynomialNode(3,2));

        PolynomialLinkedList c = add(a,b);
        c.print();
    }


    public static PolynomialLinkedList add(PolynomialLinkedList a,PolynomialLinkedList b){
        PolynomialLinkedList c;
        if(a.head().getDegree()>b.head().getDegree()){
            c = new PolynomialLinkedList(new PolynomialNode(a.head().getNum(),a.head().getDegree()));
            a.delete(a.head());
        }else if(b.head().getDegree()>a.head().getDegree()){
            c = new PolynomialLinkedList(new PolynomialNode(b.head().getNum(),b.head().getDegree()));
            b.delete(b.head());
        }else{
            c = new PolynomialLinkedList(PolynomialNode.add(new PolynomialNode(a.head().getNum(),a.head().getDegree())
                            ,new PolynomialNode(b.head().getNum(),b.head().getDegree())));
            a.delete(a.head());
            b.delete(b.head());
        }
        while(true){
            if(a.head() == null && b.head() == null){
                break;
            }else if(a.head()==null){
                c.add(new PolynomialNode(b.head().getNum(),b.head().getDegree()));
                b.delete(b.head());
                continue;
            }else if(b.head()==null){
                c.add(new PolynomialNode(a.head().getNum(),a.head().getDegree()));
                a.delete(a.head());
                continue;
            }

            if(a.head().getDegree()>b.head().getDegree()){
                c.add(new PolynomialNode(a.head().getNum(),a.head().getDegree()));
                a.delete(a.head());
            }else if(b.head().getDegree()>a.head().getDegree()){
                c.add(new PolynomialNode(b.head().getNum(),b.head().getDegree()));
                b.delete(b.head());
            }else{
                c.add(PolynomialNode.add(new PolynomialNode(a.head().getNum()
                        ,a.head().getDegree()),new PolynomialNode(b.head().getNum(),b.head().getDegree())));
                a.delete(a.head());
                b.delete(b.head());
            }
        }
        return c;
    }
}
