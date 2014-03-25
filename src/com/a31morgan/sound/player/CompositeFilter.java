package com.a31morgan.sound.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeFilter implements IFilter {
	
	private List<IFilter> filters;

	public CompositeFilter(List<IFilter> filters) {
		this.filters = new ArrayList<>(filters);
	}
	
	public CompositeFilter(IFilter... filters) {
		this(Arrays.asList(filters));
	}
	
	@Override
	public void applyNoteFilter(double[] input) {
		for (IFilter filter : this.filters) {
			filter.applyNoteFilter(input);
		}
	}

	@Override
	public void applyStreamFilter(double[] input) {
		for (IFilter filter : this.filters) {
			filter.applyStreamFilter(input);
		}
	}
}
