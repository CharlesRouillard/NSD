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
	ofstream writeFile("data/datasets/distrib_created_deleted_links.txt");

	if(readFile){

		int n(0);
		string line;
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			n = max(n, max(atoi(elts[1].c_str()),atoi(elts[2].c_str())));
		}

		readFile.clear();
		readFile.seekg(0,ios::beg);

		int nbLinks(0),nbPrevLinks(0),nbC(0),nbS(0),currTs(0);

		while(getline(readFile,line)){
			vector<string> elts(split(line));
			string state = elts[3];

			if(currTs == atoi(elts[0].c_str())){
				if(state == "C"){
					nbLinks++;
					nbC++;				
				}
				else{
					nbLinks--;
					nbS++;				
				}
			}
			else{
				int tmp = (n*(n-1));
				double tmpb = (double)tmp/2.;
				double p((double)nbC/(tmpb-(double)nbPrevLinks));

				if(nbPrevLinks == 0)
					writeFile << currTs << ' ' << (double)p << " -1" << endl;
				else{
					double q((double)nbS/(double)nbPrevLinks);
					writeFile << currTs << ' ' << (double)p << ' ' << (double)q << endl;
				}
				
				currTs = atoi(elts[0].c_str());

				nbC = 0;
				nbS = 0;
				nbPrevLinks = nbLinks;
				
				if(state == "C"){
					nbLinks++;
					nbC++;				
				}
				else{
					nbLinks--;
					nbS++;				
				}
			}
		}
		int tmp = (n*(n-1));
		double tmpb = (double)tmp/2.;
		double p((double)nbC/(tmpb-(double)nbPrevLinks));

		if(nbPrevLinks == 0)
			writeFile << currTs << ' ' << (double)p << " -1" << endl;
		else{
			double q((double)nbS/(double)nbPrevLinks);
			writeFile << currTs << ' ' << (double)p << ' ' << (double)q << endl;
		}
 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}

 	readFile.close();
	writeFile.close();

	return EXIT_SUCCESS;
}
