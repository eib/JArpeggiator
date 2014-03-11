package com.a31morgan.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lists {

	public static <T> List<T> reverse(List<T> list) {
		List<T> reversed = new ArrayList<T>(list);
		Collections.reverse(reversed);
		return reversed;
	}
}
