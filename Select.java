package ccf;
import java.util.*;
/**
*@author ZhengYuTiing
*/
public class Select {
    public static class Node{
        String name;
        String id;
        int level;
        public Node(String name, String id, int level) {
            this.name = name;
            this.id = id;
            this.level = level;
        }
    }
    static int n;
    static Node[] tree = new Node[105];
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();
        for(int i = 0; i < n ; i++) {
            String str = scanner.nextLine();
            int j = 0;
            while(str.charAt(j)=='.') {
            	j++;
            }
            int level = j/2;
            str = str.substring(j);
            String[] split = str.split(" ");
            if(split.length!=1) {//有ID
                tree[i] = new Node(split[0].toLowerCase(), split[1], level);
            }else {
                tree[i] = new Node(split[0].toLowerCase(), null, level);
            }
        }
        while(m-->0) {
            String qs = scanner.nextLine();
            process(qs);
        }
    }
    private static void process(String qs) {
        String[] split = qs.split(" ");
        for(int i = 0 ; i < split.length;i++) {//变小写
            if(!split[i].contains("#")) {
            	split[i] = split[i].toLowerCase();
            }
        }
        int k = split.length,s = 0;
        ArrayList<Integer> list = new ArrayList<>();
        find(s,k,-1,split,0,list);
        Collections.sort(list);
        int last  = -1,count = 0;
        String res = "";
        for(int i = 0 ; i < list.size() ; i++) {//去重+按序输出
            int a = list.get(i);
            if(last!=a) {
                res = res+ " "+a;
                count++;
                last = a;
            }
        }
        System.out.println(count+res);
    }
    //s~~k 去匹配 l~~n
    private static void find(int s, int k,int level,String[] q, int l, ArrayList<Integer> list) {
        if(s==k) {
            list.add(l);//匹配成功
            return;
        }
        if(l==n) {
        	return;//匹配到最后都没成功
        }
        for(int i = l; i < n ; i++) {
            if(level >= tree[i].level) {
            	return;//越级，匹配失败
            }
            if( q[s].equals(tree[i].name)||q[s].equals(tree[i].id)) {
                find(s+1,k,tree[i].level,q,i+1,list);
            }
        }
    }
}