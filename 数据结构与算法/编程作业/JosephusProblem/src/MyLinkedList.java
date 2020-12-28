class ListNode {
    ListNode (int element){
        this.element = element;
    }
    int element;
    ListNode link;
}

public class MyLinkedList {
    int length;
    ListNode head;
    ListNode rear;

    public MyLinkedList(int c) {
        head = new ListNode(1);
        ListNode p = head;
        rear = head;
        length = 1;
        for (int i=2; i<=c; i++){
            p.link = new ListNode(i);
            p = p.link;
            length++;
        }
        p.link = head;
    }     // create list

    public int length() {
        return length;
    }

    public int getIndex(ListNode node) {
        ListNode current = head;
        int count = 0;
        while(current.element!=node.element) {
            current = current.link;
            count++;
        }
        return count;
    }
    public void remove() {
        rear.link = rear.link.link;
        rear =rear.link;
        length--;
    }

}