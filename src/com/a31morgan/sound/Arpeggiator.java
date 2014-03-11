package com.a31morgan.sound;

import java.util.List;

public enum Arpeggiator {
	UP,
	DOWN {
		@Override
		public List<Pitch> getNotes(List<Pitch> notes) {
			return Lists.reverse(notes);
		}
	},
	UP_DOWN {
		@Override
		public List<Pitch> getNotes(List<Pitch> notes) {
	        if (notes.size() > 1) {
	        	notes.addAll(Lists.reverse(notes).subList(1, notes.size() - 1));
	        }
	        System.out.println(notes);
	        return notes;
		}
	},
	DOWN_UP {
		@Override
		public List<Pitch> getNotes(List<Pitch> notes) {
			if (notes.size() > 1) {
				notes.addAll(Lists.reverse(notes).subList(1, notes.size()));
				notes = Lists.reverse(notes).subList(0, notes.size() - 1);
			}
			return notes;
		}
	};

	public List<Pitch> getNotes(List<Pitch> notes) {
		return notes;
	}
	
	public List<Pitch> getArpeggio(Arpeggio arpeggio, Pitch start, int numOctaves) {
		return getNotes(arpeggio.getNotes(start, numOctaves));
	}
}
