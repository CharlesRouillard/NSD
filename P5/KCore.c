/*
 ============================================================================
 Name        : NSD_TP5.c
 Author      : Charles
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <math.h>

#define new_max(x,y) ((x) >= (y)) ? (x) : (y)

//key value pair
typedef struct {
	unsigned key;
	unsigned value;
} keyvalue;

//heap datastructure
typedef struct {
	unsigned n_max;// max number of nodes.
	unsigned n;// number of nodes.
	unsigned *pt;// pointers to nodes.
	keyvalue *kv;// (node,nck)
} bheap;

typedef struct {
	unsigned i;
	unsigned j;
} edge;

//constructing a min heap that can contain up to n_max elements
bheap *construct(unsigned n_max){
	unsigned i;
	bheap *heap=malloc(sizeof(bheap));

	heap->n_max=n_max;
	heap->n=0;
	heap->pt=malloc(n_max*sizeof(unsigned));
	for (i=0;i<n_max;i++) heap->pt[i]=-1;
	heap->kv=malloc(n_max*sizeof(keyvalue));
	return heap;
}

//swap i with j
void swap(bheap *heap,unsigned i, unsigned j) {
	keyvalue kv_tmp=heap->kv[i];
	unsigned pt_tmp=heap->pt[kv_tmp.key];
	heap->pt[heap->kv[i].key]=heap->pt[heap->kv[j].key];
	heap->kv[i]=heap->kv[j];
	heap->pt[heap->kv[j].key]=pt_tmp;
	heap->kv[j]=kv_tmp;
}

//bubble up operation (when decreasing a value)
void bubble_up(bheap *heap,unsigned i) {
	unsigned j=(i-1)/2;
	while (i>0) {
		if (heap->kv[j].value>heap->kv[i].value) {
			swap(heap,i,j);
			i=j;
			j=(i-1)/2;
		}
		else break;
	}
}

//bubble down operation (when increasing a value or extracting min)
void bubble_down(bheap *heap) {
	unsigned i=0,j1=1,j2=2,j;
	while (j1<heap->n) {
		j=( (j2<heap->n) && (heap->kv[j2].value<heap->kv[j1].value) ) ? j2 : j1 ;
		if (heap->kv[j].value < heap->kv[i].value) {
			swap(heap,i,j);
			i=j;
			j1=2*i+1;
			j2=j1+1;
			continue;
		}
		break;
	}
}

//inserting element at the end and doing bubble up
void insert(bheap *heap,keyvalue kv){
	heap->pt[kv.key]=(heap->n)++;
	heap->kv[heap->n-1]=kv;
	bubble_up(heap,heap->n-1);
}

//decrease the value of the key "key" by delta and maintaining heap using bubble up
void update(bheap *heap,unsigned key,unsigned delta){
	unsigned i=heap->pt[key];
	if (i!=-1){
		((heap->kv[i]).value)-=delta;
		bubble_up(heap,i);
	}
}

//extracting minimum and reforming heap
keyvalue popmin(bheap *heap){
	keyvalue min=heap->kv[0];
	heap->pt[min.key]=-1;
	heap->kv[0]=heap->kv[--(heap->n)];
	heap->pt[heap->kv[0].key]=0;
	bubble_down(heap);
	return min;
}

//Building the heap structure with (key,value)=(i,val[i]) for each node
bheap* mkheap(unsigned* val,unsigned n){
	unsigned i;
	keyvalue kv;
	bheap* heap=construct(n);
	for (i=0;i<n;i++){
		kv.key=i;
		kv.value=val[i];
		insert(heap,kv);
	}
	return heap;
}

//freeing memory
void freeheap(bheap *heap){
	free(heap->pt);
	free(heap->kv);
	free(heap);
}

void Mkscore(edge *edges,int size,int nb_nodes,int t){

	//printf("%d\n",size);
	int *r = calloc(nb_nodes, sizeof(int));
	float *finalr = calloc((nb_nodes), sizeof(float));
	int i,cpt = 0,size_densest = 0;
	float max_score = 0.;

	while(cpt < t){
		//printf("ITERATION NUMERO %d\n",(cpt+1));
		for(i = 0;i<size;i++){
			edge e = edges[i];
			//printf("edge %d--%d r[%d] = %d / r[%d] = %d\n",e.i,e.j,e.i,r[e.i],e.j,r[e.j]);
			if(r[e.i] <= r[e.j]){
				r[e.i]++;
				//printf("r[%d]++\n",e.i);
			}
			else{
				r[e.j]++;
				//printf("r[%d]++\n",e.j);
			}
		}
		cpt++;
	}

	for(i = 1;i<=nb_nodes;i++){
		if(r[i] != 0){
			float d = (float)r[i]/t;
			printf("score du noeud %d : %f\n",i,d);
			finalr[i] = d;
			if(max_score < d){
				max_score = d;
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
	printf("size of the densest subgraph : %d\n",size_densest);
}

void anomalous(){
	char const* const graph = "data/lj.txt";
	FILE* fileGraph = fopen(graph, "r");
	FILE* finalFile = fopen("data/final.txt", "w+");

	char line[256];
	int src,dest,nb_nodes = 0,* tabdegres,size_tabdegres=10,i = 0,j= 0,* isDeleted,**adjancy_list;

	tabdegres = calloc (10 , sizeof(int));

	/*read the co authors links and make kcore*/
	while (fgets(line, sizeof(line), fileGraph)) {
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
		token = strtok(line, "`\t");
		if(token[0]=='%')
			continue;
		src = atoi(token);
		token = strtok(NULL, "\t");
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

	printf("done anomalous\n");
	free(tabdegres);
	free(isDeleted);
	free(adjancy_list);
	freeheap(heap);
}

int main(int argc, char** argv){

	if(argc != 2){
		printf("Usage : %s <filename>");
		return -1;
	}
	/*char const* const fileName = argv[1];
    FILE* file = fopen(fileName, "r");
    char line[256];
    int src, dest,nb_nodes = 0,nb_edges = 0,ptr = 0,* tabdegres,size_tabdegres=10,i = 0,j= 0,* isDeleted,**adjancy_list;
    edge *edges;

    tabdegres = calloc (10 , sizeof(int));

    printf("Calculating degrees...\n");
    fflush(stdout);

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

    printf("Building the Adjancy List...\n");
    fflush(stdout);

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

    while(size_heap!=0){
		k = popmin(heap);
		v = k.key;
		c = new_max(c,k.value);

		for(j=0;j<tabdegres[v];j++){
			if(isDeleted[adjancy_list[v][j]]==0){
				update(heap,adjancy_list[v][j],1);
			}
		}
		isDeleted[v] = 1;
		i--;
		size_heap--;
	}

    printf("mkscore\n");
    fflush(stdout);*/

    //Mkscore(edges,nb_edges,nb_nodes,100);
    anomalous();

    printf("done\n");
    /*free(tabdegres);
	free(isDeleted);
	free(adjancy_list);
	free(edges);
	freeheap(heap);*/

	return 0;
}
