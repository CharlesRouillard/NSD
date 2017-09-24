//============================================================================
// Name        : Matrix.cpp
// Author      : Charles
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <string>
#include <bitset>
#include <math.h>
#include <vector>
#include <fstream>
#include <algorithm>

using namespace std;

int main() {

	ifstream flux;
	flux.open("C:\\Users\\Charles\\workspace\\NSD\\data\\test");
	string line;
	string f = "%";
	std::size_t found;
	int nb_nodes = 0;

	if(flux){
		while(getline(flux,line)){
			found = line.find(f);
			if(found == string::npos){
				string delim = " ";
				string src = line.substr(0,line.find(delim));
				string dest = line.substr(src.size()+1, line.find(" ",src.size()+1));

				nb_nodes = max(nb_nodes,max(atoi(src.c_str()),atoi(dest.c_str())));
			}
		}
	}

	flux.close();
	flux.open("C:\\Users\\Charles\\workspace\\NSD\\data\\test");

	bool matrix[nb_nodes][nb_nodes];

	while(getline(flux,line)){
		found = line.find(f);
		if(found == string::npos){
			string delim = " ";
			string src = line.substr(0,line.find(delim));
			string dest = line.substr(src.size()+1, line.find(" ",src.size()+1));

			matrix[atoi(src.c_str())][atoi(dest.c_str())] = 1;
			matrix[atoi(dest.c_str())][atoi(src.c_str())] = 1;
		}
	}

	flux.close();

	int i = 0;
	int j = 0;

	for(i = 0;i<nb_nodes;i++){
		for(j = 0;j<nb_nodes;j++){
			if(matrix[i][j] != 1){
				matrix[i][j] = 0;
			}
		}
	}

	for(i = 0;i<nb_nodes;i++){
		for(j = 0;j<nb_nodes;j++){
			cout << matrix[i][j];
		}
		cout << "\n";
	}

	return 0;
}
