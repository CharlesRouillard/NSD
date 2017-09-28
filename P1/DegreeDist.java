import java.io.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 
 * @authors ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class DegreeDist {
	
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
			String file = args[0];
			BufferedReader br = new BufferedReader(new FileReader(file));
			BufferedWriter bw = new BufferedWriter(new FileWriter("degree.txt")); //The file text with the degree distribution will be store in a file called degree.txt in the same directory that the execution
			HashMap<String,Integer> hm = new HashMap<String,Integer>();
			
			/**
			 * First we count the degree of each node as the same as DegreeGraph.java
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
				
				/*Then we create a second HashMap to count the degree distribution*/
				HashMap<Integer,Integer> hm2 = new HashMap<Integer,Integer>();
				
				/*Iterate through values of the first HashMap to count the degree, key is the degree, and value is the number of nodes with this degree*/
				for(Integer values : hm.values()) {
					if(hm2.get(values) != null)
						hm2.put(values, hm2.get(values)+1);
					else
						hm2.put(values, 1);
				}
				
				/*sorted the seconde hashMap and write the distribution on a file*/
				SortedSet<Integer> ss = new TreeSet<Integer>(hm2.keySet());
				Iterator<Integer> it = ss.iterator();
				int key;
				
				while(it.hasNext()) {
					key = it.next();
					bw.write(key + " " + hm2.get(key) + "\n");
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			br.close();
			bw.close();
		}
	}
}
