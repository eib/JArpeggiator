package com.a31morgan.sound;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.a31morgan.sound.Note.Length;

public class Melody {

	private List<Note> notes;
	private int bpm;
	
	public Melody(int bpm) {
		this.bpm = bpm;
		this.notes = new ArrayList<>();
	}

	public Melody(int bpm, List<Note> notes) {
		this(bpm);
		this.addAll(notes);
	}
	
	public void add(Note note) {
		this.notes.add(note);
	}
	
	public void addAll(List<Note> notes) {
		this.notes.addAll(notes);
	}
	
	public void addAll(List<Pitch> pitches, Length noteLength) {
		List<Note> notes = new ArrayList<>();
		for (Pitch p : pitches) {
			notes.add(new Note(p, noteLength));
		}
		System.out.println(pitches);
		this.addAll(notes);
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	public void writeData(OutputStream os) throws IOException {
		for (Note note : this.notes) {
			note.writeData(this.bpm, os);
		}
	}
}
