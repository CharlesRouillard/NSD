package p6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LabelPropagationBis {
	
	private static void FisherYates(LabelNode[] network)
	{
	    int index;
	    LabelNode temp;
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
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String line;
		String[] tab;
		int numberMaxNode = 0,nbNodes = 0,x,y;
		LabelNode[] network;
		ArrayList<LabelNode>[] adjacancyList;
		HashSet<Integer> hs = new HashSet<Integer>();
		HashMap<Integer,Integer> maxLabel;
		
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
		
		adjacancyList = (ArrayList<LabelNode>[]) new ArrayList[numberMaxNode+1];
		network = new LabelNode[numberMaxNode+1];
		maxLabel = new HashMap<Integer,Integer>();
		
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
					
					if(network[x] == null) {
						network[x] = nodeSrc;
					}
					
					if(network[y] == null) {
						network[y] = nodeDest;
					}
									
					if(adjacancyList[x] == null){
						adjacancyList[x] = new ArrayList<LabelNode>();
					}
					adjacancyList[x].add(nodeDest);
					
					if(adjacancyList[y] == null){
						adjacancyList[y] = new ArrayList<LabelNode>();
					}
					adjacancyList[y].add(nodeSrc);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
		
		
		
		int cpt = 0;
		//while(cpt != nbNodes) {
			//shuffle network
			FisherYates(network);
			
			/*iterate through shuffle network*/
			for(int i = 1;i<=numberMaxNode;i++) {
				//if network[i] exist, that mean node i does not exist
				if(network[i] != null) {
					LabelNode currentLabelNode = network[i];
					int currentNode = currentLabelNode.getNumber();
					
					/*iterate through de adjacancy list of the current node in network*/
					for(int j = 0;j<adjacancyList[currentNode].size();j++) {
						int neighborLabel = adjacancyList[currentNode].get(j).getLabel();
						
						if(maxLabel.get(neighborLabel) == null)
							maxLabel.put(neighborLabel, 1);
						else
							maxLabel.put(neighborLabel, maxLabel.get(neighborLabel)+1);
					}
					
					//System.out.println(maxLabel);
					
					/*find the highest frequency of neighborLabel*/
					int highest = Collections.max(maxLabel.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getValue();
					
					/*if highest = 1, then choose a random label*/
					if(highest == 1){
						int r = ThreadLocalRandom.current().nextInt(0, adjacancyList[currentNode].size());
						int randomNeighborLabel = adjacancyList[currentNode].get(r).getLabel();
						//System.out.println("random label choose " + randomLabel);
						currentLabelNode.setLabel(randomNeighborLabel);
					}
					else {
						/*if not then set current node's label if it's its label is different from the new one*/
						int highestLabel = Collections.max(maxLabel.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
						currentLabelNode.setLabel(highestLabel);				
					}
									
					maxLabel.clear();
				}
			}
			for(int i = 1;i<=numberMaxNode;i++) {
				System.out.println(network[i]);
			}
			
			//cpt++;
		//}
	}
}
