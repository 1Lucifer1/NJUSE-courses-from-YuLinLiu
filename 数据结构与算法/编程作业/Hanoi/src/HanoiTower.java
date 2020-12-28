import java.util.Scanner;

public class HanoiTower {
    public static void main(String[] args){
        System.out.println( "Enter number of disks: ");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        System.out.println( "The move are: ");
        moveDISKs(n, 'A','B','C');
    }

    private static void moveDISKs(int n, char fromTower, char toTower, char supTower) {
        if ( n == 1){
            System.out.println( "move disk " + n + " from "+ fromTower +" to "+ toTower);
        }else {
            moveDISKs(n-1, fromTower, supTower, toTower);
            System.out.println( "move disk " + n + " from "+ fromTower +" to "+ toTower);
            moveDISKs(n-1, supTower, toTower, fromTower);
        }
    }
}
