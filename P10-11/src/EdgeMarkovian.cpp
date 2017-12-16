/*
 * SimpleMetrics.cpp
 *
 *  Created on: 6 déc. 2017
 *      Author: Charles
 */

#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <set>
#include <algorithm>
#include <ctime>
#include <cstdlib>

using namespace std;

vector<string> split(string str){
	istringstream ss(str);
	string token;

	vector<string> playerInfoVector;
	while(std::getline(ss, token, ' ')) {
		playerInfoVector.push_back(token);
	}

	return playerInfoVector;
}

int main(int argc, char **argv){
	if(argc != 5){
		cout << "Usage: " << argv[0] << " <number of node> <probability of creation of links> <probability of deletion of links> <number of time steps>" << endl;
		return -1;
	}

	srand((time(0)));

	ofstream writeFile("data/datasets/edge_markovian_trace.txt");

	int n(atoi(argv[1]));

	string sp(argv[2]);
	size_t sz;
	float p(stod(sp,&sz));

	sp = argv[3];
	float d(stod(sp,&sz));

	int ts(atoi(argv[4]));

	vector<string> exist,dontExist,tmpV;
	string s("");

	for(int j = 0;j<n;j++){
		for(int k = 0;k<n;k++){
			if(j < k){
				s = to_string(j) + " " + to_string(k);
				dontExist.push_back(s);
			}
		}
	}

	for(int i = 0;i<ts;i++){
		for(unsigned int x = 0;x<dontExist.size();x++){
			float random = (float)rand() / (float)RAND_MAX;
			if(random < p){
				string tmp(dontExist.at(x));
				dontExist.erase(dontExist.begin()+x);
				tmpV.push_back(tmp);
				writeFile << i << ' ' << tmp << " C" << endl;
			}
		}

		for(unsigned int x = 0;x<exist.size();x++){
			float random = (float)rand() / (float)RAND_MAX;
			if(random < d){
				string tmp(exist.at(x));
				exist.erase(exist.begin()+x);
				dontExist.push_back(tmp);
				writeFile << i << ' ' << tmp << " S" << endl;
			}
		}

		//add tmpV to exist
		for(unsigned int j = 0;j<tmpV.size();j++){
			exist.push_back(tmpV.at(j));
		}
		tmpV.clear();
	}

	exist.clear();
	dontExist.clear();

	return EXIT_SUCCESS;
}
