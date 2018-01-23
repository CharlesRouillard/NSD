#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <set>
#include <map>
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
	if(argc != 3){
		cout << "Usage: " << argv[0] << " <filename> <id of a node>" << endl;
		return -1;
	}

	string filename(argv[1]);
	int id = atoi(argv[2]);
	ifstream readFile(filename);
	set<int> mySet,mySetB;
	set<int>::iterator it;

	if(readFile){
		string line;
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			int source = atoi(elts[0].c_str());
			int target = atoi(elts[1].c_str());
			if(source == id || mySet.find(source) != mySet.end()){
				mySet.insert(target);
				mySetB.insert(source);
			}
		}
		cout << "Latency max : " << mySetB.size() << endl;
	}

	readFile.close();
}