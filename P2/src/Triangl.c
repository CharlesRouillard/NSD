/*
 ============================================================================
 Name        : Triangl.c
 Author      : adel
 Version     :
 Copyright   : Your copyright notice
 Description : AdjancyList for Triangle
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include "qsort.h"
#include "count.h"
#define new_max(x,y) ((x) >= (y)) ? (x) : (y)

struct Edge {
   int src;
   int dest;
};
int main(int argc, char* argv[])
{
    char const* const fileName = argv[1];
    FILE* file = fopen(fileName, "r");
    char line[256];

    int src, dest;
    int nb_nodes = 0;
    int nb_edges = 0;
    int * tabdegres;
    int size_tabdegres=10;
    int i = 0;

    tabdegres = calloc ( 10 , sizeof(int) );

    printf("Calculating degrees...\n");
    while (fgets(line, sizeof(line), file)) {
        char *token;
        token = strtok(line, " ");
        if(token[0]=='%')
        	continue;
        src = atoi(token);
        token = strtok(NULL, " ");
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

    printf("Building the Adjancy List...\n");
    //struct Edge Edges[nb_edges];
    int ** adjancy_list;
    int j;
    adjancy_list = calloc((nb_nodes+1), sizeof(int *));

    // We allocate memory for each neighborhood
    for(i=0; i <= nb_nodes; i++) {
    	if(tabdegres[i]!=0)
    		adjancy_list[i] = calloc ( tabdegres[i] , sizeof(int) );
    }

    rewind(file);
    j = 0;
    while (fgets(line, sizeof(line), file)) {
        char *token;
        token = strtok(line, " ");
        if(token[0]=='%')
        	continue;
        src = atoi(token);
        token = strtok(NULL, " ");
        dest = atoi(token);
        //MAJ Voisins
        for(i=0; i < tabdegres[src]; i++) {  // ajouter dest dans le voisinage de src
            	if(adjancy_list[src][i]==0){
            		adjancy_list[src][i]=dest;
            		break;
            	}
        }
        for(i=0; i < tabdegres[dest]; i++) {  // ajouter dest dans le voisinage de src
                if(adjancy_list[dest][i]==0){
                    adjancy_list[dest][i]=src;
                    break;
                }
        }
        //Edges[j].src=src;
        //Edges[j].dest=dest;
        j++;

    }

    // Sorting ...
    for(i=0; i<=nb_nodes;i++){
    	if(adjancy_list[i]!=0)
    		quickSort(adjancy_list[i],0,tabdegres[i]-1);
    }


    //Calculating number of triangles
    rewind(file);
    int nb_triangles=0;
    while (fgets(line, sizeof(line), file)) {
        char *token;
        token = strtok(line, " ");
        if(token[0]=='%')
        	continue;
        src = atoi(token);
        token = strtok(NULL, " ");
        dest = atoi(token);

        // We delete nodes in the intersect function
        int tmp = intersect(adjancy_list[src], adjancy_list[dest], tabdegres[src], tabdegres[dest],src,dest);
        nb_triangles+=tmp;

    }

    printf("Nb of triangles: %d\n", nb_triangles);
// {{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{

    fclose(file);
    free(adjancy_list);
    free(tabdegres);
    return 0;
}
