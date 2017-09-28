//============================================================================
// Name        : graph.cpp
// Author      : Adel Zeghlache
// Version     : 1
// Copyright   :
// Description : Adjacent List implementation
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
	string test = "23";
	std::vector<int> myvector;

	string line;
	ifstream myfile ("data/out.livemocha");

	if (myfile.is_open())
	{
		int nb_nodes = 0;
		/*
		 * Finding the number of nodes in order to initialize a table of vectors
		 */
		while ( getline (myfile,line) )
		{
			std::size_t found = line.find("%");
			if (found==string::npos){
				std::string src = line.substr(0, line.find(" "));
				std::string  dest = line.substr(src.size()+1, line.find(" ",src.size()+1));
				nb_nodes = max(nb_nodes , max(stoi(src),stoi(dest)));
			}
		}

		myfile.clear();
		myfile.seekg(0, ios::beg);
		vector<int> adjList[nb_nodes+1];

		while ( getline (myfile,line) ){
			std::size_t found = line.find("%");
			if (found==string::npos){
				std::string src = line.substr(0, line.find(" "));
				std::string  dest = line.substr(src.size()+1, line.find(" ",src.size()+1));
				//cout << src << " et " << dest << endl;
				if(src!=dest){
					adjList[stoi(src)].push_back(stoi(dest));
					adjList[stoi(dest)].push_back(stoi(src));
				}
			}
		}

		/*
		 * Exercise 7
		 */
		// Print AdjList::
		int nb_zero_deg = 0;
		float average_degree = (1/(float)nb_nodes);

		int minDegree = adjList[0].size();
		int maxDegree = adjList[0].size();
		int somme_deg = 0;
		int nb_vertives = 0;
		for(int i=0; i <= nb_nodes;i++){

			 //Exercise 7
			 minDegree = adjList[i].size()<minDegree ? adjList[i].size() : minDegree;
			 maxDegree = adjList[i].size()>maxDegree ? adjList[i].size() : maxDegree;
			 if(adjList[i].size()==0)
				 nb_zero_deg++;
			 somme_deg+= adjList[i].size();

			 std::cout << "The Neighborhood of " << i <<" contains:";
			 for (std::vector<int>::iterator it = adjList[i].begin(); it != adjList[i].end(); ++it)
			    std::cout << ' ' << *it;
			 std::cout << '\n';

		}
		average_degree *= somme_deg;
		cout << "*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~" << endl;
		cout << "There is " << nb_zero_deg << " nodes of degree 0" <<endl;
		cout << "Volume: " << somme_deg/2 << " Edges" << endl;
		cout << "Size: " << nb_nodes << " Vertices" << endl;
		cout << "Density: " << ((float)(somme_deg))/(((float)nb_nodes)*((float)nb_nodes-1)) << endl;
		cout << "Average degree: " << average_degree << " edges / vertex"<< endl;
		cout << "Min Degree: " << minDegree << " Edges" << endl;
		cout << "Max Degree: " << maxDegree << " Edges" << endl;

		myfile.close();
	}
	else cout << "Unable to open file";

	return 0;
}
