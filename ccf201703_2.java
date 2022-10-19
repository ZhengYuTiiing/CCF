package ccf;
import java.util.*;
public class ccf201703_2 {
	public static void main(String [] args) {
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int []a=new int [n+1];
		for(int i=1;i<=n;i++) {
			a[i]=i;
		}
		int m=sc.nextInt();
		while((m--)!=0) {
			int p=sc.nextInt();
			int q=sc.nextInt();
			int index=0;
			for(int i=1;i<=n;i++) {
				if(a[i]==p) {
					index=i;
					break;
				}
			}
			if(q>0) {
				for(int i=index;i<q+index;i++) {
					a[i]=a[i+1];
				}
				a[index+q]=p;
			}
			if(q<0) {
				for(int i=index;i>index+q;i--) {
					a[i]=a[i-1];
				}
				a[index+q]=p;
		}
		}
		for(int i=1;i<=n;i++) {
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
}
