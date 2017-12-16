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
	if(argc != 2){
		cout << "Usage: " << argv[0] << " <filename>" << endl;
		return -1;
	}

	string filename(argv[1]);
	ifstream readFile(filename);
	ofstream writeFile("data/datasets/average_degree.txt");
	
	if(readFile){

		int nbNodes(0);
		string line;
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			nbNodes = max(nbNodes, max(atoi(elts[1].c_str()),atoi(elts[2].c_str())));
		}

		readFile.clear();
		readFile.seekg(0,ios::beg);

		set<int> mySet;
		int degree(0),currentTime(0);
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			if(currentTime == atoi(elts[0].c_str())){
				if(elts[3] == "C"){
					degree += 2;
				}
				else{
					degree -= 2;
				}
			}
			else{
				writeFile << currentTime << ' ' << ((double)degree/(double)nbNodes) << endl;
				currentTime = atoi(elts[0].c_str());

				if(elts[3] == "C"){
					degree += 2;
				}
				else{
					degree -= 2;
				}
			}
		}
		writeFile << currentTime << ' ' << ((double)degree/(double)nbNodes) << endl;
 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}	

 	readFile.close();
	writeFile.close();

	return EXIT_SUCCESS;
}
