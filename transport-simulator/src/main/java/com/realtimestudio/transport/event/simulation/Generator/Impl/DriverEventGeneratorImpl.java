package com.realtimestudio.transport.event.simulation.Generator.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.realtimestudio.transport.event.GPS_Event;
import com.realtimestudio.transport.event.simulation.Generator.DriverEventGenerator;
import com.realtimestudio.transport.model.Driver;

/*
 * 1. risk factor is 0 - 100
 * 2. 100 means all the violations will happen every time
 * 3. riskfactor = riskfactor - drivingyears
 */

public class DriverEventGeneratorImpl  implements DriverEventGenerator{
	
	private int adjRiskFactor;
	private Random random;
	
	private static List<GPS_Event> eventsList = new ArrayList<>(GPS_Event.getStatusMap().values());
	
	public DriverEventGeneratorImpl(Driver driver){
		adjRiskFactor = driver.getAdjRiskFactor();
		random = new Random(new Date().getTime());		
	}
	
	@Override
	public Set<GPS_Event> generateEvents(){
		int n = eventsList.size();
		int numEvents = 0;
		int total = n * adjRiskFactor;
		
		for(int i=n; i>0; i--){
			int max = total / i;
			int min = total/n;
			int possibility = min + random.nextInt(max-min+1);
			if(canHappen(possibility)){
				numEvents = i;
				break;
			}
			total -= possibility * i;
		}
		
		return getEvents(numEvents);		
	}
	
	private Set<GPS_Event> getEvents(int n){
		Set<GPS_Event> result = new HashSet<>();
		if(n==0) return result;
		if(n==eventsList.size()){
			result.addAll(eventsList);
			return result;
		}
		
		int[] positions = getShuffledArray();

		for(int i=0; i<n; i++){
			result.add(eventsList.get(positions[i]));
		}
		return result;		
		
	}
	
	private int[] getShuffledArray(){
		int[] positions = new int[eventsList.size()];
		for(int i=0; i<eventsList.size(); i++){
			positions[i] = i;
		}
		
		for(int i=0; i<eventsList.size(); i++){
			swap(positions, i, i+random.nextInt(eventsList.size()-i));
		}
		return positions;
	}
	
	
	private void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i]=array[j];
		array[j]=temp;
	}
	
	private boolean canHappen(int percentage){
		int rand = random.nextInt(100+1);
		
		return rand <= percentage;
	}

}
