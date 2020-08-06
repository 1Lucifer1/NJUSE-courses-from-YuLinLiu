public class Main {

    public static final int MAX = 100;
    static char[] stack = new char[MAX];
    static String[] stack1 = new String[MAX];
    private static int point = 0;

    public static void main(String[] args){
        start("(8*1-(5/5))");
    }

    public static void InfixtoPostfix(String string){

        String str = "";
        for (int i=0; i<string.length() ;i++){
            if (string.charAt(i)=='+'||string.charAt(i)=='-'||string.charAt(i)=='*'
                    ||string.charAt(i)=='/'||string.charAt(i)=='('||string.charAt(i)==')'){
                String temp = pushOrPop(string.charAt(i));
                if (temp!=null&&temp!="") str = str + temp;
            }else{
                str = str + string.charAt(i);
            }
        }
        for (int i=point-1; i>=0; i--){
            str = str + stack[i];
            point--;
        }
        System.out.println("Infix to Postfix :" + str);
        postEvaluation(str);
    }

    public static void postEvaluation(String string){

        for (int i=0; i<string.length(); i++){
            if (string.charAt(i)!='+'&&string.charAt(i)!='-'&&string.charAt(i)!='*'&&string.charAt(i)!='/'){
                point++;
                stack1[point-1] = String.valueOf(string.charAt(i));
            }else{
                if (string.charAt(i)=='*') {
                    int temp = Integer.parseInt(stack1[point-2]) * Integer.parseInt(stack1[point-1]);
                    stack1[point-2] = String.valueOf(temp);
                    point--;
                }else if (string.charAt(i)=='/'){
                    int temp = Integer.parseInt(stack1[point-2]) / Integer.parseInt(stack1[point-1]);
                    stack1[point-2] = String.valueOf(temp);
                    point--;
                }else if (string.charAt(i)=='+'){
                    int temp = Integer.parseInt(stack1[point-2]) + Integer.parseInt(stack1[point-1]);
                    stack1[point-2] = String.valueOf(temp);
                    point--;
                }else{
                    int temp = Integer.parseInt(stack1[point-2]) - Integer.parseInt(stack1[point-1]);
                    stack1[point-2] = String.valueOf(temp);
                    point--;
                }
            }
        }

        System.out.println("Postfix Evaluation :"+Integer.parseInt(stack1[0]));

    }

    public static void start(String string){
        InfixtoPostfix(string);
    }

    public static String pushOrPop(char ch){
        String result = "";
        if (ch=='-'||ch=='+') {
            while (true){
                if ((point != 0)&&(stack[point-1]=='*'||stack[point-1]=='/'
                        ||stack[point-1]=='+' ||stack[point-1]=='-')){
                    result = result + stack[point-1];
                    point--;
                }else{
                    point++;
                    stack[point-1] = ch;
                    break;
                }
            }
        }else if (ch=='*'||ch=='/'){
            while (true){
                if ((point != 0)&&(stack[point-1]=='*'||stack[point-1]=='/')){
                    result = result + stack[point-1];
                    point--;
                }else{
                    point++;
                    stack[point-1] = ch;
                    break;
                }
            }
        }else if (ch=='('){
            stack[point] = ch;
            point++;
            return null;
        }else{
            while (true){
                if (stack[point-1]!='(') {
                    result = result + stack[point-1];
                    point--;
                }else if (point==0){
                    //through
                    break;
                }else{
                    //pop '('
                    point--;
                    break;
                }
            }
        }
        return result;
    }
}

