/*
 ============================================================================
 Name        : NSD.c
 Author      : 
 Version     :
 Copyright   :
 Description :
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct edge{
	int src;
	int dest;
};

int main(int argc, char *argv[]) {

	srand(time(NULL));

	if(argc != 3){
		printf("Usage: %s n m\n",argv[0]);
		return -1;
	}

	int n,m;

	n = atoi(argv[1]);
	m = atoi(argv[2]);

	int i = 0;
	struct edge e;
	struct edge *graph = malloc( n * sizeof(struct edge));
	FILE *file;

	for(i = 0;i<m;i++){

		e.src = rand()%(n)+1;
		e.dest = rand()%(n)+1;

		while(e.src == e.dest){
			e.src = rand()%(n)+1;
			e.dest = rand()%(n)+1;
		}

		graph[i] = e;
	}

	file = fopen("data/graph.txt","w+");
	for(i = 0;i<m;i++){
		fprintf(file,"%d %d\n",graph[i].src,graph[i].dest);
	}

	fclose(file);
	free(graph);

	return EXIT_SUCCESS;
}
