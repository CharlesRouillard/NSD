import java.io.*;
import java.util.*;

/**
 * 
 * @author ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class BfsConnectedComponent {
	public static int nb = 0;
	
	private String file;
	private ArrayList<Integer>[] neighbor;
	private HashSet<Integer> hs;
	private boolean[] mark;
	private ArrayList<Integer> fifo;
	
	
	@SuppressWarnings("unchecked")
	public BfsConnectedComponent(String file) throws IOException {
		this.file = file;
		this.setNb(file);
		
		this.neighbor = (ArrayList<Integer>[]) new ArrayList[nb+1];
		this.adjacency(this.file,this.neighbor);
		
		this.hs = this.getNodes(file);
		
		this.mark = new boolean[nb+1];
		this.markNode(this.mark);
		
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
	
	public void markNode(boolean[] hm){
		for(int i = 1;i<=nb;i++){
			hm[i] = false;
		}
	}
	
	public void launch(int node, HashSet<Integer> S) {
		int n = 0;
		
		this.fifo.add(node);
		
		this.mark[node] = true;
		
		while(!fifo.isEmpty()){
			n = this.fifo.get(0);
			this.fifo.remove(0);
			
			this.hs.remove(n);
			S.add(n);
			
			for(int v : this.neighbor[n]){
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
				
			/*output connected component and size*/
			BfsConnectedComponent bfs = new BfsConnectedComponent(file);
			
			ArrayList<HashSet<Integer>> al = new ArrayList<HashSet<Integer>>();
			HashSet<Integer> S = new HashSet<Integer>();
			
			while(!bfs.hs.isEmpty()) {
				Random rnd = new Random();
				int i = rnd.nextInt(bfs.hs.size());
				node = (int)bfs.hs.toArray()[i];
				
				bfs.launch(node,S);
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
