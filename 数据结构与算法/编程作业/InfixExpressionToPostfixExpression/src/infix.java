import Stack.StackLi;
import java.util.Scanner;
import java.util.regex.*;

public class infix {
    public static void main(String[] args){

        //用例：(8*1-(5/5))
        //思路：遇到运算分量存入s栈；遇到运算符则比较比较当前运算符与ch栈栈顶运算符
        Scanner input=new Scanner(System.in);
        String cal=input.nextLine();
        calculate(cal);
    }
    public static void calculate(String cal){
        StackLi s=new StackLi();//s用于存储运算分量
        StackLi ch=new StackLi();//ch用于存储运算符
        ch.push('#');

        for (int i=0;i<cal.length();i++){
            char temp=cal.charAt(i);

            //遇到数字放入s栈
            if (Pattern.matches("[0-9]",String.valueOf(temp))){
                int j=i;
                while(Pattern.matches("[0-9]",String.valueOf(cal.charAt(i)))||cal.charAt(i)=='.'){
                    i++;
                }
                String num=cal.substring(j,i);
                s.push(Double.parseDouble(num));
                i--;
            }else if (temp==')'){

                //遇到）寻找（，中间的进行运算
                char y;
                for (y = (char) ch.topAndPop(); y != '('; y = (char) ch.topAndPop()) {
                    real_cal(y,s);
                }
            }else if (temp!='#'){

                //比较栈顶和当前变量的优先级,若栈顶变量优先级>=当前变量，则不断对栈顶变量取出运算
                char y;
                for (y= (char) ch.topAndPop(); isp(y)>=icp(temp); y= (char) ch.topAndPop()){
                    real_cal(y,s);
                }
                ch.push(y);
                ch.push(temp);
            }else{
                while(!ch.isEmpty()){
                    char y;
                    y= (char) ch.topAndPop();
                    real_cal(y,s);
                }
            }
        }
        System.out.println(s.top());
    }

    public static int isp(char a){
        if (a=='+'||a=='-') return 1;
        else if (a=='*'||a=='/'||a=='%') return 2;
        else if (a=='^') return 3;
        else if (a=='#') return -1;
        else return 0;
    }

    public static int icp(char a){
        if (a=='+'||a=='-') return 1;
        else if (a=='*'||a=='/'||a=='%') return 2;
        else if (a=='^') return 3;
        else if (a=='#') return -1;
        else return 4;
    }

    public static void real_cal(char y,StackLi s) {
            double p, q;
            switch (y) {
                case '+':
                    p = (double) s.topAndPop();
                    q = (double) s.topAndPop();
                    s.push(p + q);
                    break;
                case '-':
                    p = (double) s.topAndPop();
                    q = (double) s.topAndPop();
                    s.push(q - p);
                    break;
                case '*':
                    p = (double) s.topAndPop();
                    q = (double) s.topAndPop();
                    s.push(p * q);
                    break;
                case '/':
                    p = (double) s.topAndPop();
                    q = (double) s.topAndPop();
                    s.push(q / p);
                    break;
                case '%':
                    p = (double) s.topAndPop();
                    q = (double) s.topAndPop();
                    s.push(q % p);
                    break;
                case '^':
                    p = (double) s.topAndPop();
                    q = (double) s.topAndPop();
                    s.push(Math.pow(q, p));
                    break;
            }
    }
}
