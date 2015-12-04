package com.realtimestudio.transport.utils;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class CollectionUtils {
	public static<T> void shuffleList(List<T> list){
		Random random = new Random(new Date().getTime());
		for(int i=0; i<list.size()-1; i++){
			int j = i+random.nextInt(list.size()-i);
			swap(list, i, j);
		}
	}
	
	public static<T> void swap(List<T> list, int i, int j){
		T temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}
	

}
