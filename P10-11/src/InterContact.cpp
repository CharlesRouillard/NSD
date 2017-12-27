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

/*here we create our own object with method to compute easily the inter contact*/
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

/*check if a MyNode object is in the vector. and the set the index of the MyNode*/
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
	ofstream writeFile("data/distrib_inter_contact.txt");
	ofstream writeFileB("data/inter_contact.txt");

	vector<MyNode> list;
	map<int,int> distrib;
	map<int,int>::iterator it;

	writeFileB << "#Pair (a,b) = inter contact associated to this contact" << endl;
	writeFile << "#(inter contact X) (number of times X is found) " << endl;

	if(readFile){
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			string pair(elts[1] + ' ' + elts[2]);
			string state(elts[3]);
			int time(atoi(elts[0].c_str()));
			MyNode m(pair,state,time);//Create a MyNode object

			if(state.compare("S") == 0){
				list.push_back(m);
			}
			else{
				if(IsInList(list,m)){
					int index(m.getIndex());
					int interTime((m.getTime())-(list[index].getTime()));

					/*write the pair and the inter contact associated in the file*/
					writeFileB << "Pair (" << m.getPair() << ") = " << interTime << endl;

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
			/*write in anotther file each inter contact and the number of time we found this inter contact. Need for the plot distribution*/
	    	writeFile << it->first << ' ' << it->second << endl;
	 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}

 	cout << "File data/inter_contact.txt created." << endl;
 	cout << "File data/distrib_inter_contact.txt created." << endl;

 	list.clear();
 	distrib.clear();
 	readFile.close();
 	writeFile.close();
 	writeFileB.close();

	return EXIT_SUCCESS;
}
