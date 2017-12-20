#!/bin/bash

if [ $# -eq 0 ]
	then
		echo "Must provide a filename in args"
		exit -1
fi

mkdir data 2> /dev/null
mkdir plots/images 2> /dev/null

make

echo -e "============Simple Metrics============\n"
./simpleMetrics "$1"

echo -e "\n============Normalize file...============\n"
./normalise "$1"

echo -e "\n============Sort and split the graph...============\n"
./sortAndSplit "$1"

echo -e "\n============Inter-contact============\n"
./interContact "data/sort_split.txt"
gnuplot "plots/plot_inter_contact.txt"
echo -e "plot plots/images/distrib_inter_contact.png created. It shows the ditribution of the inter contact duration time thanks to the data/distrib_inter_contact.txt file\n"

echo -e "\n============Average degree============\n"
./averageDegree "data/sort_split.txt"
gnuplot "plots/plot_average_degree.txt"
echo -e "plot plots/images/evolution_average_degree.png created. It shows the evolution of the average degree over time thanks to the data/average_degree.txt file\n"

echo -e "\n============Creation and deletion of links============\n"
./fractionCreatedDeletedLinks "data/sort_split.txt"
gnuplot "plots/plot_created_deleted_links.txt"
echo -e "\nplot plots/images/distribution_created.png created. It shows the evolution of the fraction of created links at each time step thanks to the data/average_degree.txt file"
echo -e "plot plots/images/distribution_deleted.png created. It shows the evolution of the fraction of deleted links at each time step thanks to the data/average_degree.txt file\n"

echo -e "\n============Edge Markovian Model============\n"

echo -e "Type the number of nodes you want to generate the Edge Markovian Trace : "
read nbnodes

echo -e "Type the probability of creation of links : "
read p

echo -e "Type the probability of deletion of links : "
read d

echo -e "Type the number of time step you want : "
read ts

./edgeMarkovian "$nbnodes" "$p" "$d" "$ts"
./averageDegree "data/edge_markovian_trace.txt"
gnuplot "plots/plot_markovian.txt"
echo -e "plot plots/images/evolution_average_degree_markovian.png created. It shows the evolution of the average degree over time for the edge Markovian trace\n"

echo -e "\n============Beyond the study============\n"
echo -e "Type the number of nodes you want to generate the trace for our own model : "
read nb
./ownModel "data/created_deleted_links.txt" "$nb"
./averageDegree "data/own_model_trace.txt"
gnuplot "plots/plot_own_model.txt"
echo -e "plot plots/images/evolution_average_degree_markovian.png created. It shows the evolution of the average degree over time for the edge Markovian trace\n"


mv *.png plots/images/ 2> /dev/null
make clean