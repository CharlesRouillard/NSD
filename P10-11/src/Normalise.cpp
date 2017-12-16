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

	ifstream readFile(argv[1]);
	vector<string> toWrite;

	if(readFile){
		string line;
		int minTime(-1);

		while(getline(readFile,line)){
			vector<string> elts(split(line));
			for(unsigned int i = 0;i<elts.size();i++){
				int ts(atoi(elts[2].c_str()));

				if(minTime == -1)
					minTime = ts;
				else
					minTime = min(minTime,ts);
			}
		}

		readFile.clear();
		readFile.seekg(0,ios::beg);

		int n1(0),n2(0),ts(0),te(0);
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			for(unsigned int i = 0;i<elts.size();i++){
				n1 = atoi(elts[0].c_str());
				n2 = atoi(elts[1].c_str());
				ts = atoi(elts[2].c_str());
				te = atoi(elts[3].c_str());
			}
			string newLine;
			newLine = to_string(n1) + ' ' + to_string(n2) + ' ' + to_string((ts-minTime)) + ' ' + to_string((te-minTime));
			toWrite.push_back(newLine);
		}
 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}

 	ofstream writeFile(argv[1]);

 	for(unsigned int i = 0;i<toWrite.size();i++){
 		writeFile << toWrite.at(i) << endl;
 	}

 	readFile.close();
 	writeFile.close();

	return EXIT_SUCCESS;
}
