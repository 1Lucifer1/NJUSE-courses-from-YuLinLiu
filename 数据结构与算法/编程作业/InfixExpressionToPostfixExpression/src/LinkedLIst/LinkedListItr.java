package LinkedLIst;

public class LinkedListItr{
    ListNode current;
    public LinkedListItr(ListNode node){
        current = node;
    }
    public boolean isEnd(){
        return current==null;
    }
    public Object retrieve(){
        return isEnd()?null:current.element;
    }
    public void advance(){
        if (!isEnd()){
            current=current.next;
        }
    }

    public ListNode getCurrent() {
        return current;
    }

    public void setCurrent(ListNode current) {
        this.current = current;
    }
}