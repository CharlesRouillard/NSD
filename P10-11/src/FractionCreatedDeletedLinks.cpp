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
	ofstream writeFile("data/created_deleted_links.txt");
	double nominCreated(0.),nbCreated(0.),nominDeleted(0.),nbDeleted(0.),avgCreated(0.),avgDeleted(0.);

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

			/*increase the number of links for each time step, and the number of C and S for each time step*/
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
				/*then the fraction of creation of a link is ( number of C / (n*(n-1)/2) ) - number of previous links */
				int tmp = (n*(n-1));
				double tmpb = (double)tmp/2.;
				double p((double)nbC/(tmpb-(double)nbPrevLinks));

				nominCreated += p;
				nbCreated++;

				if(nbPrevLinks == 0)
					writeFile << currTs << ' ' << (double)p << " -1" << endl;
				else{
					double q((double)nbS/(double)nbPrevLinks);
					nominDeleted += q;
					nbDeleted++;

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

		nominCreated += p;
		nbCreated++;


		if(nbPrevLinks == 0)
			writeFile << currTs << ' ' << (double)p << " -1" << endl;
		else{
			double q((double)nbS/(double)nbPrevLinks);
			nominDeleted += q;
			nbDeleted++;

			writeFile << currTs << ' ' << (double)p << ' ' << (double)q << endl;
		}
 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}

 	avgCreated = nominCreated/nbCreated;
 	avgDeleted = nominDeleted/nbDeleted;

 	cout << "Average fraction of created links for your graph = " << avgCreated << "\nAverage fraction of deleted links for your graph = " << avgDeleted <<endl;

 	cout << "File data/created_deleted_links.txt created" << endl;

 	readFile.close();
	writeFile.close();

	return EXIT_SUCCESS;
}
