import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class SizeGraph {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/out.orkut-links"));
		HashSet<String> hs = new HashSet<String>();
		try 
		{
			int edges = 0;
			String line;
			String[] tab;
			while((line = br.readLine()) != null) {
				if(!line.contains("%")) {
					edges++;
					tab = line.split(" ");
					hs.add(tab[0]);
					hs.add(tab[1]);
				}
			}
			
			System.out.println("There is " + edges + " edges and " + hs.size() + " nodes");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
	}
}