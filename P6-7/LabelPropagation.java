package p6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LabelPropagation {	
	
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
	        network[i] = temp;
	    }
	}
	
	private static void SaveNumberOfCommunities(LabelNode[] tabNodes){
		
		HashSet<Integer> hs = new HashSet<Integer>();
		
		for(int i = 1;i<tabNodes.length;i++) {
			if(tabNodes[i] != null)
				hs.add(tabNodes[i].getLabel());
		}
		
		if(hm.get(hs.size()) == null) {
			hm.put(hs.size(),1);
		}
		else {
			hm.put(hs.size(), hm.get(hs.size())+1);
		}
	}
	
	private static void saveHistogram() throws IOException {
		FileWriter writer = new FileWriter("data/communities.txt");
		
		for (Map.Entry<Integer, Integer> entry : hm.entrySet()) {
		    Integer key = entry.getKey();
		    Integer value = entry.getValue();
		    
		    writer.write(key + " " + value + "\n");
		}
		
		writer.close();
	}
	
	private static boolean findWrongNodes(int numberMaxNode,ArrayList<Integer>[] adjacancyList,int[] network,int[] tabLabel,LabelNode[] tabNodes){
		boolean ret = false;
		for(int i = 1;i<=numberMaxNode;i++) {
			if(network[i] != 0) {
				int currentNode = network[i];
				int currentLabel = tabNodes[currentNode].getLabel();
				int numberOccurenceOfTheHighestLabel = 0;
				int occurenceOfMyLabel = 0;
				
				/*iterate through de adjacancy list of the current node in network*/
				for(int j = 0;j<adjacancyList[currentNode].size();j++) {
					int neighbor = adjacancyList[currentNode].get(j);
					int neighborLabel = tabNodes[neighbor].getLabel();
					
					tabLabel[neighborLabel]++;
					if(tabLabel[neighborLabel] > numberOccurenceOfTheHighestLabel)
						numberOccurenceOfTheHighestLabel = tabLabel[neighborLabel];
					
					if(neighborLabel == currentLabel)
						occurenceOfMyLabel++;
				}
				
				if(occurenceOfMyLabel != numberOccurenceOfTheHighestLabel){
					ret = true;
					
					/*reinitialize tabLabel*/
					for(int j = 0;j<adjacancyList[currentNode].size();j++) {
						int neighbor = adjacancyList[currentNode].get(j);
						int label = tabNodes[neighbor].getLabel();
						if(tabLabel[label] != 0)
							tabLabel[label] = 0;
					}
					
					break;
				}

				/*reinitialize tabLabel*/
				for(int j = 0;j<adjacancyList[currentNode].size();j++) {
					int neighbor = adjacancyList[currentNode].get(j);
					int label = tabNodes[neighbor].getLabel();
					if(tabLabel[label] != 0)
						tabLabel[label] = 0;
				}
			}
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException 
	{
		int cpt = 0;
		while(cpt < 10){
			System.out.println(cpt);
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			String line;
			String[] tab;
			boolean oneNeighbor;
			int numberMaxNode = 0,x,y,highestFrequencyLabel = 0,randomLabel = 0, numberOccurenceOfTheHighestLabel = 0,r,currentNode = 0,currentLabel = 0;
			int[] tabLabel,network;
			ArrayList<Integer>[] adjacancyList;
			LabelNode[] tabNodes;
			
			try {
				while((line = br.readLine()) != null) {
					if(!line.contains("%") && !line.contains("#")) {
						tab = line.split("\t");
						
						x = Integer.parseInt(tab[0]);
						y = Integer.parseInt(tab[1]);
						
						numberMaxNode = Math.max(numberMaxNode, Math.max(x, y));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			br.close();
			
			/*network is the array that will be shuffle, tabNodes is the array to found a good label*/
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
				
			do {
				//shuffle the network
				FisherYates(network);
				
				//for each node, we set the label to a label occuring with the highest frequency among its neighbours
				for(int i = 1;i<=numberMaxNode;i++) {
					if(network[i] != 0) {
						oneNeighbor = false;
						numberOccurenceOfTheHighestLabel = 0;
						currentNode = network[i];
						currentLabel = tabNodes[currentNode].getLabel();
						
						for(int j = 0;j<adjacancyList[currentNode].size();j++) {
							if(adjacancyList[currentNode].size() == 1) {
								oneNeighbor = true;
								highestFrequencyLabel = tabNodes[adjacancyList[currentNode].get(0)].getLabel();
							}
							else {
								int neighbor = adjacancyList[currentNode].get(j);
								int label = tabNodes[neighbor].getLabel();

								tabLabel[label]++;
								if(tabLabel[label] > numberOccurenceOfTheHighestLabel) {
									numberOccurenceOfTheHighestLabel = tabLabel[label];
									highestFrequencyLabel = label;
								}
								/*else if(numberOccurenceOfTheHighestLabel > 1 && tabLabel[label] == numberOccurenceOfTheHighestLabel) {
									
								}*/
							}
						}
						
						if(!oneNeighbor) {
							if(numberOccurenceOfTheHighestLabel == 1) {
								/*all neighbors have a different label, choose a random one*/
								r = ThreadLocalRandom.current().nextInt(0, adjacancyList[currentNode].size());
								int randomNeighbor = adjacancyList[currentNode].get(r);
								randomLabel = tabNodes[randomNeighbor].getLabel();
								//System.out.println("random label choose " + randomLabel);
								tabNodes[currentNode].setLabel(randomLabel);
							}
							else {
								/*a label is predominant among neighbors*/
								if(highestFrequencyLabel != tabNodes[currentNode].getLabel()) {
									tabNodes[currentNode].setLabel(highestFrequencyLabel);
								}
							}
						}
						else{
							if(highestFrequencyLabel != currentLabel)
								tabNodes[currentNode].setLabel(highestFrequencyLabel);
						}
						
						/*reinitialize tabLabel*/
						for(int j = 0;j<adjacancyList[currentNode].size();j++) {
							int neighbor = adjacancyList[currentNode].get(j);
							int label = tabNodes[neighbor].getLabel();
							if(tabLabel[label] != 0)
								tabLabel[label] = 0;
						}
					}
				}
			}
			while(findWrongNodes(numberMaxNode,adjacancyList,network,tabLabel,tabNodes));
			SaveNumberOfCommunities(tabNodes);
			cpt++;
		}
		saveHistogram();
		//System.out.println(hm);
	}
}
