import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * 
 * @authors ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class SizeGraph {

	/**
	 * 
	 * @param args the file or pathname
	 * @throws IOException if the file does not exist
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.err.println("Need to pass a filename or a path in argument");
		}
		else {
			/*read the file pass in args*/
			String file = args[0];
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
				System.out.println("There is " + edges + " edges and " + hs.size() + " nodes");
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			br.close();
		}
	}
}