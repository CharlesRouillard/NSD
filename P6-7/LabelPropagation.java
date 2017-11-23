package p6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LabelPropagation {
	
	public static ArrayList<LabelNode> wrongNodes;
	
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
			//System.out.println(tabNodes[i]);
			
		}
		System.out.println("number of community : " + hs.size());
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException 
	{
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
		HashSet<Integer> remainingNodes = new HashSet<Integer>();
		br = new BufferedReader(new FileReader(args[0]));

		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%") && !line.contains("#")) {
					tab = line.split("\t");
					x = Integer.parseInt(tab[0]);
					y = Integer.parseInt(tab[1]);
					
					remainingNodes.add(x);
					remainingNodes.add(y);
					
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
		
		int TT = 0,cpt = 0;
		boolean rand = false;
			
		while(TT != remainingNodes.size() && cpt < 50) {
			System.out.println(TT + " " + remainingNodes.size());
			FisherYates(network);
			//Collections.shuffle(network);
			/*if(remainingNodes.size() < 400)
				break;*/
			
			TT = 0;
			
			//for each node, we set the label to a label occuring with the highest frequency among its neighbours
			for(int i = 1;i<=numberMaxNode;i++) {
				if(network[i] != 0) {
					oneNeighbor = false;
					rand = false;
					numberOccurenceOfTheHighestLabel = 0;
					currentNode = network[i];
					currentLabel = tabNodes[currentNode].getLabel();
					
					//System.out.println("current node = " + currentNode + " / current label = " + currentLabel);
					//System.out.println("number of neighbor is " + adjacancyList[currentNode].size());
					//tabLabel = new int[numberMaxNode+1];
					
					for(int j = 0;j<adjacancyList[currentNode].size();j++) {
						if(adjacancyList[currentNode].size() == 1) {
							//System.out.println("number of neigh is 1");
							oneNeighbor = true;
							highestFrequencyLabel = tabNodes[adjacancyList[currentNode].get(0)].getLabel();
							if(highestFrequencyLabel != currentLabel)
								tabNodes[currentNode].setLabel(highestFrequencyLabel);
							break;
						}
						if(adjacancyList[currentNode].size() == 2) {
							tabLabel[currentLabel]++;
						}
						int neighbor = adjacancyList[currentNode].get(j);
						int label = tabNodes[neighbor].getLabel();
						//System.out.println("tabLabel[" + label + "] = " + tabLabel[label] + "++");
						/*if(twoNeighbor)
							tabLabel[currentLabel]++;*/
						tabLabel[label]++;
						if(tabLabel[label] > numberOccurenceOfTheHighestLabel) {
							numberOccurenceOfTheHighestLabel = tabLabel[label];
							highestFrequencyLabel = label;
							
							/*if(tabLabel[label] > 1) {
								if 4 neighbors, 2 has label x and the others 2 has label y
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
							rand = true;
							//System.out.println(currentNode + ".label = " + randomLabel);
						}
						else {
							/*a label is predominant among neighbors*/
							if(highestFrequencyLabel != tabNodes[currentNode].getLabel()) {
								tabNodes[currentNode].setLabel(highestFrequencyLabel);
							}
						}
					}
					
					if(!rand) {
						if(highestFrequencyLabel == tabNodes[currentNode].getLabel()) {
							TT++;
						}
					}
					
					
					/*if(adjacancyList[currentNode].size() == 0) {
						remainingNodes.remove(currentNode);
						tabNodes[currentNode].setLabel(0);
					}*/
					
					//System.out.println("numberOccurenceOfTheHighestLabel is " + numberOccurenceOfTheHighestLabel);
					
					/*if(oneNeighbor) {
						//System.out.println("node has only one neighbor");
						//System.out.println("highestFrequencyLabel " + highestFrequencyLabel);
						//remainingNodes.remove(currentNode);
						//System.out.println("new size = " + remainingNodes.size());
						tabNodes[currentNode].setLabel(highestFrequencyLabel);
						//if(remainingNodes.size() < 400)
							//System.out.println("1");
						//System.out.println(currentNode + ".label = " + highestFrequencyLabel);
					}
					else {
						if(numberOccurenceOfTheHighestLabel == 1) {
							r = ThreadLocalRandom.current().nextInt(0, adjacancyList[currentNode].size());
							int randomNeighbor = adjacancyList[currentNode].get(r);
							randomLabel = tabNodes[randomNeighbor].getLabel();//here not highest but random
							//System.out.println("random label choose " + randomLabel);
							tabNodes[currentNode].setLabel(randomLabel);
							//System.out.println(currentNode + ".label = " + randomLabel);
							/*if(twoNeighbor)
								remainingNodes.remove(currentNode);
						}
						else {
							if(currentLabel == highestFrequencyLabel) {
								//System.out.println("node is good, remove");
								remainingNodes.remove(currentNode);
								//if(remainingNodes.size() < 400)
									//System.out.println("2");
								//System.out.println("new size = " + remainingNodes.size());
							}
							else {
								//System.out.println("label that occurs "+ numberOccurenceOfTheHighestLabel + " time is " + highestFrequencyLabel);
								tabNodes[currentNode].setLabel(highestFrequencyLabel);
								//System.out.println(currentNode + ".label = " + highestFrequencyLabel);
								//cpt = 0;
							}
						}
					}*/
					
					/*reinitialize tabLabel*/
					for(int j = 0;j<adjacancyList[currentNode].size();j++) {
						int neighbor = adjacancyList[currentNode].get(j);
						int label = tabNodes[neighbor].getLabel();
						if(tabLabel[label] != 0)
							tabLabel[label] = 0;
					}
				}
			}
			cpt++;
		}
		/*for(int e : remainingNodes) {
			System.out.println("e = " + e + " network[e] = " + network[e] + " adjacancyList " + adjacancyList[e] + " tabNodes[e] " + tabNodes[e]);
		}*/
		System.out.println("done");
		//SaveNumberOfCommunities(tabNodes);
	}
}
