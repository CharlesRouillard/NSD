#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>



int intersect(int u[], int v[], int sizeU, int sizeV,int u_node, int v_node){
	int iv = 0;
	int iu = 0;
	int n = 0;

	while(iu < sizeU && iv < sizeV){
		//truncate
		if(u[iu]==v_node){
			u[iu]=-1;
		}
		if(v[iv]==u_node){
			v[iv]=-1;
		}
		//ignore -1
		if(u[iu]==-1){
			iu++;
			continue;
		}
		if(v[iv]==-1){
			iv++;
			continue;
		}
		if(u[iu]<v[iv]){
			iu++;
		}
		else if(v[iv]<u[iu]){
			iv++;
		}
		else{
			n++;
			iu++;
			iv++;
		}
	}

	return n;
}

