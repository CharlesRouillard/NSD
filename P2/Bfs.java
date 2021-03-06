import java.io.*;
import java.util.*;

/**
 * 
 * @author ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class Bfs {
	public static int nb = 0;
	
	private String file;
	private ArrayList<Integer>[] neighbor;
	private boolean[] mark;
	private ArrayList<Integer> fifo;
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Bfs(String file) throws IOException {
		this.file = file;
		this.setNb(file);
		
		this.neighbor = (ArrayList<Integer>[]) new ArrayList[nb+1];
		this.adjacency(this.file,this.neighbor);
		
		this.mark = new boolean[nb+1];
		this.markNode(this.mark);
		
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
		int n = 0,i = 0;
		
		this.fifo.add(node);
		this.mark[node] = true;
		
		while(!fifo.isEmpty() && i<20){
			
			n = this.fifo.get(0);
			this.fifo.remove(0);
			
			
			System.out.println(n);
			
			for(int v : this.neighbor[n]){
				if(!this.mark[v]){
					this.fifo.add(v);
					this.mark[v] = true;
				}
			}
			i++;
		}
	}
	
	public static void main(String[] args) throws IOException {
		if(args.length != 2){
			System.err.println("Usge: java Bfs <filename> <node>");
		}
		else{
			String file = args[0];
			int node = Integer.parseInt(args[1]);
				
			Bfs bfs = new Bfs(file);
			bfs.launch(node);
			System.out.println("Done. Printed only 20 first nodes");
		}
	}
}
