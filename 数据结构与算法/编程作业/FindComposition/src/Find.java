import java.util.Scanner;

public class Find {
    public static void main(String[] args){
        System.out.print("Enter n:");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.print("Enter r:");
        int r = scanner.nextInt();
        count(n,r,new int[r]);
    }

    private static void count(int n, int r,int[] out){
        if(r == 1){
            for(int i = n;i>=1;i--){
                for(int j = 0;j<out.length-1;j++){
                    System.out.print(out[j]);
                    System.out.print(" ");
                }
                System.out.print(i);
                System.out.println();
            }
        }
        else{
            for(int i = n; i>=r;i--) {
                out[out.length - r] = i;
                count(i - 1, r - 1, out);
            }
        }
    }
}
