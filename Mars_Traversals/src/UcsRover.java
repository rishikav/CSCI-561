import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Comparator;
public class UcsRover {
	int startx;
	int starty;
	int targetx;
	int targety;
	int maxDiff;
	int rows;
	int cols;
	Node parent;
	Node[][] nodeMap;
	UcsRover (int startx,int starty,int maxDiff,int rows, int cols, Node[][] nodeMap)
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
		boolean path = UCS(start, target);
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
	
	boolean UCS(Node start, Node target)
	{
		PriorityQueue<Node> queue = new PriorityQueue<Node>(rows*cols, new NodeComparator()); //capacity of queue
		int targetx = target.x;
		int targety = target.y;
		start.distance = 0;
		queue.add(start);
		start.isVisited=true;
		while(!queue.isEmpty())
		{
			Node popped = queue.poll();
			if(popped.x==targetx && popped.y==targety)
			{
				return true;
			}
			else
			{
				//System.out.println(popped.x + " " + popped.y);
				addNeighbours(queue,popped);
			}
			
		}
		return false;
	}
	
	PriorityQueue<Node> addNeighbours(PriorityQueue<Node> queue, Node popped)
	{
		if(isValid(popped, popped.x-1, popped.y-1))
		{
			nodeMap[popped.x-1][popped.y-1].parent = popped;
			nodeMap[popped.x-1][popped.y-1].distance = popped.distance + 14;
			nodeMap[popped.x-1][popped.y-1].isVisited=true;
			queue.add(nodeMap[popped.x-1][popped.y-1]);
		}
		if(isValid(popped, popped.x-1, popped.y+1))
		{
			nodeMap[popped.x-1][popped.y+1].parent = popped;
			nodeMap[popped.x-1][popped.y+1].distance = popped.distance + 14;
			nodeMap[popped.x-1][popped.y+1].isVisited=true;
			queue.add(nodeMap[popped.x-1][popped.y+1]);
		}
		if(isValid(popped, popped.x-1, popped.y))
		{
			nodeMap[popped.x-1][popped.y].parent = popped;
			nodeMap[popped.x-1][popped.y].distance = popped.distance + 10;
			nodeMap[popped.x-1][popped.y].isVisited=true;
			queue.add(nodeMap[popped.x-1][popped.y]);
		}
		if(isValid(popped, popped.x+1, popped.y-1))
		{
			nodeMap[popped.x+1][popped.y-1].parent = popped;
			nodeMap[popped.x+1][popped.y-1].distance = popped.distance + 14;
			nodeMap[popped.x+1][popped.y-1].isVisited=true;
			queue.add(nodeMap[popped.x+1][popped.y-1]);
		}
		if(isValid(popped, popped.x+1, popped.y))
		{
			nodeMap[popped.x+1][popped.y].parent = popped;
			nodeMap[popped.x+1][popped.y].distance = popped.distance + 10;
			nodeMap[popped.x+1][popped.y].isVisited=true;
			queue.add(nodeMap[popped.x+1][popped.y]);
		}
		if(isValid(popped, popped.x+1, popped.y+1))
		{
			nodeMap[popped.x+1][popped.y+1].parent = popped;
			nodeMap[popped.x+1][popped.y+1].distance = popped.distance + 14;
			nodeMap[popped.x+1][popped.y+1].isVisited=true;
			queue.add(nodeMap[popped.x+1][popped.y+1]);
		}
		if(isValid(popped, popped.x, popped.y-1))
		{
			nodeMap[popped.x][popped.y-1].parent = popped;
			nodeMap[popped.x][popped.y-1].distance = popped.distance + 10;
			nodeMap[popped.x][popped.y-1].isVisited=true;
			queue.add(nodeMap[popped.x][popped.y-1]);
		}
		if(isValid(popped, popped.x, popped.y+1))
		{
			nodeMap[popped.x][popped.y+1].parent = popped;
			nodeMap[popped.x][popped.y+1].distance = popped.distance + 10;
			nodeMap[popped.x][popped.y+1].isVisited=true;
			queue.add(nodeMap[popped.x][popped.y+1]);
		}
		return queue;
	}
	
	boolean isValid(Node popped, int x, int y)
	{
		if(x<0 || x>(rows-1) || y<0 || y>(cols-1))
		{
			return false;
		}
		if((Math.abs(nodeMap[x][y].z) - popped.z)>maxDiff)
		{
			return false;
		}
		if(nodeMap[x][y].isVisited==true)
		{
			return false;
		}
		return true;
	}
	

}
class NodeComparator implements Comparator<Node>{ 
    
    public int compare(Node n1, Node n2) { 
        if (n1.distance > n2.distance) 
            return 1; 
        else if (n1.distance == n2.distance) 
            return 0; 
                        return -1; 
        } 
} 
