import java.util.*;

public class Josephus {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("input n:");
        int n = sc.nextInt();
        System.out.println("input m:");
        int m = sc.nextInt();
        josephueWithLinkedList(n,m);
        josephueWithList(n,m);
    }

    private static void josephueWithList(int n, int m) {
        int[] list= new int[n];
        for(int i = 0;i < n;i++){
            list[i] = i+1;
        }
        int w = m -1;
        while(n!=1){
            for(int j = w;j < n-1;j++){
                list[j] = list[j+1];
            }
            n--;
            w = w + m -1;
            while(w>=n){
                w = w - n;
            }
        }
        System.out.println(list[0]);
    }


    private static void josephueWithLinkedList(int n,int m) {
        MyLinkedList list = new MyLinkedList(n);

        while (list.length()!=1) {
            for (int i=1; i<=m-2; i++) {
                list.rear = list.rear.link;
            }
            list.remove();
        }
        System.out.println(list.rear.element);
    }

}
