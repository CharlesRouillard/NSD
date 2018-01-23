#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <set>
#include <map>
#include <algorithm>

using namespace std;

ofstream writeFile("data/flooding.txt");

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
	int nb(0);
	ifstream readFile(filename);

	if(readFile){
		string line;
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			int src = atoi(elts[0].c_str());
			int dest = atoi(elts[1].c_str());

			nb = max(nb,max(src,dest));

		}
	}
	
	readFile.close();
	return nb;
}

void flood(int n,vector<vector<int>> adjList,map<int,int> mm){
	for(unsigned int i = 0;i<adjList[n].size();i++){
		if(mm[adjList[n][i]] == 0){
			writeFile << n << ' ' << adjList[n][i] << endl;
			mm[n] = 1;
			flood(adjList[n][i],adjList,mm);
		}
	}
}

int main(int argc, char **argv){
	if(argc != 2){
		cout << "Usage: " << argv[0] << " <filename>" << endl;
		return -1;
	}

	string filename(argv[1]);
	ifstream readFile(filename);
	ofstream writeFile("data/flooding.txt");
	int nbNodes = getNbNodes(filename);
	vector<vector<int>> adjList(nbNodes);
	map<int,int> mm;

	if(readFile){
		string line;
		while(getline(readFile,line)){
			vector<string> elts(split(line));

			int source = atoi(elts[0].c_str());
			int target = atoi(elts[1].c_str());
			
			adjList[source].push_back(target);
		}
	}

	int rand = 2;
	vector<int> ms;

	for(unsigned int i = 0;i<adjList[rand].size();i++){
		if(adjList[rand][i] != rand){
			writeFile << rand << ' ' << adjList[rand][i] << endl;
			mm[rand] = 1;
			flood(adjList[rand][i],adjList,mm);
		}
	}

	readFile.close();
}