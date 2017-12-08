#!/bin/bash

CXX=g++
CXXFLAGS= -ansi -O5 -Wall -std=c++11

DIRSRC= ./src/

EXEC=SimpleMetrics

all: $(EXEC)

SimpleMetrics: $(DIRSRC)SimpleMetrics.cpp
	$(CXX) -o $@ $^ $(CXXFLAGS)

clean:
	rm -f *~ $(EXEC)
