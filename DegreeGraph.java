import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @authors ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class DegreeGraph {
	
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
			HashMap<String,Integer> hm = new HashMap<String,Integer>();
			
			/**
			 * Here we're using a HashMap which is a structure with a couple of (key,value), each key is unique. So the key is a node number, and the value is the degree of this node.
			 * so here for every line we get 2 numbers. Each number id added to the hashMap as a key.
			 * if there's already a key for this number, we update its value by adding 1 to the current value (that means the degree for this node is the previous degree + 1)
			 * if there's no key for this number, we add it to the HashMap with a degree of 1.
			 */
			try 
			{
				String line;
				String[] tab;
				while((line = br.readLine()) != null) {
					if(!line.contains("%")) {
						tab = line.split(" ");
						if(hm.get(tab[0]) != null) {
							hm.put(tab[0], hm.get(tab[0])+1);
						}
						else {
							hm.put(tab[0],1);
						}
						
						if(hm.get(tab[1]) != null) {
							hm.put(tab[1], hm.get(tab[1])+1);
						}
						else {
							hm.put(tab[1],1);
						}
					}
				}
				
				/*Iterate through the HashMap and print the degree of each node on the standard output, only 20 lines max*/
				int i = 0;
				for(Map.Entry<String, Integer> entry : hm.entrySet()) {
					if(i<20)
						System.out.println("The node " + entry.getKey() + " has a degree of " + entry.getValue());
					else
						break;
					i++;
				}
				System.out.println("Only 20 lines printed...");
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			br.close();
		}
	}
}
