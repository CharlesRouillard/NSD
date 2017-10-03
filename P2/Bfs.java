package dfs_bfs;

import java.io.*;
import java.util.*;

public class Bfs {
	public static int nb = 0;
	
	private String file;
	private ArrayList<Integer>[] adj;
	private HashMap<Integer,Boolean> hm;
	private HashSet<Integer> hs;
	private HashMap<Integer,Integer> distances;
	private ArrayList<Integer> fifo;
	
	
	@SuppressWarnings("unchecked")
	public Bfs(String file) throws IOException {
		this.file = file;
		this.setNb(file);
		
		this.adj = (ArrayList<Integer>[]) new ArrayList[nb+1];
		this.adjacency(this.file,this.adj);
		
		this.hm = new HashMap<Integer,Boolean>();
		this.mark(this.hm);
		
		this.hs = this.getNodes(file);
		this.distances = new HashMap<Integer,Integer>();
		this.fifo = new ArrayList<Integer>();
	}

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
	
	public void mark(HashMap<Integer,Boolean> hm){
		for(int i = 1;i<=nb;i++){
			hm.put(i, false);
		}
	}
	
	public HashSet<Integer> getNodes(String file) throws IOException {
		HashSet<Integer> hs = new HashSet<Integer>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String[] tab;
		
		try {
			while((line = br.readLine()) != null) {
				if(!line.contains("%")) {
					tab = line.split(" ");
					hs.add(Integer.parseInt(tab[0]));
					hs.add(Integer.parseInt(tab[1]));
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		br.close();
		
		return hs;
	}
	
	public void launch(int node) {
		int n = 0;
		
		this.fifo.add(node);
		this.hm.put(node, true);
		this.distances.put(node, 0);
		
		while(!fifo.isEmpty()){
			n = this.fifo.get(0);
			this.fifo.remove(0);
			
			for(int v : this.adj[n]){
				if(this.distances.get(v) == null)
					this.distances.put(v, this.distances.get(n)+1);
				
				if(!this.hm.get(v)){
					this.fifo.add(v);
					this.hm.put(v, true);
				}
			}
		}
	}
	
	public void launchB(int node, HashSet<Integer> S) {
		int n = 0;
		
		this.fifo.add(node);
		this.hm.put(node, true);
		
		while(!fifo.isEmpty()){
			n = this.fifo.get(0);
			this.fifo.remove(0);
			
			this.hs.remove(n);
			S.add(n);
			
			for(int v : this.adj[n]){
				if(!this.hm.get(v)){
					this.fifo.add(v);
					this.hm.put(v, true);
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
			int distNodeMax = 0,diameter = 0, choix;
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Would you like to :\nFound the diameter of the graph, then press 1\nFound all connected compenents and their sizes, then press 2");
			choix = sc.nextInt();
			
			while(choix != 1 && choix != 2) {
				System.out.println("Would you like to :\nFound the diameter of the graph, then press 1\nFound all connected compenents and their sizes, then press 2");
				choix = sc.nextInt();
			}
			sc.close();
				
			if(choix == 1) {
				/*Found the diameter*/
				
				Bfs bfs = new Bfs(file);
				//do it twice to find the diameter
				for(int i = 0;i<3;i++) {
					bfs.launch(node);
					distNodeMax = Collections.max(bfs.distances.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
					diameter = Math.max(diameter,bfs.distances.get(distNodeMax));
					node = distNodeMax;
					bfs = new Bfs(file);
				}
				System.out.println("diameter found : " + diameter);
			}
			else if(choix == 2) {
				/*output connected component and size*/
				
				Bfs bfs = new Bfs(file);
				ArrayList<HashSet<Integer>> al = new ArrayList<HashSet<Integer>>();
				HashSet<Integer> S = new HashSet<Integer>();
				
				while(!bfs.hs.isEmpty()) {
					Random rnd = new Random();
					int i = rnd.nextInt(bfs.hs.size());
					node = (int)bfs.hs.toArray()[i];
					
					bfs.launchB(node,S);
					al.add(S);
					S = new HashSet<Integer>();
				}
				System.out.println(al.size() + " connected component");
				for(int i = 0;i<al.size();i++) {
					System.out.println("Connected component " + (i+1) + " (size = " + al.get(i).size() + ")" + " : " + al.get(i));
				}
			}
		}
	}
}
