#!/bin/bash

CXX=g++
CXXFLAGS= -O5 -Wall -std=c++11

DIRSRC= ./src/

EXEC=simpleMetrics normalise sortAndSplit

all: $(EXEC)

simpleMetrics: $(DIRSRC)SimpleMetrics.cpp
	$(CXX) -o $@ $^ $(CXXFLAGS)

normalise: $(DIRSRC)Normalise.cpp
	$(CXX) -o $@ $^ $(CXXFLAGS)

sortAndSplit: $(DIRSRC)SortAndSplit.cpp
	$(CXX) -o $@ $^ $(CXXFLAGS)
	
clean:
	rm -f *~ $(EXEC)
