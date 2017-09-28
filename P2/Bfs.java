package dfs_bfs;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Bfs {
	
	public static int nb = 0;
	
	
	public static int getNumberOfNodes(String file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		HashSet<String> hs = new HashSet<String>();
		/*count the line in the file to get the number of edges, and every node in a HashSet that will demete the duplicate nodes, so at the end, the size of the HashSet is the number od nodes*/
		try 
		{
			int edges = 0;
			String line;
			String[] tab;
			while((line = br.readLine()) != null) {
				/*avoid % char*/
				if(!line.contains("%")) {
					edges++;
					tab = line.split(" ");
					hs.add(tab[0]);
					hs.add(tab[1]);
				}
			}
			
			/*print the number of edges and nodes on the standard output*/
			nb =  hs.size();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
		
		return nb;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Integer>[] adjancy(String file) throws IOException{
		ArrayList<Integer>[] al;
		int nb = Bfs.getNumberOfNodes(file);
		al = (ArrayList<Integer>[]) new ArrayList[nb+1];
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String[] tab;
		
		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%")) {
					tab = line.split(" ");
					

					
					if(al[Integer.parseInt(tab[0])] == null){
						al[Integer.parseInt(tab[0])] = new ArrayList<Integer>();
					}

					al[Integer.parseInt(tab[0])].add(Integer.parseInt(tab[1]));
					
					if(al[Integer.parseInt(tab[1])] == null){
						al[Integer.parseInt(tab[1])] = new ArrayList<Integer>();
					}
					al[Integer.parseInt(tab[1])].add(Integer.parseInt(tab[0]));
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return al;
	}
	
	public static HashMap<Integer,Boolean> mark(){
		HashMap<Integer,Boolean> hm = new HashMap<Integer,Boolean>();
		for(int i = 1;i<=nb;i++){
			hm.put(i, false);
		}
		
		return hm;
	}

	public static void main(String[] args) throws IOException {
		if(args.length != 2){
			System.err.println("Usge: java Bfs <filename> <node>");
		}
		else{
			String file = args[0];
			int node = Integer.parseInt(args[1]);
			ArrayList<Integer>[] adjancy;
			adjancy = Bfs.adjancy(file);
			HashMap<Integer,Boolean> hm = Bfs.mark();
			
			//pour chaque noeud, la liste des voisins
			ArrayList<Integer> fifo = new ArrayList<Integer>();
			fifo.add(node);
			hm.put(node, true);
			
			int i = 0;
			while(!fifo.isEmpty() && i < 20){
				int n = fifo.get(0);
				fifo.remove(0);
				System.out.println(n);
				for(int v : adjancy[n]){
					if(hm.get(v) == false){
						fifo.add(v);
						hm.put(v, true);
					}
				}
				i++;
			}
			System.out.println("Only printed the 20th first line of the BFS");
		}
	}
}
