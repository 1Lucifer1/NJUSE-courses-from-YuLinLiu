package Stack;

import LinkedLIst.ListNode;

import java.nio.BufferUnderflowException;

public class StackLi {
    private ListNode topOfStack;
    public StackLi (){
        topOfStack=null;
    }
    public boolean isFull(){
        return false;
    }
    public boolean isEmpty(){
        return topOfStack==null;
    }
    public void push(Object x){
        topOfStack=new ListNode(x,topOfStack);
    }
    public Object top(){
        if (isEmpty()) return null;
        else return topOfStack.getElement();
    }
    public void pop()throws BufferUnderflowException{
        topOfStack=topOfStack.getNext();
    }
    public Object topAndPop(){
        if (isEmpty()) return null;
        Object topItem = topOfStack.getElement();
        topOfStack=topOfStack.getNext();
        return topItem;
    }
}
