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

	ifstream flux(argv[1]);

	if(flux){
		set<int> mySet;
		string line;
		int nbLine(0),minTime(-1),maxTime(0),duration(0);

		while(getline(flux,line)){
			vector<string> elts(split(line));
			for(unsigned int i = 0;i<elts.size();i++){
				int n1(atoi(elts[0].c_str())),
						n2(atoi(elts[1].c_str())),
						ts(atoi(elts[2].c_str())),
						te(atoi(elts[3].c_str()));

				if(minTime == -1)
					minTime = ts;
				else
					minTime = min(minTime,ts);
				maxTime = max(maxTime,te);

				mySet.insert(n1);
				mySet.insert(n2);
			}
			nbLine++;
		}
		duration = (maxTime-minTime);
		cout << "Graph " << argv[1] << "\nNumber of nodes = " << mySet.size() << "\nNumber of contacts = " << nbLine << "\nTotal duration = " << duration << endl;
 	}
	return EXIT_SUCCESS;
}
