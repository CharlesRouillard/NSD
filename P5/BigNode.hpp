/*
 * BIgNode.h
 *
 *  Created on: 21 oct. 2017
 *      Author: Charles
 */

#ifndef BIGNODE_HPP_
#define BIGNODE_HPP_

class BigNode
{
	private:

	unsigned long int number;
	unsigned long int c;
	unsigned long int eta;
	//bool del;

	public:

	BigNode();
	BigNode(unsigned long int number);
	unsigned long int getC();
	void setC(unsigned long int c);
	unsigned long int getEta();
	void setEta(unsigned long int eta);
	//bool isDel();
	//void setIsDel(bool del);
	int getNumber();
	void setNumber(unsigned long int number);
};

#endif /* BIGNODE_HPP_ */
