import java.io.*;
import java.util.*;

public class Main {
    public static final String filename = "src" + File.separator + "Node.txt";

    private static Map<String,ArrayList<String>> Graph = new HashMap<>();

    static ArrayList<String> POINTS = new ArrayList<>();

    private static ArrayList<String> NodeList = new ArrayList<>();  //遍历的结果

    public static void main(String[] args){
        initGraph();

        //每个point有没有环
        for (int i=0; i<POINTS.size(); i++){
            startCycle(POINTS.get(i));
            NodeList.clear();
        }
    }

    /**
     * 根据filename指向的文件来创建图（以Map形式，顶点存在point里）。文件里，每个顶点之间的关系表达的形式：1,2\n
     */
    public static void initGraph(){

        try{
            File file = new File(filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                String[] temp = line.split(",");

                //判断此关系有没有新顶点
                if (!POINTS.contains(temp[0])){
                    POINTS.add(temp[0]);
                }
                if (!POINTS.contains(temp[1])){
                    POINTS.add(temp[1]);
                }

                //Map的value存放key的有向边指向的顶点
                if (Graph.containsKey(temp[0])){
                    ArrayList<String> A = Graph.get(temp[0]);  //key相同=存放从相同顶点出发地有向边
                    A.add(temp[1]);
                    Graph.put(temp[0],A);
                }
                else {
                    ArrayList<String> l = new ArrayList<>();
                    l.add(temp[1]);
                    Graph.put(temp[0],l);
                }
            }
            bufferedReader.close();
        }catch (Exception e){
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static void startCycle(String Node) {
        NodeList.add(Node);
        ArrayList<String> next = Graph.get(Node);

        //没有出度的顶点
        if (next==null) {
            NodeList.remove(NodeList.size()-1);
            return ;
        }

        //遍历所有由node出去的顶点
        for (int i = 0; i < next.size(); i++) {
            if (Node.equals(NodeList.get(0))&&NodeList.size()!=1) {
                System.out.println(NodeList.toString());
                NodeList.remove(NodeList.size()-1);
                break;
            } else if (NodeList.indexOf(Node)!=NodeList.size()-1&&NodeList.indexOf(Node)!=-1){
                NodeList.remove(NodeList.size()-1);
                break;
            } else{
                startCycle(next.get(i));
            }

            if (i==next.size()-1) {
                NodeList.remove(NodeList.size()-1);
                break;
            }
        }
    }


}
