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

class MyNode{
	public:
		MyNode(string pair, string state, int time) : m_pair(pair), m_state(state), m_time(time), m_index(0){}

		string getPair(){
			return m_pair;
		}

		int getTime(){
			return m_time;
		}

		int getIndex(){
			return m_index;
		}

		void setIndex(int index){
			m_index = index;
		}

	private:
		string m_pair;
		string m_state;
		int m_time;
		int m_index;
};

bool IsInList(vector<MyNode> list, MyNode& m){
	for(unsigned int i = 0;i<list.size();i++){
		if(list[i].getPair() == m.getPair()){
			m.setIndex(i);
			return true;
		}
	}
	return false;
}

int main(int argc, char **argv){
	if(argc != 2){
		cout << "Usage: " << argv[0] << " <filename>" << endl;
		return -1;
	}

	string filename(argv[1]),line;
	ifstream readFile(filename);
	ofstream writeFile("data/datasets/distribution_inter_contact_duration_time.txt");

	vector<MyNode> list;
	map<int,int> distrib;
	map<int,int>::iterator it;

	if(readFile){
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			string pair(elts[1] + ' ' + elts[2]);
			string state(elts[3]);
			int time(atoi(elts[0].c_str()));
			MyNode m(pair,state,time);

			if(state.compare("S") == 0){
				list.push_back(m);
			}
			else{
				if(IsInList(list,m)){
					int index(m.getIndex());
					int interTime((m.getTime())-(list[index].getTime()));
					//cout << "Pair (" << m.getPair() << ") - inter contact duration time = " << interTime << endl;
					it = distrib.find(interTime);
					if(it != distrib.end()){
						it->second = it->second + 1;
					}
					else{
						distrib[interTime] = 1;
					}
					list.erase(list.begin()+index);
				}
			}
		}
		for (it=distrib.begin(); it!=distrib.end(); it++)
	    	writeFile << it->first << ' ' << it->second << endl;
	 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}

 	list.clear();
 	distrib.clear();
 	readFile.close();
 	writeFile.close();

	return EXIT_SUCCESS;
}
