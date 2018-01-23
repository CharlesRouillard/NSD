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

int getNbNodes(string filename){
	set<int> nodes;
	ifstream readFile(filename);

	if(readFile){
		string line;
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			nodes.insert(atoi(elts[0].c_str()));
			nodes.insert(atoi(elts[1].c_str()));
		}
	}
	
	readFile.close();
	return nodes.size();
}

int main(int argc, char **argv){
	if(argc != 2){
		cout << "Usage: " << argv[0] << " <filename>" << endl;
		return -1;
	}

	string filename(argv[1]);
	int nbNodes = getNbNodes(filename);
	int nbCopies(0),nbInfected(0);
	ifstream readFile(filename);
	map<int,int> complex;
	map<int,int>::iterator it;
	float C(0.),I(0.);

	if(readFile){
		string line;
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			int recipient = atoi(elts[1].c_str());

			it = complex.find(recipient);
			if(it != complex.end()){
				it->second = it->second + 1;
			}
			else{
				complex[recipient] = 1;
			}
		}

	}
	
	if(nNodes != complex.size()){
		cout << "Not all nodes have received the message" << endl;
	}
	else{
		cout << "All nodes have received the message" << endl;
	}

	readFile.close();
}