/*
 * Kcore.h
 *
 *  Created on: 31 oct. 2017
 *      Author: Charles
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <math.h>
#include "heap.h"

#define new_max(x,y) ((x) >= (y)) ? (x) : (y)

#ifndef KCORE_H_
#define KCORE_H_

typedef struct {
	unsigned i;
	unsigned j;
} edge;

int Mkscore(edge *,int,int,int);
int anomalous(char const* const);



#endif /* KCORE_H_ */
