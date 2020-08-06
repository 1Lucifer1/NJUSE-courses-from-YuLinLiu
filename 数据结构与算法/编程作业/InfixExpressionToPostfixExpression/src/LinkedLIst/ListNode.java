package LinkedLIst;

public class ListNode{
    Object element;
    ListNode next;
    ListNode(Object Element){
        this(Element,null);
    }
    public ListNode(Object Element, ListNode n){
        element=Element;
        next=n;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public Object getElement() {
        return element;
    }
}
