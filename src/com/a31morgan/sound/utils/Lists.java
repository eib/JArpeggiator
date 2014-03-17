package com.a31morgan.sound.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lists {

	public static <T> List<T> reverse(List<T> list) {
		List<T> reversed = new ArrayList<T>(list);
		Collections.reverse(reversed);
		return reversed;
	}
	
	public static <T> List<T> times(T fillerObject, int numTimes) {
		List<T> list = new ArrayList<>(numTimes);
		for (int ii = 0; ii < numTimes; ii++) {
			list.add(fillerObject);
		}
		return list;
	}
}
