public class PolynomialLinkedList {

    private int length;
    private PolynomialNode head;
    public PolynomialLinkedList(PolynomialNode head){
        this.head = head;
        length = 1;
    }

    public PolynomialNode head() { return head; }

    public void print(){
        PolynomialNode next = head.getNext();
        System.out.printf("%dx^%d ",head.getNum(),head.getDegree());
        while (next!=null){
            System.out.printf("%dx^%d ",next.getNum(),next.getDegree());
            next = next.getNext();
        }
    }
    public int length(){
        return length;
    }
    public boolean isEmpty(){
        if(length==0){
            return true;
        }else{
            return false;
        }
    }
    public boolean add(PolynomialNode node){
        PolynomialNode after = head.getNext();
        PolynomialNode before = head;
        if(before.getDegree()<=node.getDegree()){
            node.setNext(head);
            head = node;
            length++;
            return true;
        }
        while(true){
            if(after==null){
                before.setNext(node);
                length++;
                return true;
            }else if((after.getDegree()<=node.getDegree())&&(before.getDegree()>=node.getDegree())){
                break;
            }
            after = after.getNext();
            before = before.getNext();
        }
        before.setNext(node);
        node.setNext(after);
        length++;
        return true;
    }
    public boolean delete(PolynomialNode node){
        PolynomialNode tem = head;
        if(node.equals(head)){
            head = head.getNext();
            length--;
            return true;
        }
        while(true){
            if(tem.getNext().equals(node)){
                break;
            }else if(tem.getNext()==null){
                return false;
            }
            tem = tem.getNext();
        }
        tem.setNext(tem.getNext().getNext());
        return true;
    }
    public PolynomialNode find(int x){
        PolynomialNode tem = head;
        for(int n = 1;n < x;n++){
            tem = tem.getNext();
            if(tem == null){
                return null;
            }
        }
        return tem;
    }

}
