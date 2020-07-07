import java.io.*;
public class homework {
	public static void main (String args[]) throws Exception
	{
		
		String file ="/Users/rishikaverma/Desktop/test_workspace/Mars1/input.txt";
		BufferedReader br = new BufferedReader(new FileReader(file));
		String algo = br.readLine();
		String[] size = (br.readLine().split(" "));
		
		
		int cols = Integer.parseInt(size[0]);
		int rows = Integer.parseInt(size[1]);
		
		
		String[] landing = (br.readLine().split(" "));
		int startx = Integer.parseInt(landing[1]);
		int starty = Integer.parseInt(landing[0]);		
		int maxDiff = Integer.parseInt(br.readLine());
		int numTarget = Integer.parseInt(br.readLine());
		Node[][] nodeMap = new Node[rows][cols];
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				nodeMap[i][j] = new Node(i,j);
			}
		}
		
		Node[] targetNode = new Node[numTarget];
		for (int i=0;i<numTarget;i++)
		{
			String[] targets = br.readLine().split(" ");
			int targety = Integer.parseInt(targets[0]);
			int targetx = Integer.parseInt(targets[1]);
			targetNode[i] = nodeMap[targetx][targety];
		}
		
		for(int i=0;i<rows;i++)
		{
			//System.out.println("Rows "+rows +"i"+i);
			String[] elevation = br.readLine().split(" ");
			//System.out.println(elevation.length);
			/*for(String e : elevation)
			{
				System.out.println("Elevation "+e);
			}*/
			for(int j=0;j<cols;j++)
			{
				int ele = Integer.parseInt(elevation[j]);
				nodeMap[i][j].setElevation(ele);
				//System.out.println("i"+i+"j"+j+" "+nodeMap[i][j].z);
			}
		}
		br.close();
		if(algo.equals("BFS"))
		{
		BfsRover bfsObj = new BfsRover(startx,starty,maxDiff,rows,cols,nodeMap);
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));		
		for(Node n : targetNode)
				{
					String path = bfsObj.ifPathExists(n);
					writer.write(path);
					writer.newLine();
					System.out.println(path) ; 
				}
				
				writer.close();
		}
		else if(algo.equals("UCS"))
		{
			UcsRover ucsObj = new UcsRover(startx,starty,maxDiff,rows,cols,nodeMap);
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));		
			for(Node n : targetNode)
					{
						String path = ucsObj.ifPathExists(n);
						writer.write(path);
						writer.newLine();
						System.out.println(path) ; 
					}
					
					writer.close();
			} 
		else if(algo.equals("A*"))
		{
			AstarRover astarObj = new AstarRover(startx,starty,maxDiff,rows,cols,nodeMap);
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));		
			for(Node n : targetNode)
					{
						String path = astarObj.ifPathExists(n);
						writer.write(path);
						writer.newLine();
						System.out.println(path) ; 
					}
					
					writer.close();
			}
			
		}
}
