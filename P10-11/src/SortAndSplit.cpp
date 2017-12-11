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

bool sortFinal(vector<string> i, vector<string> j){return atoi(i[0].c_str()) < atoi(j[0].c_str());}

int main(int argc, char **argv){
	if(argc != 2){
		printf("Usage: %s <filename or path> \n",argv[0]);
		return -1;
	}

	string filename(argv[1]);
	string newF("");
	size_t pos(filename.find("."));

	if(pos != string::npos){
		newF = filename.substr(0,pos) + "_splited_and_sorted_by_time.txt";
	}
	else{	
		newF = filename + "_splited_and_sorted_by_time.txt";
	}

	ifstream readFile(filename);
	ofstream writeFile(newF);

	vector<vector<string>> lines,final;

	if(readFile){
		string line,newC(""),newS("");

		while(getline(readFile,line)){
			vector<string> elts(split(line));
			lines.push_back(elts);
		}

		for(unsigned int i = 0;i<lines.size();i++){

			vector<string> tempC;
			tempC.push_back(lines[i][2]);
			tempC.push_back(lines[i][0]);
			tempC.push_back(lines[i][1]);
			tempC.push_back("C");

			vector<string> tempS;
			int newTe = atoi(lines[i][3].c_str())+1;
			tempS.push_back(to_string(newTe));
			tempS.push_back(lines[i][0]);
			tempS.push_back(lines[i][1]);
			tempS.push_back("S");

			final.push_back(tempC);
			final.push_back(tempS);
		}

		sort(final.begin(),final.end(),sortFinal);

		for(unsigned int i = 0;i<final.size();i++){
			for(unsigned int j = 0;j<final[i].size();j++){
				writeFile << final[i][j] << ' ';
			}
			writeFile << "\n";
		}
 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}

 	readFile.close();
 	writeFile.close();

	return EXIT_SUCCESS;
}
