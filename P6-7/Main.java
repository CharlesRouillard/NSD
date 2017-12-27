//package p6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static void main(String[] args) throws IOException {
		Node[] tabNode = new Node[400];
		Node n;
		double p = 0.4,q = 0.01;
		BufferedWriter writer = new BufferedWriter(new FileWriter("data/graph.txt"));
		
		for(int i = 0;i<400;i++) {
			n = new Node();
			n.setNb(i);
			if(i < 100)
				n.setCluster(1);
			else if(i >= 100 && i < 200)
				n.setCluster(2);
			else if(i >= 200 && i < 300) 
				n.setCluster(3);
			else if(i >= 300 && i < 400)
				n.setCluster(4);
			
			tabNode[i] = n;
		}
		
		for(int i = 0;i<400;i++) {
			for(int j = i;j<400;j++) {
				if(i != j) {
					double rand = ThreadLocalRandom.current().nextDouble();
					//System.out.println(" TabNode[" + i + "].getCluster() = " + tabNode[i].getCluster() + " TabNode[" + j + "].getCluster() = " + tabNode[j].getCluster());
					if(tabNode[i].getCluster() == tabNode[j].getCluster()) {
						if(rand <= p) {
							writer.write(i + "\t" + j + "\n");
						}
					}
					else {
						//System.out.println(rand <= q);
						if(rand <= q) {
							writer.write(i + "\t" + j + "\n");
						}
					}
				}
			}
		}
		
		writer.close();
	}
}
