package com.a31morgan.sound.player;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class FilterOutputStream extends ByteArrayOutputStream {

	private final Filter[] filters;
	
	public FilterOutputStream(Filter... filters) {
		this.filters = filters;
	}

	@Override
	public synchronized void write(byte[] data, int start, int length) {
		byte[] filtered = Arrays.copyOfRange(data, start, start +length);
		for (Filter filter : this.filters) {
			filtered = filter.applyNoteFilter(filtered, 0, length);	
		}
		super.write(filtered, 0, filtered.length);
	}

	@Override
	public synchronized byte[] toByteArray() {
		byte[] data = super.toByteArray();
		for (Filter filter : this.filters) {
			data = filter.applyStreamFilter(data);
		}
		return data;
	}
}