import java.io.*;
import java.util.*;
public class homework3 {
	public static void main (String args[])throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("/Users/rishikaverma/Desktop/input.txt"));
		int queryCount = Integer.parseInt(br.readLine());
		String[] queryArray = new String[queryCount];
		for(int i = 0; i < queryCount; i++)
		{
			//Store every query as a sentence object
			queryArray[i] = br.readLine();
			//System.out.println("Query"+queryArray[i]);
		}
		int kbSize = Integer.parseInt(br.readLine());
		//kbSize = kbSize+1;
		String[] kbArray = new String[kbSize];
		for(int i = 0; i < kbSize; i++)
		{
			//Read each line 
			//Call function to convert each line to cnf
			//Store every converted sentence in kb hashmap
			//Store the arguments 
			kbArray[i] = br.readLine().replaceAll("\\s",""); //ArrayList of KBSentences
			//remove all spaces from KB sentences
		}
		//loop over all the queries
		for(int i = 0; i < queryCount; i++)
		{
			//Store every query as a sentence object

		HashMap<Predicate, List<Predicate>> kbHashMap = Sentence.hashKB(kbArray, queryArray[i]);

		}
		br.close();
	}
	

}
