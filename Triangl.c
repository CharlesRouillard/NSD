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
#define new_max(x,y) ((x) >= (y)) ? (x) : (y)

int main(int argc, char* argv[])
{
    char const* const fileName = argv[1];
    FILE* file = fopen(fileName, "r");
    char line[256];

    int src, dest;
    int nb_nodes = 0;
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
    }

    printf("Building the Adjancy List...\n");

    int ** adjancy_list;
    int j;
    adjancy_list = calloc((nb_nodes+1), sizeof(int *));

    // We allocate memory for each neighborhood
    for(i=0; i <= nb_nodes; i++) {
    	if(tabdegres[i]!=0)
    		adjancy_list[i] = calloc ( tabdegres[i] , sizeof(int) );
    }

    rewind(file);

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
    }

/*
    for(i = 0; i<=nb_nodes;i++){
    	printf("%d => ", i);
    	for(j=0; j<tabdegres[i]; j++){
    		printf(" %d :",adjancy_list[i][j]);
    	}
    	printf("\n");
    }
*/
// {{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{
    printf("max : %d\n", nb_nodes);
    printf("size tab degres : %d", size_tabdegres);

    fclose(file);

    free(adjancy_list);
    free(tabdegres);
    return 0;
}
