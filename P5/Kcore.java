package kcore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Kcore {
	
	public static void PrintKCore(ArrayList<Node> list) {
		for(Node n : list) {
			BigNode b = (BigNode)n.getData();
			System.out.println("number : " + b.getNumber() + " c = " + b.getC() + " eta = " + b.getEta());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		String file = args[0];
		BufferedReader br = new BufferedReader(new FileReader(file));
		HashSet<Integer> hs = new HashSet<Integer>();//list de tous les noeuds
		HashMap<BigNode,Integer> hm;
		
		Node[] tabNode;
		ArrayList<Integer>[] neighbor;
		FibonacciHeap minHeap,finalHeap;
		
		String line;
		String[] tab;
		int nb_nodes = 0;
		int x,y;

		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%") && !line.contains("#")) {
					tab = line.split("\t");
					
					x = Integer.parseInt(tab[0]);
					y = Integer.parseInt(tab[1]);
					
					hs.add(x);
					hs.add(y);
					
					nb_nodes = Math.max(nb_nodes, Math.max(x, y));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
		
		//System.out.println(nb_nodes + " " + hs.size());
		
		neighbor = (ArrayList<Integer>[]) new ArrayList[nb_nodes+1];
		hm = new HashMap<BigNode,Integer>(hs.size());
		tabNode = new Node[nb_nodes+1];
		minHeap = new FibonacciHeap();
		finalHeap = new FibonacciHeap();
		
		br = new BufferedReader(new FileReader(file));

		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%") && !line.contains("#")) {
					tab = line.split("\t");
					x = Integer.parseInt(tab[0]);
					y = Integer.parseInt(tab[1]);
					
					if(neighbor[x] == null){
						//System.out.println("create al");
						neighbor[x] = new ArrayList<Integer>();
					}
					//System.out.println("add " + y);
					neighbor[x].add(y);
					
					if(neighbor[y] == null){
						neighbor[y] = new ArrayList<Integer>();
					}
					neighbor[y].add(x);
					
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		br.close();

		for(int i = 1;i<neighbor.length;i++) {
			if(neighbor[i] != null) {
				BigNode b = new BigNode(i,0,0);
				Node n = minHeap.insert(b, neighbor[i].size());
				tabNode[i] = n;
			}
		}
		
		int i = nb_nodes, c = 0;

		while(!hs.isEmpty()) {
			//System.out.println("size");
			BigNode v = (BigNode)minHeap.min().getData();
			int degV = (int)minHeap.min().getKey();
			//System.out.println(v.getNumber());
			//System.out.println("number");
			c = Math.max(c, degV);
			//System.out.println("c");
			v.setC(c);
			//System.out.println("setc");
			hs.remove(v.getNumber());
			//System.out.println("remove");
			minHeap.removeMin();
			
			//System.out.println("bn");
			v.setDelete(true);
			//System.out.println("delete");
			//finalHeap.insert(v, degV);
			hm.put(v, degV);
			//System.out.println("heap");
			
			for(int j = 0;j<neighbor[v.getNumber()].size();j++) {
				//System.out.println("t1");
				Node n = tabNode[neighbor[v.getNumber()].get(j)];
				//System.out.println("t2");
				//System.out.println(n.getData());
				BigNode b = (BigNode)n.getData();
				//System.out.println("t3");
				if(!b.isDelete()) {
					//System.out.println("t4");
					minHeap.decreaseKey(n, n.getData(), (int)n.getKey() - 1);
					//System.out.println("t5");
				}
				v.setEta(i);
				//System.out.println("t6");
			}
			i--;
		}
		//PrintKCore(finalHeap.nodeList());
		
		int val = 0;
		for(int j = 0;j<hm.size();j++) {
			float t = 
			//val = Math.max(val, )
		}
	}
}
