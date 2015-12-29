package com.realtimestudio.transport.misc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import com.realtimestudio.transport.event.simulation.preparer.SimulatorPreparerAction;

@Configuration
@ComponentScan("com.realtimestudio.transport")
public class SimulatorPreparerApp {
	public static void main(String[] args) {

		try (AbstractApplicationContext context = new AnnotationConfigApplicationContext(SimulatorPreparerApp.class)) {
			context.registerShutdownHook();
			
			context.getBean(SimulatorPreparerAction.class).process();			
		}

	}

}
