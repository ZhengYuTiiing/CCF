package ccf;
import java.util.*;
/**
*@author ZhengYuTiing
*@date 创建时间：2022年10月31日 下午2:23:35
*/
public class FileSystem {
	static class FileInfo{
	public FileInfo(boolean isFile,long size) {
		this.isFile=isFile;
		if(!isFile) {
			child=new HashMap<>();
			limitAll=0;
			limitDir=0;
		}
		else {
			this.size=size;
		}
	}
	boolean allChanged=false;
	boolean nextChanged=false;
	private Map<String,FileInfo> child;
	public boolean isFile;
	public long size;
	public long nextSize;
	public long allSize;
	public long limitDir;
	public long limitAll;
	
	public FileInfo findFile(String [] split,int cur) {
		if(cur==split.length) {
			return this;
		}
		if(!isFile&&child.containsKey(split[cur])) {
			return child.get(split[cur]).findFile(split,cur+1);
		}
		else {
			return null;
		}
	}
	public long getNextSize() {
		if(!nextChanged) {
			return nextSize;
		}
		long ans=0;
		for(FileInfo f:child.values()) {
			ans+=f.size;
		}
		nextSize=ans;
		nextChanged=false;
		return ans;
	}
	
	public long getAllSize() {
		if(!allChanged) {
			return allSize;
		}
		long ans=0;
		for(FileInfo f:child.values()) {
			if(f.isFile) {
				ans+=f.size;
			}
			else {
				ans+=f.getAllSize();
			}
		}
		allSize=ans;
		allChanged= false;
		return ans;
	}
	
	public boolean createFile(String []split,long size,int cur) {
		if(cur+1==split.length) {
			if((limitAll!=0&&getAllSize()+size>limitAll)||
					(limitDir!=0&&getNextSize()+size>limitDir)) {
				return false;
			}
			FileInfo next=child.get(split[cur]);
			if(next==null) {
				next=new FileInfo(true,size);
				child.put(split[cur], next);
			}else {
				next.size=size;
			}
			allChanged=true;
			nextChanged=true;
			return true;
		}else {
			if((limitAll!=0&&getAllSize()+size>limitAll)) {
				return false;
			}
			FileInfo next=child.get(split[cur]);
			if(next==null) {
				next= new FileInfo(false,0);
				child.put(split[cur], next);
			}
			if(next.isFile) {
				return false;
			}else {
				if(next.createFile(split, size, cur+1)) {
					allChanged=true;
					return true;
				}
			}
			return false;
		}
	}
	public boolean removeFile(String []split,int cur) {
		if(cur+1==split.length) {
			nextChanged=true;
			allChanged=true;
			child.remove(split[cur]);
			return true;
		}else {
			FileInfo next=child.get(split[cur]);
			if(next!=null&&!next.isFile&&next.removeFile(split, cur+1)) {
				allChanged=true;
				return true;
			}
			return false;
		}
	}
	}
	public static void main(String []args) {
		Scanner input=new Scanner(System.in);
		int t=input.nextInt();
		
		FileInfo root=new FileInfo(false,0);
		for(int i=0;i<t;i++) {
			boolean result=false;
			String cmd=input.next();
			if("C".equals(cmd)) {
				String path=input.next();
				String[] paths=path.substring(1).split("/");
				long size=input.nextLong();
				FileInfo f=root.findFile(paths, 0);
				if(f!=null) {
					if(f.isFile) {
						long changeSize=size-f.size;
						if(root.createFile(paths, changeSize, 0)) {
							result=true;
						}
					}
				}else {
					if(root.createFile(paths, size, 0)) {
						result=true;
					}
				}
			}else if("R".equals(cmd)) {
				String path=input.next();
				root.removeFile(path.substring(1).split("/"),0);
				result=true;
			}else {
				String path=input.next();
				long ld=input.nextLong();
				long lr=input.nextLong();
				
				String[] paths=path.substring(1).split("/");
				FileInfo target=root.findFile(paths, 0);
				if("".equals(paths[0])) {
					target=root;
				}
				if(target!=null&&!target.isFile) {
					if((lr==0||target.getAllSize()<=lr)
							&&(ld==0||target.getNextSize()<=ld)) {
						target.limitAll=lr;
						target.limitDir=ld;
						result=true;
					}
				}
			}
			
			if(result==false) {
				System.out.println("N");
			}else {
				System.out.println("Y");
			}
			
		}
		
	}

}
