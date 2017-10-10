/*
 * TP3.c
 *
 *  Created on: 7 oct. 2017
 *      Author: ZEGHLACHE Adel & ROUILLARD Charles
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>

#define new_max(x,y) ((x) >= (y)) ? (x) : (y)

void ProdMATVect(float *** M, int* deg_out, int nb_nodes, float* P){
	int i=0,j,t;
	float* tmp;
	tmp = malloc((nb_nodes+1)*sizeof(float));

	for(t=0;t<=nb_nodes;t++)
		tmp[t]=0;

	for(i =0; i<=nb_nodes; i++){
		for(j=0;j<deg_out[i];j++){
			tmp[(int)M[i][0][j]] += M[i][1][j] * P[i];
		}
	}

	for(t=0;t<=nb_nodes;t++)
		P[t]=tmp[t];
	free(tmp);
}

void normalize(float *P, int nb_nodes){
	float norm = 0;
	int i=0;

	for(i=0; i<=nb_nodes;i++ )
		norm += (P[i]);

	for(i=0; i<=nb_nodes;i++)
		P[i]+=(1-norm)/(nb_nodes+1);
}

int main(int argc, char* argv[]) {

	if(argc != 3){
		printf("Usage : %s <filename> <iteration>\n",argv[0]);
		return -1;
	}

	char line[256];
	char const* const fileName = argv[1];
    int i,j,src,t,dest,*deg_out, *deg_in, size_tabdegres = 1, nodeMin = 0, nodeMax = 0;
    float ***adjancy_list,alpha = 0.15,*P, valMin = 1.,valMax = 0.;
    long nb_nodes = 1;

    FILE* file = fopen(fileName, "r");

    deg_out = calloc ( 5 , sizeof(int) );
    deg_in = calloc(5, sizeof(int));

    while (fgets(line, sizeof(line), file)) {
        char *token;
        token = strtok(line, "\t");

        if(token[0]=='#')
        	continue;

        src = atoi(token);
        token = strtok(NULL, "\t");

        dest = atoi(token);
        nb_nodes = new_max(nb_nodes, new_max(src,dest));

        if(nb_nodes >= size_tabdegres){
        	int old_size = size_tabdegres;
        	deg_out = realloc (deg_out, (nb_nodes+1) * sizeof(int) );
        	deg_in = realloc(deg_in, (nb_nodes+1) * sizeof(int));

        	size_tabdegres = nb_nodes+1;
        	i = 0;

        	for(i=old_size; i<size_tabdegres;i++){
        		deg_out[i]=0;
        		deg_in[i] = 0;
        	}
        }

        deg_out[src]++;
        deg_in[dest]++;
    }

    adjancy_list = (float ***) malloc(nb_nodes*sizeof(float**));

    // We allocate memory for each neighborhood
    for(i=0; i < nb_nodes+1; i++) {
    	if(deg_out[i]!=0){
    		adjancy_list[i] = (float**) malloc( 2 * sizeof( float * ) );
    		for ( j = 0; j < 2; j++ )
    			adjancy_list[i][j] = calloc( deg_out[i], sizeof( float ) );

    	}
    }

    rewind(file);//position offset at the beg of the file
    j = 0;
    while (fgets(line, sizeof(line), file)) {
        char *token;
        token = strtok(line, "\t");
        if(token[0]=='#')
        	continue;
        src = atoi(token);
        token = strtok(NULL, "\t");
        dest = atoi(token);

        for(i=0; i < deg_out[src]; i++) {  // ajouter dest dans le voisinage de src

        	if(adjancy_list[src][0][i]==0){
            	adjancy_list[src][0][i] = dest;
            	adjancy_list[src][1][i] = 1./(deg_out[src]);
            	break;
        	}
        }
        adjancy_list[src][1][deg_out[src]-1] = 1./(deg_out[src]);
    }
    fclose(file);

    P = malloc((nb_nodes+1)*sizeof(float));

    for(i=0 ;i<=nb_nodes; i++){
    	P[i] = 1./((nb_nodes+1));
    }

    t = atoi(argv[2]);

    for(i=0;i<t;i++){
    	ProdMATVect(adjancy_list,deg_out,nb_nodes, P);

        for(j=0 ;j<=nb_nodes; j++){
        	P[j] = P[j]*(1-alpha) + (alpha/(nb_nodes+1));
        }

        normalize(P, nb_nodes);
    }

    for(i = 1;i<=nb_nodes;i++){
    	if(P[i] > valMax){
    		nodeMax = i;
    		valMax = P[i];
    	}

    	if(P[i] < valMin && (deg_out[i] != 0 && deg_in[i] != 0)){
    		nodeMin = i;
    		valMin = P[i];
    	}
    }

    printf("Node min = %d (value = %.10f), node max = %d (value = %.10f)\n",nodeMin, valMin, nodeMax, valMax);

    free(P);
    free(deg_out);
    free(deg_in);
    free(adjancy_list);

	return EXIT_SUCCESS;
}

