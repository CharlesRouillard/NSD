/*
 * BigNode.cpp
 *
 *  Created on: 21 oct. 2017
 *      Author: Charles
 */

#include "BigNode.hpp"

using namespace std;

BigNode::BigNode(){
	number = 0;
	c = 0;
	eta = 0;
}

BigNode::BigNode(unsigned long int n)
{
	number = n;
	c = 0;
	eta = 0;
	//del = false;
}


unsigned long int BigNode::getC() {
	return c;
}

void BigNode::setC(unsigned long int c) {
	this->c = c;
}

unsigned long int BigNode::getEta() {
	return eta;
}

void BigNode::setEta(unsigned long int eta) {
	this->eta = eta;
}

/*bool BigNode::isDel() {
	return del;
}

void BigNode::setIsDel(bool del) {
	this->del = del;
}*/

int BigNode::getNumber() {
	return number;
}

void BigNode::setNumber(unsigned long int number) {
	this->number = number;
}
