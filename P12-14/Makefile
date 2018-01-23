#!/bin/bash

CXX=g++
CXXFLAGS= -O5 -Wall -std=c++11

DIRSRC= ./src/

EXEC=complexInfec reliability latency flooding

all: $(EXEC)

complexInfec: $(DIRSRC)ComplexInfec.cpp
	$(CXX) -o $@ $^ $(CXXFLAGS)

reliability: $(DIRSRC)Reliability.cpp
	$(CXX) -o $@ $^ $(CXXFLAGS)

latency: $(DIRSRC)Latency.cpp
	$(CXX) -o $@ $^ $(CXXFLAGS)

flooding: $(DIRSRC)Flooding.cpp
	$(CXX) -o $@ $^ $(CXXFLAGS)
	
clean:
	rm -f *~ $(EXEC)
