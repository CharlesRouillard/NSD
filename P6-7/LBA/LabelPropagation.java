import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LabelPropagation{	
	
	private static HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
	
	private static void FisherYates(int[] network)
	{
	    int index,temp;
	    Random random = new Random();
	    int min = 1,max = (network.length-1);
	    for (int i = network.length - 1; i > 0; i--)
	    {
	        index = random.nextInt((max - min) + 1) + min;
	        temp = network[index];
	        network[index] = network[i];
	    }
	}
	
	private static void SaveNumberOfCommunities(LabelNode[] tabNodes){
		
		HashSet<Integer> hs = new HashSet<Integer>();
		
		for(int i = 1;i<tabNodes.length;i++) {
			if(tabNodes[i] != null)
				hs.add(tabNodes[i].getLabel());
		}
		System.out.println("number of communities = " + hs.size());
		if(hm.get(hs.size()) == null) {
			hm.put(hs.size(),1);
		}
		else {
			hm.put(hs.size(), hm.get(hs.size())+1);
		}
		hs.clear();
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException 
	{	
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String line;
		String[] tab;
		boolean oneNeighbor;
		int numberMaxNode = 0,x,y,highestFrequencyLabel = 0,randomLabel = 0, numberOccurenceOfTheHighestLabel = 0,r,currentNode = 0,currentLabel = 0,nbNodes = 0;
		int[] tabLabel,network;
		ArrayList<Integer>[] adjacancyList;
		LabelNode[] tabNodes;
		HashSet<Integer> hs = new HashSet<Integer>();
		
		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%") && !line.contains("#")) {
					tab = line.split("\t");
					
					x = Integer.parseInt(tab[0]);
					y = Integer.parseInt(tab[1]);
					
					numberMaxNode = Math.max(numberMaxNode, Math.max(x, y));
					
					hs.add(x);
					hs.add(y);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
		
		nbNodes = hs.size();
		hs.clear();
		
		/*network is the array that will be shuffled, tabNodes is the array to found a good label*/
		adjacancyList = (ArrayList<Integer>[]) new ArrayList[numberMaxNode+1];
		network = new int[numberMaxNode+1];
		tabNodes = new LabelNode[numberMaxNode+1];
		tabLabel = new int[numberMaxNode+1];		
		LabelNode nodeSrc,nodeDest;

		br = new BufferedReader(new FileReader(args[0]));

		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%") && !line.contains("#")) {
					tab = line.split("\t");
					x = Integer.parseInt(tab[0]);
					y = Integer.parseInt(tab[1]);
					
					nodeSrc = new LabelNode(x,x);
					nodeDest = new LabelNode(y,y);
					
					if(tabNodes[x] == null) {
						network[x] = x;
						tabNodes[x] = nodeSrc;
					}
					
						
					if(tabNodes[y] == null) {
						network[y] = y;
						tabNodes[y] = nodeDest;
					}
									
					if(adjacancyList[x] == null){
						adjacancyList[x] = new ArrayList<Integer>();
					}
					adjacancyList[x].add(y);
					
					if(adjacancyList[y] == null){
						adjacancyList[y] = new ArrayList<Integer>();
					}
					adjacancyList[y].add(x);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
		
		boolean cont = true;
		int t = 0;
		while(cont) {
			t++;
			cont = false;
			
			//shuffle the network
			FisherYates(network);
			
			//for each node, we set the label to a label occuring with the highest frequency among its neighbours
			for(int i = 1;i<=numberMaxNode;i++) {
				if(network[i] != 0) {
					numberOccurenceOfTheHighestLabel = 0;
					currentNode = network[i];
					
					/*iterate through all the neighbors of the current node*/
					for(int j = 0;j<adjacancyList[currentNode].size();j++) {
							int neighbor = adjacancyList[currentNode].get(j);
							int label = tabNodes[neighbor].getLabel();
							tabLabel[label]++;
					}
					
					//get highet frequency
					
					for(int j = 0;j<adjacancyList[currentNode].size();j++) {
						int neighbor = adjacancyList[currentNode].get(j);
						int label = tabNodes[neighbor].getLabel();
						
						if(tabLabel[label] > numberOccurenceOfTheHighestLabel) {
							numberOccurenceOfTheHighestLabel = tabLabel[label];
						}
					}
					
					
					
					ArrayList<Integer> bestlabels = new ArrayList<Integer>();
					Random randomGenerator = new Random();
					boolean test = false;
					for(int j = 0;j<adjacancyList[currentNode].size();j++) {
						int neighbor = adjacancyList[currentNode].get(j);
						int label = tabNodes[neighbor].getLabel();
						if(tabLabel[label]==numberOccurenceOfTheHighestLabel){
							//ajouter dans bestlabels
							if(label == tabNodes[currentNode].getLabel()){
								test = true;
								
							}
							if(!bestlabels.contains(label)){
								bestlabels.add(label);	
							}						
						}
					}
					if(test){
						cont = cont || false;
						for(int j = 0;j<adjacancyList[currentNode].size();j++) {
							int neighbor = adjacancyList[currentNode].get(j);
							int label = tabNodes[neighbor].getLabel();
							if(tabLabel[label] != 0)
								tabLabel[label] = 0;
						}
						continue;
					}
					int new_label = bestlabels.get(randomGenerator.nextInt(bestlabels.size())); //rand
					tabNodes[currentNode].setLabel(new_label);
							
					
					/*reinitialize tabLabel*/
					for(int j = 0;j<adjacancyList[currentNode].size();j++) {
						int neighbor = adjacancyList[currentNode].get(j);
						int label = tabNodes[neighbor].getLabel();
						if(tabLabel[label] != 0)
							tabLabel[label] = 0;
					}
					cont = cont || (bestlabels.size() > 1);
				}	
			}
		}
		SaveNumberOfCommunities(tabNodes);
	}
}