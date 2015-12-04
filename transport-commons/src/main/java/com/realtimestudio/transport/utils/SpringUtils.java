package com.realtimestudio.transport.utils;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

public class SpringUtils{
	private static AbstractApplicationContext  context;
	
	static{
		context = new AnnotationConfigApplicationContext(SpringUtilsConfig.class);
		context.registerShutdownHook();
	}

		
	public static<T> T getBean(Class<T> clazz){
		return context.getBean(clazz);
	}

}

@Configuration
@ComponentScan("com.realtimestudio.transport")
class SpringUtilsConfig{
	
}
