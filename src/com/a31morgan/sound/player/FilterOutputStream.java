package com.a31morgan.sound.player;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class FilterOutputStream extends ByteArrayOutputStream {

	private final IFilter[] filters;
	
	public FilterOutputStream(IFilter... filters) {
		this.filters = filters;
	}

	@Override
	public synchronized void write(byte[] data, int start, int length) {
		byte[] filtered = Arrays.copyOfRange(data, start, start +length);
		for (IFilter filter : this.filters) {
			filter.applyNoteFilter(filtered);	
		}
		super.write(filtered, 0, filtered.length);
	}

	@Override
	public synchronized byte[] toByteArray() {
		byte[] data = super.toByteArray();
		for (IFilter filter : this.filters) {
			filter.applyStreamFilter(data);
		}
		return data;
	}
}
