package Gaem;
import Gaem.Point3D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
 
public class ThreeDimensionalKDTree {
  static ArrayList<Point3D> data_sources=new ArrayList<Point3D>();
  //constructor for a TreeNode
	public class TreeNode
	{
		TreeNode left, right;
		Point3D data;
		private int type;
		private Point3D p;
		TreeNode(TreeNode left, Point3D data, TreeNode right, Point3D p, int type)
		{
			this.left=left;
			this.data=data;
			this.right=right;
			this.type=type;	
			this.p=p;
		}
	}
	
	public class xComparator implements Comparator<Point3D>
    {
            public int compare(Point3D a, Point3D b)
            {
                    if (a.x == b.x) return 0;
                    if (a.x < b.x) return -1;
                    else return 1;
            }
    }
    public class yComparator implements Comparator<Point3D> 
    {
    	public int compare(Point3D a, Point3D b)
    	{
	    		if(a.y==b.y) return 0;
	    		if(a.y<b.y) return -1;
	    		else return 1;
    	}
    }
    public class zComparator implements Comparator<Point3D>
    {
	    public int compare(Point3D a, Point3D b)
		{
			if(a.z==b.z) return 0;
			if(a.z<b.z) return -1;
			else return 1;
		}
    }
    TreeNode root = null; 
	xComparator xComp = new xComparator();
	yComparator yComp = new yComparator();
	zComparator zComp = new zComparator();
	
	public LinkedList<Point3D> neighbors(Point3D p, TreeNode n) {
        LinkedList<Point3D> neighbors = new LinkedList<Point3D>();
        if (n == null) return neighbors;
        if (sqr(p.x-n.p.x) + sqr(p.y-n.p.y) < radius) {
            neighbors.add(n.p);
            neighbors.addAll(neighbors(p,n.left));
            neighbors.addAll(neighbors(p,n.right));
        } else if ((n.type == 0 && sqr(p.x-n.p.x) < radius) ||
                (n.type == 1 && sqr(p.y-n.p.y) < radius)) {
            neighbors.addAll(neighbors(p,n.left));
            neighbors.addAll(neighbors(p,n.right));
        } else if ((n.type == 0 && p.x < n.p.x) ||
                (n.type == 1 && p.y < n.p.y)) {
            neighbors.addAll(neighbors(p,n.left));
        } else {
            neighbors.addAll(neighbors(p,n.right));
        }
        return neighbors;
    }
	private double sqr(double x) {return x*x;}
    
    public LinkedList<Point3D> neighbors(Point3D p) {
        LinkedList<Point3D> neighbors = new LinkedList<Point3D>();
        neighbors.addAll(neighbors(p,root));
        return neighbors;
    }
	//building the three dimensional tree.
	public TreeNode buildTree(ArrayList<Point3D> data, int depth)
	{
		if(data.size()==0) return null;
		
		int end=data.size();
		int median=data.size()/2;
		int axis=depth%3;
		
		if(axis==0 || axis==3) {
			Collections.sort(data,xComp);
		}
		if(axis==1) {
			Collections.sort(data,yComp);
		}
		if(axis==2) {
			Collections.sort(data,zComp);
		}
		ArrayList<Point3D> LesserHalf = new ArrayList<Point3D>();
		ArrayList<Point3D> GreaterHalf = new ArrayList<Point3D>();
		//copy the data from data_sources into two arraylists
		for(int i=0;i<median;i++) {
			Point3D Element = data.get(i);
			LesserHalf.add(i,Element);
		}
		for(int j=median+1;j<end;j++) {
			int counter=0;
			Point3D Element = data.get(j);
			GreaterHalf.add(counter,Element);
			counter++;
		}
		TreeNode n = new TreeNode(null,null,null,null,0);
			n.left=buildTree(LesserHalf,depth+1);
   	 		n.right=buildTree(GreaterHalf,depth+1);
   	 		return n;
	}
}
