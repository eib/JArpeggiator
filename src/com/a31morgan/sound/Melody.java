package com.a31morgan.sound;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.a31morgan.sound.Note.Length;

public class Melody {

	private List<Note> notes;
	private int bps;
	
	public Melody(int bps) {
		this.bps = bps;
		this.notes = new ArrayList<>();
	}

	public Melody(int bps, List<Note> notes) {
		this(bps);
		this.addAll(notes);
	}
	
	public void addAll(List<Note> notes) {
		this.notes.addAll(notes);
	}
	
	public void addAll(List<Pitch> pitches, Length noteLength) {
		List<Note> notes = new ArrayList<>();
		for (Pitch p : pitches) {
			notes.add(new Note(p, noteLength));
		}
		this.addAll(notes);
	}

	public int getBps() {
		return bps;
	}

	public void setBps(int bps) {
		this.bps = bps;
	}

	public void writeData(OutputStream os) throws IOException {
		for (Note note : this.notes) {
			note.writeData(this.bps, os);
		}
	}
}
