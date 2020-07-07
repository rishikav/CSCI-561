import java.util.*;
public class BfsRover {
	int startx;
	int starty;
	int targety;
	int maxDiff;
	int rows;
	int cols;
	Node parent;
	Node[][] nodeMap;
	BfsRover (int startx,int starty,int maxDiff,int rows, int cols, Node[][] nodeMap)
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
		boolean path = BFS(start, target);
        if(startx==target.x && starty==target.y)
        {
            finalPath = startx+","+starty;
            return finalPath;
        }
		if(path)
		{
		//finalPath = target.y+","+target.x+" ";
			stack.push(target);
		do
		{
			//finalPath = finalPath +" "+target.parent.y+","+target.parent.x;
			stack.push(target.parent);
			target = target.parent;
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
	
	boolean BFS(Node start, Node target)
	{
		Node startNode = nodeMap[startx][starty];
		Queue<Node> queue = new LinkedList<Node>();
		int targetx = target.x;
		int targety = target.y;
		queue.add(startNode);
		startNode.isVisited=true;
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
				addNeighbours(queue,popped);
			}
			
		}
		return false;
	}
	
	Queue<Node> addNeighbours(Queue<Node> queue, Node popped)
	{
		if(isValid(popped, popped.x-1, popped.y-1))
		{
			nodeMap[popped.x-1][popped.y-1].parent = popped;
			nodeMap[popped.x-1][popped.y-1].isVisited=true;
			queue.add(nodeMap[popped.x-1][popped.y-1]);
		
		}
		if(isValid(popped, popped.x-1, popped.y+1))
		{
			nodeMap[popped.x-1][popped.y+1].parent = popped;
			nodeMap[popped.x-1][popped.y+1].isVisited=true;
			queue.add(nodeMap[popped.x-1][popped.y+1]);
		
		}
		if(isValid(popped, popped.x-1, popped.y))
		{
			nodeMap[popped.x-1][popped.y].parent = popped;
			nodeMap[popped.x-1][popped.y].isVisited=true;
			queue.add(nodeMap[popped.x-1][popped.y]);
		
		}
		if(isValid(popped, popped.x+1, popped.y-1))
		{
			nodeMap[popped.x+1][popped.y-1].parent = popped;
			nodeMap[popped.x+1][popped.y-1].isVisited=true;
			queue.add(nodeMap[popped.x+1][popped.y-1]);
		
		}
		if(isValid(popped, popped.x+1, popped.y))
		{
			nodeMap[popped.x+1][popped.y].parent = popped;
			nodeMap[popped.x+1][popped.y].isVisited=true;
			queue.add(nodeMap[popped.x+1][popped.y]);
		
		}
		if(isValid(popped, popped.x+1, popped.y+1))
		{
			//System.out.println("Popped x "+popped.x+" Popped y "+popped.y);
			nodeMap[popped.x+1][popped.y+1].parent = popped;
			nodeMap[popped.x+1][popped.y+1].isVisited=true;
			queue.add(nodeMap[popped.x+1][popped.y+1]);
		
		}
		if(isValid(popped, popped.x, popped.y-1))
		{
			nodeMap[popped.x][popped.y-1].parent = popped;
			nodeMap[popped.x][popped.y-1].isVisited=true;
			queue.add(nodeMap[popped.x][popped.y-1]);
		
		}
		if(isValid(popped, popped.x, popped.y+1))
		{
			nodeMap[popped.x][popped.y+1].parent = popped;
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
		if(Math.abs((nodeMap[x][y].z) - popped.z)>maxDiff)
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
