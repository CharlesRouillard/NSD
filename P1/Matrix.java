import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @authors ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class Matrix {
	
	/**
	 * 
	 * @param args the file or pathname
	 * @throws IOException if the file does not exist
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Need to pass a filename or a path in argument");
		}
		else {
			String file = args[0];
			int nb_nodes = 0;
			
			/*First we count the number of nodes in the graph*/
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				 while ((line = br.readLine()) != null) {
					 if(!line.contains("%")) {
			    		String[] parts = line.split(" ");
			    		nb_nodes = Math.max(nb_nodes, Math.max(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
			    	}
				 }
				 br.close(); 
				 
				 /**
				  * Then we create a bidimensionnal array of boolean, if there is a path between node x and node y then matrix[x][y] = true
				  */
				 br = new BufferedReader(new FileReader(file));
				 boolean[][] matrix = new boolean[nb_nodes+1][nb_nodes+1];
				 
				 while ((line = br.readLine()) != null) {
					 if(!line.contains("%")) {
			    		String[] parts = line.split(" ");
			    		matrix[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])] = true;
			    		matrix[Integer.parseInt(parts[1])][Integer.parseInt(parts[0])] = true;
			    	}
				 }
				 
				 br.close();
				 
				/*Each cell of the matrix who's not true then it's false*/
	         	for(int i=0;i<nb_nodes;i++){
	         		for(int j=0;j<nb_nodes;j++){
	         			if(matrix[i][j]!=true){
	         				matrix[i][j] = false;
	         			}
	         		}
	         	}
	         	
	         	/*Then we print the matrix on the standard output. If true print '1' if false print '0'*/
	         	for(int i=0;i<matrix.length;i++) {
	         		for(int j = 0;j<matrix[i].length;j++) {
	         			if(matrix[i][j])
	         				System.out.print("1");
	         			else
	         				System.out.print("0");
	         		}
	         		System.out.println();
	         	}
				 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
