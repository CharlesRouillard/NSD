import java.io.*;
import java.util.*;

/**
 * 
 * @author ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class BfsDiameter{
	public static int nb = 0;
	
	private String file;
	private ArrayList<Integer>[] neighbor;
	private boolean[] mark;
	private int[] distances;
	private ArrayList<Integer> fifo;
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public BfsDiameter(String file) throws IOException {
		this.file = file;
		this.setNb(file);
		
		this.neighbor = (ArrayList<Integer>[]) new ArrayList[nb+1];
		this.adjacency(this.file,this.neighbor);
		
		this.mark = new boolean[nb+1];
		this.markNode(this.mark);
		
		this.distances = new int[nb+1];
		
		this.fifo = new ArrayList<Integer>();
	}

	/**
	 * set the public nb var to the number of nodes in the graph
	 * @param file
	 * @throws IOException
	 */
	public void setNb(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String[] tab;
		
		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%")) {
					tab = line.split(" ");
					nb = Math.max(nb, Math.max(Integer.parseInt(tab[0]), Integer.parseInt(tab[1])));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
	}
	
	/**
	 * set the adjacency list for the graph
	 * @param file
	 * @param al
	 * @throws IOException
	 */
	public void adjacency(String file, ArrayList<Integer>[] al) throws IOException{
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
			e.printStackTrace();
		}
		br.close();
	}
	
	/**
	 * use to mark node
	 * @param hm
	 */
	public void markNode(boolean[] hm){
		for(int i = 1;i<=nb;i++){
			hm[i] = false;
		}
	}
	
	/**
	 * launch the bfs process
	 * @param node
	 */
	public void launch(int node) {
		int n = 0;
		
		this.fifo.add(node);
		this.mark[node] = true;
		this.distances[node] = 0;
		
		while(!fifo.isEmpty()){
			n = this.fifo.get(0);
			this.fifo.remove(0);
			
			for(int v : this.neighbor[n]){
				if(this.distances[v] == 0)
					this.distances[v] = this.distances[n]+1;
				
				if(!this.mark[v]){
					this.fifo.add(v);
					this.mark[v] = true;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		if(args.length != 2){
			System.err.println("Usge: java Bfs <filename> <node>");
		}
		else{
			String file = args[0];
			int node = Integer.parseInt(args[1]);
			
			int distNodeMax = 0,diameter = 0;
				
			/*Found the diameter*/
			BfsDiameter bfs = new BfsDiameter(file);
			
			//do it twice to find the diameter
			for(int i = 0;i<3;i++) {
				bfs.launch(node);
				distNodeMax = Arrays.stream(bfs.distances).max().getAsInt();
				diameter = Math.max(diameter,distNodeMax);
				
				for(int j=0;j<bfs.distances.length;j++) {
					if(bfs.distances[j] == distNodeMax)
						node = j;
				}
				
				bfs = new BfsDiameter(file);
			}
			System.out.println("diameter found for the connected component associated with the node " + args[1] + " : " + diameter);
		}
	}
}
