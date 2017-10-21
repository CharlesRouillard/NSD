import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Kcore {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		String file = args[0];
		BufferedReader br = new BufferedReader(new FileReader(file));
		HashSet<Integer> hs = new HashSet<Integer>();//list de tous les noeuds
		
		ArrayList<Integer>[] neighbor;
		PriorityQueue<BigNode> minHeap,finalHeap;
		
		
		String line;
		String[] tab;
		int nb = 0;
		int x,y;
		int[] adj;
		
		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%") && !line.contains("#")) {
					tab = line.split("\t");
					
					x = Integer.parseInt(tab[0]);
					y = Integer.parseInt(tab[1]);
					
					hs.add(x);
					hs.add(y);
					
					nb = Math.max(nb, Math.max(x, y));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
		
		neighbor = (ArrayList<Integer>[]) new ArrayList[nb+1];
		minHeap = new PriorityQueue<BigNode>(nb+1,new MyComparator());
		finalHeap = new PriorityQueue<BigNode>(nb+1,new MyComparator());
		
		br = new BufferedReader(new FileReader(file));
		
		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%") && !line.contains("#")) {
					tab = line.split("\t");
					x = Integer.parseInt(tab[0]);
					y = Integer.parseInt(tab[1]);
					
					if(neighbor[x] == null){
						neighbor[x] = new ArrayList<Integer>();
					}
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
			try {
				BigNode b = new BigNode(i,0,0,neighbor[i].size());
				minHeap.add(b);
			}
			catch(NullPointerException e) {
				continue;
			}
		}
		
		int i = nb, c = 0;
		
		while(!hs.isEmpty()) {
			System.out.println(hs.size());
			//System.out.println(hs.size());
			BigNode v = minHeap.peek();
			//System.out.println("Node with mini degree is " + v);
			c = Math.max(c, v.getDegree());
			v.setC(c);
			//System.out.println("new c = " + c);
			hs.remove(v.getNumber());
			//System.out.println("remove v");
			
			for(int j = 0;j<neighbor[v.getNumber()].size();j++) {
				int node = neighbor[v.getNumber()].get(j);
				
				/* PARTIE PROBLEME */
				for(BigNode b : minHeap) {
					if(b.getNumber() == node) {
						//System.out.println(node + " est voisin de " + v);
						b.setDegree(b.getDegree() - 1);
						//System.out.println("le degres de " + node + " passe a " + b.getDegree());
					}
				}
			}
			
			v.setEta(i);
			//System.out.println("Son eta passe a " + v.getEta());
			i--;
			finalHeap.add(minHeap.remove());
		}
		System.out.println("done");
	}
}
