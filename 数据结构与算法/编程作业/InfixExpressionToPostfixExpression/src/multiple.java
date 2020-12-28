import LinkedLIst.LinkedList;
import LinkedLIst.LinkedListItr;
import LinkedLIst.Literal;

import java.util.Scanner;
public class multiple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input=new Scanner(System.in);
		String a=input.nextLine();
		String b=input.nextLine();
		LinkedList list1=set(a);
		LinkedList list2=set(b);
		LinkedList res=add(list1,list2);
		res.printLiteral();
	}
	public static LinkedList set(String str){
		LinkedList list=new LinkedList();
		int p=1;//p是指针，指向一个int最后方
		int r=1;//r也是指针
		int judge=1;//judge判断数字是coef还是exp，若是1则为coef，0为exp
		int coef=0;
		int exp=0;
		while(p<=str.length()-1){
			if((p!=str.length()-1)&&(str.charAt(p)=='+'||str.charAt(p)=='-'||str.charAt(p)=='x')) {
				int min = 1;
				if (str.charAt(r-1) == '-') min = -1;
				if (p != str.length() - 1 && judge == 1) {
					coef = min * Integer.parseInt(str.substring(r, p));
					judge = 0;
				} else if (p != str.length() - 1 && judge == 0) {
					exp =  Integer.parseInt(str.substring(r, p));
					judge = 1;
				}
				r = p + 1;//重定向r指针
				if (judge == 1) {
					Literal data = new Literal(coef, exp);
					list.insert(data,list.now());
				}
			}else if (p==str.length()-1){
				exp = Integer.parseInt(str.substring(r));
				judge = 1;
				Literal data = new Literal(coef, exp);
				list.insert(data,list.now());
			}
			p++;
		}
		return list;
	}
//	public void insertTerm(LinkedLIst.Literal data){
//
//	}
	public static LinkedList add(LinkedList a, LinkedList b){
		LinkedList res=new LinkedList();
		LinkedListItr current_a=a.first();
		LinkedListItr current_b=b.first();
		while ((!current_a.isEnd())&&(!current_b.isEnd())){
			Literal data_a=(Literal) current_a.retrieve();
			Literal data_b=(Literal) current_b.retrieve();
			if (data_a.getExponent()==data_b.getExponent()){
				if (data_a.getCoefficient()+data_b.getCoefficient()!=0)
				res.insert(new Literal(data_a.getCoefficient()+data_b.getCoefficient(),data_a.getExponent()),res.now());
				current_a.advance();
				current_b.advance();
			}else if (data_a.getExponent()>data_b.getExponent()){
				res.insert(new Literal(data_a.getCoefficient(),data_a.getExponent()),res.now());
				current_a.advance();
			}else{
				res.insert(new Literal(data_b.getCoefficient(),data_b.getExponent()),res.now());
				current_b.advance();
			}
		}
		if (current_a.isEnd()){
			res.link(current_b);
		}else if (current_b.isEnd()){
			res.link(current_a);
		}
		return res;
	}


}

