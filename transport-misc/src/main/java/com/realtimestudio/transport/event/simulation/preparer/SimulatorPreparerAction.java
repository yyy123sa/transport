package com.realtimestudio.transport.event.simulation.preparer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimulatorPreparerAction {
	@Autowired
	private List<SimulatorPreparer> preparerList;
	
	public void process(){
		for(SimulatorPreparer preparer : preparerList){
			preparer.prepare();
		}
	}

}
