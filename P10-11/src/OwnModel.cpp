/*
 * SimpleMetrics.cpp
 *
 *  Created on: 6 déc. 2017
 *      Authors: ZEGHLACHE Adel & ROUILLARD Charles
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
	if(argc != 3){
		cout << "Usage: " << argv[0] << "<filename of created and deleted links> <number of nodes>" << endl;
		return -1;
	}

	srand((time(0)));

	string filename(argv[1]);
	ifstream readFile(filename);

	ofstream writeFile("data/own_model_trace.txt");

	int n(atoi(argv[2]));

	vector<string> exist,dontExist,tmpV;
	string s("");

	/*create every link possible*/
	for(int j = 0;j<n;j++){
		for(int k = 0;k<n;k++){
			if(j < k){
				s = to_string(j) + " " + to_string(k);
				dontExist.push_back(s);
			}
		}
	}

	string line;
	/*loop from 0 to timestamp given in args*/
	while(getline(readFile,line))
	{
		if(line.find("#") != string::npos)
			continue;
		vector<string> elts(split(line));
		size_t sz;
		double p = stod(elts[1],&sz);
		double d = stod(elts[2],&sz);

		/*then read the list of nodes that does not exist*/
		for(unsigned int x = 0;x<dontExist.size();x++){
			float random = (float)rand() / (float)RAND_MAX;
			if(random < p){
				string tmp(dontExist.at(x));
				dontExist.erase(dontExist.begin()+x);
				tmpV.push_back(tmp);
				writeFile << elts[0] << ' ' << tmp << " C" << endl; //write on the file every creation 
			}
		}

		/*read the list of nodes that does exist*/
		for(unsigned int x = 0;x<exist.size();x++){
			float random = (float)rand() / (float)RAND_MAX;
			if(random < d){
				string tmp(exist.at(x));
				exist.erase(exist.begin()+x);
				dontExist.push_back(tmp);
				writeFile << elts[0] << ' ' << tmp << " S" << endl; //write on the file every deletion 
			}
		}

		//add tmpV to exist
		for(unsigned int j = 0;j<tmpV.size();j++){
			exist.push_back(tmpV.at(j));
		}
		tmpV.clear();
	}

	cout << "File data/own_model_trace.txt created" << endl;

	exist.clear();
	dontExist.clear();

	return EXIT_SUCCESS;
}