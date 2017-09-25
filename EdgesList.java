import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @authors ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class EdgesList {
	
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
			ArrayList<String> al = new ArrayList<String>();
			
			/**
			 * Store each node of each in line in an arrayList
			 * if the first line is "x y" then it will store and prints '(x,y)'
			 **/
			try 
			{
				String line;
				String[] tab;
				while((line = br.readLine()) != null) {
					if(!line.contains("%")) {
						tab = line.split(" ");
						al.add("(" + tab[0] + "," + tab[1] + ")");
					}
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			br.close();
			
			for(int i = 0;i<al.size();i++) {
				System.out.println(al.get(i));
			}
		}
	}
}
