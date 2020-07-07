import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Comparator;
public class AstarRover {
	int startx;
	int starty;
	int targetx;
	int targety;
	int maxDiff;
	int rows;
	int cols;
	Node parent;
	Node[][] nodeMap;
	AstarRover (int startx,int starty,int maxDiff,int rows, int cols, Node[][] nodeMap)
	{
		this.startx=startx;
		this.starty=starty;
		this.maxDiff=maxDiff;
		this.rows=rows;
		this.cols=cols;
		this.nodeMap=nodeMap;
	}
	String ifPathExists(Node target)
	{
		String finalPath="";
		Stack<Node> stack = new Stack<Node>();
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				nodeMap[i][j].isVisited = false;
			}
		}
		Node start = nodeMap[startx][starty];
		boolean path = Astar(start, target);
        if(startx==target.x && starty==target.y)
        {
            finalPath = startx+","+starty;
            return finalPath;
        }
        
		if(path)
		{
			stack.push(target);
			//finalPath = target.y+","+target.x+" ";
			do
			{
				stack.push(target.parent);
				target=target.parent;
			}while(!(start.x==target.x && start.y==target.y));
			while(!stack.isEmpty())
			{
				Node n = stack.pop();
				finalPath = finalPath + n.y+","+n.x+" ";
			}
			return finalPath.trim();
		}
		else 
		{
			return "FAIL";
		}
	}
	
	boolean Astar(Node start, Node target)
	{
		PriorityQueue<Node> queue = new PriorityQueue<Node>(rows*cols, new AstarComparator()); //capacity of queue
		int targetx = target.x;
		int targety = target.y;
		start.distance = 0;
		queue.add(start);
		start.isVisited=true;
		while(!queue.isEmpty())
		{
			Node popped = queue.poll();
			//System.out.println(popped.z);
			if(popped.x==targetx && popped.y==targety)
			{
				
				return true;
			}
			else
			{
				addNeighbours(queue,popped,target);
			}
			
		}
		return false;
	}
	
	PriorityQueue<Node> addNeighbours(PriorityQueue<Node> queue, Node popped, Node target)
	{
		
		if(isValid(popped, popped.x-1, popped.y-1))
		{
			Node n1 = nodeMap[popped.x-1][popped.y-1];
			n1.parent = popped;
			n1.distance = Heuristic(n1,popped,target) + 14;
			n1.isVisited=true;
			queue.add(n1);
		}
		if(isValid(popped, popped.x-1, popped.y+1))
		{
			Node n2 = nodeMap[popped.x-1][popped.y+1];
			n2.parent = popped;
			n2.distance = Heuristic(n2,popped,target) + 14;
			n2.isVisited=true;
			queue.add(n2);
		}
		if(isValid(popped, popped.x-1, popped.y))
		{
			Node n3 = nodeMap[popped.x-1][popped.y];
			n3.parent = popped;
			n3.distance = Heuristic(n3,popped,target) + 10;
			n3.isVisited=true;
			queue.add(n3);
		}
		if(isValid(popped, popped.x+1, popped.y-1))
		{
			
			Node n4 = nodeMap[popped.x+1][popped.y-1];
			n4.parent = popped;
			n4.distance = Heuristic(n4,popped,target) + 14;
			n4.isVisited=true;
			queue.add(n4);
		}
		if(isValid(popped, popped.x+1, popped.y))
		{
			Node n5 = nodeMap[popped.x+1][popped.y];
			n5.parent = popped;		
			n5.distance = Heuristic(n5,popped,target) + 10;
			n5.isVisited=true;
			queue.add(n5);
		}
		if(isValid(popped, popped.x+1, popped.y+1))
		{
			Node n6 = nodeMap[popped.x+1][popped.y+1];
			n6.parent = popped;
			n6.distance = Heuristic(n6,popped,target) + 14;
			n6.isVisited=true;
			queue.add(n6);
		}
		if(isValid(popped, popped.x, popped.y-1))
		{
			Node n7 = nodeMap[popped.x][popped.y-1];
			n7.parent = popped;
			n7.distance = Heuristic(n7,popped,target) + 10;
			n7.isVisited=true;
			queue.add(n7);
		}
		if(isValid(popped, popped.x, popped.y+1))
		{
			Node n8 = nodeMap[popped.x][popped.y+1];
			n8.parent = popped;
			n8.distance = Heuristic(n8,popped,target) + 10;
			n8.isVisited=true;
			queue.add(n8);
		}
		return queue;
	}
	
	boolean isValid(Node popped, int x, int y)
	{
		if(x<0 || x>(rows-1) || y<0 || y>(cols-1))
		{
			//System.out.println("Out Of Bounds");
			return false;
		}
		if((Math.abs(nodeMap[x][y].z - popped.z))>maxDiff)
		{
			//System.out.println("MaxDiff");
			return false;
		}
		if(nodeMap[x][y].isVisited==true)
		{
			//System.out.println("Visited");
			return false;
		}
		return true;
	}
	int Heuristic(Node n, Node popped, Node target)
	{
		int dist = popped.distance + Math.abs(n.z-popped.z) + Math.abs(n.x-target.x) + Math.abs(n.y-target.y) ;
		return dist;
	}
	

}
class AstarComparator implements Comparator<Node>{ 
    
    public int compare(Node n1, Node n2) { 
        if (n1.distance > n2.distance) 
            return 1; 
        else if (n1.distance== n2.distance) 
            return 0; 
        return -1; 
        }
} 

