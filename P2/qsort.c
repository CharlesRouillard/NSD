/*
 * qsort.c
 *
 *  Created on: 29 sept. 2017
 *      Author: OpenClassRoom
 */
#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>


void echanger(int tableau[], int a, int b)
{
    int temp = tableau[a];
    tableau[a] = tableau[b];
    tableau[b] = temp;
}

void quickSort(int tableau[], int debut, int fin)
{
    int gauche = debut-1;
    int droite = fin+1;
    const int pivot = tableau[debut];

    /* Si le tableau est de longueur nulle, il n'y a rien � faire. */
    if(debut >= fin)
        return;

    /* Sinon, on parcourt le tableau, une fois de droite � gauche, et une
       autre de gauche � droite, � la recherche d'�l�ments mal plac�s,
       que l'on permute. Si les deux parcours se croisent, on arr�te. */
    while(1)
    {
        do droite--; while(tableau[droite] > pivot);
        do gauche++; while(tableau[gauche] < pivot);

        if(gauche < droite)
            echanger(tableau, gauche, droite);
        else break;
    }

    /* Maintenant, tous les �l�ments inf�rieurs au pivot sont avant ceux
       sup�rieurs au pivot. On a donc deux groupes de cases � trier. On utilise
       pour cela... la m�thode quickSort elle-m�me ! */
    quickSort(tableau, debut, droite);
    quickSort(tableau, droite+1, fin);
}
