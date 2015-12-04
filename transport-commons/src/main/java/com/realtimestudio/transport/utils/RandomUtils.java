package com.realtimestudio.transport.utils;

import java.util.ArrayList;
import java.util.List;

public class RandomUtils {
	public static List<Integer> getRandomNumbers(int start, int end /*inclusive*/, int count){
		if(start>end) throw new IllegalArgumentException("Start number has to be smaller than end number");
		List<Integer> list = new ArrayList<>(end-start+1);
		for(int i=start; i<=end; i++){
			list.add(i);
		}
		CollectionUtils.shuffleList(list);
		return list.subList(0,  count);
		
	}

}
