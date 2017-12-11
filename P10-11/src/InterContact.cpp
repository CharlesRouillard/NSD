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
		printf("Usage: %s <filename or path> \n",argv[0]);
		return -1;
	}

	string filename(argv[1]);

	ifstream readFile(filename);

	if(readFile){
		
 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}

	return EXIT_SUCCESS;
}
