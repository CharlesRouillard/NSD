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

using namespace std;

/*method use in every program, to split the file by space and put strings on a vector*/
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

	/*the file to read*/
	ifstream flux(argv[1]);

	if(flux){
		/*here we create a set so it can only contains unique element. thanks to that the size of the set is the number of nodes in the graph*/
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

				/*minTime will be the minimum timestamp, and maxTime the max. Then we substract and we have the duration*/
				if(minTime == -1)
					minTime = ts;
				else
					minTime = min(minTime,ts);
				maxTime = max(maxTime,te);

				mySet.insert(n1);
				mySet.insert(n2);
			}
			/*we count every line to display the number of contact observed*/
			nbLine++;
		}
		duration = (maxTime-minTime);
		cout << "Graph " << argv[1] << "\nNumber of nodes = " << mySet.size() << "\nNumber of contacts = " << nbLine << "\nTotal duration = " << duration << endl;
 	}
	return EXIT_SUCCESS;
}
