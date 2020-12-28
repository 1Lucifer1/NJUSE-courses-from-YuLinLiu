package LinkedLIst;

public class LinkedList {
	private ListNode header;
	public LinkedList(){
		header=new ListNode(null);
	}
	//查看当前链表末尾
	public LinkedListItr now(){
		LinkedListItr itr=new LinkedListItr(header);
		while(itr.current.next!=null) itr.advance();
		return itr;
	}
	//在当前链表末尾链接一个链表/节点
	public void link(LinkedListItr itr){
		this.now().current.next=itr.current;
	}
	//打印literal链表
	public void printLiteral(){
		LinkedListItr itr=this.first();
		while(!itr.isEnd()){
			Literal literal=(Literal)itr.retrieve();
			System.out.print(literal.getExponent()+" ");
			System.out.println(literal.getCoefficient());
			itr.advance();
		}
	}
	public boolean isEmpty(){
		return header.next==null;
	}
	public void makeEmpty(){
		header.next=null;
	}
	public LinkedListItr zeroth(){
		return new LinkedListItr(header);
	}
	public LinkedListItr first(){
		return new LinkedListItr(header.next);
	}
	public LinkedListItr find(Object x){
		ListNode itr=header.next;
		while(itr!=null&&!itr.element.equals(x)){
			itr=itr.next;
		}
		return new LinkedListItr(itr);
	}

	public void remove(Object x){
		LinkedListItr p=findprevious(x);
		if (p.current.next!=null)
			p.current.next=p.current.next.next;
	}
	public LinkedListItr findprevious(Object x){
		ListNode itr=header;
		while(itr.next!=null&&itr.next.element.equals(x))
			itr=itr.next;
		return new LinkedListItr(itr);
	}
	public void insert(Object x,LinkedListItr p){
		if (p!=null&&p.current!=null)
			p.current.next=new ListNode(x,p.current.next);
	}
	
}



