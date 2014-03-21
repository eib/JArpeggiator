package com.a31morgan.sound.player;

public interface IFilter {

	public void applyNoteFilter(byte[] input);

	public void applyStreamFilter(byte[] input);

}