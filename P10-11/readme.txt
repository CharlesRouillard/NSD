Warning: Repositories “data” and “plots” are mandatory for the good of the execution. If you want to execute each program separately, make sure you always type commands in the root of the project and not in a subdirectory. (make, gnuplot, etc).
Each results of the program are explained on the report. 

There is 2 ways to execute the project :
-	With the shell script, simply type “./launch.sh <datasets>” and you will going through all the programs and somehow you will have to type values in the console 
(everything is explained on the report) and at the end all the result plots and result files will be created.

-	Execute manually each program, you need to compile all the sources. For that, just type “make”, at the root of the project and then execute all the executables generated.

	./simpleMetrics <dataset>
	./normalise <dataset>
	./sortAndSplit <dataset> => will create and trace with sort and split format. (data/sort_split.txt). If you execute again with another dataset, the file will changed.

	Warning: Each program from now that need to take a filename in args must take the sort and split version of the dataset, and not the default one.(It’s automatically done with the shell script).

	./interContact "data/sort_split.txt"
	gnuplot "plots/plot_inter_contact.txt"

	./averageDegree "data/sort_split.txt"
	gnuplot "plots/plot_average_degree.txt"

	./fractionCreatedDeletedLinks "data/sort_split.txt"
	gnuplot "plots/plot_created_deleted_links.txt"

	./edgeMarkovian <nb nodes><probabilty p><probabilty q><number of time step>
	./averageDegree "data/edge_markovian_trace.txt"
	gnuplot "plots/plot_markovian.txt"

	./ownModel "data/created_deleted_links.txt" <number of nodes>
	./averageDegree "data/own_model_trace.txt"
	gnuplot "plots/plot_own_model.txt"