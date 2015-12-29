#!/bin/bash

cd `dirname $0`
source ~/.bash_profile

java -cp `pwd`:transport-simulator.jar com.realtimestudio.transport.event.simulation.App
