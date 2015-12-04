package com.realtimestudio.transport.event.simulation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import com.realtimestudio.transport.event.simulation.emitter.Preparer;
import com.realtimestudio.transport.event.simulation.emitter.SignalEmitter;

@Configuration
@ComponentScan("com.realtimestudio.transport")
public class App {
	public static void main(String[] args) {

		try (AbstractApplicationContext context = new AnnotationConfigApplicationContext(App.class)) {
			context.registerShutdownHook();
			if (args.length > 0 && args[0].equalsIgnoreCase("prepare"))
				context.getBean(Preparer.class).prepare();

			context.getBean(SignalEmitter.class).emit();
		}

	}

}
