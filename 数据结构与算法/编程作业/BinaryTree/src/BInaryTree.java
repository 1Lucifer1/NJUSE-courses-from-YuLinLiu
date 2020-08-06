import java.io.*;

public class BInaryTree {

    private static int cnt = 1;

    private static final String TREE_PATH = "src" + File.separator + "tree.txt";

    public static void main(String[] args) throws Exception {
        FileReader fileReader = new FileReader(new File(TREE_PATH));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String list = bufferedReader.readLine();
        Node root = new Node(list.charAt(0));

        createBinaryTree(root,list);

        preOrder(root);
        System.out.println();
        inOrder(root);
        System.out.println();
        postOrder(root);
        System.out.println();
    }


    /**
     * 根据输入的广义表做递归创建树
     * @param node
     * @param list
     */
    private static void createBinaryTree(Node node, String list){
        while(cnt < list.length()){
            switch (list.charAt(cnt)){
                case '(':
                    cnt++;
                    if(list.charAt(cnt)==','){
                        createBinaryTree(node,list);
                    }else{
                        node.setLeft(new Node(list.charAt(cnt)));
                        cnt++;
                        createBinaryTree(node.getLeft(),list);
                    }
                    break;
                case  ')':
                    cnt++;
                    return;
                case ',':
                    cnt++;
                    if(list.charAt(cnt)==')'){
                        createBinaryTree(node,list);
                    }else{
                        return;
                    }
                    break;
                default:
                    node.setRight(new Node(list.charAt(cnt)));
                    cnt++;
                    createBinaryTree(node.getRight(),list);
            }
        }
    }

    /**
     * 先序遍历
     * @param node
     */
    private static void preOrder(Node node) {
        if(node==null){
            return;
        }
        System.out.print(node.getElement());
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    /**
     * 中序遍历
     * @param node
     */
    private static void inOrder(Node node) {
        if(node==null){
            return;
        }
        inOrder(node.getLeft());
        System.out.print(node.getElement());
        inOrder(node.getRight());

    }

    /**
     * 后序遍历
     * @param node
     */
    private static void postOrder(Node node) {
        if(node==null){
            return;
        }
        postOrder(node.getLeft());
        postOrder(node.getRight());
        System.out.print(node.getElement());

    }

}
