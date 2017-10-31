#include "Kcore.h"

int Mkscore(edge *edges,int size,int nb_nodes,int t){
	int *r = calloc((nb_nodes+1), sizeof(int));
	float *finalr = calloc((nb_nodes+1), sizeof(float));
	int i,cpt = 0,size_densest = 0,Mh = 0,Nh = 0;
	float max_score = 0.;

	while(cpt < t){
		for(i = 0;i<size;i++){
			edge e = edges[i];
			if(r[e.i] <= r[e.j]){
				r[e.i]++;
			}
			else{
				r[e.j]++;
			}
		}
		cpt++;
	}

	for(i = 1;i<=nb_nodes;i++){
		if(r[i] != 0){
			float d = (float)r[i]/t;
			float near = roundf(d);
			finalr[i] = near;
			if(max_score < near){
				max_score = near;
			}
		}
	}

	for(i = 1;i<=nb_nodes;i++){
		if(finalr[i] != 0.){
			if(finalr[i] == max_score){
				size_densest++;
			}
		}
	}

	Nh = size_densest;

	for(i = 0;i<size;i++){
		edge e = edges[i];
		if(finalr[e.i] == max_score && finalr[e.j] == max_score){
			Mh++;
		}
	}

	float add = (float)Mh/Nh;
	float ed = (float)(2*Mh) / (Nh*(Nh-1));

	printf("==============Mkscore of a densest prefix for a non increasing density score ordering==============\nMh = %d, Nh = %d\nAverage degree density : %f\nEdge density = %f\nsize %d\n\n",Mh,Nh,add,ed,size_densest);
	fflush(stdout);

	return EXIT_SUCCESS;
}

int anomalous(char const* const coAuthors){
	printf("anomalous\n");
	fflush(stdout);

	FILE* fileGraph = fopen(coAuthors, "r");
	if(fileGraph == NULL){
		printf("Error while opening %s\n",coAuthors);
		return -1;
	}
	FILE* finalFile = fopen("final.txt", "w+");

	char line[256];
	int src,dest,nb_nodes = 0,* tabdegres,size_tabdegres=10,i = 0,j= 0,* isDeleted,**adjancy_list;

	tabdegres = calloc (10 , sizeof(int));

	/*read the co authors links and make kcore*/
	while (fgets(line, sizeof(line), fileGraph)) {
		char *token;
		token = (char *)strtok(line, " ");
		if(token[0]=='%')
			continue;
		src = atoi(token);
		token = (char *)strtok(NULL, " ");
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
	}

	adjancy_list = calloc((nb_nodes+1), sizeof(int *));
	for(i=0; i <= nb_nodes; i++) {
		if(tabdegres[i]!=0)
			adjancy_list[i] = calloc ( tabdegres[i] , sizeof(int) );
	}

	rewind(fileGraph);
	while (fgets(line, sizeof(line), fileGraph)) {
		char *token;
		token = strtok(line, " ");
		if(token[0]=='%')
			continue;
		src = atoi(token);
		token = strtok(NULL, " ");
		dest = atoi(token);

		for(i=0; i < tabdegres[src]; i++) {
			if(adjancy_list[src][i]==0){
				adjancy_list[src][i]=dest;
				break;
			}
		}
		for(i=0; i < tabdegres[dest]; i++) {
			if(adjancy_list[dest][i]==0){
				adjancy_list[dest][i]=src;
				break;
			}
		}
	}

	keyvalue kv;
	bheap* heap=construct(nb_nodes);
	int size_heap = 0;
	for (i=1;i<=nb_nodes;i++){
		if(tabdegres[i]!=0){
			size_heap++;
			kv.key=i;
			kv.value=tabdegres[i];
			insert(heap,kv);
		}
	}

	i = nb_nodes;
	int c = 0;
	keyvalue k;
	int v;
	isDeleted = calloc ( nb_nodes , sizeof(int) );

	while(size_heap!=0){
		k = popmin(heap);
		v = k.key;
		c = new_max(c,k.value);

		//write in final.txt node c-value degree
		fprintf(finalFile,"%d %d %d\n",k.key,c,tabdegres[k.key]);

		for(j=0;j<tabdegres[v];j++){
			if(isDeleted[adjancy_list[v][j]]==0){
				update(heap,adjancy_list[v][j],1);
			}
		}
		isDeleted[v] = 1;
		i--;
		size_heap--;
	}

	printf("==============Graph mining with k-core==============\nFile used : %s\nfile 'final.txt' created. run 'gnuplot plot.txt' to create the plot of the google scholar dataset in png format\n\n",coAuthors);
	fflush(stdout);

	fclose(fileGraph);
	fclose(finalFile);
	free(tabdegres);
	free(isDeleted);
	free(adjancy_list);
	freeheap(heap);

	return EXIT_SUCCESS;
}

int main(int argc, char** argv){
	if(argc != 3){
		printf("Usage : %s <filename of graph> <filename of the list of undirected co-authorship links of the google scholar dataset>",argv[0]);
		return -1;
	}

	char const* const coAuthors = argv[2];

	char const* const fileName = argv[1];
	printf("File used : %s\n\n",fileName);
	fflush(stdout);

    FILE* file = fopen(fileName, "r");
    if(file == NULL){
    	printf("Error while opening %s\n",fileName);
    	return -1;
    }

    char line[256];
    int src, dest,nb_nodes = 0,nb_edges = 0,ptr = 0,* tabdegres,size_tabdegres=10,i = 0,j= 0,* isDeleted,**adjancy_list;
    edge *edges;
    tabdegres = calloc (10 , sizeof(int));
    while (fgets(line, sizeof(line), file)) {
        char *token;
        token = (char *)strtok(line, "\t");
        if(token[0]=='%')
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
        //MAJ DEGRES
        tabdegres[src]++;
        tabdegres[dest]++;
        nb_edges++;
    }
    adjancy_list = calloc((nb_nodes+1), sizeof(int *));
    edges = malloc((nb_edges) * sizeof(edge));
    // We allocate memory for each neighborhood
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
        //pour Mkscore
        edge e;
		e.i = src;
		e.j = dest;
		edges[ptr++] = e;
        //MAJ Voisins
        for(i=0; i < tabdegres[src]; i++) {  // ajouter dest dans le voisinage de src
			if(adjancy_list[src][i]==0){
				adjancy_list[src][i]=dest;
				break;
			}
        }
        for(i=0; i < tabdegres[dest]; i++) {  // ajouter src dans le voisinage de dest
			if(adjancy_list[dest][i]==0){
				adjancy_list[dest][i]=src;
				break;
			}
        }
       j++;
    }
    keyvalue kv;
	bheap* heap=construct(nb_nodes);
	int size_heap = 0;
	for (i=1;i<=nb_nodes;i++){
		if(tabdegres[i]!=0){
			size_heap++;
			kv.key=i;
			kv.value=tabdegres[i];
			insert(heap,kv);
		}
	}
	//######### CORE DECOMPOSITION ########
	i = nb_nodes;
	int c = 0;
	keyvalue k;
	int v;
    isDeleted = calloc ( nb_nodes , sizeof(int) );
    int * pos_core = calloc ( nb_nodes , sizeof(int) );
    while(size_heap!=0){
		k = popmin(heap);
		v = k.key;
		c = new_max(c,k.value);
		for(j=0;j<tabdegres[v];j++){
			if(isDeleted[adjancy_list[v][j]]==0){
				update(heap,adjancy_list[v][j],1);
			}
		}

		pos_core[v] = i;

		isDeleted[v] = 1;
		i--;
		size_heap--;
	}

    int * tab_haut = calloc ( nb_nodes , sizeof(int) );
	int * tab_bas = calloc ( nb_nodes , sizeof(int) );
	int somme_nav = 0;

	for(i=1;i<=nb_nodes;i++){ // pour chaque noeud
		int n_av = 0;
		for(j=0;j<tabdegres[i];j++){ // pour chaque voisin
			if(pos_core[i]>pos_core[adjancy_list[i][j]])
				n_av++;
		}
		tab_haut[pos_core[i]-1]= n_av;

	}

	for(i=0;i<nb_nodes;i++){  // On remplie tab_bas
		somme_nav +=tab_haut[i];
		tab_bas[i] = somme_nav;
	}

	float * tab_ratios = calloc ( nb_nodes , sizeof(int) );
	float best_ratio = 0;
	int pos_best_ratio = 0;
	float edge_density = 0;

	for(i=0;i<nb_nodes;i++){
		tab_ratios[i] = (float)tab_bas[i]/(i+1);
		if(best_ratio<tab_ratios[i]){
			best_ratio = tab_ratios[i];
			edge_density = (float)(2*tab_bas[i])/((i+1)*(float)i);
			pos_best_ratio = i+1;
		}
	}

    printf("==============k-core decomposition of a densest core ordering prefix==============\nAverage degree density = %f\nEdge density = %f\nsize = %d\n\n",tab_ratios[pos_best_ratio-1],edge_density,pos_best_ratio);
    fflush(stdout);

    anomalous(coAuthors);

    Mkscore(edges,nb_edges,nb_nodes,100);

    fclose(file);
    free(tab_ratios);
    free(tab_bas);
    free(tab_haut);
    free(tabdegres);
	free(isDeleted);
	free(adjancy_list);
	free(edges);
	freeheap(heap);

	return EXIT_SUCCESS;
}
