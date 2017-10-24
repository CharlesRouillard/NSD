//============================================================================
// Name        : NSD_TP5.cpp
// Author      : Charles
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>
#include <algorithm>
#include <stdio.h>
#include "BigNode.hpp"
#include <boost/config.hpp>
#include <boost/graph/random.hpp>
#include <boost/random/mersenne_twister.hpp>
#include <boost/pending/fibonacci_heap.hpp>
#include <boost/graph/graph_utility.hpp>
#include <boost/pending/indirect_cmp.hpp>

using namespace std;

int main(int argc, char **argv) {

	if(argc != 2){
		printf("Usage: %s <filename>",argv[0]);
		return -1;
	}

	/*char *file = argv[1];
	ifstream flux;
	flux.open(file);
	string line;
	string f = "#";
	std::size_t found;
	unsigned long int nb_nodes = 0;
	int x,y;
	//vector<int> nodeList;

	if(flux){
		while(getline(flux,line)){
			found = line.find(f);
			if(found == string::npos){
				string delim = "\t";
				string src = line.substr(0,line.find(delim));
				string dest = line.substr(src.size()+1, line.find(" ",src.size()+1));

				x = atoi(src.c_str());
				y = atoi(dest.c_str());

				/*if(find(nodeList.begin(),nodeList.end(),x) == nodeList.end()){
					nodeList.push_back(x);
				}

				if(find(nodeList.begin(),nodeList.end(),y) == nodeList.end()){
					nodeList.push_back(y);
				}

				nb_nodes = max((int)nb_nodes,max(x,y));
			}
		}
	}*/


	/*for(int i = 0;i<nodeList.size();i++){
		cout << nodeList[i] << endl;
	}*/

	//flux.close();

	//vector<vector<unsigned long int> > neighbors(nb_nodes+1);
	//vector<fibonacci_heap<unsigned long int,BigNode>::node> tabNode(nb_nodes+1);
	//fibonacci_heap<int,BigNode>::node *tabNode = new fibonacci_heap<int,BigNode>::node[nb_nodes+1];


	//cout << "test" << endl;

	/*flux.open(file);
	if(flux){
		while(getline(flux,line)){
			found = line.find(f);
			if(found == string::npos){
				string delim = "\t";
				string src = line.substr(0,line.find(delim));
				string dest = line.substr(src.size()+1, line.find(" ",src.size()+1));

				x = atoi(src.c_str());
				y = atoi(dest.c_str());

				neighbors[x].push_back(y);
				neighbors[y].push_back(x);
			}
		}
	}
	flux.close();*/

	//cout << "test" << endl;

	FibonacciHeap<int> h;
	h.insert(3);
	auto n = h.insert(5);

	cout << h.getMinimum() << endl;

	h.decreaseKey(n,2);

	cout << h.getMinimum() << endl;

	/*for(unsigned long int i = 0;i<2000000;i++){
		BigNode b(i);
		//if(neighbors[i].size() != 0){
			//auto n = h.insert(i,b);
			//cout << n->
			//cout << n.data().getNumber() << " size " << neighbors[i].size() << endl;
			//tabNode.push_back(n);
		//}
	}*/

	//cout << "test" << endl;

	/*int i = nb_nodes,c = 0;
	while(minHeap.size() != 0){
		cout << "size " << minHeap.size() << endl;
		BigNode v = minHeap.top().data();
		//cout << v.getNumber() << " key : " << minHeap.top().key() <<endl;
		//cout << "remove " << v.getNumber() << endl;
		//nodeList.erase(nodeList.begin() + v.getNumber());
		//nodeList.erase(remove(nodeList.begin(),nodeList.end(),v.getNumber()),nodeList.end());
		auto rem = minHeap.remove();
		//cout << "remove from heap : " << v.getNumber() << endl;
		//v.setIsDel(true);
		//tabNode[neighbors[][]]
		//cout << "new value del of " << v.getNumber() << " is " << v.isDel() << endl;
		//insert to final ?


		for(unsigned int i = 0;i<neighbors[v.getNumber()].size();i++){
			auto node = tabNode[neighbors[v.getNumber()][i]];
			//cout << "voisins de " << v.getNumber() << " " << neighbors[v.getNumber()][i] << endl;
			BigNode b = node.data();
			//cout << b.isDel() << " " << b.getNumber() << endl;
			//n.internal->structure.expired()
			if(minHeap.isIn(node)){
				minHeap.decrease_key(node,(node.key() -1));
				//cout << " degrees of " << node.data().getNumber() << " pass a " << node.key() << endl;
			}
			else{
				//cout << "zap " << b.getNumber() << endl;
			}
		}
		i--;
	}*/

	//delete &tabNode;
	//delete &neighbors;
	return EXIT_SUCCESS;
}
