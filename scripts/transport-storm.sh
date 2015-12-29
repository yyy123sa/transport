#!/bin/bash

cd `dirname $0`
source ~/.bash_profile

storm jar transport-storm.jar com.realtimestudio.transport.storm.GPSSignalTopology
