public class Node {
	int x;
	int y;
	int z;
	boolean isVisited;
	Node parent;
	int distance;
	Node[][] nodeMap;
	
	Node(int x, int y)
	{
		
		this.x=x;
		this.y=y;
		this.z=0;
	}
	void setElevation(int z)
	{
		this.z=z;
	}

}
