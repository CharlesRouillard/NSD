import java.io.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class DegreeDist {
	public static void main(String[] args) throws IOException {
		
		/*read the file and create a HashMap to store the degree of each node*/
		BufferedReader br = new BufferedReader(new FileReader("data/out.roadNet-CA"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("out/degree.txt"));
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
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
			
			/*Create a second HashMap to count the degree distribution*/
			HashMap<Integer,Integer> hm2 = new HashMap<Integer,Integer>();
			
			/*Iterate through values of the first HashMap to count the degree, key is the degree, and value is the number of nodes with this degree*/
			for(Integer values : hm.values()) {
				if(hm2.get(values) != null)
					hm2.put(values, hm2.get(values)+1);
				else
					hm2.put(values, 1);
			}
			
			/*sorted and write the distribution on a file*/
			SortedSet<Integer> ss = new TreeSet<Integer>(hm2.keySet());
			Iterator<Integer> it = ss.iterator();
			int key;
			
			while(it.hasNext()) {
				key = it.next();
				bw.write(key + " " + hm2.get(key) + "\n");
			}
			
			System.out.println("done");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		br.close();
		bw.close();
	}
}
