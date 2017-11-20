#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <math.h>
#include <time.h>

#define new_max(x,y) ((x) >= (y)) ? (x) : (y)

typedef struct {
	unsigned int number;
	unsigned int label;
} LPNode;

int FisherYates(LPNode *network, int n) {
     int i, j;
	 LPNode tmp;

     for (i = n - 1; i > 0; i--) {
         j = rand() % (i + 1);
         if(j != 0){
        	 tmp = network[j];
			 network[j] = network[i];
			 network[i] = tmp;
         }
     }
     return 0;
}

int main(int argc, char **argv) {
	srand(time(NULL));
	if(argc != 2){
		printf("Usage : %s <filename>",argv[0]);
		return -1;
	}
	char const* const fileName = argv[1];
	FILE* file = fopen(fileName, "r");
	if(file == NULL){
		printf("Error while opening %s\n",fileName);
		return -1;
	}

	char line[256];
	int src, dest,nb_nodes = 0,nb_edges = 0,* tabdegres,size_tabdegres=10,i = 0,j= 0, **adjancy_list,*tab_label;
	LPNode *network;

	tabdegres = calloc (10 , sizeof(int));

	while (fgets(line, sizeof(line), file)) {
		char *token;
		token = (char *)strtok(line, "\t");
		if(token[0]=='#')
			continue;
		src = atoi(token);
		token = (char *)strtok(NULL, "\t");
		dest = atoi(token);

		nb_nodes = new_max(nb_nodes, new_max(src,dest));

		if(nb_nodes >= size_tabdegres){
			int old_size = size_tabdegres;
			tabdegres = realloc (tabdegres, (nb_nodes+1) * sizeof(int) );
			size_tabdegres = nb_nodes+1;
			for(int i=old_size; i<size_tabdegres;i++){
				tabdegres[i]=0;
			}
		}

		tabdegres[src]++;
		tabdegres[dest]++;
		nb_edges++;
	}

	adjancy_list = calloc((nb_nodes+1), sizeof(int *));
	network = calloc((nb_nodes+1), sizeof(LPNode));
	tab_label = calloc((nb_nodes+1), sizeof(int));

	for(i=0; i <= nb_nodes; i++) {
		if(tabdegres[i]!=0)
			adjancy_list[i] = calloc ( tabdegres[i] , sizeof(int) );
	}
	rewind(file);
	j = 0;
	while (fgets(line, sizeof(line), file)) {
		char *token;
		token = strtok(line, "\t");
		if(token[0]=='%')
			continue;
		src = atoi(token);
		token = strtok(NULL, "\t");
		dest = atoi(token);

		LPNode node_src,node_dest;
		node_src.number = src;
		node_src.label = src;

		node_dest.number = dest;
		node_dest.label = dest;

		if(network[src].number == 0)
			network[src] = node_src;
		if(network[dest].number == 0)
			network[dest] = node_dest;

		for(i=0; i < tabdegres[src]; i++) {// ajouter dest dans le voisinage de src
			//printf("src %d\n",adjancy_list[src][i].number);
			if(adjancy_list[src][i]==0){
				adjancy_list[src][i]=dest;
				break;
			}
		}
		for(i=0; i < tabdegres[dest]; i++) {// ajouter src dans le voisinage de dest
			//printf("dest %d\n",adjancy_list[dest][i].number);
			if(adjancy_list[dest][i]==0){
				adjancy_list[dest][i]=src;
				break;
			}
		}
	   j++;
	}

	int cpt = 0;
	while(cpt < 1000){
		/*printf("%d\n",cpt);
		fflush(stdout);*/
		//arrange node in a random order
		FisherYates(network, (nb_nodes+1));

		//for each node, we set the label to a label occuring with the highest frequency among its neighbours
		int highest_frequency_label = 0, number_occurence_of_the_highest_label = 0,r = 0;
		for(i=1; i <= nb_nodes; i++) {
			if(tabdegres[i] > 0){
				//tab_label[network[i].label]++;
				for(j=0; j<tabdegres[i]; j++){
					int neighbor = adjancy_list[i][j];
					int label = network[neighbor].label;
					tab_label[label]++;
					if(tab_label[label] >= number_occurence_of_the_highest_label){
						number_occurence_of_the_highest_label = tab_label[label];
						highest_frequency_label = label;
					}
				}

				if(number_occurence_of_the_highest_label == 1){
					//all neighbour's label are different, choose randomly
					r = rand()%tabdegres[i];
					int randomNeighbor = adjancy_list[i][r];
					highest_frequency_label = network[randomNeighbor].label;
					//printf("random label choose %d\n",highest_frequency_label);
				}
				else{
					//printf("label that occurs %d time is %d\n",number_occurence_of_the_highest_label,highest_frequency_label);
				}

				//printf("%d.label etait %d\n",network[i].number,network[i].label);
				network[i].label = highest_frequency_label;
				//printf("%d.label devient %d\n",network[i].number,network[i].label);

				for(j=0; j<tabdegres[i]; j++){
					int neighbor = adjancy_list[i][j];
					int label = network[neighbor].label;
					if(tab_label[label] != 0)
						tab_label[label] = 0;
				}

				number_occurence_of_the_highest_label = 0;
			}
		}
		cpt++;
	}

	FILE* graph = fopen("data/label.txt", "w+");
	for(i=1; i <= nb_nodes; i++){
		if(network[i].number != 0 && network[i].label != 0){
			fprintf(graph,"%d %d\n",network[i].number,network[i].label);
			//printf("%d %d\n",network[i].number,network[i].label);
		}
	}

	return EXIT_SUCCESS;
}
