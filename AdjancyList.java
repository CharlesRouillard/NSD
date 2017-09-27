import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class AdjancyList {

	@SuppressWarnings("unchecked")
	public static void main(String[] args)throws IOException {
		if(args.length != 1){
			System.err.println("Usge: java Bfs <filename>");
		}
		else {
			String file = args[0];
			int nb = 0;
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
				
				nb =  hs.size();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			br.close();
			
			
			
			ArrayList<Integer>[] al;
			al = (ArrayList<Integer>[]) new ArrayList[nb+1];
			
			br = new BufferedReader(new FileReader(file));
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

			br.close();
			
			try {
				for(int i = 1;i<=20;i++) {
					System.out.println("Node " + i + " : " + al[i].toString());
				}
				System.out.println("Only 20 lines printed...");
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Done");
			}
		}
	}
}
