package ccf;
import java.util.*;
/**
*@author ZhengYuTiing
*@date 创建时间：2022年10月20日 下午2:14:57
*/
public class Hearthstone {
	
	static class role{
		int H;
		int A;
		role(int h,int a){
			H=h;
			A=a;
		}
	}
	static class player{
		ArrayList<role> mylist=new ArrayList<role>();
		player(){
			role hero=new role(30,0);
			mylist.add(hero);
		}
	}
	static public void main(String [] args) {
		Scanner sc=new Scanner(System.in);
		player p1=new player();
		player p2=new player();
		int n=sc.nextInt();
		int end=0;
		for(int i=0;i<n;i++) {
			if(end%2==0) {     //先手
				String op=sc.next();
				if(op.equals("summon")) {
				int p=sc.nextInt();
				int a=sc.nextInt();
				int h=sc.nextInt();
				summon(p1,p,h,a);
				}
				if(op.equals("attack")) {
				int a=sc.nextInt();
				int d=sc.nextInt();
				attack(p1,p2,a,d);
				}
				if(op.equals("end")) {
					end++;
					continue;
				}
			}
			if(end%2==1)		//后手{
			{	String op=sc.next();
				if(op.equals("summon")) {
					int p=sc.nextInt();
					int a=sc.nextInt();
					int h=sc.nextInt();
					summon(p2,p,h,a);
				}
				if(op.equals("attack")) {
					int a=sc.nextInt();
					int d=sc.nextInt();
					attack(p2,p1,a,d);
				}
				if(op.equals("end")) {
					end++;
					continue;
				}
			}
		}
		if(p1.mylist.get(0).H<=0&&p2.mylist.get(0).H>0) {
			System.out.println(-1);
		}
		else if(p1.mylist.get(0).H>0&&p2.mylist.get(0).H<=0){
			System.out.println(1);
		}
		else {
			System.out.println(0);
		}
		display(p1);
		display(p2);
		
	}
	static void display(player p) {
		System.out.println(p.mylist.get(0).H);
		int sum=p.mylist.size()-1;
		System.out.print(sum+" ");
		for(int i=1;i<p.mylist.size();i++) {
			System.out.print(p.mylist.get(i).H+" ");
		}
		System.out.println();
	}
	static void summon(player player,int p,int h,int a) {
		role temp=new role(h,a);
		if(p>player.mylist.size()) 
		{
			player.mylist.add( temp);
		}
		else{
			player.mylist.add(p, temp);
		}
	}
	static void attack(player p1,player p2,int a,int d) {
		
		p1.mylist.get(a).H-=p2.mylist.get(d).A;
		p2.mylist.get(d).H-=p1.mylist.get(a).A;
		if(p1.mylist.get(a).H<=0&&a!=0) {
			p1.mylist.remove(a);
		}
		if(p2.mylist.get(d).H<=0&&d!=0) {
			p2.mylist.remove(d);
		}
	}
	
}
