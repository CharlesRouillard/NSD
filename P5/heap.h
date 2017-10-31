/*
 * heap.h
 *
 *  Created on: 31 oct. 2017
 *      Author: Charles
 */

#include <stdio.h>
#include <stdlib.h>

#ifndef HEAP_H_
#define HEAP_H_

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

#endif /* HEAP_H_ */
